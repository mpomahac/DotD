package hr.mpomahac.dotd.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.SortedMap;
import java.util.TreeMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class APValuesBoxController implements Initializable {
	
	private static String[] kills = {
			"5",
			"10",
			"25",
			"50",
			"100",
			"125",
			"250",
			"500",
			"1000",
			"2000",
			"2500",
			"5000",
			"N/A"
	};
	
	private static String[] aps = {
		"5",
		"10",
		"25",
		"50",
		"100",
		"250",
		"500",
		"1000",
		"2000",
		"N/A"
	};
	
	private static ObservableList<String> killValues;
	private static ObservableList<String> apTiers;
	static {
		killValues = FXCollections.observableArrayList();
		apTiers = FXCollections.observableArrayList();
		killValues.addAll(kills);
		apTiers.addAll(aps);
	}
	
	public ChoiceBox<String> cbApTier1;
	public ChoiceBox<String> cbApTier2;
	public ChoiceBox<String> cbApTier3;
	public ChoiceBox<String> cbApTier4;
	public ChoiceBox<String> cbApTier5;
	public ChoiceBox<String> cbApTier6;
	public ChoiceBox<String> cbApTier7;
	public ChoiceBox<String> cbKillTier1;
	public ChoiceBox<String> cbKillTier2;
	public ChoiceBox<String> cbKillTier3;
	public ChoiceBox<String> cbKillTier4;
	public ChoiceBox<String> cbKillTier5;
	public ChoiceBox<String> cbKillTier6;
	public ChoiceBox<String> cbKillTier7;
	
	public Button btnSubmit;
	public Button btnCancel;
	
	static SortedMap<String, String> apValues = new TreeMap<>((v1, v2) -> Integer.valueOf(v1).compareTo(Integer.valueOf(v2)));
	
	List<ChoiceBox<String>> cbAp = new ArrayList<>();
	List<ChoiceBox<String>> cbKill = new ArrayList<>();
	
	public static String s = "";
	
	static Stage window = new Stage();
	static {
		window.initModality(Modality.APPLICATION_MODAL);
	}
	
	public static SortedMap<String, String> display(Parent root, String size) {
		window.setTitle("Raid AP Values");
		window.setScene(new Scene(root));
		window.showAndWait();
		
		SortedMap<String, String> apVals = new TreeMap<>((v1, v2) -> Integer.valueOf(v1).compareTo(Integer.valueOf(v2)));
		apVals.putAll(apValues);
		
		return apVals;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		cbAp.add(cbApTier1);
		cbAp.add(cbApTier2);
		cbAp.add(cbApTier3);
		cbAp.add(cbApTier4);
		cbAp.add(cbApTier5);
		cbAp.add(cbApTier6);
		cbAp.add(cbApTier7);
		
		cbKill.add(cbKillTier1);
		cbKill.add(cbKillTier2);
		cbKill.add(cbKillTier3);
		cbKill.add(cbKillTier4);
		cbKill.add(cbKillTier5);
		cbKill.add(cbKillTier6);
		cbKill.add(cbKillTier7);
		
		apValues.clear();
		
		for(int i = 0; i < 7; i++) {
			ChoiceBox<String> cb = new ChoiceBox<>();
			cb = cbAp.get(i);
			cb.setItems(apTiers);
			cb.setValue(apTiers.get(i));
			cb = cbKill.get(i);
			cb.setItems(killValues);
			cb.setValue(killValues.get(i));
		}
		
		cbAp.get(0).getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
			int newIndex = apTiers.indexOf(newValue) + 1;
			if(newIndex > apTiers.size() - 1) newIndex = apTiers.size() - 1;
			cbAp.get(1).setValue(apTiers.get(newIndex));
		});
		cbAp.get(1).getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
			int newIndex = apTiers.indexOf(newValue) + 1;
			if(newIndex > apTiers.size() - 1) newIndex = apTiers.size() - 1;
			cbAp.get(2).setValue(apTiers.get(newIndex));
		});
		cbAp.get(2).getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
			int newIndex = apTiers.indexOf(newValue) + 1;
			if(newIndex > apTiers.size() - 1) newIndex = apTiers.size() - 1;
			cbAp.get(3).setValue(apTiers.get(newIndex));
		});
		cbAp.get(3).getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
			int newIndex = apTiers.indexOf(newValue) + 1;
			if(newIndex > apTiers.size() - 1) newIndex = apTiers.size() - 1;
			cbAp.get(4).setValue(apTiers.get(newIndex));
		});
		cbAp.get(4).getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
			int newIndex = apTiers.indexOf(newValue) + 1;
			if(newIndex > apTiers.size() - 1) newIndex = apTiers.size() - 1;
			cbAp.get(5).setValue(apTiers.get(newIndex));
		});
		cbAp.get(5).getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
			int newIndex = apTiers.indexOf(newValue) + 1;
			if(newIndex > apTiers.size() - 1) newIndex = apTiers.size() - 1;
			cbAp.get(6).setValue(apTiers.get(newIndex));
		});
		
		cbKill.get(0).getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
			int newIndex = killValues.indexOf(newValue) + 1;
			if(newIndex > killValues.size() - 1) newIndex = killValues.size() - 1;
			cbKill.get(1).setValue(killValues.get(newIndex));
		});
		cbKill.get(1).getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
			int newIndex = killValues.indexOf(newValue) + 1;
			if(newIndex > killValues.size() - 1) newIndex = killValues.size() - 1;
			cbKill.get(2).setValue(killValues.get(newIndex));
		});
		cbKill.get(2).getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
			int newIndex = killValues.indexOf(newValue) + 1;
			if(newIndex > killValues.size() - 1) newIndex = killValues.size() - 1;
			cbKill.get(3).setValue(killValues.get(newIndex));
		});
		cbKill.get(3).getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
			int newIndex = killValues.indexOf(newValue) + 1;
			if(newIndex > killValues.size() - 1) newIndex = killValues.size() - 1;
			cbKill.get(4).setValue(killValues.get(newIndex));
		});
		cbKill.get(4).getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
			int newIndex = killValues.indexOf(newValue) + 1;
			if(newIndex > killValues.size() - 1) newIndex = killValues.size() - 1;
			cbKill.get(5).setValue(killValues.get(newIndex));
		});
		cbKill.get(5).getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
			int newIndex = killValues.indexOf(newValue) + 1;
			if(newIndex > killValues.size() - 1) newIndex = killValues.size() - 1;
			cbKill.get(6).setValue(killValues.get(newIndex));
		});
		
		if(s.equals("Small")) {
			cbKill.get(0).setValue(killValues.get(1));
			cbKill.get(1).setValue(killValues.get(3));
			cbKill.get(2).setValue(killValues.get(4));
			cbKill.get(3).setValue(killValues.get(6));
			cbKill.get(4).setValue(killValues.get(7));
			cbKill.get(5).setValue(killValues.get(8));
			cbKill.get(6).setValue(killValues.get(11));
			
			cbAp.get(0).setValue(apTiers.get(1));
			cbAp.get(1).setValue(apTiers.get(2));
			cbAp.get(2).setValue(apTiers.get(3));
			cbAp.get(3).setValue(apTiers.get(4));
			cbAp.get(4).setValue(apTiers.get(5));
			cbAp.get(5).setValue(apTiers.get(6));
			cbAp.get(6).setValue(apTiers.get(7));
		}
		else if(s.equals("Medium")) {
			cbKill.get(0).setValue(killValues.get(1));
			cbKill.get(1).setValue(killValues.get(3));
			cbKill.get(2).setValue(killValues.get(4));
			cbKill.get(3).setValue(killValues.get(6));
			cbKill.get(4).setValue(killValues.get(7));
			cbKill.get(5).setValue(killValues.get(8));
			cbKill.get(6).setValue(killValues.get(11));
			
			cbAp.get(0).setValue(apTiers.get(2));
			cbAp.get(1).setValue(apTiers.get(3));
			cbAp.get(2).setValue(apTiers.get(4));
			cbAp.get(3).setValue(apTiers.get(5));
			cbAp.get(4).setValue(apTiers.get(6));
			cbAp.get(5).setValue(apTiers.get(7));
			cbAp.get(6).setValue(apTiers.get(8));
		}
		else {
			cbKill.get(0).setValue(killValues.get(1));
			cbKill.get(1).setValue(killValues.get(3));
			cbKill.get(2).setValue(killValues.get(4));
			cbKill.get(3).setValue(killValues.get(6));
			cbKill.get(4).setValue(killValues.get(7));
			cbKill.get(5).setValue(killValues.get(8));
			cbKill.get(6).setValue(killValues.get(11));
			
			cbAp.get(0).setValue(apTiers.get(3));
			cbAp.get(1).setValue(apTiers.get(4));
			cbAp.get(2).setValue(apTiers.get(5));
			cbAp.get(3).setValue(apTiers.get(6));
			cbAp.get(4).setValue(apTiers.get(7));
			cbAp.get(5).setValue(apTiers.get(8));
			cbAp.get(6).setValue(apTiers.get(8));
		}
		
		btnSubmit.setOnAction(e -> {
			
			int i = 0;
			while(i < 7) {
				if(cbAp.get(i).getValue().equals("N/A") || cbKill.get(i).getValue().equals("N/A")) break;
				apValues.put(cbKill.get(i).getValue(), cbAp.get(i).getValue());
				i++;
			}
			window.close();
		});
		
		btnCancel.setOnAction(e -> window.close());
	}
	
}
