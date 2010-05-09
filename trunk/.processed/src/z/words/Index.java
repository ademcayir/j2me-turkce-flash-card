package z.words;

import java.util.Random;

public class Index {
	private static Random random;
	public Index(){
		if (random == null){
			random = new Random();
		}
	}
	private Index index[];
	private Index parent;
	private char c;
	private int values[];
	private short ids[];
	
	public String getWord(){
		StringBuffer buf = new StringBuffer();
		return getWord(buf);
	}
	private String getWord(StringBuffer word){
		if (parent == null){
			return word.toString();
		} else {
			word.insert(0, c);
			return parent.getWord(word);
		}
	}
	public Position getPositions(){
		return new Position().set(values[1],values[2]);
	}
	public Index getRandom(){
		if (index == null){
			return this;
		}
		int rnd = (int)Math.abs(random.nextInt() % index.length);
		return index[rnd].getRandom();
	}
	public Index get(int id){
		for (int i = 0; i < index.length; i++) {
			if (index[i].values != null && index[i].values[0] == id){
				return index[i];
			}
			
			if (index[i].ids != null) {
				if ( index[i].ids[0] <= id && id <= index[i].ids[1]){
					return index[i].get(id);
				}
			}
		}
		return null;
	}
	public Index get(StringBuffer word){
		if (word == null || word.length() == 0){
			if (index == null){
				return this;
			} else {
				return null;
			}
		}
		if (index == null){
			return null;
		}
		char c = word.charAt(0);
		word.deleteCharAt(0);
		
		for (int i = 0; i < index.length; i++) {
			if (c == index[i].c){
				return index[i].get(word);
			}
		}
		return null;
	}
	public void put(int id,StringBuffer word,int offset,int lenght){
		if (word.length() == 0){
			this.values = new int[3];
			this.values[0] = id;
			this.values[1] = offset;
			this.values[2] = lenght;
			return;
		}
		int index_pos=-1;
		char c = word.charAt(0);
		word.deleteCharAt(0);
		boolean init = false;
		if (index == null){
			index = new Index[1];
			index[0] = new Index(); 
			index[0].c = c;
			index[0].parent = this;
			index[0].ids = new short[2];
			init = true;
			index_pos = 0;
		} else {
			for (int i = 0; i < index.length; i++) {
				if (index[i].c == c){
					index_pos = i;
					break;
				}
			}
			if (index_pos == -1){
				Index tmp[] = new Index[index.length + 1];
				System.arraycopy(index, 0, tmp, 0, tmp.length - 1);
				index_pos = tmp.length- 1;
				tmp[index_pos] = new Index();
				tmp[index_pos].c = c;
				tmp[index_pos].parent = this;
				tmp[index_pos].ids = new short[2];
				init = true;
				index = tmp;
			}
		}
		if (init ){
			index[index_pos].ids[0] = (short)id;
			index[index_pos].ids[1] = (short)id;
		} else {
			if ( index[index_pos].ids[0] > id ){
				index[index_pos].ids[0] = (short)id;
			} else if ( index[index_pos].ids[1] < id ){
				index[index_pos].ids[1] = (short)id;
			}
		}
		index[index_pos].put(id,word, offset,lenght);
	}
	
	
	public String toString() {
		return "Index[word="+getWord()+";id="+values[0]+";offset="+values[1]+";lenght="+values[2]+"]";
	}
	
	public void show(String s){
		if (c != 0){
			System.out.print(s+">"+c);
			if (values != null){
				int i[] = (int[])values;
				System.out.print("("+i[1]+","+i[2]+")");
			}
			System.out.println();
		}
		if (index == null){
			return;
		}
		for (int i = 0; i < index.length; i++) {
			if (index[i] == null){
				System.out.println("NULL ");
			} else {
				index[i].show(s+"-");
			}
		}
	}
}
