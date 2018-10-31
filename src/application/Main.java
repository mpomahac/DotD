package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application{
	
	public static Stage window;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		Camp.loadCamps();
		Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));
		window = primaryStage;
		window.setTitle("DotD Tools");
		window.setScene(new Scene(root));
		window.show();
	}
}
