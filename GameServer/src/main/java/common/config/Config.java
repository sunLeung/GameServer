package common.config;

import java.io.File;

/**
 * 
 * @Description 项目配置
 * @author liangyx
 * @date 2013-7-1
 * @version V1.0
 */
public class Config {
	public static String ROOT_DIR=System.getProperty("user.dir");
	/**配置文件根目录*/
	public static String CONFIG_DIR="config";
	/**守护线程运行间隔*/
	public static int WATCH_SECOND=10;
	/**日志配置文件*/
	public static String LOGGER_CONFIG="logger.xx";
	/**数据库配置*/
	public static String DB_CONFIG="c3p0-config.xml";
	
	/**
	 * 初始化配置
	 */
	public static void init(){
		CONFIG_DIR=ROOT_DIR+File.separator+CONFIG_DIR+File.separator;
		LOGGER_CONFIG=CONFIG_DIR+LOGGER_CONFIG;
		DB_CONFIG=CONFIG_DIR+DB_CONFIG;
	}
}
