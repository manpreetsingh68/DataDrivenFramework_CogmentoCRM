package com.datadriven.utils;

public class TestConfig {

	public static String server = "smtp.gmail.com";
	public static String from = "er.manpreet68@gmail.com";
	public static String password = "";
	public static String[] to = { "er.manpreet68@gmail.com", "er_manpreet68@yahoo.com" };
	public static String subject = "Data Driven Project Extent Report";

	public static String messageBody = "Extent Report from Data Driven Framework project";
	public static String attachmentPath = "c:\\screenshot\\2017_10_3_14_49_9.jpg";
	public static String attachmentName = "error.jpg";

	// SQL DATABASE DETAILS
	public static String driver = "net.sourceforge.jtds.jdbc.Driver";
	public static String dbConnectionUrl = "jdbc:jtds:sqlserver://192.101.44.22;DatabaseName=monitor_eval";
	public static String dbUserName = "sa";
	public static String dbPassword = "$ql$!!1";

	// MYSQL DATABASE DETAILS
	public static String mysqldriver = "com.mysql.jdbc.Driver";
	public static String mysqluserName = "root";
	public static String mysqlpassword = "selenium";
	public static String mysqlurl = "jdbc:mysql://localhost:3306/acs";

}
