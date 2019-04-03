package hr.mpomahac.dotd.extensions;

import java.io.File;

import hr.mpomahac.dotd.controllers.Main;

public class LoggingSetup {

	public static final String RESOURCE_FOLDER_PATH = new File("src/main/resources").getAbsolutePath().replaceAll("\\\\", "/");
	
	public static void main(String[] args) {
		
		String log4j2XMLAbsPath = RESOURCE_FOLDER_PATH + "/log4j2.xml";
		String logFolderAbsPath = log4j2XMLAbsPath.replaceAll("/src/main/resources/log4j2\\.xml", "/logs");
		
		System.out.println("Creating log4j2.xml editor with path: " + log4j2XMLAbsPath);
		Log4j2XMLEditor log4j2XMLEditor = new Log4j2XMLEditor(log4j2XMLAbsPath);
		System.out.println("Setting log path in log4j2.xml to " + logFolderAbsPath);
		log4j2XMLEditor.setLogPath(logFolderAbsPath);
		
		Main.main(null);
	}

}
