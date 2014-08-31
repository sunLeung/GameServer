package game.player;

import common.db.Pojo;

public class Player extends Pojo{
	private static final long serialVersionUID = 1L;
	private String name;
	private String email;
	private String phone;
	/**0女,1男*/
	private int sex;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	
}
