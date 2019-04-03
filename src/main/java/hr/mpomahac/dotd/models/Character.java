package hr.mpomahac.dotd.models;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hr.mpomahac.dotd.controllers.Main;
import hr.mpomahac.dotd.models.Camp.Achievement;

import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

public class Character {
	
	private static final Logger LOGGER = LogManager.getLogger("resourceLogger");
	
	public static String FILE;
	
	public String name;
	public SortedMap<String, String> raidKills = new TreeMap<>((k1, k2) -> Integer.valueOf(k1).compareTo(Integer.valueOf(k2)));
	public SortedMap<String, String> campKills = new TreeMap<>((k1, k2) -> Integer.valueOf(k1).compareTo(Integer.valueOf(k2)));
	
	//Create new from user input
	public Character(String name) {
		this.name = name;
		for(Raid r : Main.allRaids) {
			raidKills.put(r.id.toString(), "0");
		}
		for(Camp c : Main.allCamps) {
			for(Achievement a : c.achievements) {
				campKills.put(Integer.valueOf(c.id*10 + a.ordinal()).toString(), "0");
			}
		}
		
		Main.allChars.add(this);
		saveCharacters();
	}
	
	//Read from file
	public Character(String name, SortedMap<String, String> raidKills, SortedMap<String, String> campKills) {
		this.name = name;
		this.raidKills = raidKills;
		this.campKills = campKills;
		
		List<String> raidIds = new ArrayList<>();
		List<String> campIds = new ArrayList<>();
		
		for(Raid r : Main.allRaids) {
			raidIds.add(r.id.toString());
			if(!this.raidKills.containsKey(r.id.toString())) {
				this.raidKills.put(r.id.toString(), "0");
			}
		}
		
		for(Camp c : Main.allCamps) {
			for(Achievement a : c.achievements) {
				campIds.add(String.valueOf(10 * c.id + a.ordinal()));
				if(!this.campKills.containsKey(String.valueOf(10 * c.id + a.ordinal()))) {
					this.campKills.put(String.valueOf(10 * c.id + a.ordinal()), "0");
				}
			}
		}
		
		if(this.raidKills.size() > Main.allRaids.size()) {
			List<String> rIds = new ArrayList<>();
			rIds.addAll(this.raidKills.keySet());
			for(String s : rIds) {
				if(!raidIds.contains(s)) {
					this.raidKills.remove(s);
				}
				if(this.raidKills.size() == Main.allRaids.size()) break;
			}
		}
		
		if(this.campKills.size() > Main.allCamps.size()) {
			List<String> cIds = new ArrayList<>();
			cIds.addAll(this.campKills.keySet());
			for(String s : cIds) {
				if(!campIds.contains(s)) {
					this.campKills.remove(s);
				}
				if(this.campKills.size() == Main.allCamps.size()) break;
			}
		}

		Main.allChars.add(this);
		saveCharacters();
	}
	
	public String toString() {
		String str = this.name + "|";
		
		int i = 0;
		for(Entry<String, String> e : this.raidKills.entrySet()) {
			i++;
			str += "(" + e.getKey() + " " + e.getValue() + ")";
			if(i != this.raidKills.size()) str += ",";
		}
		
		str += "|";
		
		i = 0;
		for(Entry<String, String> e : this.campKills.entrySet()) {
			i++;
			str += "(" + e.getKey() + " " + e.getValue() + ")";
			if(i != this.campKills.size()) str += ",";
		}
		
		return str;
	}
	
	public static void fromString(String str) {
		String[] data = str.split("\\|");
		String[] raids = data[1].replaceAll("\\(|\\)", "").split(",");
		String[] camps = data[2].replaceAll("\\(|\\)", "").split(",");
		
		String name = data[0];
		SortedMap<String, String> rKills = new TreeMap<>((k1, k2) -> Integer.valueOf(k1).compareTo(Integer.valueOf(k2)));
		SortedMap<String, String> cKills = new TreeMap<>((k1, k2) -> Integer.valueOf(k1).compareTo(Integer.valueOf(k2)));
		
		for(String s : raids) {
			String[] pair = s.split(" ");
			rKills.put(pair[0], pair[1]);
		}
		
		for(String s : camps) {
			String[] pair = s.split(" ");
			cKills.put(pair[0], pair[1]);
		}
		
		LOGGER.info("Loaded character " + name);
		
		new Character(name, rKills, cKills);
	}
	
	public static void saveCharacters() {
		File f = new File(FILE);
		try {			
			FileWriter fw = new FileWriter(f, false);
			PrintWriter pw = new PrintWriter(fw, true);
			
			for(Character c : Main.allChars) {
				pw.println(c.toString());
			}
			pw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void loadCharacters() {
		LOGGER.info("************************************************");
		LOGGER.info("LOADING CHARACTERS FROM " + FILE + " FILE.");
		LOGGER.info("************************************************");
		File f = new File(FILE);
		try {
			Scanner scanner = new Scanner(f);
			String str;
			
			while(scanner.hasNextLine()) {
				str = scanner.nextLine();
				LOGGER.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				String[] data = str.split("\\|");
				if(data.length == 3) {
					Character.fromString(str);
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		LOGGER.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}
	
	public static void reloadCharacters() {
		Main.allChars.clear();
		loadCharacters();
	}
	
	public static void deleteCharacters(List<Integer> ids) {
		for(Integer id : ids) {
			Main.allChars.remove(id.intValue());
		}
		saveCharacters();
		reloadCharacters();
	}
}
