package z;

import javax.microedition.lcdui.Font;

import org.j4me.ui.Theme;

public class Theme1 extends Theme {
	private Font font;
	private Font font(){
		if (font == null){
			font = Font.getFont(Font.FACE_MONOSPACE, Font.STYLE_PLAIN, Font.SIZE_SMALL);
		}
		return font;
	}
	
	public Font getFont() {
		return font();
	}
	public Font getTitleFont() {
		return font();
	}
	public Font getMenuFont() {
		return font();
	}
}
