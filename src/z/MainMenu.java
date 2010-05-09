package z;

import java.io.DataInputStream;
import java.io.InputStream;
import java.util.Vector;

import org.j4me.logging.Log;
import org.j4me.ui.DeviceScreen;
import org.j4me.ui.Dialog;
import org.j4me.ui.Menu;
import org.j4me.ui.MenuItem;
import org.j4me.ui.UIManager;
import org.xml.sax.InputSource;

import z.words.Index;
import z.words.Word;

public class MainMenu extends Menu {
	private Index allWords;
	private static MainMenu instance;
	public static MainMenu instance(){
		return instance;
	}
	public MainMenu(){
		instance = this;
		setTitle("Flash Card");
		appendMenuOption("Random Words", new ShowRandom(this));
		appendMenuOption("Start Test",new ShowTest());
		appendMenuOption("Statistic",new ShowStat());
		appendMenuOption("Settings",new Settings());
		appendMenuOption(new MenuItem(){
			public String getText() {
				return "Quit";
			}
			public void onSelection() {
				UIManager.quit();
			}
		});
		loadAllWords();
	}
	private void loadAllWords(){
		allWords = new Index();
		InputStream input = getClass().getResourceAsStream("/db/db_head");
		try {
			DataInputStream din = new DataInputStream(input);
			System.gc();
			StringBuffer b = new StringBuffer();
			long ti = System.currentTimeMillis();
			int id_count = 0;
			while (din.available() > 0){
				String src = din.readUTF();
				int offset = din.readInt();
				int len = din.readInt();
				b.append(src);
				allWords.put(id_count,b, offset,len );
				id_count++;
			}
			ti = System.currentTimeMillis() - ti;
			Log.info("Total read time: "+ti);
		} catch (Exception e) {
			Log.error("load all words ex", e);
		} finally {
			try {
				input.close();
			} catch (Exception e) {
			}
		}
	}
	
	public Index getWord(String word){
		return allWords.get(new StringBuffer(word));
	}
	public Index getWordById(int id){
		return allWords.get(id);
	}
	public Index getRandomWord(){
		return allWords.getRandom();
	}
	
	
	
	
	
}
