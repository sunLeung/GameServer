package game.player;

public class Player {
	private PlayerBean bean;
	private long loadTime;
	private long updateTime;
	
	public int getId(){
		return bean.getId();
	}
	
	public String getDeviceid() {
		return bean.getDeviceid();
	}
	
	public String getToken() {
		return bean.getToken();
	}
	
	public PlayerBean getBean() {
		return bean;
	}

	public void setBean(PlayerBean bean) {
		this.bean = bean;
	}

	public long getLoadTime() {
		return loadTime;
	}

	public void setLoadTime(long loadTime) {
		this.loadTime = loadTime;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}
}
