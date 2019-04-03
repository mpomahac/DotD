package hr.mpomahac.dotd.models;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hr.mpomahac.dotd.controllers.Main;

public class Camp {
	
	private static final Logger LOGGER = LogManager.getLogger("resourceLogger");
	
	public static String FILE;
	
	public static final SortedMap<String, String> apTiers;
	static
	{
		apTiers = new TreeMap<>((v1, v2) -> Integer.valueOf(v1).compareTo(Integer.valueOf(v2)));
		apTiers.put("1", "25");
		apTiers.put("5", "50");
		apTiers.put("10", "100");
		apTiers.put("25", "250");
		apTiers.put("50", "500");
		apTiers.put("100", "1000");
		apTiers.put("250", "2000");
	}
			
	public Integer id;
	public String name;
	public List<Achievement> achievements = new ArrayList<>();
	
	public static enum Achievement{
		COMPLETE,
		ANY_CHALLENGE,
		SPEED_RUN,
		HAILSTORM,
		NERFED,
		FATIGUE,
		ENDURANCE,
		RARE_SPAWN
	}
	
	public Camp(String name, List<Achievement> achievements) {
		this.id = Main.allCamps.size() + 1;
		this.name = name;
		this.achievements = achievements;
		Main.allCamps.add(this);
		System.out.println("Camp created! " + this.toString());
		saveCamps();
	}
	
	public Camp(int id, String name, List<Achievement> achievements) {
		this.id = id;
		this.name = name;
		this.achievements = achievements;
		Main.allCamps.add(this);
	}
	
	public String toString() {
		String str = id.toString() + "|" + name + "|";		
		for(int i = 0; i < achievements.size(); i++) {
			str += achievements.get(i).toString();
			if(i < achievements.size() - 1) str += ",";
		}
		return str;
	}
	
	public static void fromString(String str) {
		String[] data = str.split("\\|");
		String[] achieves = data[2].split(",");
		
		int id = Integer.parseInt(data[0]);
		String name = data[1];
		LOGGER.info("Read camp " + name + " with id " + id + ".");
		
		List<Achievement> achievements = new ArrayList<>();
		LOGGER.info("Achievements list:");
		for(String s : achieves) {
			achievements.add(Achievement.valueOf(s));
			LOGGER.info("***" + Achievement.valueOf(s).name() + "***");
		}
		
		new Camp(id, name, achievements);
		
	}
	
	public static void saveCamps() {
		File f = new File(FILE);
		try {
			FileWriter fw = new FileWriter(f, false);
			PrintWriter pw = new PrintWriter(fw, true);
			
			for(Camp c : Main.allCamps) {
				pw.println(c.toString());
			}
			pw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void loadCamps() {
		LOGGER.info("************************************************");
		LOGGER.info("LOADING CAMPS FROM " + FILE + " FILE.");
		LOGGER.info("************************************************");
		File f = new File(FILE);
		try {
			Scanner scanner = new Scanner(f);
			String str;
			
			while(scanner.hasNextLine()) {
				str = scanner.nextLine();
				LOGGER.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				Camp.fromString(str);
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		LOGGER.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}
	
	public static void reloadCamps() {
		Main.allCamps.clear();
		loadCamps();
	}
	
	public static void deleteCamp(List<Integer> ids) {
		
	}
}
