package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;

public class Raid {
	
	public static Map<String, BigDecimal> numbers;
	static {
		numbers = new HashMap<>();
		numbers.put("k", new BigDecimal("1000"));
		numbers.put("m", new BigDecimal("1000000"));
		numbers.put("b", new BigDecimal("1000000000"));
		numbers.put("t", new BigDecimal("1000000000000"));
		numbers.put("q", new BigDecimal("1000000000000000"));
		numbers.put("Q", new BigDecimal("1000000000000000000"));
	}
	
	public Integer id;
	public String name;
	public Type type;
	public Size size;
	public BigDecimal slots;
	public BigDecimal hp;
	public BigDecimal ap;
	public BigDecimal fs;
	public BigDecimal os;
	public BigDecimal ms;
	public Map<String, String> apValues = new HashMap<>();
	
	public enum Size{
		SMALL,
		MEDIUM,
		LARGE,
		EPIC,
		COLOSSAL,
		GIGANTIC,
	}
	
	public enum Type{
		REGULAR,
		GUILD,
		GUILD_EVENT
	}
	
	//Create from user input
	public Raid(String name, Type type, Size size, String slots, String hp, String os, String ms, Map<String, String> apValues) {
		if(type.equals(Type.REGULAR)) {
			this.id = (int) (1000 * (1 + size.ordinal()) + Main.allRaids.stream().filter(r -> r.size.equals(size) && r.type.equals(type)).count());
		}
		else if(type.equals(Type.GUILD)) {
			this.id = (int) (7000 + Main.allRaids.stream().filter(r -> r.type.equals(type)).count());
		}
		else {
			this.id = (int) (7900 + Main.allRaids.stream().filter(r -> r.type.equals(type)).count());
		}
		
		this.name = name;
		this.hp = new BigDecimal(hp);
		this.size = size;
		this.type = type;
		this.slots = new BigDecimal(slots);
		this.fs = this.hp.divide(this.slots, 2, RoundingMode.CEILING);
		this.ap = this.fs.divide(new BigDecimal("2"), 2, RoundingMode.CEILING);
		this.os = new BigDecimal(os);
		this.ms = new BigDecimal(ms);
		this.apValues = apValues;
		Main.allRaids.add(this);
		Main.allRaids.sort((r1, r2) -> r1.id.compareTo(r2.id));
		saveRaids();
	}
	
	//Read from file
	public Raid(int id, String name, Type type, Size size, String slots, String hp, String ap, String fs, String os, String ms, Map<String, String> apValues) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.size = size;
		this.slots = new BigDecimal(slots);
		this.hp = new BigDecimal(hp);
		this.ap = new BigDecimal(ap);
		this.fs = new BigDecimal(fs);
		this.os = new BigDecimal(os);
		this.ms = new BigDecimal(ms);
		this.apValues = apValues;
		Main.allRaids.add(this);
		Main.allRaids.sort((r1, r2) -> r1.id.compareTo(r2.id));
	}

	public String toString() {
		String hp = shortValue(this.hp);
		String ap = shortValue(this.ap);
		String fs = shortValue(this.fs);
		String os = shortValue(this.os);
		String ms = shortValue(this.ms);
		
		String str = this.id.toString() + "|" + this.name + "|" + this.type.toString() + "|" + this.size.toString() + "|" + this.slots.toString() + "|" + hp +
				"|" + ap + "|" + fs + "|" + os + "|" + ms + "|";
		
		int i = 0;
		for(Entry<String, String> e : this.apValues.entrySet()) {
			i++;
			str += "(" + e.getKey() + " " + e.getValue() + ")";
			if(i < this.apValues.size()) str += ",";
		}
		return str;
	}
	
	public static void fromString(String str) {
		String[] data = str.split("\\|");
		String[] apPairs = data[10].replaceAll("\\(|\\)", "").split(",");
		
		int id = Integer.parseInt(data[0]);
		String name = data[1];
		Type type = Type.valueOf(data[2]);
		Size size = Size.valueOf(data[3]);
		String slots = data[4];
		String hp = longValue(data[5]);
		String ap = longValue(data[6]);
		String fs = longValue(data[7]);
		String os = longValue(data[8]);
		String ms = longValue(data[9]);
		Map<String, String> apValues = new HashMap<>();
		
		for(String s : apPairs) {
			String[] pair = s.split(" ");
			apValues.put(pair[0], pair[1]);
		}
		
		new Raid(id, name, type, size, slots, hp, ap, fs, os, ms, apValues);
	}
	
	private String shortValue(BigDecimal value) {
		String val = "";
		if(value.compareTo(numbers.get("k")) == -1) {
			val = value.toString();
		}
		else if(value.compareTo(numbers.get("m")) == -1) {
			val = value.divide(numbers.get("k")).toString() + "k";
		}
		else if(value.compareTo(numbers.get("b")) == -1) {
			val = value.divide(numbers.get("m")).toString() + "m";
		}
		else if(value.compareTo(numbers.get("t")) == -1) {
			val = value.divide(numbers.get("b")).toString() + "b";
		}
		else if(value.compareTo(numbers.get("q")) == -1) {
			val = value.divide(numbers.get("t")).toString() + "t";
		}
		else if(value.compareTo(numbers.get("Q")) == -1) {
			val = value.divide(numbers.get("q")).toString() + "q";
		}
		
		return val;
	}
	
	private static String longValue(String value) {
		String val = "";
		String suffix = "";
		
		value = value.replaceAll("\\,", ".");
		
		if(value.matches(".*[kmbtqQ]$")) {
			System.out.println("Matched with k");
			suffix = value.substring(value.length()-1);
			value = value.replaceAll("[kmbtqQ]", "");
			System.out.println("Removed k: " + value);
			
			int decPlaces = 0;
			
			if(value.contains(".")) {
				decPlaces = value.length() - value.indexOf(".") - 1;
				value = value.replaceAll("\\.", "");
			}
			val += value;
			int addZeros = 0;
			
			if(suffix.equals("k")) {
				addZeros = 3;
			}
			else if(suffix.equals("m")) {
				addZeros = 6;
			}
			else if(suffix.equals("b")) {
				addZeros = 9;
			}
			else if(suffix.equals("t")) {
				addZeros = 12;
			}
			else if(suffix.equals("q")) {
				addZeros = 15;
			}
			else if(suffix.equals("Q")) {
				addZeros = 18;
			}
			
			addZeros -= decPlaces;
			
			for(int i = 0; i < addZeros; i++) {
				val += "0";
			}
			System.out.println("Value after load: " + val);
		}
		else {
			if(value.contains(".")) {
				value = value.substring(0, value.indexOf("."));
			}
			val = value;
		}
		
		return val;
	}
	
	public static void saveRaids() {
		File f = new File("./raids.dat");
		try {
			FileWriter fw = new FileWriter(f, false);
			PrintWriter pw = new PrintWriter(fw, true);
			
			for(Raid r : Main.allRaids) {
				pw.println(r.toString());
			}
			pw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void loadRaids() {
		File f = new File("./raids.dat");
		try {
			Scanner scanner = new Scanner(f);
			String str;
			
			while(scanner.hasNextLine()) {
				str = scanner.nextLine();
				Raid.fromString(str);
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
}
