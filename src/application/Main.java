package application;
	
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Main extends Application{
	
	Button btn1, btn2, btn3, btn4, closeBtn;
	Scene scene1, scene2, scene3;
	Stage window;
	ComboBox<String> comboBox;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		window = primaryStage;
		window.setTitle("DotD Tools");
		
		//Dropdown
		ChoiceBox<String> choiceBox = new ChoiceBox<>();
		choiceBox.getItems().addAll("Small", "Medium", "Large", "Epic", "Colossal", "Gigantic");
		choiceBox.setValue(choiceBox.getItems().get(0));
		
		//Dropdown Listener for change
		choiceBox.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> System.out.println(oldValue + ", " + newValue));
		
		//ComboBox
		comboBox = new ComboBox<>();
		comboBox.getItems().addAll(
					"Small",
					"Medium",
					"Large",
					"Epic",
					"Colossal",
					"Gigantic"
				);
		comboBox.setPromptText("---Raid size---");
		
		Label label1 = new Label("Welcome to the first scene!");
		btn1 = new Button("Go to next scene");
		btn1.setOnAction(e -> {
			boolean result = ConfirmBox.display("Are you sure?", "Move to next scene?");
			if(result) {
				window.setScene(scene2);
			}
			else {
				getChoice(choiceBox);
			}
		});
		
		closeBtn = new Button("Close");
		closeBtn.setOnAction(e -> {
			boolean result = ConfirmBox.display("Close program?", "Are you sure you want to close the program?");
			if(result) {
				closeProgram();
			}
		});
		
		btn2 = new Button("Go to previous scene");
		btn2.setOnAction(e -> window.setScene(scene1));
		
		btn3 = new Button("Add new raid");
		btn3.setOnAction(e -> AlertBox.display("New raid", "In creation..."));
		
		btn4 = new Button("Next scene");
		btn4.setOnAction(e -> window.setScene(scene3));
		
		//Layout 1 (Embedded layouts)
		HBox topMenu = new HBox();
		topMenu.getChildren().addAll(label1, btn1);
		
		VBox leftMenu = new VBox();
		leftMenu.getChildren().addAll(closeBtn, choiceBox, comboBox);
		
		BorderPane layout1 = new BorderPane();
		layout1.setTop(topMenu);
		layout1.setLeft(leftMenu);
		
		scene1 = new Scene(layout1, 600, 400);
		
		
		//Layout 2
		VBox layout2 = new VBox(50);
		layout2.getChildren().addAll(btn2, btn3, btn4);
		layout2.setAlignment(Pos.CENTER);
		scene2 = new Scene(layout2, 600, 400);
		
		//Layout 3
		GridPane layout3 = new GridPane();
		//Set padding from window border
		layout3.setPadding(new Insets(10, 10, 10, 10));
		//Set padding between cells
		layout3.setVgap(8);
		layout3.setHgap(10);
		/********************Inputs********************/
					//Name label and input//
		Label lName = new Label("Raid name:");
		GridPane.setConstraints(lName, 0, 0);
		TextField iName = new TextField();
		iName.setPromptText("Name of the raid");
		GridPane.setConstraints(iName, 1, 0);
					//Size label and input//
		Label lSize = new Label("Raid size:");
		GridPane.setConstraints(lSize, 0, 1);
		TextField iSize = new TextField();
		iSize.setPromptText("Size of the raid (1-Small, 6-Gigantic");
		GridPane.setConstraints(iSize, 1, 1);
					//Submit button//
		Button submitBtn = new Button("Submit");
		GridPane.setConstraints(submitBtn, 1, 2);
		
		submitBtn.setOnAction(e -> {
			if(isInt(iSize, iSize.getText())) {
				System.out.println("Name: " + iName.getText() + "\nSize: " + iSize.getText());
				window.setScene(scene2);
			}
		});
		
		layout3.getChildren().addAll(lName, iName, lSize, iSize, submitBtn);		
		scene3 = new Scene(layout3, 600, 400);
		
		window.setScene(scene1);
		window.show();
		window.setOnCloseRequest(e -> {
			e.consume();
			boolean result = ConfirmBox.display("Close program?", "Are you sure you want to close the program?");
			if(result) {
				closeProgramByX();
			}
		});
	}
	
	private void closeProgram() {
		System.out.println("Program closed by button");
		window.close();
	}
	
	private void closeProgramByX() {
		System.out.println("Program closed by X button");
		window.close();
	}
	
	private boolean isInt(TextField input, String message) {
		try {
			Integer.parseInt(input.getText());
			return true;
		}catch(NumberFormatException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private void getChoice(ChoiceBox choiceBox) {
		System.out.println(choiceBox.getValue());
	}
	
}
