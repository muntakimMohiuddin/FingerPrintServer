import com.machinezoo.sourceafis.FingerprintImage;
import com.machinezoo.sourceafis.FingerprintImageOptions;
import com.machinezoo.sourceafis.FingerprintTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Dumper {

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

    public static String[] getFingerPrintPaths(String dirPath) {
        ArrayList<String> fingerPrintPaths = new ArrayList<String>();
        String[] dateDirs = listDir(dirPath);
        for (String dateDir : dateDirs) {
            String[] personDirs = listDir(dateDir);
            for (String personDir : personDirs) {
                String personID = personDir.split("/")[personDir.split("/").length - 1];
                String[] personFiles = listDir(personDir);
                for (String personFile : personFiles) {
                    if (personFile.endsWith(".wsq")) {
                        fingerPrintPaths.add(personFile);
                    }
                }

            }
        }
        String[] fingetprintPathArray = new String[fingerPrintPaths.size()];
        for (int i = 0; i < fingerPrintPaths.size(); i++) {
            fingetprintPathArray[i] = fingerPrintPaths.get(i);
        }
        Arrays.sort(fingetprintPathArray);
        return fingetprintPathArray;
    }

    public static void main(String[] args) throws IOException {
        String candidateDir = "/home/drs-hive/data/fingers";
        String[] candidatePaths = getFingerPrintPaths(candidateDir);
        String templateDir="template";
        for (int i = 0; i < candidatePaths.length; i++) {
            String personID = candidatePaths[i].split("/")[candidatePaths[i].split("/").length - 1].replace(".wsq", "");
//            personID = personID.split("_")[0];
            FingerprintTemplate candidate = new FingerprintTemplate(
                    new FingerprintImage(Files.readAllBytes(Paths.get(candidatePaths[i])), new FingerprintImageOptions()
                            .dpi(500)));
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(templateDir+"/"+personID + ".cbor"));
            objectOutputStream.writeObject(candidate.toByteArray());
            objectOutputStream.close();
        }
        System.out.println();
    }
}
