package common.admin;

import common.boot.GameServer;

public class AdminService {
	/**
	 * 停服
	 */
	public static void stopServer(){
		GameServer.stop();
	}
	
	public static String test(String s){
		return s;
	}
}
