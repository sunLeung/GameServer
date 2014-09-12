package game.song;

import game.dao.SongDao;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SongService {
	
	private static Map<Integer,Song> songContent=new ConcurrentHashMap<Integer, Song>();
	
	/**
	 * 加载所有歌曲
	 */
	public static void initSongContent(){
		List<Song> list=SongDao.load();
		if(list!=null&&list.size()>0){
			for(Song song:list){
				songContent.put(song.getId(), song);
			}
		}
	}
	
	/**
	 * 获取歌曲
	 * @param songid
	 * @return
	 */
	public static Song getSong(int songid){
		return songContent.get(songid);
	}
	

}
