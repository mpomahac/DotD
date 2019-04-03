package hr.mpomahac.dotd.extensions;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Log4j2XMLEditor {
	
	public Document doc;

	public Log4j2XMLEditor(String filePath) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			this.doc = builder.parse(filePath);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setLogPath(String logPath) {
		NodeList properties = doc.getElementsByTagName("Properties").item(0).getChildNodes();
		for(int i = 0; i < properties.getLength(); i++) {
			Node p = properties.item(i);
			if(p.getNodeType() == Node.ELEMENT_NODE && ((Element) p).getAttribute("name").equals("log_folder")) {
				((Element) p).setTextContent(logPath);
			}
		}
		
		saveChanges();
	}
	
	private void saveChanges() {
		TransformerFactory factory = TransformerFactory.newInstance();
		try {
			Transformer transformer = factory.newTransformer();
			DOMSource src = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("src/main/resources/log4j2.xml"));
			transformer.transform(src, result);
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
	
}
