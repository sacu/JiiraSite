package org.jiira.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import org.jiira.dao.RedPacketDao;
import org.jiira.dao.UserRedPacketDao;
import org.jiira.pojo.RedPacket;
import org.jiira.pojo.UserRedPacket;
import org.jiira.service.RedisRedPacketService;
import org.jiira.service.UserRedPacketService;

import redis.clients.jedis.Jedis;

@Service
public class UserRedPacketServiceImpl implements UserRedPacketService {

	@Autowired
	private UserRedPacketDao userRedPacketDao = null;

	@Autowired
	private RedPacketDao redPacketDao = null;

	// 失败
	private static final int FAILED = 0;

	/**
	 * 直接数据库抢红包 使用悲观锁
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	public int grapRedPacket(Long redPacketId, Long userId) {
		/**
		 * 普通方式获取红包信息 会存在超发问题，即：A查询有红包，B查询有红包，A领取后提交信息（这时没红包了），B又提交领取信心
		 */
		// RedPacket redPacket = redPacketDao.getRedPacket(redPacketId);
		/**
		 * 悲观锁方式获取红包信息 也称为独占锁 通过主键查询，只锁定单条信息 如果通过其他列查询，则会锁定整表（慎用） 这里悲观锁用了 for update
		 * 也就是会锁定更新（在完成前 会将其他所有更新操作挂起） 解决超发问题 性能降低1/3
		 * 
		 */
		RedPacket redPacket = redPacketDao.getRedPacketForUpdate(redPacketId);
		// 当前小红包库存大于0
		if (redPacket.getStock() > 0) {
			redPacketDao.decreaseRedPacket(redPacketId);
			// 生成抢红包信息
			UserRedPacket userRedPacket = new UserRedPacket();
			userRedPacket.setRedPacketId(redPacketId);
			userRedPacket.setUserId(userId);
			userRedPacket.setAmount(redPacket.getUnitAmount());
			userRedPacket.setNote("抢红包 " + redPacketId);
			// 插入抢红包信息
			int result = userRedPacketDao.grapRedPacket(userRedPacket);
			return result;
		}
		// 失败返回
		return FAILED;
	}

	// 乐观锁，无重入
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	public int grapRedPacketForVersion(Long redPacketId, Long userId) {
		// 获取红包信息,注意version值
		RedPacket redPacket = redPacketDao.getRedPacket(redPacketId);
		// 当前小红包库存大于0
		if (redPacket.getStock() > 0) {
			/**
			 * 如果和现在版本号相同，则更新数据，并更新version数值
			 * 否则失败
			 * 不会对表进行锁定，解决了超发问题，性能基本不会下降，但是有可能会丢失操作（比如version发生变化）
			 * 丢失操作也取决于网络和服务器性能，网络略差、服务器性能好，丢失率会降低
			 */
			int update = redPacketDao.decreaseRedPacketForVersion(redPacketId, redPacket.getVersion());
			// 如果没有数据更新，则说明其他线程已经修改过数据，本次抢红包失败
			if (update == 0) {
				return FAILED;
			}
			// 生成抢红包信息
			UserRedPacket userRedPacket = new UserRedPacket();
			userRedPacket.setRedPacketId(redPacketId);
			userRedPacket.setUserId(userId);
			userRedPacket.setAmount(redPacket.getUnitAmount());
			userRedPacket.setNote("抢红包 " + redPacketId);
			// 插入抢红包信息
			int result = userRedPacketDao.grapRedPacket(userRedPacket);
			return result;
		}
		// 失败返回
		return FAILED;
	}

	// 乐观锁，按时间戳重入
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	public int grapRedPacketForVersionRepeat(Long redPacketId, Long userId) {
		// 记录开始时间
		long start = System.currentTimeMillis();
		// 无限循环，等待成功或者时间满100毫秒退出
		while (true) {
			// 获取循环当前时间
			long end = System.currentTimeMillis();
			// 当前时间已经超过100毫秒，返回失败
			if (end - start > 100) {
				return FAILED;
			}
			// 获取红包信息,注意version值
			RedPacket redPacket = redPacketDao.getRedPacket(redPacketId);
			// 当前小红包库存大于0
			if (redPacket.getStock() > 0) {
				// 再次传入线程保存的version旧值给SQL判断，是否有其他线程修改过数据
				int update = redPacketDao.decreaseRedPacketForVersion(redPacketId, redPacket.getVersion());
				// 如果没有数据更新，则说明其他线程已经修改过数据，则重新抢夺
				if (update == 0) {
					continue;
				}
				// 生成抢红包信息
				UserRedPacket userRedPacket = new UserRedPacket();
				userRedPacket.setRedPacketId(redPacketId);
				userRedPacket.setUserId(userId);
				userRedPacket.setAmount(redPacket.getUnitAmount());
				userRedPacket.setNote("抢红包 " + redPacketId);
				// 插入抢红包信息
				int result = userRedPacketDao.grapRedPacket(userRedPacket);
				return result;
			} else {
				// 一旦没有库存，则马上返回
				return FAILED;
			}
		}
	}

	// 乐观锁，按次数重入
	// @Override
	// @Transactional(isolation = Isolation.READ_COMMITTED, propagation =
	// Propagation.REQUIRED)
	// public int grapRedPacketForVersion(Long redPacketId, Long userId) {
	// for (int i = 0; i < 3; i++) {
	// // 获取红包信息，注意version值
	// RedPacket redPacket = redPacketDao.getRedPacket(redPacketId);
	// // 当前小红包库存大于0
	// if (redPacket.getStock() > 0) {
	// // 再次传入线程保存的version旧值给SQL判断，是否有其他线程修改过数据
	// int update = redPacketDao.decreaseRedPacketForVersion(redPacketId,
	// redPacket.getVersion());
	// // 如果没有数据更新，则说明其他线程已经修改过数据，则重新抢夺
	// if (update == 0) {
	// continue;
	// }
	// // 生成抢红包信息
	// UserRedPacket userRedPacket = new UserRedPacket();
	// userRedPacket.setRedPacketId(redPacketId);
	// userRedPacket.setUserId(userId);
	// userRedPacket.setAmount(redPacket.getUnitAmount());
	// userRedPacket.setNote("抢红包 " + redPacketId);
	// // 插入抢红包信息
	// int result = userRedPacketDao.grapRedPacket(userRedPacket);
	// return result;
	// } else {
	// // 一旦没有库存，则马上返回
	// return FAILED;
	// }
	// }
	// return FAILED;
	// }

	@Autowired
	private RedisTemplate<String, RedisTemplate<String, String>> redisTemplate = null;

	@Autowired
	private RedisRedPacketService redisRedPacketService = null;

	// Lua脚本
	String script = "local listKey = 'red_packet_list_'..KEYS[1] \n" + "local redPacket = 'red_packet_'..KEYS[1] \n"
			+ "local stock = tonumber(redis.call('hget', redPacket, 'stock')) \n"
			+ "if stock == nil then return 0 end\n" + "if stock <= 0 then return 0 end \n" + "stock = stock -1 \n"
			+ "redis.call('hset', redPacket, 'stock', tostring(stock)) \n" + "redis.call('rpush', listKey, ARGV[1]) \n"
			+ "if stock == 0 then return 2 end \n" + "return 1 \n";

	// 在缓存LUA脚本后，使用该变量保存Redis返回的32位的SHA1编码，使用它去执行缓存的LUA脚本[加入这句话]
	String sha1 = null;

	@Override
	public Long grapRedPacketByRedis(Long redPacketId, Long userId) {
		// 当前抢红包用户和日期信息
		String args = userId + "-" + System.currentTimeMillis();
		Long result = null;
		// 获取底层Redis操作对象
		Jedis jedis = (Jedis) redisTemplate.getConnectionFactory().getConnection().getNativeConnection();
		try {
			// 如果脚本没有加载过，那么进行加载，这样就会返回一个sha1编码
			if (sha1 == null) {
				sha1 = jedis.scriptLoad(script);
			}
			// 执行脚本，返回结果
			Object res = jedis.evalsha(sha1, 1, redPacketId + "", args);
			//0失败没库存，1成功，2成功，最后一个
			result = (Long) res;
			// 返回2时为最后一个红包，此时将抢红包信息通过异步保存到数据库中
			if (result == 2) {
				// 获取单个小红包金额
				String unitAmountStr = jedis.hget("red_packet_" + redPacketId, "unit_amount");
				// 触发保存数据库操作
				Double unitAmount = Double.parseDouble(unitAmountStr);
				System.err.println("thread_name = " + Thread.currentThread().getName());
				redisRedPacketService.saveUserRedPacketByRedis(redPacketId, unitAmount);
			}
		} finally {
			// 确保jedis顺利关闭
			if (jedis != null && jedis.isConnected()) {
				jedis.close();
			}
		}
		return result;
	}
}
