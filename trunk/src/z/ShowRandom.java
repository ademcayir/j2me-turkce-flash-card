package z;


import java.io.DataInputStream;
import java.io.InputStream;
import java.util.Enumeration;

import org.j4me.logging.Log;
import org.j4me.ui.DeviceScreen;
import org.j4me.ui.Dialog;
import org.j4me.ui.UIManager;
import org.j4me.ui.components.HorizontalRule;
import org.j4me.ui.components.Label;

import z.words.Index;
import z.words.Position;
import z.words.Word;
import z.words.WordBase;

public class ShowRandom extends Dialog {
	
	private DeviceScreen prev;
	private Label word;
	private Label tr;
	private Label terim;
	private Word w_template;
	private HorizontalRule rule;
	public ShowRandom(DeviceScreen prev){
		this.prev = prev;
		setTitle("Test");
		setMenuText("Back", "Next");
		word = new Label();
		tr = new Label();
		rule = new HorizontalRule();
		terim = new Label();
		UIManager.setTheme(new Theme1());
		append(word);
		append(rule);
		append(tr);
		append(rule);
		append(terim);
	}
	public void showNotify() {
		nextWord();
	}
	protected void declineNotify() {
		if (UIManager.getPrevScreen() != null){
			UIManager.getPrevScreen().show();
		}
	}
	protected void acceptNotify() {
		nextWord();
	}
	
	private void nextWord(){
		Index i = MainMenu.instance().getRandomWord();
		//Index i = MainMenu.instance().getWordById(1577);
		String w = i.getWord();
		Position p = i.getPositions();
		word.setLabel(w);
		setTitle(w);
		
		word.repaint();
		if (w_template != null){
			w_template.free();
			System.gc();
		}
		w_template = new Word(w,p.offset,p.lenght);
		InputStream input = getClass().getResourceAsStream("/db/db_"+w.charAt(0));
		try {
			w_template.loadFromStream(new DataInputStream(input), 0);
		} catch (Exception e) {
			Log.error("ShowText.nextWord ex", e);
		} finally {
			try {
				input.close();
			} catch (Exception e) {
			}
		}
		Enumeration e = w_template.getTR();
		StringBuffer b = new StringBuffer();
		int count = 1;
		while (e.hasMoreElements()){
			WordBase base = (WordBase)e.nextElement();
			b.append((count++)+". "+base.getAnlam());
			String type = base.getType();
			if (!type.equals("")){
				b.append(" ("+type+")");
			}
			b.append("\n");
		}
		tr.setLabel(b.toString());
		tr.repaint();
		
		e = w_template.getTERIM();
		b = new StringBuffer();
		count = 1;
		while (e.hasMoreElements()){
			WordBase base = (WordBase)e.nextElement();
			b.append((count++)+". "+base.getTerim());
			b.append(": "+base.getAnlam());
			String type = base.getType();
			if (!type.equals("")){
				b.append(" ("+type+")");
			}
			b.append("\n");
		}
		terim.setLabel(b.toString());
		terim.repaint();
		
		setSelected(word);
	}
	
}
