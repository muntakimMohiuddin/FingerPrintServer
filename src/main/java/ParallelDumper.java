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
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ParallelDumper extends RecursiveAction {
    int id;
    String[] candidatePaths;
    String templateDir;
    int start;
    int end;

    public ParallelDumper(int id, String[] candidatePaths, String templateDir, int start, int end) {
        this.id = id;
        this.candidatePaths = candidatePaths;
        this.templateDir = templateDir;
        this.start = start;
        this.end = end;
    }

    public ParallelDumper(String[] candidatePaths, String templateDir) {
        this.id = -1;
        this.candidatePaths = candidatePaths;
        this.templateDir = templateDir;
        this.start = 0;
        this.end = candidatePaths.length;
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

    void dump(String[] candidatePaths, String templateDir) throws IOException {
        for (int i = start; i < end; i++) {
            String personID = candidatePaths[i].split("/")[candidatePaths[i].split("/").length - 1].replace(".wsq", "");
//            personID = personID.split("_")[0];
            FingerprintTemplate candidate = new FingerprintTemplate(
                    new FingerprintImage(Files.readAllBytes(Paths.get(candidatePaths[i])), new FingerprintImageOptions()
                            .dpi(500)));
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(templateDir + "/" + personID + ".cbor"));
            objectOutputStream.writeObject(candidate.toByteArray());
            objectOutputStream.close();
        }
    }

    public static void main(String[] args) throws IOException {
        String candidateDir = "/home/drs-hive/data/rohinga_fingers";
        String[] candidatePaths = getFingerPrintPaths(candidateDir);
        String templateDir = "template";
//        for (int i = 0; i < candidatePaths.length; i++) {
//            String personID = candidatePaths[i].split("/")[candidatePaths[i].split("/").length - 1].replace(".wsq", "");
////            personID = personID.split("_")[0];
//            FingerprintTemplate candidate = new FingerprintTemplate(
//                    new FingerprintImage(Files.readAllBytes(Paths.get(candidatePaths[i])), new FingerprintImageOptions()
//                            .dpi(500)));
//            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(templateDir+"/"+personID + ".cbor"));
//            objectOutputStream.writeObject(candidate.toByteArray());
//            objectOutputStream.close();
//        }
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        double beginningTime = System.nanoTime();
        forkJoinPool.invoke(new ParallelDumper(candidatePaths, templateDir));
        System.out.println("completed in " + (System.nanoTime() - beginningTime) / 1000000000);


        System.out.println();
    }

    @Override
    protected void compute() {
        if (id == -1) {
            int logicalCoreCount = Runtime.getRuntime().availableProcessors();
            ParallelDumper[] processes = new ParallelDumper[logicalCoreCount];
            int split = (int) (end / logicalCoreCount);
            for (int i = 0; i < logicalCoreCount - 1; i++) {
                processes[i] = new ParallelDumper(i, candidatePaths, templateDir, split * i, split * (i + 1));
            }
            processes[logicalCoreCount - 1] = new ParallelDumper(logicalCoreCount - 1, candidatePaths, templateDir, split * (logicalCoreCount - 1), end);
            invokeAll(processes);
        } else {
            try {
                dump(candidatePaths, templateDir);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
