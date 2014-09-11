package protocol.http;

import game.player.Player;
import game.player.PlayerCache;
import game.player.PlayerService;
import game.song.Song;
import game.song.SongService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

import common.net.HttpAction;
import common.net.HttpPacket;
import common.net.HttpProtocol;
import common.utils.Def;
import common.utils.JsonRespUtils;
import common.utils.JsonUtils;

@HttpProtocol(Def.PROTOCOL_RESOURCE)
public class ResourceAction extends HttpAction{

	@Override
	public String excute(HttpPacket packet) {
		String data=packet.getData();
		JsonNode node=JsonUtils.decode(data);
		String action = JsonUtils.getString("action", node);
		if("checkResourceAuth".equals(action)){
			return checkResourceAuth(packet);
		}else if("getPlayerSongs".equals(action)){
			return getPlayerSongs(packet);
		}
		return JsonRespUtils.fail(Def.CODE_QUERY_RESOURCE_FAIL, "Can not find action:"+action);
	}
	
	public static String checkResourceAuth(HttpPacket packet){
		String data=packet.getData();
		JsonNode node=JsonUtils.decode(data);
		int resourceid = JsonUtils.getInt("resourceid", node);
		int playerid=packet.getPlayerid();
		if(resourceid!=-1&&playerid!=-1){
			boolean b=PlayerService.hasThisSong(playerid, resourceid);
			if(b){
				Map<String, Object> r=new HashMap<String, Object>();
				Player p=PlayerCache.getPlayer(playerid);
				r.put("secret", p.getBean().getSecret());
				return JsonRespUtils.success(r);
			}
		}
		return JsonRespUtils.fail(Def.CODE_QUERY_RESOURCE_FAIL, "Player has not this resource.");
	}
	
	/**
	 * 获取玩家所有歌曲信息
	 * @param packet
	 * @return
	 */
	public static String getPlayerSongs(HttpPacket packet){
		Player p = PlayerCache.getPlayer(packet.getPlayerid());
		if(p!=null){
			List<Integer> songs=p.getSongs();
			List<Song> songlist=new ArrayList<Song>();
			if(songs!=null){
				for(Integer songid:songs){
					Song s=SongService.getSong(songid);
					if(s!=null)
						songlist.add(s);
				}
			}
			return JsonRespUtils.success(songlist);
		}
		return JsonRespUtils.fail(Def.CODE_FAIL, "This player does not exist.");
	}
	
	/**
	 * 获取歌曲数据
	 * @param packet
	 * @return
	 */
	public static String getSongInfo(HttpPacket packet){
		String data=packet.getData();
		JsonNode node=JsonUtils.decode(data);
		int resourceid = JsonUtils.getInt("resourceid", node);
		if(resourceid!=-1){
			Song song=SongService.getSong(resourceid);
			if(song!=null){
				return JsonRespUtils.success(song);
			}
		}
		return JsonRespUtils.fail(Def.CODE_FAIL, "This song does not exist.");
	}
	
}
