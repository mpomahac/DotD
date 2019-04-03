package hr.mpomahac.dotd.controllers;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hr.mpomahac.dotd.extensions.Log4j2XMLEditor;
import hr.mpomahac.dotd.models.Camp;
import hr.mpomahac.dotd.models.Character;
import hr.mpomahac.dotd.models.Raid;
import hr.mpomahac.dotd.models.War;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{
	
	public static final String RESOURCE_FOLDER_PATH = new File("src/main/resources").getAbsolutePath().replaceAll("\\\\", "/");
	
	public static Stage window;
	public static List<Camp> allCamps = new ArrayList<>();
	public static List<Raid> allRaids = new ArrayList<>();
	public static List<Character> allChars = new ArrayList<>();
	public static War war;
	
	public static void main(String[] args) {
		
		String log4j2XMLAbsPath = RESOURCE_FOLDER_PATH + "/log4j2.xml";
		String logFolderAbsPath = log4j2XMLAbsPath.replaceAll("/src/main/resources/log4j2\\.xml", "/logs");
		
		System.out.println("Creating log4j2.xml editor with path: " + log4j2XMLAbsPath);
		Log4j2XMLEditor log4j2XMLEditor = new Log4j2XMLEditor(log4j2XMLAbsPath);
		System.out.println("Setting log path in log4j2.xml to " + logFolderAbsPath);
		log4j2XMLEditor.setLogPath(logFolderAbsPath);
		
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		
		//TESTS START
		/*
		*/
		//TESTS END
		
		Logger LOGGER = LogManager.getLogger("mainLogger");
		
		LOGGER.info("***********************");
		LOGGER.info("Loading app data...");
		LOGGER.info("***********************\n");

		LOGGER.info("=========================================");
		LOGGER.info("Loading camps from file: " + RESOURCE_FOLDER_PATH + "/data/camps.dat");
		long start = System.currentTimeMillis();
		Camp.FILE = RESOURCE_FOLDER_PATH + "/data/camps.dat";
		Camp.loadCamps();
		LOGGER.info("Camp loading time: " + (System.currentTimeMillis() - start) + "ms");
		
		LOGGER.info("=========================================");
		LOGGER.info("Loading raids from file: " + RESOURCE_FOLDER_PATH + "/data/raids.dat");
		start = System.currentTimeMillis();
		Raid.FILE = RESOURCE_FOLDER_PATH + "/data/raids.dat";
		Raid.loadRaids();
		LOGGER.info("Raid loading time: " + (System.currentTimeMillis() - start) + "ms");
		
		LOGGER.info("=========================================");
		LOGGER.info("Loading characters from file: " + RESOURCE_FOLDER_PATH + "/data/chars.dat");
		start = System.currentTimeMillis();
		Character.FILE = RESOURCE_FOLDER_PATH + "/data/chars.dat";
		Character.loadCharacters();
		LOGGER.info("Character loading time: " + (System.currentTimeMillis() - start) + "ms");
		
		LOGGER.info("=========================================");
		LOGGER.info("Loading war from file: " + RESOURCE_FOLDER_PATH + "/data/war.dat");
		start = System.currentTimeMillis();
		War.FILE = RESOURCE_FOLDER_PATH + "/data/war.dat";
		War.loadWar();
		LOGGER.info("War loading time: " + (System.currentTimeMillis() - start) + "ms");
		LOGGER.info("=========================================");
		
		start = System.currentTimeMillis();
		Parent root = FXMLLoader.load(getClass().getResource("/fxml/Home.fxml"));
		System.out.println("Setting primary stage to window.");
		window = primaryStage;
		window.setTitle("DotD Tools");
		window.setScene(new Scene(root));
		window.show();
		HomeController.setWindowOnCloseRequest();
		System.out.println("Main window loading time: " + (System.currentTimeMillis() - start) + "ms");
	}
}
