package hr.mpomahac.dotd.controllers;

import hr.mpomahac.dotd.models.Character;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AddCharController {
	
	public Label lNameErr = new Label();
	
	public TextField txtName;
	
	public Button btnSubmit;
	public Button btnCancel;
	
	public void submit() {
		lNameErr.setVisible(false);
		boolean valid = true;
		
		if(txtName.getText().isEmpty() || txtName.getText() == "") {
			valid = false;
			lNameErr.setVisible(true);
			System.out.println("Name error");
		}
		
		if(valid) {
			new Character(txtName.getText());
			
			HomeController.window.close();
		}
	}
	
	public void cancel() {
		HomeController.window.close();
	}

}
