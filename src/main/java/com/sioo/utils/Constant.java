package com.sioo.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class Constant {
	public static String MESSAGE = "";
	public static String SCROLLMESSAGE = "";
	public static String UPLOAD_PATH = "";
	public static String MMS_PATH = "";
	public static String URL1 = "";
	public static String URL2 = "";
	public static String URL3 = "";
	public static String URL4 = "";
	public static String DB_USER = "";
	public static String DB_PASS = "";
	public static int PACK_NUM = 0;
	public static int CHAR_SIZE = 0;
	public static int KOU_SIZE = 0;
	public static int PAGE_SIZE = 0;
	public static String TITLE_VALUE = "";
	public static int RELEASE_VALUE = 1;
	public static int PAUSE_VALUE = 1;
	public static int LOGIN_NUM_VALUE = 10;
	public static int LOGIN_TIME_VALUE = 100;
	public static String DownloadUrl="/services/userDownload";
	
	//tomcat name对应TaskLoad中当前加载的tomcat
	public static String TOMCAT_NAME = "";

	// 中国移动134.135.136.137.138.139.150.151.152.157.158.159.182.187.188 ,147(数据卡)
	// 中国联通130.131.132.155.156.185.186.1707.1708
	// 中国电信133.153.180.189
	public static String ALL_REG = "";
	public static String MOBILE_REG = "";
	public static String UNICOM_REG = "";
	public static String TELECOM_REG = "";

	public static List<String> blackNumbers = null;

	public static HashMap<Short, String> SEND_STAT = new HashMap();
	public static HashMap<Byte, String> PAY_TYPE = new HashMap(4);
	public static HashMap<Byte, String> PAY_STATUS = new HashMap(2);
	public static HashMap<Byte, String> NOTICE_TYPE = new HashMap(3);
	public static List<String> USER_STAREGARY_CASH = new ArrayList<String>();//更新用户策略缓存
	public static List<String> USER_INFO_CASH = new ArrayList<String>();//更新用户信息缓存
	public static List<String> USER_SIGN_CASH = new ArrayList<String>();//更新用户签名缓存
	public static List<String> USER_ALERT = new ArrayList<String>();//更新用户余额提醒缓存
	static {
		//外网
//		USER_STAREGARY_CASH.add("http://47.102.213.154:9001");
//		USER_STAREGARY_CASH.add("http://47.102.211.138:9001");
//		
//		USER_INFO_CASH.add("http://47.102.208.224:9009");
//		USER_INFO_CASH.add("http://47.102.208.224:9011");
//		USER_INFO_CASH.add("http://47.102.213.154:9001");
//		USER_INFO_CASH.add("http://47.102.213.154:9005");
//		USER_INFO_CASH.add("http://47.102.211.138:9001");
//		USER_INFO_CASH.add("http://47.102.211.138:9005");
		
		
		
		//内网
		USER_STAREGARY_CASH.add("http://172.19.98.64:9001");
		USER_STAREGARY_CASH.add("http://172.19.98.61:9001");
		USER_STAREGARY_CASH.add("http://172.19.98.66:9001");
			
//		USER_INFO_CASH.add("http://172.19.98.63:9009");
//		USER_INFO_CASH.add("http://172.19.98.63:9011");
//		USER_INFO_CASH.add("http://172.19.98.64:9001");
//		USER_INFO_CASH.add("http://172.19.98.64:9005");
//		USER_INFO_CASH.add("http://172.19.98.61:9001");
//		USER_INFO_CASH.add("http://172.19.98.61:9005");
		
		USER_INFO_CASH.add("http://172.19.98.63:9009");
		USER_INFO_CASH.add("http://172.19.98.63:9021");
		USER_INFO_CASH.add("http://172.19.98.63:9023");
		USER_INFO_CASH.add("http://172.19.98.66:9001");
		USER_INFO_CASH.add("http://172.19.98.64:9001");
		USER_INFO_CASH.add("http://172.19.98.64:9005");
		USER_INFO_CASH.add("http://172.19.98.61:9001");
		USER_INFO_CASH.add("http://172.19.98.61:9005");

		USER_SIGN_CASH.add("http://172.19.98.64:9001");
		USER_SIGN_CASH.add("http://172.19.98.64:9005");
		USER_SIGN_CASH.add("http://172.19.98.66:9001");
		USER_SIGN_CASH.add("http://172.19.98.61:9001");
		USER_SIGN_CASH.add("http://172.19.98.61:9005");
		
		USER_ALERT.add("http://172.19.98.64:9001");
		USER_ALERT.add("http://172.19.98.61:9001");
		USER_ALERT.add("http://172.19.98.66:9001");
		// RPT_STAT.put((short) 1, "DELIVRD");
		// RPT_STAT.put((short) 0, "UNDELIVRD");
		// RPT_STAT.put((short) -1, "UNKNOWN"); // 等待报告
		// RPT_STAT.put((short) -2, "EXPIRED");
		// RPT_STAT.put((short) -3, "REJECTD");
		// RPT_STAT.put((short) -4, "MB:0066");
		// RPT_STAT.put((short) -5, "MK:0001");// 欠费或停机
		// RPT_STAT.put((short) -6, "MK:0013");
		// RPT_STAT.put((short) -7, "MK:0015");
		// RPT_STAT.put((short) -8, "MK:0036");
		// RPT_STAT.put((short) -8, "MK:0041");
		// RPT_STAT.put((short) -9, "IB:0008");//流速快
		// RPT_STAT.put((short) -10, "IB:0011");
		// RPT_STAT.put((short) -11, "DB:0018");
		// RPT_STAT.put((short) -12, "DB:0140");// 用户未点播该业务
		// RPT_STAT.put((short) -13, "DB:0139");// 黑名单
		// RPT_STAT.put((short) -14, "MB:0241");// SMSC厂家自定义的错误码 移动内部错误 不处理
		// RPT_STAT.put((short) -15, "UNDELIVRD");// 其他状态显示为这个状态
		// RPT_STAT.put((short) -16, "IB:0064");
		// RPT_STAT.put((short) -17, "MK:0005");
		// RPT_STAT.put((short) -18, "MK:0017");
		// RPT_STAT.put((short) -19, "MK:0011");
		// RPT_STAT.put((short) -20, "MK:0000");
		// RPT_STAT.put((short) -21, "MK:0004");
		// RPT_STAT.put((short) -22, "MI:0010");
		// RPT_STAT.put((short) -23, "MI:0013");//1069移动停机状态
		// RPT_STAT.put((short) -24, "MI:0024");//1069移动关机状态
		// RPT_STAT.put((short) -25, "MI:0029");
		// RPT_STAT.put((short) -26, "MI:0081");
		// RPT_STAT.put((short) -27, "MI:0084");
		// RPT_STAT.put((short) -28, "DB:0142");//--短信包月第三方下行控制,超过下行上限
		// RPT_STAT.put((short) -29, "IB:0100");//重号过滤
		// RPT_STAT.put((short) -30, "MC:0001");//签名未报备
		// RPT_STAT.put((short) -31, "XA:0001");//驳回失败
		// RPT_STAT.put((short) -32, "MI:0075");//1069移动空号状态
		// RPT_STAT.put((short) -33, "MN:0001");

		// 0001-1999 系统错误码
		// XA:0001 驳回失败 //
		// XA:0100 重号过滤
		// XA:0003 签名未报备
		// XA:0004 触发自动屏蔽词
		// XA:0139 触发系统黑名单

		// 2000-3999 用户错误码
		// XA:2139 触发用户黑名单
		// XA:2001 非用户白名单
		// XA:2002 非用户短信模板

		SEND_STAT.put((short) (1), "成功");
		SEND_STAT.put((short) (0), "失败");
		SEND_STAT.put((short) (22), "未知");
		SEND_STAT.put((short) (11), "禁发,金额退还");
		SEND_STAT.put((short) (12), "定时禁发,金额退还");
		SEND_STAT.put((short) (13), "定时成功");
		SEND_STAT.put((short) (100), "操作成功");
		SEND_STAT.put((short) (101), "操作失败");
		SEND_STAT.put((short) (102), "验证失败");
		SEND_STAT.put((short) (103), "号码数超过限制");
		SEND_STAT.put((short) (104), "内容过长");
		SEND_STAT.put((short) (105), "操作频率过快");
		SEND_STAT.put((short) (-5), "登录账户错误");
		SEND_STAT.put((short) (-1), "未提交");
		SEND_STAT.put((short) (-2), "网络不通");
		SEND_STAT.put((short) (-3), "号码过多");
		SEND_STAT.put((short) (-4), "非法文字");
		SEND_STAT.put((short) (-6), "通信数据传送");
		SEND_STAT.put((short) (-7), "未初始化");
		SEND_STAT.put((short) (-8), "扩展号");
		SEND_STAT.put((short) (-9), "号码不合");
		SEND_STAT.put((short) (-11), "内容太长");
		SEND_STAT.put((short) (-13), "余额不足");
		SEND_STAT.put((short) (-14), "扩展号错");
		SEND_STAT.put((short) (99), "无结果");
		SEND_STAT.put((short) (-12), "本小时流量到");
		SEND_STAT.put((short) (-15), "重号过滤");

		PAY_TYPE.put((byte) (1), "人工充值");
		PAY_TYPE.put((byte) (2), "快钱充值");
		PAY_TYPE.put((byte) (3), "淘宝充值");
		PAY_TYPE.put((byte) (4), "网银在线");

		PAY_STATUS.put((byte) (0), "失败");
		PAY_STATUS.put((byte) (1), "成功");
		PAY_STATUS.put((byte) (2), "处理中..");

		NOTICE_TYPE.put((byte) (1), "公告");
		NOTICE_TYPE.put((byte) (2), "通知");
		NOTICE_TYPE.put((byte) (3), "行业新闻");
		loadConfig();
		loadSysConfig();
	}

	public static void loadConfig() {
		InputStream in2 = Constant.class.getResourceAsStream("/resource/jdbc.properties");
		Properties p2 = new Properties();
		try {
			p2.load(in2);
			URL1 = p2.getProperty("jdbc.url").trim();
			URL2 = p2.getProperty("jdbc.url2").trim();
			URL3 = p2.getProperty("jdbc.url3").trim();
			URL4 = p2.getProperty("jdbc.url4").trim();
			DB_USER = p2.getProperty("jdbc.username").trim();
			DB_PASS = p2.getProperty("jdbc.password").trim();
			TOMCAT_NAME= p2.getProperty("tomcatName").trim();
		} catch (IOException e) {
			System.out.println("加载jdbc.properties失败:" + e.getMessage());

			if (in2 != null)
				try {
					in2.close();
				} catch (IOException localIOException4) {
				}
		} finally {
			if (in2 != null)
				try {
					in2.close();
				} catch (IOException localIOException5) {
				}
		}
	}

	public static void loadSysConfig() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(Constant.URL1, Constant.DB_USER, Constant.DB_PASS);
			Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery("select pagesize,charsize,kousize,packnum,uploadpath,mmspath,`release`,pause,"
					+ "loginnum,logintime,allreg,mobilereg,unicomreg,telecomreg,title,message,scrollmessage from smshy.sms_config limit 0,1");
			if (rs.next()) {
				PAGE_SIZE = rs.getInt("pagesize");
				CHAR_SIZE = rs.getInt("charsize");
				KOU_SIZE = rs.getInt("kousize");
				PACK_NUM = rs.getInt("packnum");
				UPLOAD_PATH = rs.getString("uploadpath");
				MMS_PATH = rs.getString("mmspath");
				RELEASE_VALUE = rs.getInt("release");
				PAUSE_VALUE = rs.getInt("pause");
				LOGIN_NUM_VALUE = rs.getInt("loginnum");
				LOGIN_TIME_VALUE = rs.getInt("logintime");
				ALL_REG = rs.getString("allreg");
				MOBILE_REG = rs.getString("mobilereg");
				UNICOM_REG = rs.getString("unicomreg");
				TELECOM_REG = rs.getString("telecomreg");
				TITLE_VALUE = rs.getString("title");
				MESSAGE = rs.getString("message");
				SCROLLMESSAGE = rs.getString("scrollmessage");
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception ee) {
					ee.printStackTrace();
				}
			}
		}
	}

	public static void saveSysConfig() {

		String field = "pagesize=" + PAGE_SIZE + ",charsize=" + CHAR_SIZE + ",kousize=" + KOU_SIZE + ",packnum=" + PACK_NUM + ",uploadpath='" + UPLOAD_PATH + "'" + ",mmspath='" + MMS_PATH + "'"
				+ ",`release`=" + RELEASE_VALUE + ",pause=" + PAUSE_VALUE + ",loginnum=" + LOGIN_NUM_VALUE + ",logintime=" + LOGIN_TIME_VALUE + ",allreg='" + ALL_REG + "'" + ",mobilereg='"
				+ MOBILE_REG + "'" + ",unicomreg='" + UNICOM_REG + "'" + ",telecomreg='" + TELECOM_REG + "'" + ",message='" + MESSAGE + "'" + ",scrollmessage='" + SCROLLMESSAGE + "'" + ",title='"
				+ TITLE_VALUE + "'";

		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(Constant.URL1, Constant.DB_USER, Constant.DB_PASS);
			Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			stmt.execute("update sms_config set " + field);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception ee) {
					ee.printStackTrace();
				}
			}
		}
	}
}