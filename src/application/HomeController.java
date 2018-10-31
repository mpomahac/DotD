package application;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class HomeController {

	public Button btnAddChar;
	public Button btnDeleteChar;
	public Button btnAddRaid;
	public Button btnDeleteRaid;
	public Button btnAddCamp;
	public Button btnDeleteCamp;
	public Button btnStartWar;
	public Button btnSaveAndQuit;
	
	public Menu menuHome;
	public Menu menuRaidCamp;
	public Menu menuWar;
	public Menu menuLandCalc;
	public Menu menuLogFilter;
	
	public static Stage window = new Stage();
	static{
		window.initModality(Modality.APPLICATION_MODAL);
	}
	
	public void addCamp() {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("AddCamp.fxml"));
			window.setTitle("Add campaign");
			window.setScene(new Scene(root));
			window.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void saveAndQuit() {
		if(ConfirmBox.display("Are you sure?", "Are you sure you want to save and quit?")) {
			//Save data
			
			
			//Close
			Main.window.close();
		}
	}
	
}
