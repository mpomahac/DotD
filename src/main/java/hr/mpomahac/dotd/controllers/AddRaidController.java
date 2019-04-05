package hr.mpomahac.dotd.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.mpomahac.dotd.models.Raid;
import hr.mpomahac.dotd.models.Raid.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.paint.Color;

public class AddRaidController implements Initializable {
	
	public static ObservableList<String> types;
	public static ObservableList<String> sizes;
	static {
		types = FXCollections.observableArrayList();
		sizes = FXCollections.observableArrayList();
		for(Type t : Raid.Type.values()) types.add((t.toString().substring(0, 1) + t.toString().substring(1).toLowerCase()).replace("_", " "));
		for(Size s : Raid.Size.values()) sizes.add(s.toString().substring(0, 1) + s.toString().substring(1).toLowerCase());
	}
	
	public Label lNameErr = new Label();
	public Label lSlotsErr = new Label();
	public Label lAPErr = new Label();
	
	public ChoiceBox<String> cbType;
	public ChoiceBox<String> cbSize;

	public TextField txtName;
	public TextField txtSlots;
	public TextField txtHP;
	public TextField txtOS;
	public TextField txtMS;

	public Button btnSubmit;
	public Button btnCancel;
	public Button btnAPTiers;
	
	public SortedMap<String, String> apValues = new TreeMap<>((v1, v2) -> Integer.valueOf(v1).compareTo(Integer.valueOf(v2)));
	
	public void submit() {
		lNameErr.setVisible(false);
		lSlotsErr.setVisible(false);
		lAPErr.setVisible(false);
		boolean valid = true;
		
		String name = txtName.getText();
		Type type = Type.valueOf(cbType.getValue().toUpperCase());
		Size size = Size.valueOf(cbSize.getValue().toUpperCase());
		String slots = null;
		if(txtName.getText().isEmpty() || txtName.getText() == "") {
			valid = false;
			lNameErr.setVisible(true);
			System.out.println("Name error");
		}
		
		if(txtSlots.getText().matches("[0-9]*$") && !txtSlots.getText().isEmpty() && !(txtSlots.getText() == "")) slots = txtSlots.getText();
		else{
			valid = false;
			lSlotsErr.setVisible(true);
			System.out.println("Slots error");
		}
		
		String hp = null;
		if(checkNumberFormat(txtHP.getText()) && !txtHP.getText().isEmpty() && !(txtHP.getText() == "")) {
			hp = convertToNumber(txtHP.getText());
		} else {
			valid = false;
			txtHP.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, null, null)));
			System.out.println("HP error");
		}
		
		String os = null;
		if(checkNumberFormat(txtOS.getText()) && !txtOS.getText().isEmpty() && !(txtOS.getText() == "")) {
			os = convertToNumber(txtOS.getText());
		} else {
			valid = false;
			txtOS.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, null, null)));
			System.out.println("OS error");
		}
		
		String ms = null;
		if(checkNumberFormat(txtMS.getText()) && !txtMS.getText().isEmpty() && !(txtMS.getText() == "")) {
			ms = convertToNumber(txtMS.getText());
		} else {
			valid = false;
			txtMS.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, null, null)));
			System.out.println("MS error");
		}
		
		if(apValues.size() == 0) {
			lAPErr.setVisible(true);
			valid = false;
			System.out.println("AP values error");
		}
		
		
		if(valid) {
			new Raid(name, type, size, slots, hp, os, ms, apValues);
			
			HomeController.window.close();
		}
	}
	
	private boolean checkNumberFormat(String value) {
		if(value.matches("[0-9]*[,.]?[0-9]*(?<![,.])[kmbtqQ]?$")) return true;
		return false;
	}
	
	private String convertToNumber(String value) {
		String val = "";
		String suffix = "";
		
		value = value.replaceAll("\\,", ".");
		
		if(value.matches(".*[kmbtqQ]$")) {
			suffix = value.substring(value.length()-1);
			value = value.replaceAll("[kmbtqQ]", "");
			
			
			int decPlaces = 0;
			if(value.contains(".")) {
				decPlaces = value.length() - value.indexOf(".") - 1;
				value = value.replaceAll("\\.", "");
			}
			val += value;
			int addZeros = 0;
			
			if(suffix.equals("k")) {
				addZeros = 3;
			}
			else if(suffix.equals("m")) {
				addZeros = 6;
			}
			else if(suffix.equals("b")) {
				addZeros = 9;
			}
			else if(suffix.equals("t")) {
				addZeros = 12;
			}
			else if(suffix.equals("q")) {
				addZeros = 15;
			}
			else if(suffix.equals("Q")) {
				addZeros = 18;
			}
			
			addZeros -= decPlaces;
			
			for(int i = 0; i < addZeros; i++) {
				val += "0";
			}
		}
		else {
			if(value.contains(".")) {
				value = value.substring(0, value.indexOf("."));
			}
			val = value;
		}
		
		return val;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cbType.setItems(types);
		cbType.setValue(types.get(0));
		cbSize.setItems(sizes);
		cbSize.setValue(sizes.get(0));
		
		btnAPTiers.setOnAction(e -> {
			try {
				APValuesBoxController.s = cbSize.getValue().toString();
				apValues = APValuesBoxController.display(FXMLLoader.load(new URL("file:///" + Main.RESOURCE_FOLDER_PATH + "/fxml/APValuesBox.fxml")), cbSize.getValue());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		
		btnCancel.setOnAction(e -> HomeController.window.close());
	}

}
