import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import common.utils.HttpUtils;
import common.utils.StringUtils;


public class Test {
	public static void main(String[] args) {
		AtomicInteger count=new AtomicInteger(0);
		for(int i=0;i<1024;i++){
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					Map<String,String> requestProperty=new HashMap<String, String>();
					requestProperty.put("deviceid", "abc");
					requestProperty.put("token", "abc");
					requestProperty.put("protocol", "0x01");
					String a=HttpUtils.doPost("http://127.0.0.1:4000", requestProperty,"");
					if(StringUtils.isBlank(a))
						count.incrementAndGet();
					System.out.println(Thread.currentThread().getName()+">>>>>"+count.get());
				}
			},"Thread-"+i).start();;
		}
	}
}
