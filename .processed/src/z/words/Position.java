package z.words;

public class Position {
	public int offset;
	public int lenght;
	Position set(int of,int le) {
		offset = of;
		lenght = le;
		return this;
	}
}
