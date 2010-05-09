package org.j4me.ui;

import org.j4me.ui.components.Component;

public interface ActionListener {
	public static final int ON_PRESS = 1;
	public static final int ON_SELECT = 2;
	public static final int ON_UNSELECT = 3;
	public void actionPerformed(Component component,int event);
}
