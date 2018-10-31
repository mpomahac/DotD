package application;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Raid {
	
	public Integer id;
	public String name;
	public Type type;
	public Size size;
	public Integer slots;
	public Integer hp;
	public Integer ap;
	public Integer fs;
	public Integer os;
	public Integer ms;
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
	public Raid(String name, int hp, Size size, Type type, int slots, int os, int ms, Map<String, String> apValues) {
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
		this.hp = hp;
		this.size = size;
		this.type = type;
		this.slots = slots;
		this.fs = this.hp / this.slots;
		this.ap = this.fs / 2;
		this.os = os;
		this.ms = ms;
		this.apValues = apValues;
		Main.allRaids.add(this);
		Main.allRaids.sort((r1, r2) -> r1.id.compareTo(r2.id));
	}
	
	//Read from file
	public Raid(int id, String name, Type type, Size size, int slots, int hp, int ap, int fs, int os, int ms, Map<String, String> apValues) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.size = size;
		this.slots = slots;
		this.hp = hp;
		this.ap = ap;
		this.fs = fs;
		this.os = os;
		this.ms = ms;
		this.apValues = apValues;
		Main.allRaids.add(this);
		Main.allRaids.sort((r1, r2) -> r1.id.compareTo(r2.id));
	}

	public String toString() {
		String str = this.id.toString() + "|" + this.name + "|" + this.type.toString() + "|" + this.size.toString() + "|" + this.slots.toString() + "|" + this.hp.toString() +
				"|" + this.ap.toString() + "|" + this.fs.toString() + "|" + this.os.toString() + "|" + this.ms.toString() + "|";
		
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
		int slots = Integer.parseInt(data[4]);
		int hp = Integer.parseInt(data[5]);
		int ap = Integer.parseInt(data[6]);
		int fs = Integer.parseInt(data[7]);
		int os = Integer.parseInt(data[8]);
		int ms = Integer.parseInt(data[9]);
		Map<String, String> apValues = new HashMap<>();
		
		for(String s : apPairs) {
			String[] pair = s.split(" ");
			apValues.put(pair[0], pair[1]);
		}
		
		new Raid(id, name, type, size, slots, hp, ap, fs, os, ms, apValues);
	}

	public static void loadRaids() {
		
	}
	
	
}
