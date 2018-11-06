package application;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class RaidTableEntry {

	private int id;
	private String name;
	private String killed;
	private String goal;
	private String left;
	private String ap;
	private BigDecimal value;
	
	public RaidTableEntry() {
		this.id = 0;
		this.name = "";
		this.killed = "0";
		this.goal = "0";
		this.left = "0";
		this.ap = "0";
		this.value = new BigDecimal("0.0");
	}
	
	public RaidTableEntry(Raid r, Character c) {
		this.id = r.id;
		this.name = r.name;
		
		this.killed = c.raidKills.get(r.id.toString());
		if(this.killed == null) {
			this.killed = "0";
		}
		
		for(String s : r.apValues.keySet()) {
			if(Integer.valueOf(this.killed) < Integer.valueOf(s)) {
				this.goal = s;
				this.ap = r.apValues.get(s);
				break;
			}
		}
		
		this.left = String.valueOf(Integer.valueOf(this.goal) - Integer.valueOf(this.killed));
		
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

	public String getKilled() {
		return killed;
	}

	public void setKilled(String killed) {
		this.killed = killed;
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

	public void setLeft(String remain) {
		this.left = remain;
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
