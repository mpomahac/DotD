package application;

import java.math.BigDecimal;
import java.math.RoundingMode;

import application.Camp.Achievement;

public class CampTableEntry {

	private int id;
	private String name;
	private String achievement;
	private String kill;
	private String goal;
	private String left;
	private String ap;
	private BigDecimal value;
	
	public CampTableEntry() {
		this.id = 0;
		this.name = "";
		this.achievement = "";
		this.kill = "";
		this.goal = "";
		this.left = "";
		this.ap = "";
		this.value = new BigDecimal("0.0");
	}
	
	public CampTableEntry(Camp c, Achievement a , Character ch) {
		this.id = c.id * 10 + a.ordinal();
		this.name = c.name;
		this.achievement = (a.toString().substring(0, 1) + a.toString().substring(1).toLowerCase()).replaceAll("_", " ");
		this.kill = ch.campKills.get(String.valueOf(this.id));
		
		for(String s : Camp.apTiers.keySet()) {
			if(Integer.valueOf(this.kill) < Integer.valueOf(s)) {
				this.goal = s;
				this.left = String.valueOf(Integer.valueOf(this.goal) - Integer.valueOf(this.kill));
				this.ap = Camp.apTiers.get(s);
				break;
			}
		}
		
		this.value = new BigDecimal(Double.valueOf(this.ap) / Double.valueOf(this.left)).setScale(2, RoundingMode.CEILING);
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAchievement() {
		return achievement;
	}

	public void setAchievement(String achievement) {
		this.achievement = achievement;
	}

	public String getKill() {
		return kill;
	}

	public void setKill(String kill) {
		this.kill = kill;
	}

	public String getGoal() {
		return goal;
	}

	public void setGoal(String goal) {
		this.goal = goal;
	}

	public String getLeft() {
		return left;
	}

	public void setLeft(String left) {
		this.left = left;
	}

	public String getAp() {
		return ap;
	}

	public void setAp(String ap) {
		this.ap = ap;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}
	
	
	
}
