import java.io.*;

import javax.xml.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class splitKML {
	
	public static void main(String[] args) throws Exception {
		File kmlInput = new File("...");
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		Document doc = dbf.newDocumentBuilder().parse(kmlInput);
		XPath xpath = XPathFactory.newInstance().newXPath();
		
		NodeList nodes = (NodeList) xpath.evaluate("...", doc, XPathConstants.NODESET);
		
		int itemsPerFile = nodes.getLength();
		
		int fileNumber = 0;
		
		Document currentDoc = dbf.newDocumentBuilder().newDocument();
		Node rootNode = currentDoc.createElement("Placemark");
		File currentFile = new File(fileNumber+".xml");
        for (int i=1; i <= nodes.getLength(); i++) {
            Node imported = currentDoc.importNode(nodes.item(i-1), true);
            rootNode.appendChild(imported);

            if (i % itemsPerFile == 0) {
                writeToFile(rootNode, currentFile);

                rootNode = currentDoc.createElement("Placemark");
                currentFile = new File((++fileNumber)+".xml");
            }
        }

        writeToFile(rootNode, currentFile);
    }

    private static void writeToFile(Node node, File file) throws Exception {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(new DOMSource(node), new StreamResult(new FileWriter(file)));
    }

}
	
	
	
