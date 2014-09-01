package common.net;

import io.netty.handler.codec.http.HttpRequest;


public class HttpPacket {
	private int playerid;
	private String deviceid;
	private String token;
	private int protocol;
	private String data;
	private HttpRequest request;
	public HttpPacket(int playerid,String deviceid,String token,int protocol,String data,HttpRequest request){
		this.playerid=playerid;
		this.deviceid=deviceid;
		this.token=token;
		this.protocol=protocol;
		this.data=data;
	}
	
	public int getPlayerid() {
		return playerid;
	}

	public void setPlayerid(int playerid) {
		this.playerid = playerid;
	}

	public String getDeviceid() {
		return deviceid;
	}
	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public int getProtocol() {
		return protocol;
	}
	public void setProtocol(int protocol) {
		this.protocol = protocol;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}

	public HttpRequest getRequest() {
		return request;
	}

	public void setRequest(HttpRequest request) {
		this.request = request;
	}
	
}
