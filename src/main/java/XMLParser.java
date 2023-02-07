import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class XMLParser {


    public static HashMap<String, String> parse(String filename) throws ParserConfigurationException, IOException, SAXException {
        File file = new File(filename);
        HashMap<String, String> personInfo = new HashMap<String, String>();
        //an instance of factory that gives a document builder
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        //an instance of builder to parse the specified xml file
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(file);
        doc.getDocumentElement().normalize();
        NodeList nodeList = doc.getElementsByTagName("field");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            personInfo.put(node.getAttributes().item(0).getNodeValue(), node.getAttributes().item(1).getNodeValue());
//            System.out.println(node.getAttributes().item(0).getNodeValue());
        }
        return personInfo;
    }
}
