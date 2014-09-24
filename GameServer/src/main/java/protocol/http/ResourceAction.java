package protocol.http;

import game.player.Player;
import game.player.PlayerCache;
import game.player.PlayerService;
import game.song.Song;
import game.song.SongBean;
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
import common.utils.StringUtils;

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
		}else if("getSongList".equals(action)){
			return getSongList(packet);
		}else if("querySong".equals(action)){
			return querySong(packet);
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
	 * 获取歌曲库前多少条歌曲
	 * @param packet
	 * @return
	 */
	public static String getSongList(HttpPacket packet){
		String data=packet.getData();
		JsonNode node=JsonUtils.decode(data);
		long time = JsonUtils.getLong("time", node);
		if(time!=-1){
			List<SongBean> list=SongService.getSongBeanList(time);
			List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
			for(SongBean bean:list){
				Map<String,Object> map=new HashMap<String, Object>();
				map.put("name", bean.getName());
				map.put("singer", bean.getSinger());
				map.put("mp3URL", bean.getMp3URL());
				map.put("bpm", bean.getBpm());
				map.put("level", bean.getLevel());
				Player p=PlayerCache.getPlayer(bean.getTopplayer());
				boolean hasRight=false;
				if(p!=null){
					map.put("topPlayerName", p.getBean().getName());
					List<Integer> ls=p.getSongs();
					if(ls!=null&&ls.contains(bean.getId())){
						hasRight=true;
					}
				}else{
					map.put("topPlayerName", "");
				}
				map.put("topScore", bean.getTopscore());
				map.put("state", bean.getState());
				map.put("price", bean.getPrice());
				map.put("createTime", bean.getCreateTime());
				map.put("md5", bean.getMd5());
				map.put("hasRight", hasRight);
				result.add(map);
			}
			return JsonRespUtils.success(result);
		}
		return JsonRespUtils.fail(Def.CODE_FAIL, "Error param 'time'.");
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
			List<SongBean> songlist=new ArrayList<SongBean>();
			if(songs!=null){
				for(Integer songid:songs){
					SongBean s=SongService.getSong(songid);
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
			SongBean song=SongService.getSong(resourceid);
			if(song!=null){
				return JsonRespUtils.success(song);
			}
		}
		return JsonRespUtils.fail(Def.CODE_FAIL, "This song does not exist.");
	}
	
	
	/**
	 * 歌曲查找
	 * @param packet
	 * @return
	 */
	public static String querySong(HttpPacket packet){
		List<SongBean> result=new ArrayList<SongBean>();
		String data=packet.getData();
		JsonNode node=JsonUtils.decode(data);
		String key=JsonUtils.getString("key", node);
		int option=JsonUtils.getInt("option", node);
		if(option==1){//根据作者查找
			for(Song song:SongService.getSongBeanList()){
				String singer=song.getBean().getSinger();
				if(StringUtils.isNotBlank(singer)&&singer.contains(key)){
					result.add(song.getBean());
				}
			}
		}else{//根据歌曲名查找
			for(Song song:SongService.getSongBeanList()){
				String name=song.getBean().getName();
				if(StringUtils.isNotBlank(name)&&name.contains(key)){
					result.add(song.getBean());
				}
			}
		}
		return JsonRespUtils.success(result);
	}
	
}
