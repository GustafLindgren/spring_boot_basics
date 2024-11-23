create table if not exists photoz (
	/*identity SQL Server equivalent to auto_increment in MySQL*/
	id bigint auto_increment primary key,
	file_name varchar(255),
	content_type varchar(255),
	data varbinary(max)
	
);