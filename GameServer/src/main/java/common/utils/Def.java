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
	
	public static final int PROTOCOL_PLAYER=0X0a;
	public static final int PROTOCOL_RESOURCE=0X0b;
	
	/*****************************返回码***************************/
	/******************0-19 基础业务******************/
	/**操作成功*/
	public static final int CODE_SUCCESS=0;
	/**操作失败*/
	public static final int CODE_FAIL=1;
	/**操作异常*/
	public static final int CODE_EXCEPTION=2;
	/**业务路由失败*/
	public static final int CODE_ROUTE_FAIL=3;
	
	/******************20-29 登录业务******************/
	public static final int CODE_LOGIN_FAIL=20;
	public static final int CODE_LOGIN_EXCEPTION=21;
	/******************30-39 注册业务******************/
	public static final int CODE_REGISTER_FAIL=30;
	public static final int CODE_REGISTER_EXCEPTION=31;
	/******************40-49 玩家数据查询业务******************/
	public static final int CODE_QUERY_PLAYER_FAIL=40;
	public static final int CODE_QUERY_PLAYER_EXCEPTION=41;
	/******************50-59 资源数据查询业务******************/
	public static final int CODE_QUERY_RESOURCE_FAIL=50;
	public static final int CODE_QUERY_RESOURCE_EXCEPTION=51;
}
