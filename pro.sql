DROP PROCEDURE IF EXISTS sa_jiira_db.insert_book_case;
DELIMITER &&
CREATE PROCEDURE sa_jiira_db.insert_book_case(IN openid varchar(32), IN news_id int, IN name_id int)
BEGIN
	DECLARE rows INT DEFAULT 0;
	SELECT COUNT(*) into rows FROM sa_dt_bookcase where (sa_dt_bookcase.openid=openid and sa_dt_bookcase.news_id=news_id);
	IF rows=0 THEN
		insert into sa_dt_bookcase(openid, news_id, name_id) values(openid, news_id, name_id);
	END IF;
	SELECT openid;
	SELECT news_id;
	SELECT rows;
END &&