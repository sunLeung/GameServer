package common.utils;

public class Def {
	/*****************************货币**********************************/
	/**CNY(Chinese Yuan)人民币*/
	public static final String CNY="CNY";
	/**USD(United States Dollar)美元*/
	public static final String USD="USD";
	/**NTD(New Taiwan Dollar)新台币*/
	public static final String NTD="NTD";
	
	
	
	/*****************************支付状态**********************************/
	public static final int PaySucceed=0;
	public static final int PayFail=1;
	
	
	/*****************************请求控制**********************************/
	/**不连通服务器的情况下默认请求次数*/
	public static final int RequestLoop=3;
	
	/*****************************Security***************************/
	public static final int Orderid=0x00;
	
	/**登陆密码secure*/
	public static final String PasswordSecret="keyhAimOan#";
	
	/*****************************Logger业务日志key***************************/
	public static final String MONEY_LOG="money";
	
	/*****************************协议***************************/
	public static final int PROTOCOL_LOGIN=0X00;
	public static final int PROTOCOL_REGISTER=0X01;
	public static final int PROTOCOL_RESOURCE=0X02;
	
	public static final int PROTOCOL_PLAYER=0X0a;
}
