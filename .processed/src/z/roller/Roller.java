package z.roller;

import java.util.Enumeration;
import java.util.Vector;

public class Roller implements Runnable {
	private Vector rolls;
	private Roller(){
		rolls = new Vector();
	}
	private static Roller instance;
	public static Roller instance(){
		if (instance == null){
			instance = new Roller();
			Thread t = new Thread(instance);
			t.start();
		}
		return instance;
	}
	public void run() {
		while (true){
			if (rolls.size() == 0){
				synchronized (rolls) {
					try {
						rolls.wait();
					} catch (InterruptedException e) {
					}
				}
			}
			Enumeration e = rolls.elements();
			while (e.hasMoreElements()){
				Rollable r = (Rollable)e.nextElement();
				r.rollIt();
			}
			Thread.yield();
			try {
				Thread.sleep(100);
			} catch (Exception ex) {
			}
		}
	}
	public void removeAnim(Rollable r){
		synchronized (rolls) {
			while (rolls.removeElement(r));
		}
	}
	public void addAnim(Rollable r){
		rolls.addElement(r);
		synchronized (rolls) {
			rolls.notifyAll();
		}
	}
	
	
}
