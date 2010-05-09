package org.j4me.ui.components;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import org.j4me.ui.ActionListener;
import org.j4me.ui.DeviceScreen;
import org.j4me.ui.Theme;

public class Link extends Label {
	private static final int WGAP = 4;
	private int max_text_width;
	protected synchronized void paintComponent(Graphics g, Theme theme,
			int width, int height, boolean selected) {
		if (getLabel() != null){
			
			if ( (lines == null) || (widthForLines != width) )
			{
				max_text_width=0;
				Font font = getFont( theme );
				lines = breakIntoLines( font, getLabel(), width );
				widthForLines = width;
				
				for (int i=0;i<lines.length;i++){
					int w = font.stringWidth(lines[i]);
					if (w > max_text_width){
						max_text_width = w;
					}
				}
			}
		}
		
		if (selected ){
			int fontColor = getFontColor( theme );
			g.setColor(fontColor);
			g.drawRect(0, 1, max_text_width+2*WGAP, height-2);
			g.drawRect(1, 0, max_text_width+2*WGAP-2, height);
		}
		g.translate(WGAP, 0);
		super.paintComponent(g, theme, width-WGAP*2, height, selected);
		g.translate(-WGAP, 0);
	
	}
	public boolean acceptsInput() {
		return true;
	}
	
	public void keyPressed(int keyCode) {
		if (keyCode == DeviceScreen.FIRE){
			event_occured(ActionListener.ON_PRESS);
		}
	}
}
