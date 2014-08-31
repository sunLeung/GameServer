package game.player;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class PlayerService {
	private static Map<Long,AtomicInteger> map=new ConcurrentHashMap<Long, AtomicInteger>();
	
	public static Player getPlayer(int id){
		Player p=new Player();
		p.setEmail("liangyuxin.02@gmail.com");
		p.setPhone("13570290013");
		p.setId(1);
		p.setName("liangyx");
		p.setSex(1);
		try {
			Thread.sleep(1000);
			Long key=Thread.currentThread().getId();
			if(map.containsKey(key)){
				AtomicInteger i= map.get(key);
				i.incrementAndGet();
			}else{
				map.put(key, new AtomicInteger(0));
			}
			System.out.println(map.size());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return p;
	}
}
