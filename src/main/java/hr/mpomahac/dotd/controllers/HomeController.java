package hr.mpomahac.dotd.controllers;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import hr.mpomahac.dotd.models.Camp;
import hr.mpomahac.dotd.models.CampTableEntry;
import hr.mpomahac.dotd.models.Character;
import hr.mpomahac.dotd.models.Raid;
import hr.mpomahac.dotd.models.RaidTableEntry;
import hr.mpomahac.dotd.models.War;
import hr.mpomahac.dotd.models.Camp.Achievement;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class HomeController implements Initializable{
	
	public static Stage window = new Stage();
	public static boolean raidsUpdated = false;
	public static boolean threadRun = true;
	public static boolean threadIsRunning = true;
	
	private static ObservableList<String> chars;
	
	static{
		window.initModality(Modality.APPLICATION_MODAL);
		chars = FXCollections.observableArrayList();
		for(Character c : Main.allChars) {
			chars.add(c.name);
		}
	}

	public Label lAP;
	public Label lFS;
	public Label lOS;
	public Label lMS;
	public Label lWarTitle;
	public Label lWarTimer;
	public Label lPersT1;
	public Label lPersT2;
	public Label lPersT3;
	public Label lPersT4;
	public Label lPersT5;
	public Label lPersT6;
	public Label lPersT7;
	public Label lPersT8;
	public Label lPersT9;
	public Label lPersT10;
	public Label lPersTExpec;
	public Label lPersProgress;
	public Label lCommT1;
	public Label lCommT2;
	public Label lCommT3;
	public Label lCommT4;
	public Label lCommTExpec;
	public Label lCommProgress;
	
	public Button btnAddChar;
	public Button btnDeleteChar;
	public Button btnAddRaid;
	public Button btnDeleteRaid;
	public Button btnAddCamp;
	public Button btnDeleteCamp;
	public Button btnStartWar;
	public Button btnSaveAndQuit;
	public Button btnRefreshWar;
	
	public ProgressBar progBarPersContri;
	public ProgressBar progBarCommContri;
	public ProgressBar progBarTimer;
	
	public TextField txtPersContri;
	public TextField txtCommContri;
	
	public ChoiceBox<String> cbCharRaidCamp;
	
	public TableView<RaidTableEntry> tRaids;
	public TableView<CampTableEntry> tCamps;
	
	public TableColumn<RaidTableEntry, Integer> tcRaidId;
	public TableColumn<RaidTableEntry, String> tcRaidName;
	public TableColumn<RaidTableEntry, String> tcRaidKill;
	public TableColumn<RaidTableEntry, String> tcRaidGoal;
	public TableColumn<RaidTableEntry, String> tcRaidLeft;
	public TableColumn<RaidTableEntry, String> tcRaidAP;
	public TableColumn<RaidTableEntry, BigDecimal> tcRaidValue;
	
	public TableColumn<CampTableEntry, Integer> tcCampId;;
	public TableColumn<CampTableEntry, String> tcCampName;
	public TableColumn<CampTableEntry, String> tcCampAchievement;
	public TableColumn<CampTableEntry, String> tcCampKill;
	public TableColumn<CampTableEntry, String> tcCampGoal;
	public TableColumn<CampTableEntry, String> tcCampLeft;
	public TableColumn<CampTableEntry, String> tcCampAP;
	public TableColumn<CampTableEntry, BigDecimal> tcCampValue;

	public Character chara = null;
	
	private Thread tRaidCampRefresh = new Thread() {
		public void run() {
			while(threadRun) {
				if(raidsUpdated) {
					System.out.println("Reloading and refreshing");
					Character.reloadCharacters();
					tRaids.setItems(getRaids());
					tRaids.refresh();
					tCamps.setItems(getCamps());
					tCamps.refresh();
					System.out.println("Raid info update complete");
					raidsUpdated = false;
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {}
				}
				else {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {}
				}
			}
			threadIsRunning = false;
			System.out.println("Raid and camp refresh thread stopped");
			tWarRefresh.interrupt();
		}
	};
	
	private Thread tWarRefresh = new Thread() {
		public void run() {
			while(War.isActive && threadRun) {
				System.out.println("Refreshing war...");
				
				Platform.runLater(new Runnable() {
					public void run() {
						refreshWarStats();
					}
				});
				
				try {
					Thread.sleep(20000);
				} catch (InterruptedException e) {}
			}
			threadIsRunning = false;
			System.out.println("War refresh thread stopped");
		}
	};
	
	
	public void refreshWarStats() {
		int timeDiff = (int) TimeUnit.MINUTES.convert(Main.war.end.getTime() - new Date().getTime(), TimeUnit.MILLISECONDS);
		int hr = timeDiff / 60;
		int min = timeDiff % 60;
		
		progBarTimer.setProgress((double) timeDiff - (double) (TimeUnit.MINUTES.convert(Main.war.end.getTime() - Main.war.start.getTime(), TimeUnit.MILLISECONDS)));
		
		lWarTimer.setText("Time left: " + String.valueOf(hr) + ":" + String.valueOf(min) + "h");
		if(txtPersContri.getText().isEmpty() || txtPersContri.getText() == null) {
			txtPersContri.setText("Time left: ---");
		}
		
		int contri;
		
		List<Label> lPersTiers = new ArrayList<>();
		lPersTiers.add(lPersT1);
		lPersTiers.add(lPersT2);
		lPersTiers.add(lPersT3);
		lPersTiers.add(lPersT4);
		lPersTiers.add(lPersT5);
		lPersTiers.add(lPersT6);
		lPersTiers.add(lPersT7);
		lPersTiers.add(lPersT8);
		lPersTiers.add(lPersT9);
		lPersTiers.add(lPersT10);
		
		List<Label> lCommTiers = new ArrayList<>();
		lCommTiers.add(lCommT1);
		lCommTiers.add(lCommT2);
		lCommTiers.add(lCommT3);
		lCommTiers.add(lCommT4);
		
		timeDiff = (int) TimeUnit.MINUTES.convert(new Date().getTime() - Main.war.start.getTime(), TimeUnit.MILLISECONDS);
		int totalWarTime = (int) TimeUnit.MINUTES.convert(Main.war.end.getTime() - Main.war.start.getTime(), TimeUnit.MILLISECONDS);
		double timePercent = (double) timeDiff / (double) totalWarTime;
		
		try {
			contri = Integer.parseInt(txtPersContri.getText());
			
			lPersProgress.setText(contri + " / " + Main.war.personalTiers.get(String.valueOf("1")));
			progBarPersContri.setProgress((double) contri / Double.valueOf(Main.war.personalTiers.get(String.valueOf("1"))));
			
			for(int i = 0; i < Main.war.personalTiers.size(); i++) {
				if(contri >= Integer.valueOf(Main.war.personalTiers.get(String.valueOf(i + 1)))) {
					lPersTiers.get(i).setText("T" + (i+1) + ": DONE");
					lPersTExpec.setText("Expected tier: T" + (i+1));
					if(contri < Integer.valueOf(Main.war.personalTiers.get(String.valueOf(i + 2)))) {
						progBarPersContri.setProgress((double) contri / Double.valueOf(Main.war.personalTiers.get(String.valueOf((i+2)))));
						lPersProgress.setText(contri + " / " + Main.war.personalTiers.get(String.valueOf((i+2))));
					}
				}
				else {
					lPersTiers.get(i).setText("T" + (i+1) + ": " + String.valueOf((contri - Integer.valueOf(Main.war.personalTiers.get(String.valueOf((i + 1)))) * timePercent)));
					if(contri - Integer.valueOf(Main.war.personalTiers.get(String.valueOf((i + 1)))) * timePercent >= 0) {
						lPersTExpec.setText("Expected tier: T" + (i+1));
					}
				}
			}
			
		} catch(NumberFormatException e) {
			e.printStackTrace();
		}
		
		try {
			contri = Integer.parseInt(txtCommContri.getText());
			
			lCommProgress.setText(contri + " / " + Main.war.communityTiers.get(String.valueOf("1")));
			progBarCommContri.setProgress((double) contri / Double.valueOf(Main.war.communityTiers.get(String.valueOf("1"))));
			
			for(int i = 0; i < Main.war.communityTiers.size(); i++) {
				if(contri >= Integer.valueOf(Main.war.communityTiers.get(String.valueOf(i + 1)))) {
					lCommTiers.get(i).setText("T" + (i+1) + ": DONE");
					lCommTExpec.setText("Expected tier: T" + (i+1));
					if(contri < Integer.valueOf(Main.war.communityTiers.get(String.valueOf(i + 2)))) {
						progBarCommContri.setProgress((double) contri / Double.valueOf(Main.war.communityTiers.get(String.valueOf((i+2)))));
						lCommProgress.setText(contri + " / " + Main.war.communityTiers.get(String.valueOf((i+2))));
					}
				}
				else {
					lCommTiers.get(i).setText("T" + (i+1) + ": " + String.valueOf((contri - Integer.valueOf(Main.war.communityTiers.get(String.valueOf((i + 1)))) * timePercent)));
					if(contri - Integer.valueOf(Main.war.communityTiers.get(String.valueOf((i + 1)))) * timePercent >= 0) {
						lCommTExpec.setText("Expected tier: T" + (i+1));
					}
				}
			}
			
		} catch(NumberFormatException e) {
			e.printStackTrace();
		}
		
	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
				
		txtPersContri.setText("0");
		txtCommContri.setText("0");
		
		tRaidCampRefresh.start();
		tWarRefresh.start();
		
		lAP.setVisible(false);
		lFS.setVisible(false);
		lOS.setVisible(false);
		lMS.setVisible(false);
		
		cbCharRaidCamp.setItems(chars);
		cbCharRaidCamp.setValue(chars.get(0));
		
		tcRaidId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tcRaidName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tcRaidKill.setCellValueFactory(new PropertyValueFactory<>("killed"));
		tcRaidGoal.setCellValueFactory(new PropertyValueFactory<>("goal"));
		tcRaidLeft.setCellValueFactory(new PropertyValueFactory<>("left"));
		tcRaidAP.setCellValueFactory(new PropertyValueFactory<>("ap"));
		tcRaidValue.setCellValueFactory(new PropertyValueFactory<>("value"));
		
		tRaids.setItems(getRaids());
		
		tcCampId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tcCampName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tcCampAchievement.setCellValueFactory(new PropertyValueFactory<>("achievement"));
		tcCampKill.setCellValueFactory(new PropertyValueFactory<>("kill"));
		tcCampGoal.setCellValueFactory(new PropertyValueFactory<>("goal"));
		tcCampLeft.setCellValueFactory(new PropertyValueFactory<>("left"));
		tcCampAP.setCellValueFactory(new PropertyValueFactory<>("ap"));
		tcCampValue.setCellValueFactory(new PropertyValueFactory<>("value"));
		
		tCamps.setItems(getCamps());
		
		tRaids.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) ->{
			if(newValue != null) {
				Raid raid = null;
				for(Raid r : Main.allRaids) {
					if(r.id.equals(newValue.getId())) {
						raid = r;
					}
				}
				lAP.setText("AP: " + raid.shortValue(raid.ap));
				lAP.setVisible(true);
				lFS.setText("FS: " + raid.shortValue(raid.fs));
				lFS.setVisible(true);
				lOS.setText("OS: " + raid.shortValue(raid.os));
				lOS.setVisible(true);
				lMS.setText("MS: " + raid.shortValue(raid.ms));
				lMS.setVisible(true);
			}
		});
		
		tRaids.setOnKeyPressed(key -> {
			final RaidTableEntry item = tRaids.getSelectionModel().getSelectedItem();
			if(item != null) {
				int kills = Integer.valueOf(item.getKilled());
				if(key.getCode().equals(KeyCode.ADD)) {
					kills++;
					item.setKilled(String.valueOf(kills));
					chara.raidKills.put(String.valueOf(item.getId()), String.valueOf(kills));
					checkTiers(chara, item);
				}
				else if(key.getCode().equals(KeyCode.SUBTRACT)) {
					kills--;
					if(kills < 0) {
						kills = 0;
					}
					item.setKilled(String.valueOf(kills));
					chara.raidKills.put(String.valueOf(item.getId()), String.valueOf(kills));
					checkTiers(chara, item);
				}
			}
			tRaids.refresh();
			Character.saveCharacters();
		});
		
		tCamps.setRowFactory(tv -> {
			TableRow<CampTableEntry> tr = new TableRow<>();
			tr.setOnMouseClicked(e -> {
				if(e.getClickCount() == 1) {
					lAP.setText("");
					lAP.setVisible(false);
					lFS.setText("");
					lFS.setVisible(false);
					lOS.setText("");
					lOS.setVisible(false);
					lMS.setText("");
					lMS.setVisible(false);
				}
			});
			return tr;
		});
		
		tCamps.setOnKeyPressed(key -> {
			final CampTableEntry item = tCamps.getSelectionModel().getSelectedItem();
			if(item != null) {
				int kills = Integer.valueOf(item.getKill());
				if(key.getCode().equals(KeyCode.ADD)) {
					kills++;
					item.setKill(String.valueOf(kills));
					chara.campKills.put(String.valueOf(item.getId()), String.valueOf(kills));
					checkTiers(chara, item);
				}
				else if(key.getCode().equals(KeyCode.SUBTRACT)) {
					kills--;
					if(kills < 0) {
						kills = 0;
					}
					item.setKill(String.valueOf(kills));
					chara.campKills.put(String.valueOf(item.getId()), String.valueOf(kills));
					checkTiers(chara, item);
				}
			}
			tCamps.refresh();
			Character.saveCharacters();
		});
	}
	
	private void checkTiers(Character c, RaidTableEntry item) {
		Raid r = null;
		for(Raid r1 : Main.allRaids) {
			if(r1.id == item.getId()) {
				r = r1;
				break;
			}
		}
		
		int kills = Integer.valueOf(c.raidKills.get(String.valueOf(item.getId())));
		
		if(kills >= Integer.valueOf(r.apValues.lastKey())) {
			item.setGoal("DONE");
			item.setAp("DONE");
			item.setLeft("DONE");
			item.setValue(new BigDecimal(0));
		}
		else {
			for(String s : r.apValues.keySet()) {
				if(kills < Integer.valueOf(s)) {
					item.setGoal(s);
					item.setAp(r.apValues.get(s));
					item.setLeft(String.valueOf(Integer.valueOf(item.getGoal()) - Integer.valueOf(item.getKilled())));
					item.setValue(new BigDecimal(Double.valueOf(item.getAp()) / Double.valueOf(item.getLeft())).setScale(2, RoundingMode.CEILING));
					break;
				}
			}
		}
	}
	
	private void checkTiers(Character ch, CampTableEntry item) {
		Camp c = null;
		for(Camp c1 : Main.allCamps) {
			if(c1.id == item.getId()) {
				c = c1;
				break;
			}
		}
		
		int kills = Integer.valueOf(ch.campKills.get(String.valueOf(item.getId())));
		
		if(kills >= Integer.valueOf(Camp.apTiers.lastKey())) {
			item.setGoal("DONE");
			item.setAp("DONE");
			item.setLeft("DONE");
			item.setValue(new BigDecimal(0));
		}
		else {
			for(String s : Camp.apTiers.keySet()) {
				if(kills < Integer.valueOf(s)) {
					item.setGoal(s);
					item.setAp(Camp.apTiers.get(s));
					item.setLeft(String.valueOf(Integer.valueOf(item.getGoal()) - Integer.valueOf(item.getKill())));
					item.setValue(new BigDecimal(Double.valueOf(item.getAp()) / Double.valueOf(item.getLeft())).setScale(2, RoundingMode.CEILING));
					break;
				}
			}
		}
	}
	
	public ObservableList<RaidTableEntry> getRaids(){
		ObservableList<RaidTableEntry> raids = FXCollections.observableArrayList();
		
		for(Character c : Main.allChars) {
			if(c.name.equals(cbCharRaidCamp.getValue().toString())) {
				chara = c;
				break;
			}
		}
		
		for(Raid r : Main.allRaids) {
			raids.add(new RaidTableEntry(r, chara));
		}
		
		return raids;
	}
	
	public ObservableList<CampTableEntry> getCamps(){
		ObservableList<CampTableEntry> camps = FXCollections.observableArrayList();
		
		for(Character c : Main.allChars) {
			if(c.name.equals(cbCharRaidCamp.getValue().toString())) {
				chara = c;
				break;
			}
		}
		
		for(Camp c : Main.allCamps) {
			for(Achievement a : c.achievements) {
				camps.add(new CampTableEntry(c, a, chara));
			}
		}
		
		return camps;
	}
	
	public void addChar() {
		try {
			Parent root = FXMLLoader.load(new URL("file:///" + Main.RESOURCE_FOLDER_PATH + "/fxml/AddChar.fxml"));
			window.setTitle("Add character");
			window.setScene(new Scene(root));
			window.show();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteChar() {
		try {
			Parent root = FXMLLoader.load(new URL("file:///" + Main.RESOURCE_FOLDER_PATH + "/fxml/DeleteChar.fxml"));
			window.setTitle("Delete character");
			window.setScene(new Scene(root));
			window.show();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addRaid() {
		try {
			Parent root = FXMLLoader.load(new URL("file:///" + Main.RESOURCE_FOLDER_PATH + "/fxml/AddRaid.fxml"));
			window.setTitle("Add raid");
			window.setScene(new Scene(root));
			window.show();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteRaid() {
		try {
			Parent root = FXMLLoader.load(new URL("file:///" + Main.RESOURCE_FOLDER_PATH + "/fxml/DeleteRaid.fxml"));
			window.setTitle("Delete raid");
			window.setScene(new Scene(root));
			window.show();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addCamp() {
		try {
			Parent root = FXMLLoader.load(new URL("file:///" + Main.RESOURCE_FOLDER_PATH + "/fxml/AddCamp.fxml"));
			window.setTitle("Add campaign");
			window.setScene(new Scene(root));
			window.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteCamp() {
		try {
			Parent root = FXMLLoader.load(new URL("file:///" + Main.RESOURCE_FOLDER_PATH + "/fxml/DeleteCamp.fxml"));
			window.setTitle("Delete campaign");
			window.setScene(new Scene(root));
			window.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void startWar() {
		try {
			Parent root = FXMLLoader.load(new URL("file:///" + Main.RESOURCE_FOLDER_PATH + "/fxml/StartWar.fxml"));
			window.setTitle("Start war");
			window.setScene(new Scene(root));
			window.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void saveAndQuit() {
		if(ConfirmBox.display("Are you sure?", "Are you sure you want to save and quit?")) {
			threadRun = false;
			tWarRefresh.interrupt();
			Main.window.close();
		}
	}
	
	public void closeProgram() {
		threadRun = false;
		tWarRefresh.interrupt();
		Main.window.close();
	}
	
	public static void setWindowOnCloseRequest() {
		Main.window.setOnCloseRequest(e -> {
			e.consume();
			boolean result = ConfirmBox.display("Close program?", "Are you sure you want to close the program?");
			if(result) {
				threadRun = false;
				Main.window.close();
			}
		});
	}
	
}
