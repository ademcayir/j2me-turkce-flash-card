package z.words;

public class WordBase {
	public static final int YAYGIN = 1;
	public static final int GENEL = 2;
	public static final int BILGISAYAR = 3;
	public static final int TEKNIK = 4;
	public static final int KONUSMA = 5;
	public static final int NOUN =100;
	public static final int ADJ = 101;
	public static final int ADV = 102;
	private String anlam;
	private String terim;
	private int type;
	WordBase(String anlam,String terim,int type) {
		this.anlam = anlam;
		this.terim = terim;
		this.type = type;
	}
	public String getType(){
		return Types.instance().get(type);
	}
	public String getAnlam(){
		return anlam;
	}
	public String getTerim(){
		return terim;
	}
	//#if DEBUG
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append("WordBase[");
		buf.append("mean="+anlam+";");
		buf.append("terim="+terim+";");
		buf.append("type="+typeToString(type)+";");
		buf.append("]");
		return super.toString();
	}
	private String typeToString(int type){
		switch (type) {
		case YAYGIN:
			return "Yaygýn";
		case GENEL:
			return "Genel";
		case BILGISAYAR:
			return "Bilgisayar";
		case TEKNIK:
			return "Teknik";
		case KONUSMA:
			return "Konuþma";
		case NOUN:
			return "Noun";
		case ADJ:
			return "Adjective";
		case ADV:
			return "Adverb";
		default:
			return "UnknownType["+type+"]";
		}
	}
	//#endif
}
