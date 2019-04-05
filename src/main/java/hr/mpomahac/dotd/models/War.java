package hr.mpomahac.dotd.models;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hr.mpomahac.dotd.controllers.Main;

import java.util.SortedMap;
import java.util.TreeMap;

public class War {
	
	private static final Logger LOGGER = LogManager.getLogger("resourceLogger");
	
	public static String FILE;
	public static SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy'T'HH:mm");
	public static boolean isActive = false;
	
	public String name;
	public Date start;
	public Date end;
	public SortedMap<String, String> personalTiers = new TreeMap<>((v1, v2) -> Integer.valueOf(v1).compareTo(Integer.valueOf(v2)));
	public SortedMap<String, String> communityTiers = new TreeMap<>((v1, v2) -> Integer.valueOf(v1).compareTo(Integer.valueOf(v2)));
	
	public War(String name, Date start, Date end, SortedMap<String, String> personalTiers, SortedMap<String, String> communityTiers) {
		this.name = name;
		this.start = start;
		this.end = end;
		this.personalTiers = personalTiers;
		this.communityTiers = communityTiers;
		Main.war = this;
		saveWar();
		isWarActive(start, end);
	}
	
	public String toString() {
		String str = this.name + "|" + df.format(this.start) + "|" + df.format(this.end) + "|";
		int i = 0;
		
		for(Entry<String, String> e : this.personalTiers.entrySet()) {
			i++;
			str += "(" + e.getKey() + " " + e.getValue() + ")";
			if(i != this.personalTiers.size()) str += ",";
		}
		
		str += "|";
		i = 0;
		
		for(Entry<String, String> e : this.communityTiers.entrySet()) {
			i++;
			str += "(" + e.getKey() + " " + e.getValue() + ")";
			if(i != this.communityTiers.size()) str += ",";
		}
		
		return str;
	}
	
	public static void fromString(String str) {
		String[] data = str.split("\\|");
		String[] pTiers = data[3].replaceAll("\\(|\\)", "").split(",");
		String[] cTiers = data[4].replaceAll("\\(|\\)", "").split(",");
		
		String name = data[0];
		Date start = new Date();
		Date end = new Date();
		SortedMap<String, String> personalTiers = new TreeMap<>((v1, v2) -> Integer.valueOf(v1).compareTo(Integer.valueOf(v2)));
		SortedMap<String, String> communityTiers = new TreeMap<>((v1, v2) -> Integer.valueOf(v1).compareTo(Integer.valueOf(v2)));
		
		try {
			start = df.parse(data[1]);
			end = df.parse(data[2]);
		} catch(ParseException e) {
			e.printStackTrace();
		}
		
		for(String s : pTiers) {
			String[] pair = s.split(" ");
			personalTiers.put(pair[0], pair[1]);
		}
		
		for(String s : cTiers) {
			String[] pair = s.split(" ");
			communityTiers.put(pair[0], pair[1]);
		}
		
		LOGGER.info("Loaded war " + name + " with start time at " + start + " and end time at " + end + ".");
		
		new War(name, start, end, personalTiers, communityTiers);
	}
	
	public static void saveWar() {
		File f = new File(FILE);
		try {
			FileWriter fw = new FileWriter(f, false);
			PrintWriter pw = new PrintWriter(fw, true);
			
			pw.println(Main.war.toString());
				
			pw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void loadWar() {
		LOGGER.info("************************************************");
		LOGGER.info("LOADING WARS FROM " + FILE + " FILE.");
		LOGGER.info("************************************************");
		File f = new File(FILE);
		try {
			Scanner scanner = new Scanner(f);
			String str;
			
			while(scanner.hasNextLine()) {
				str = scanner.nextLine();
				if(str.equals("")) {
					break;
				}
				LOGGER.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				War.fromString(str);
			}
			
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		LOGGER.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}
	
	private void isWarActive(Date start, Date end) {
		if(end.before(new Date()) || start.after(new Date())) {
			War.isActive = false;
			LOGGER.info(this.name + " is not active.");
		}
		else {
			War.isActive = true;
			LOGGER.info(this.name + " is active.");
		}
	}

}
