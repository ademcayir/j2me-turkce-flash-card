package z;

import javax.microedition.lcdui.Graphics;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

import org.j4me.ui.Menu;
import org.j4me.ui.MenuItem;
import org.j4me.ui.Theme;
import org.j4me.ui.UIManager;
import org.j4me.ui.components.CheckBox;
import org.j4me.ui.components.Component;

public class Starter extends MIDlet {
	
	private MainMenu menu;
	protected void startApp() throws MIDletStateChangeException {
		UIManager.init(this);
		if (menu == null){
			menu = new MainMenu();
		}
		menu.show();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.microedition.midlet.MIDlet#pauseApp()
	 */
	protected void pauseApp() {
		// The application has no state so ignore pauses.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.microedition.midlet.MIDlet#destroyApp(boolean)
	 */
	protected void destroyApp(boolean cleanup)
			throws MIDletStateChangeException {
		// The application holds no resources that need cleanup.
	}
}

/**
 * Options available from a menu that change the application's theme.
 */
class ThemeMenuItem implements MenuItem {
	private final String name;
	private final Theme theme;

	public ThemeMenuItem(String name, Theme theme) {
		this.name = name;
		this.theme = theme;
	}

	public String getText() {
		// The name as it appears in the menu.
		return name;
	}

	public void onSelection() {
		// Applies a theme to the example midlet.
		UIManager.setTheme(theme);

		// Repaint the screen so the changes take effect.
		UIManager.getScreen().repaint();
	}
}
