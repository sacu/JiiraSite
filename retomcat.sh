#!/bin/sh
# 获取tomcat进程ID  /usr/local/tomcat
TomcatID=$(ps -ef |grep tomcat |grep -w 'tomcat'|grep -v 'grep'|awk '{print $2}')
# 定义要监控的页面地址
WebUrl=http://localhost:8080/ad

# 日志输出 
TomcatMonitorLog=/usr/share/tomcat/log/retomcat.log
Monitor()
{
  if [[ $TomcatID ]];then # 这里判断TOMCAT进程是否存在  
    # 检测是否启动成功(成功的话页面会返回状态"200")
    TomcatServiceCode=$(curl -I -m 10 -o /dev/null -s -w %{http_code} $WebUrl)
    if [ $TomcatServiceCode -ne 200 ];then
      echo "[error]tomcat页面出错,请注意......状态码为$TomcatServiceCode,错误日志已输"
      echo "[error]页面访问出错,开始重启tomcat"
      echo "[info]sleep3"
      sleep 3
      systemctl restart tomcat
      echo "[info]启动成功......................................"
    fi
  else 
    echo "[error]tomcat进程不存在!tomcat开始自动重启..."
    echo "[info]请稍候......"
    systemctl restart tomcat
    echo "[info]启动成功......................................"
  fi
}
Monitor>>$TomcatMonitorLog
