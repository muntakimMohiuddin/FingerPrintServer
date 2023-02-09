import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

public class XMLParser {

    static String dateUitl(String dateString) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M/d/yyyy hh:mm:ss");
        Date bdDate = simpleDateFormat.parse(dateString);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(bdDate);
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date time = calendar.getTime();
        SimpleDateFormat outputFmt = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        outputFmt.setTimeZone(TimeZone.getTimeZone("UTC"));
        String out = outputFmt.format(time);
        return out;
    }

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
            System.out.println(node.getAttributes().item(0).getNodeValue() + " " + node.getAttributes().item(1).getNodeValue());
        }
        try {
            for (String date : new String[]{"CREATION_DATE", "DATE_OF_ENTRY"})
                personInfo.put(date, dateUitl(personInfo.get(date)));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return personInfo;
    }
}
