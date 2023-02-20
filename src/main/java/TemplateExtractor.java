import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TemplateExtractor {

    TemplateExtractor() {

    }

    public static TemplatePathPair[] extract(String templateDir) throws IOException, ClassNotFoundException {
        int loadfactor = 4;
        String candidatePaths[] = listDir(templateDir);
        TemplatePathPair[] candidates = new TemplatePathPair[candidatePaths.length * loadfactor];
        for (int i = 0; i < candidatePaths.length; i++) {
            try {
                String candidatePath = candidatePaths[i % candidatePaths.length];
                ObjectInputStream objectInputStream = new ObjectInputStream(new
                        FileInputStream(candidatePath));
                byte[] templateAsByteArray = (byte[]) objectInputStream.readObject();
                for (int j = 0; j < loadfactor; j++) {
                    candidates[i * loadfactor + j] = new TemplatePathPair(candidatePath,
                            new Template(templateAsByteArray));
                }
                objectInputStream.close();
            } catch (Exception e) {
                System.out.println();
            }
        }

        for (int i = 0; i < candidates.length; i++) {
            if (candidates[i] == null) {
                System.out.println(i);
            }
        }
        System.out.println();
        return candidates;
    }

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
            Arrays.sort(listString);
            return listString;
        } catch (Exception e) {
            System.out.println(dirPath);
            throw new RuntimeException();
        }
    }
}
