package org.j4me.util;

public class StringUtil {

	
	public static String replace(String src,String from,String to){
		int loc = src.indexOf(from);
		while (loc != -1){
			src = src.substring(0,loc)+to+src.substring(loc+from.length());
			loc = src.indexOf(from);
		}
		return src;
	}
}
