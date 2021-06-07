package index;

import java.util.HashMap;
import java.util.Map;
import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.StringTokenizer;

public class shortword {
	private HashMap<Character, ArrayList> LookupTable1 = new HashMap<Character, ArrayList>();
	private HashMap<Character, String> LookupTable2 = new HashMap<Character, String>();
	ArrayList<String> word = new ArrayList<String>();
	private static final String word1[] = { "\u12C8\u12ED\u12D8\u122E",
			"\u12C8\u12ED\u12D8\u122A\u1275", "\u12C8\u120D\u12F0",
			"\u12C8\u1273\u12F0\u122D" };
	private static final String word2[] = { "\u134D\u122D\u12F5",
			"\u134D\u1245\u1228" };
	private static final String word3[] = { "\u1200\u12ED\u1208",
			"\u1200\u121D\u1233" };
	private static final String word4[] = { "\u1218\u1235\u122A\u12EB",
			"\u1218\u1270" };

	public void replace(String str1, String str2) {
		str1 = str2;
		// return str2;
	}

	public String Expander(String str) {
		LookupTable2.put('\u1275', "\u1275\u121D\u1205\u122D\u1275");
		LookupTable2.put('\u133D', "\u133D\u1205\u1348\u1275");
		LookupTable2.put('\u122D', "\u122D\u12A5\u1230");
		LookupTable2.put('\u120C', "\u120C\u1270\u1293\u120D");
		LookupTable2.put('\u1320', "\u1320\u1245\u120B\u12ED");
		LookupTable2.put('\u12F6', "\u12F6\u12AD\u1270\u122D");
		LookupTable2.put('\u1264', "\u1264\u1270");
		LookupTable2.put('\u121D', "\u121D\u12AD\u1275\u120D");
		LookupTable2.put('\u12A0', "\u12A0\u12F2\u1235 \u12A0\u1260\u1263");
		LookupTable2.put('\u12F0', "\u12F0\u1265\u1228");
		LookupTable2.put('\u121A', "\u121A\u1295\u1235\u1275\u122D");
		LookupTable2.put('\u1232', "\u1232\u1235\u1270\u122D");
		LookupTable2.put('\u12AD', "\u12AD\u134D\u1208");
		LookupTable2.put('\u12AE', "\u12AE\u1208\u1294\u120D");
		LookupTable2.put('\u1355', "\u1355\u122E\u134C\u1230\u122D");
		LookupTable2.put('\u121C', "\u121C\u1300\u122D");
		LookupTable2.put('\u1265', "\u1265\u122D\u130B\u12F4\u122D");
		LookupTable2.put('\u1270', "\u1270\u12AD\u1208");
		LookupTable2.put('\u1308', "\u1308\u1265\u1228");
		LookupTable2.put('\u120A', "\u120A\u1240");
		LookupTable2.put('\u134D', "\u134D\u122D\u12f5");
		for (String words : word1)
			word.add(words);
		LookupTable1.put('\u12C8', word);
		for (String words : word2)
			word.add(words);
		LookupTable1.put('\u134D', word);
		for (String words : word3)
			LookupTable1.put('\u1203', word);
		for (String words : word4)
			LookupTable1.put('\u1218', word);
		str = str.trim();
		int index = str.indexOf('/');
		System.out.println(index);
		if (index == -1) {
			return str;
		} else if (index > 0) {
			char k = str.charAt(index - 1);
			System.out.println(k);
			String str1 = str.substring(0, index);
			String str3 = str.substring(index + 1);
			if (LookupTable2.containsKey(k)) {
				String str2 = LookupTable2.get(k);

				if (str2.endsWith(str3)) {

					str = str2;
					return str;
				} else if (k == '\u12A0')
					return str2;
				else {
					String r = str2 + " " + str3;
					str = r;
					return str;

				}
			} else if (LookupTable1.containsKey(k)) {

				ArrayList ar = LookupTable1.get(k);
				for (int i = 0; i < ar.size(); i++) {
					String wrd = (String) ar.get(i);
					if (k == '\u12C8') {
						if (wrd.endsWith(str3)) {
							str = wrd;
							return str;
							// return wrd;
						} else {

							String r = "\u12c8\u120d\u12f0" + " " + str3;
							str = r;
							return str;
						}
					} else if (k == '\u134D') {

						if (str3.equals("\u1264\u1275")) {
							String r = "\u134D\u122D\u12F5" + " " + str3;
							str = r;
							return str;
						} else {

							String r = "\u134D\u1245\u1228" + " " + str3;
							str = r;
							return str;
						}
					} else if (k == '\u1200') {

						if (str3.equals("\u12A0\u1208\u1243")) {
							String r = "\u1203\u121D\u1233" + " " + str3;
							str = r;
							return str;
						} else {
							String r = "\u1203\u12ed\u1208" + " " + str3;
							str = r;
							return str;
						}
					} else if (k == '\u1218') {

						if (str3.equals("\u12A0\u1208\u1243")) {
							String r = "\u1218\u1276" + " " + str3;
							str = r;
							return str;
						} else if (wrd.endsWith(str3)) {
							str = wrd;
							return str;
							// return wrd;
						} else if (str3.equals("\u1264\u1275")) {

							String r = "\u1218\u1235\u122A\u12EB" + " " + str3;
							str = r;
							return str;
						}
					}

				} // for loop

			}

		}
		return str;
	}

	public static void main(String[] args) throws Exception {
		shortword rdd = new shortword();
		// rdd.create();
		FileInputStream fin = new FileInputStream(new File("c:/ii.txt"));
		InputStreamReader read = new InputStreamReader(fin, "utf-8");
		BufferedReader rd = new BufferedReader(read);
		String line;
		StringBuffer sb = new StringBuffer();
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		String sentence = sb.toString();
		sentence = sentence.trim();
		String newline = System.getProperty("line.separator");

		StringTokenizer tokens = new StringTokenizer(sentence,
				"\u1361\u1363\u1364\u1365\u1366\u0020\u0021");
	//"\u1361\u1362\u1363\u1364\u1365\u1366\u0020\u0021"); :: deleted above
		FileOutputStream fout = new FileOutputStream(new File("c:/kio2.txt"));
		OutputStreamWriter rt = new OutputStreamWriter(fout, "utf-8");
		BufferedWriter rtt = new BufferedWriter(rt);

		while (tokens.hasMoreTokens()) {
			String kk = tokens.nextToken().toString();
			rdd.Expander(kk);
			rtt.write(rdd.Expander(kk) + newline);
		}
		rtt.close();

	}
}