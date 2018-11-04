package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
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
			"2500"
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
		"2000"
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
	
	static Map<String, String> apValues = new HashMap<>();
	
	static List<ChoiceBox<String>> cbAp = new ArrayList<>();
	static List<ChoiceBox<String>> cbKill = new ArrayList<>();
	
	static Stage window = new Stage();
	
	public static Map<String, String> display(Parent root, String size) {
		window.setTitle("Raid AP Values");
		window.setScene(new Scene(root));
		window.initModality(Modality.APPLICATION_MODAL);
		window.showAndWait();
		
		return apValues;
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
		
		for(int i = 0; i < 7; i++) {
			if(i == 6) {
				apTiers.add("N/A");
				killValues.add("N/A");
			}
			ChoiceBox<String> cb = new ChoiceBox<>();
			cb = cbAp.get(i);
			cb.setItems(apTiers);
			cb.setValue(apTiers.get(0));
			cb = cbKill.get(i);
			cb.setItems(killValues);
			cb.setValue(killValues.get(0));
		}
		
		btnSubmit.setOnAction(e -> {
			if(cbAp.get(6).getValue().equals("N/A") || cbKill.get(6).getValue().equals("N/A")) {
				for(int i = 0; i < 6; i++) {
					apValues.put(cbKill.get(i).getValue(), cbAp.get(i).getValue());
				}
			}
			else {
				for(int i = 0; i < 7; i++) {
					apValues.put(cbKill.get(i).getValue(), cbAp.get(i).getValue());
				}
			}
			window.close();
		});
		
		btnCancel.setOnAction(e -> window.close());
	}
	
}
