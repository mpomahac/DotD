package application;
	
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application{
	
	public static Stage window;
	public static List<Camp> allCamps = new ArrayList<>();
	public static List<Raid> allRaids = new ArrayList<>();
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		Camp.loadCamps();
		Raid.loadRaids();
		Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));
		window = primaryStage;
		window.setTitle("DotD Tools");
		window.setScene(new Scene(root));
		window.show();
	}
}
