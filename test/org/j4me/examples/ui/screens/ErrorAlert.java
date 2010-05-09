package org.j4me.examples.ui.screens;

import javax.microedition.lcdui.*;
import org.j4me.ui.*;
import org.j4me.ui.components.*;

/**
 * Objects of this class alert the user about an error.
 */
public class ErrorAlert
	extends Dialog
{
	/**
	 * The screen that will be returned to once the user dismisses this error
	 * alert.
	 */
	protected final DeviceScreen parent;
	
	/**
	 * The UI component that displays the error message.
	 */
	private Label label = new Label();
	
	/**
	 * Constructs an error alert screen.
	 * 
	 * @param title is the title of the screen.
	 * @param error is the reason of the failure.  Often this will include
	 *  a <code>Throwable.toString</code>.
	 * @param parent is the screen to return to once the user dismisses
	 *  this error alert.
	 */
	public ErrorAlert (String title, String error, DeviceScreen parent)
	{
		// Set the alert title.
		setTitle( title );
		
		// Set the error text.
		label.setHorizontalAlignment( Graphics.HCENTER );
		label.setLabel( error );
		append( label );
		
		// Add the menu buttons.
		setMenuText( "Back", null );

		// Record the parent screen.
		this.parent = parent;
	}
	
	/**
	 * @return The error message.
	 */
	public String getText ()
	{
		return label.getLabel();
	}
	
	/**
	 * Responds to the user dismissing this screen.
	 * 
	 * @see DeviceScreen#declineNotify()
	 */
	public void declineNotify ()
	{
		if ( parent != null )
		{
			// Return to the parent screen.
			parent.show();
		}
	}
		
	/**
	 * Notifies the user of an error.
	 * 
	 * @see DeviceScreen#showNotify()
	 */
	public void showNotify ()
	{
		// Play the error sound.
		Display display = UIManager.getDisplay();
		AlertType.ERROR.playSound( display );
		
		// Vibrate the device.
		display.vibrate( 1000 );  // 1 second
		
		// Continue processing the event.
		super.showNotify();
	}
}
