DELIMITER &&

DROP PROCEDURE IF EXISTS sa_jiira_db.insert_book_case;

CREATE PROCEDURE sa_jiira_db.insert_book_case(IN openid varchar(32), IN news_id int, IN name_id int)
BEGIN
	DECLARE rows INT DEFAULT 0;
	SELECT COUNT(*) into rows FROM sa_dt_bookcase where (sa_dt_bookcase.openid=openid and sa_dt_bookcase.news_id=news_id);
	IF rows=0 THEN
		insert into sa_dt_bookcase(openid, news_id, name_id) values(openid, news_id, name_id);
	END IF;
	SELECT rows;
END &&

DROP PROCEDURE IF EXISTS sa_jiira_db.insert_consume;

CREATE PROCEDURE sa_jiira_db.insert_consume(IN openid varchar(32), IN vouchers int, IN out_trade_no varchar(32))
BEGIN
	OUT DECLARE temp INT DEFAULT 0;
	SELECT COUNT(*) into temp FROM sa_dt_consume where (sa_dt_consume.openid=openid and sa_dt_consume.out_trade_no=out_trade_no);
	IF temp=0 THEN
		insert into sa_dt_consume(openid, vouchers, out_trade_no) values(openid, vouchers, out_trade_no);
		set temp = 1;
	ELSE
		set temp = 0;
	END IF;
	
END &&
