package hr.mpomahac.dotd.controllers;

import java.util.ArrayList;
import java.util.List;

import hr.mpomahac.dotd.models.Camp;
import hr.mpomahac.dotd.models.Camp.*;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AddCampController {
	
	public Label labNameErr = new Label();
	public Label labAchieveErr = new Label();
	
	public TextField txtCampName;
	
	public CheckBox cbComplete;
	public CheckBox cbAnyChallenge;
	public CheckBox cbSpeedRun;
	public CheckBox cbHailstorm;
	public CheckBox cbNerfed;
	public CheckBox cbFatigue;
	public CheckBox cbEndurance;
	public CheckBox cbRareSpawn;
	
	public Button btnSubmit;
	public Button btnCancel;
	
	public void submit() {
		String name = txtCampName.getText();
		List<Achievement> achievements = new ArrayList<>();
		
		if(cbComplete.isSelected()) {
			achievements.add(Achievement.COMPLETE);
		}
		if(cbAnyChallenge.isSelected()) {
			achievements.add(Achievement.ANY_CHALLENGE);
		}
		if(cbSpeedRun.isSelected()) {
			achievements.add(Achievement.SPEED_RUN);
		}
		if(cbHailstorm.isSelected()) {
			achievements.add(Achievement.HAILSTORM);
		}
		if(cbNerfed.isSelected()) {
			achievements.add(Achievement.NERFED);
		}
		if(cbFatigue.isSelected()) {
			achievements.add(Achievement.FATIGUE);
		}
		if(cbEndurance.isSelected()) {
			achievements.add(Achievement.ENDURANCE);
		}
		if(cbRareSpawn.isSelected()) {
			achievements.add(Achievement.RARE_SPAWN);
		}
		
		if(checkValidity(name) && checkValidity(achievements)) {
			labNameErr.setVisible(false);
			labAchieveErr.setVisible(false);
			
			new Camp(name, achievements);
			
			HomeController.window.close();
		}
		else {
			if(!checkValidity(name)) {
				labNameErr.setVisible(true);
			}
			else labNameErr.setVisible(false);
			if(!checkValidity(achievements)) {
				labAchieveErr.setVisible(true);
			}
			else labAchieveErr.setVisible(false);
		}
	}
	
	public void cancel() {
		HomeController.window.close();
	}
	
	private boolean checkValidity(String name) {
		if(!name.isEmpty() && name != null) {
			return true;
		}
		return false;
	}
	
	private boolean checkValidity(List<Achievement> achievements) {
		if(achievements.size() == 0) {
			return false;
		}
		return true;
	}
	
}
