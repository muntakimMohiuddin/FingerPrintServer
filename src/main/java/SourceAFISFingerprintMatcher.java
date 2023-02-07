import com.machinezoo.sourceafis.FingerprintImage;
import com.machinezoo.sourceafis.FingerprintImageOptions;
import com.machinezoo.sourceafis.FingerprintTemplate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SourceAFISFingerprintMatcher implements FingerprintMatcher {

    HashMap<String, FingerprintTemplate> candidates = new HashMap<String, FingerprintTemplate>();
    String enrolledFingerprintsDir;

    public SourceAFISFingerprintMatcher(String enrolledFingerprintsDir) throws IOException {
        this.enrolledFingerprintsDir = enrolledFingerprintsDir;
        Object[] candidatePaths = Stream.of(new File(enrolledFingerprintsDir).listFiles())
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .collect(Collectors.toSet()).toArray();
        for (int i = 0; i < candidatePaths.length; i++) {
            candidates.put(candidatePaths[i].toString(), new FingerprintTemplate(
                    new FingerprintImage(Files.readAllBytes(Paths.get(enrolledFingerprintsDir + "/" + candidatePaths[i].toString())), new FingerprintImageOptions()
                            .dpi(500))));
        }
    }

    public void add(String filename) throws IOException {
        candidates.put(filename, new FingerprintTemplate(
                new FingerprintImage(Files.readAllBytes(Paths.get(enrolledFingerprintsDir + "/" + filename)), new FingerprintImageOptions()
                        .dpi(500))));

    }

    public String match(String filepath) throws IOException {
        FingerprintTemplate probe = new FingerprintTemplate(
                new FingerprintImage(Files.readAllBytes(Paths.get(filepath)), new FingerprintImageOptions()
                        .dpi(500)));
        var matcher = new com.machinezoo.sourceafis.FingerprintMatcher(probe);
        boolean anyMatches = false;
        for (Map.Entry<String, FingerprintTemplate> candidate : candidates.entrySet()) {
            FingerprintTemplate candidateTemplate = candidate.getValue();
            String candidatePath = candidate.getKey();
            double similarity = matcher.match(candidateTemplate);
            double threshold = 40;
            boolean matches = similarity >= threshold;
//                            System.out.println(similarity+": "+candidatePath);
            if (matches) {
                anyMatches = true;
                System.out.println("matched: " + candidatePath + " with similarity score of :" + similarity);
//                System.out.println(" in " + (System.nanoTime() - beginningTime) / 1000000000 + "seconds");
                return candidatePath;
            }
        }
        if (!anyMatches) {
            System.out.println("no matches");
        }
        return null;
    }
}
