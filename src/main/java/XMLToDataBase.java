import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class XMLToDataBase {
    static String[] listDir(String dirPath) {
        try {


            File[] f = new File(dirPath).listFiles();
            var listObject = Stream.of(new File(dirPath).listFiles())
                    .map(File::getName)
                    .collect(Collectors.toSet()).toArray();

            String[] listString = new String[listObject.length];
            for (int i = 0; i < listObject.length; i++) {
                listString[i] = dirPath + "/" + listObject[i].toString();
            }

            return listString;
        } catch (Exception e) {
            System.out.println(dirPath);
            throw new RuntimeException();
        }
    }

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, SQLException {
        String dir = "/home/drs-hive/data/rohinga_fingers";
        String[] dateDirs = listDir(dir);
        PGManager pgManager = new PGManager();
        for (String dateDir : dateDirs) {
            String[] personDirs = listDir(dateDir);
            for (String personDir : personDirs) {
                String personID = personDir.split("/")[personDir.split("/").length - 1];
                String[] personFiles = listDir(personDir);
                for (String personFile : personFiles) {
                    if (personFile.endsWith(".xml")) {
                        HashMap<String, String> personInfo = XMLParser.parse(personFile);
                        for (String acc : new String[]{"LI", "LM", "LR", "LL", "LT", "RI", "RM", "RR", "RL", "RT"}) {
                            String filename = personDir + "/" + personID + "_" + acc + ".wsq";
                            if (new File(filename).exists()) {
                                personInfo.put(acc, filename);
                            }
                        }
                        String filename = personDir + "/" + personID + "_photo.jpg";
                        if (new File(filename).exists()) {
                            personInfo.put("PHOTO",filename);
                        }
                        pgManager.hashMapToPG(personInfo);
                        System.out.println(personInfo);
                    }
                }
            }
        }
    }
}
