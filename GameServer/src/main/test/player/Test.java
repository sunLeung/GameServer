package player;
import java.util.HashMap;
import java.util.Map;

import common.utils.HttpUtils;
import common.utils.JsonUtils;


public class Test {
	public static void main(String[] args) {
		query();
	}
	
	public static void save(){
		Map<String,String> requestProperty=new HashMap<String, String>();
		requestProperty.put("deviceid", "akdsfjlquadsfnn");
		requestProperty.put("protocol", "0x01");
		
		Map<String,Object> body = new HashMap<String, Object>();
		body.put("name", "liangyx3");
		body.put("email", "liangyuxin3.02@gmail.com");
		body.put("password1", "123456");
		body.put("password2", "123456");
		body.put("sex", 1);
		String data=JsonUtils.encode2Str(body);
		String a=HttpUtils.doPost("http://127.0.0.1:4000", requestProperty,data);
		System.out.println(a);
	}
	
	public static void query(){
		Map<String,String> requestProperty=new HashMap<String, String>();
		requestProperty.put("deviceid", "akdsfjlquadsfnn");
		requestProperty.put("protocol", "0x0a");
		requestProperty.put("playerid", "4");
		requestProperty.put("token", "1720f6a8d2834dacb05eba07548246d6");
		
		Map<String,Object> body = new HashMap<String, Object>();
		body.put("attribute", "phone#name");
		String data=JsonUtils.encode2Str(body);
		String a=HttpUtils.doPost("http://115.28.234.110:4000", requestProperty,data);
		System.out.println(a);
	}
}
