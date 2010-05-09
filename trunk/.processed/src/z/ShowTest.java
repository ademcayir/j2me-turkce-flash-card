package z;

import java.io.DataInputStream;
import java.io.InputStream;
import java.util.Enumeration;

import org.j4me.logging.Log;
import org.j4me.ui.ActionListener;
import org.j4me.ui.Dialog;
import org.j4me.ui.UIManager;
import org.j4me.ui.components.Link;
import org.j4me.ui.components.CheckBox;
import org.j4me.ui.components.Component;
import org.j4me.ui.components.Label;
import org.j4me.ui.components.ProgressBar;
import org.j4me.ui.components.RadioButton;
import org.j4me.util.MathFunc;

import z.words.Index;
import z.words.Word;
import z.words.WordBase;

public class ShowTest extends Dialog implements ActionListener {
	private ProgressBar loadingbar;
	private Word words[];
	private int number_of_words_of_test;
	private int current_ask_word;
	private int right_answer;
	private Label ask;
	private CheckBox checks[];
	private Link btn;
	private Link next_to_question;
	private String default_header;
	
	private int total_right_answer;
	
	public ShowTest(){
		default_header = "Test yout might";
		setTitle(default_header);
	}
	protected void declineNotify() {
		if (UIManager.getPrevScreen() != null){
			UIManager.getPrevScreen().show();
		}
	}
	public void showNotify() {
		if (loadingbar == null){
			loadingbar = new ProgressBar();
		}
		if (ask == null){
			ask = new Label();
		}
		if (checks != null){
			for (int i = 0; i < checks.length; i++) {
				delete(checks[i]);
			}
		}
		if (btn == null){
			btn = new Link();
			btn.setActionListener(this);
		}
		if (next_to_question == null){
			next_to_question = new Link();
			next_to_question.setActionListener(this);
			next_to_question.setLabel("Show Next Question");
		}
		delete(btn);
		delete(ask);
		delete(loadingbar);
		append(loadingbar);
		loadingbar.visible(true);
		number_of_words_of_test = 10;
		Thread t = new Thread(new Runnable(){
			public void run() {
				generateTest();
				ShowTest.this.delete(loadingbar);
			}
		});
		t.start();
	}
	private void generateTest(){
		Index words_index[];
		total_right_answer = 0;
		words_index = new Index[number_of_words_of_test + number_of_words_of_test/2];
		loadingbar.setMaxValue(number_of_words_of_test*4);
		int current_progress = 0;
		for (int i = 0; i < words_index.length; ) {
			Index can = MainMenu.instance().getRandomWord();
			boolean found = false;
			for (int j = 0; j < i; j++) {
				if (words_index[j] == can){
					found= true;
					break;
				}
			}
			if (found){
				continue;
			}
			words_index[i] = can;
			current_progress++;
			loadingbar.setValue(current_progress);
			i++;
		}
		words = new Word[words_index.length];
		for (int i = 0; i < words_index.length; i++ ) {
			words[i] = new Word(words_index[i]);
		}
		sort();
		InputStream input = null;
		try {
			char last = '0';
			int current = 0;
			DataInputStream dinput = null;
			for (int i=0;i<words.length;i++){
				char c = words[i].getSource().charAt(0);
				if (c != last){
					if (input != null){
						try {
							input.close();
						} catch (Exception e) {
						}
					}
					current = 0;
					last = c;
					input = getClass().getResourceAsStream("/db/db_"+c);
					dinput = new DataInputStream(input);
				}
				words[i].loadFromStream(dinput, current);
				current_progress+=3;
				loadingbar.setValue(current_progress);
				current = words[i].getByteStartPosition() + words[i].getByteLength();				
			}
		} catch (Exception e) {
			Log.error("loading words error", e);
		} finally {
			try {
				input.close();
			} catch (Exception e) {
			}
		}
		if (checks == null){
			checks = new CheckBox[5];
			for (int i = 0; i < checks.length; i++) {
				checks[i] = new CheckBox();
				checks[i].setActionListener(this);
			}
		}
		append(ask);
		for (int i = 0; i < checks.length; i++) {
			append(checks[i]);
		}
		append(btn);
		current_ask_word = 0;
		nextQuestion();
	}
	
	private void nextQuestion(){
		if (current_ask_word == number_of_words_of_test){
			showEndScreen();
			return;
		}
		Word target = words[current_ask_word];
		ask.setLabel(target.getSource());
		right_answer = MathFunc.random(0, checks.length);
		int random_mean = MathFunc.random(0, target.getTRSize());
		Enumeration e = target.getTR();
		boolean first = true;
		int count = 0;
		while (e.hasMoreElements()){
			WordBase tr = (WordBase)e.nextElement();
			if (first){
				checks[right_answer].setLabel(tr.getAnlam());
			}
			if (count == random_mean){
				checks[right_answer].setLabel(tr.getAnlam());
				break;
			}
			count++;
		}
		for (int i = 0; i < checks.length; i++) {
			checks[i].setChecked(false);
			checks[i].setUnderlined(false);
			if (i != right_answer){
				while (true){
					int rand = MathFunc.random(0, words.length);
					while (rand == current_ask_word){
						rand = MathFunc.random(0, words.length);
					}
					int tr_size = words[rand].getTRSize();
					int mean_rand = MathFunc.random(0, tr_size);
					count = 0;
					e = words[rand].getTR();
					String mean = null;
					while (e.hasMoreElements()){
						WordBase w = (WordBase)e.nextElement();
						if (count == mean_rand){
							mean = w.getAnlam();
							break;
						}
						count++;
					}
					if (mean != null && !isTRMean(target, mean)){
						checks[i].setLabel(mean);
						break;
					}
				}
			}
		}
		btn.setLabel("OK");
		repaint();
		current_ask_word++;
	}
	private void showEndScreen(){
		
	}
	private boolean isTRMean(Word w,String mean){
		Enumeration e = w.getTR();
		mean = mean.toLowerCase();
		while (e.hasMoreElements()){
			WordBase b = (WordBase)e.nextElement();
			if (b.getAnlam().equals(mean)){
				return true;
			}
		}
		return false;
	}
	
	public void actionPerformed(Component component, int event) {
		if (component instanceof CheckBox ){
			if (event == ON_SELECT || event == ON_UNSELECT ){
				for (int i = 0; i < checks.length; i++) {
					if (checks[i] != component){
						checks[i].setChecked(false);
					} else {
						checks[i].setChecked(true);
					}
				}
			}
		}
		if ( component == btn ){
			int checked = -1;
			for (int i = 0; i < checks.length; i++) {
				if (checks[i].isChecked()){
					checked = i;
					break;
				}
			}
			
			if (checked != -1){
				checks[right_answer].setUnderlined(true);
				checks[right_answer].repaint();
				if (getComponentIndex(next_to_question) == -1){
					append(next_to_question);
					if (checked == right_answer){
						total_right_answer ++;
					}
					setTitle(default_header+" ("+total_right_answer+"/"+current_ask_word+")");
					repaint();
				}
			}
		} else if (component == next_to_question){
			delete(next_to_question);
			nextQuestion();
		}
		
	}
	private void sort(){
		for (int i = 0; i < words.length - 1; i++) {
			for (int j = 0; j < words.length - i - 1; j++) {
				if (words[j].getSource().compareTo(words[j+1].getSource()) > 0){
					Word tmp = words[j];
					words[j] = words[j + 1];
					words[j + 1] = tmp;
				}
			}
		}
	}
}
