package z.words;

import java.io.DataInputStream;
import java.io.InputStream;
import java.util.Hashtable;

import org.j4me.logging.Log;

public class Types {
	private Hashtable types;
	private CustomKey key;
	private Types(){
		types = new Hashtable();
		key = new CustomKey();
		load();
		
	}
	private static Types instance;
	public static Types instance(){
		if (instance == null){
			instance = new Types();
		}
		return instance;
	}
	private void load(){
		InputStream in = getClass().getResourceAsStream("/db/db_cat");
		try {
			DataInputStream di = new DataInputStream(in);
			int total = di.readInt();
			for (int i = 0; i < total; i++) {
				String k = di.readUTF();
				int v = di.readInt();
				CustomKey c = new CustomKey();
				c.key = v;
				types.put(c,k);
			}
		} catch (Exception e) {
			Log.error("Types.load ex", e);
		} finally {
			try {
				in.close();
			} catch (Exception e) {
			}
		}
	}
	public String get(int type){
		key.key = type;
		String s = (String)types.get(key);
		if (s == null){
			return "";
		}
		return s;
	}
	
	private class CustomKey {
		int key;
		public boolean equals(Object obj) {
			if (obj instanceof CustomKey) {
				CustomKey new_name = (CustomKey) obj;
				return new_name.key == key;
			}
			return false;
		}
		public int hashCode() {
			return key;
		}
	}
}
