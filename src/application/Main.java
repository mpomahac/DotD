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
	public static List<Character> allChars = new ArrayList<>();
	public static War war;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		/*
		//TESTS
		BigDecimal test = new BigDecimal("1020.3523");
		System.out.println(test.toString());
		
		if(test.compareTo(Raid.numbers.get("k")) == -1) System.out.println("Number is smaller that 1000");
		else {
			System.out.println("Number shorthand is " + test.divide(Raid.numbers.get("k"), 2, RoundingMode.CEILING) + "k");
		}
		
		String[] tests = {
				"123",
				"123.",
				"1234,56",
				"1234.56",
				"1234,56k",
				"1234.56k",
				"1234,56bb",
				"1234..b",
				"1234.t",
				"1234.123,4b",
				"123b",
				"1234bt",
				"1234.56.k"
		};
		
		for(String str : tests) {
			if(str.matches("[0-9]*[,.]?[0-9]*(?<![,.])[kmbtqQ]?$")) System.out.println(str + " matches!");
			else System.out.println(str + " doesn't match!");
		}
		
		if("123".matches("^[0-9]*$")) System.out.println("OK");
		
		String value = "400k";
		
		System.out.println(value.replaceAll("[kmbtqQ]", ""));
		
		*/
		
		
		//TESTS END
		
		long start = System.currentTimeMillis();
		Camp.loadCamps();
		System.out.println("Camp loading time: " + (System.currentTimeMillis() - start) + "ms");
		start = System.currentTimeMillis();
		Raid.loadRaids();
		System.out.println("Raid loading time: " + (System.currentTimeMillis() - start) + "ms");
		start = System.currentTimeMillis();
		Character.loadCharacters();
		System.out.println("Character loading time: " + (System.currentTimeMillis() - start) + "ms");
		start = System.currentTimeMillis();
		War.loadWar();
		System.out.println("War loading time: " + (System.currentTimeMillis() - start) + "ms");
		
		
		start = System.currentTimeMillis();
		Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));
		window = primaryStage;
		window.setTitle("DotD Tools");
		window.setScene(new Scene(root));
		window.show();
		System.out.println("Main window loading time: " + (System.currentTimeMillis() - start) + "ms");
	}
}
