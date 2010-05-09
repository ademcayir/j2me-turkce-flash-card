package z.words;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;

import org.j4me.util.StringUtil;

public class Word {
	private String src;
	private int offset;
	private int lenght;
	private Vector tr;
	private Vector terim;
	private Vector syn;
	public Word(Index index){
		this(index.getWord(),index.getPositions());
	}
	public Word(String src,Position pos){
		this(src,pos.offset,pos.lenght);
	}
	public Word(String src,int offset,int len){
		this.src = src;
		this.offset = offset;
		this.lenght = len;
		tr = new Vector();
		terim = new Vector();
		syn = new Vector();
	}
	public String getSource(){
		return src;
	}
	public void loadFromStream(DataInputStream din, int current) throws IOException {
		int read = offset - current;
		while (read > 0){
			int l = din.skipBytes(read);
			read -= l;
		}
		int len = din.readInt();
		for (int i = 0; i < len; i+=2) {
			int type = din.readInt();
			String src = din.readUTF();
			WordBase base = new WordBase(src,null,type);
			tr.addElement(base);
		}
		
		len = din.readInt();
		for (int i = 0; i < len; i+=3) {
			int type = din.readInt();
			String terim = din.readUTF();
			String anlam = din.readUTF();
			terim = StringUtil.replace(terim, "%", src);
			WordBase base = new WordBase(anlam,terim,type);
			this.terim.addElement(base);
		}
		
		len = din.readInt();
		for (int i = 0; i < len; i+=2) {
			int type = din.readInt();
			String src = din.readUTF();
			WordBase base = new WordBase(src,null,type);
			syn.addElement(base);
		}
	}
	public void free(){
		tr.removeAllElements();
		terim.removeAllElements();
		syn.removeAllElements();
	}
	public int getByteStartPosition(){
		return offset;
	}
	public int getByteLength(){
		return lenght;
	}
	public Enumeration getTR(){
		return tr.elements();
	}
	public int getTERIMSize(){
		return terim.size();
	}
	public int getTRSize(){
		return tr.size();
	}
	public Enumeration getTERIM(){
		return terim.elements();
	}
	//#if DEBUG
	public String toString() {
		StringBuffer buf = new StringBuffer("Word[");
		buf.append("base="+src+";");
		buf.append("offset="+offset+";");
		buf.append("byte.length="+lenght+";");
		Enumeration e = tr.elements();
		buf.append("TR[");
		while (e.hasMoreElements()){
			buf.append(e.nextElement()+";");
		}
		buf.append("]");
		e = terim.elements();
		buf.append("TERIM[");
		while (e.hasMoreElements()){
			buf.append(e.nextElement()+";");
		}
		buf.append("]");
		e = syn.elements();
		buf.append("SYN[");
		while (e.hasMoreElements()){
			buf.append(e.nextElement()+";");
		}
		buf.append("]");
		
		buf.append("]");
		return buf.toString();
	}
	//#endif
	
}
