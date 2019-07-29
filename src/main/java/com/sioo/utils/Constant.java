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
	
	//tomcat name��ӦTaskLoad�е�ǰ���ص�tomcat
	public static String TOMCAT_NAME = "";

	// �й��ƶ�134.135.136.137.138.139.150.151.152.157.158.159.182.187.188 ,147(���ݿ�)
	// �й���ͨ130.131.132.155.156.185.186.1707.1708
	// �й�����133.153.180.189
	public static String ALL_REG = "";
	public static String MOBILE_REG = "";
	public static String UNICOM_REG = "";
	public static String TELECOM_REG = "";

	public static List<String> blackNumbers = null;

	public static HashMap<Short, String> SEND_STAT = new HashMap();
	public static HashMap<Byte, String> PAY_TYPE = new HashMap(4);
	public static HashMap<Byte, String> PAY_STATUS = new HashMap(2);
	public static HashMap<Byte, String> NOTICE_TYPE = new HashMap(3);
	public static List<String> USER_STAREGARY_CASH = new ArrayList<String>();//�����û����Ի���
	public static List<String> USER_INFO_CASH = new ArrayList<String>();//�����û���Ϣ����
	public static List<String> USER_SIGN_CASH = new ArrayList<String>();//�����û�ǩ������
	public static List<String> USER_ALERT = new ArrayList<String>();//�����û�������ѻ���
	static {
		//����
//		USER_STAREGARY_CASH.add("http://47.102.213.154:9001");
//		USER_STAREGARY_CASH.add("http://47.102.211.138:9001");
//		
//		USER_INFO_CASH.add("http://47.102.208.224:9009");
//		USER_INFO_CASH.add("http://47.102.208.224:9011");
//		USER_INFO_CASH.add("http://47.102.213.154:9001");
//		USER_INFO_CASH.add("http://47.102.213.154:9005");
//		USER_INFO_CASH.add("http://47.102.211.138:9001");
//		USER_INFO_CASH.add("http://47.102.211.138:9005");
		
		
		
		//����
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
		// RPT_STAT.put((short) -1, "UNKNOWN"); // �ȴ�����
		// RPT_STAT.put((short) -2, "EXPIRED");
		// RPT_STAT.put((short) -3, "REJECTD");
		// RPT_STAT.put((short) -4, "MB:0066");
		// RPT_STAT.put((short) -5, "MK:0001");// Ƿ�ѻ�ͣ��
		// RPT_STAT.put((short) -6, "MK:0013");
		// RPT_STAT.put((short) -7, "MK:0015");
		// RPT_STAT.put((short) -8, "MK:0036");
		// RPT_STAT.put((short) -8, "MK:0041");
		// RPT_STAT.put((short) -9, "IB:0008");//���ٿ�
		// RPT_STAT.put((short) -10, "IB:0011");
		// RPT_STAT.put((short) -11, "DB:0018");
		// RPT_STAT.put((short) -12, "DB:0140");// �û�δ�㲥��ҵ��
		// RPT_STAT.put((short) -13, "DB:0139");// ������
		// RPT_STAT.put((short) -14, "MB:0241");// SMSC�����Զ���Ĵ����� �ƶ��ڲ����� ������
		// RPT_STAT.put((short) -15, "UNDELIVRD");// ����״̬��ʾΪ���״̬
		// RPT_STAT.put((short) -16, "IB:0064");
		// RPT_STAT.put((short) -17, "MK:0005");
		// RPT_STAT.put((short) -18, "MK:0017");
		// RPT_STAT.put((short) -19, "MK:0011");
		// RPT_STAT.put((short) -20, "MK:0000");
		// RPT_STAT.put((short) -21, "MK:0004");
		// RPT_STAT.put((short) -22, "MI:0010");
		// RPT_STAT.put((short) -23, "MI:0013");//1069�ƶ�ͣ��״̬
		// RPT_STAT.put((short) -24, "MI:0024");//1069�ƶ��ػ�״̬
		// RPT_STAT.put((short) -25, "MI:0029");
		// RPT_STAT.put((short) -26, "MI:0081");
		// RPT_STAT.put((short) -27, "MI:0084");
		// RPT_STAT.put((short) -28, "DB:0142");//--���Ű��µ��������п���,������������
		// RPT_STAT.put((short) -29, "IB:0100");//�غŹ���
		// RPT_STAT.put((short) -30, "MC:0001");//ǩ��δ����
		// RPT_STAT.put((short) -31, "XA:0001");//����ʧ��
		// RPT_STAT.put((short) -32, "MI:0075");//1069�ƶ��պ�״̬
		// RPT_STAT.put((short) -33, "MN:0001");

		// 0001-1999 ϵͳ������
		// XA:0001 ����ʧ�� //
		// XA:0100 �غŹ���
		// XA:0003 ǩ��δ����
		// XA:0004 �����Զ����δ�
		// XA:0139 ����ϵͳ������

		// 2000-3999 �û�������
		// XA:2139 �����û�������
		// XA:2001 ���û�������
		// XA:2002 ���û�����ģ��

		SEND_STAT.put((short) (1), "�ɹ�");
		SEND_STAT.put((short) (0), "ʧ��");
		SEND_STAT.put((short) (22), "δ֪");
		SEND_STAT.put((short) (11), "����,����˻�");
		SEND_STAT.put((short) (12), "��ʱ����,����˻�");
		SEND_STAT.put((short) (13), "��ʱ�ɹ�");
		SEND_STAT.put((short) (100), "�����ɹ�");
		SEND_STAT.put((short) (101), "����ʧ��");
		SEND_STAT.put((short) (102), "��֤ʧ��");
		SEND_STAT.put((short) (103), "��������������");
		SEND_STAT.put((short) (104), "���ݹ���");
		SEND_STAT.put((short) (105), "����Ƶ�ʹ���");
		SEND_STAT.put((short) (-5), "��¼�˻�����");
		SEND_STAT.put((short) (-1), "δ�ύ");
		SEND_STAT.put((short) (-2), "���粻ͨ");
		SEND_STAT.put((short) (-3), "�������");
		SEND_STAT.put((short) (-4), "�Ƿ�����");
		SEND_STAT.put((short) (-6), "ͨ�����ݴ���");
		SEND_STAT.put((short) (-7), "δ��ʼ��");
		SEND_STAT.put((short) (-8), "��չ��");
		SEND_STAT.put((short) (-9), "���벻��");
		SEND_STAT.put((short) (-11), "����̫��");
		SEND_STAT.put((short) (-13), "����");
		SEND_STAT.put((short) (-14), "��չ�Ŵ�");
		SEND_STAT.put((short) (99), "�޽��");
		SEND_STAT.put((short) (-12), "��Сʱ������");
		SEND_STAT.put((short) (-15), "�غŹ���");

		PAY_TYPE.put((byte) (1), "�˹���ֵ");
		PAY_TYPE.put((byte) (2), "��Ǯ��ֵ");
		PAY_TYPE.put((byte) (3), "�Ա���ֵ");
		PAY_TYPE.put((byte) (4), "��������");

		PAY_STATUS.put((byte) (0), "ʧ��");
		PAY_STATUS.put((byte) (1), "�ɹ�");
		PAY_STATUS.put((byte) (2), "������..");

		NOTICE_TYPE.put((byte) (1), "����");
		NOTICE_TYPE.put((byte) (2), "֪ͨ");
		NOTICE_TYPE.put((byte) (3), "��ҵ����");
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
			System.out.println("����jdbc.propertiesʧ��:" + e.getMessage());

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