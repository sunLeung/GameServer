package player;

import game.dao.PlayerDao;
import game.player.PlayerBean;

public class CreatePlayer {
	public static void main(String[] args) {
		PlayerBean p=new PlayerBean();
		p.setEmail("liangyuxin.02@gmail.com");
		p.setName("樱花宇下千次狼");
		p.setPhone("13570290013");
		p.setSex(1);
		PlayerDao.save(p);
	}
}
