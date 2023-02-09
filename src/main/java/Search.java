import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ForkJoinPool;

public class Search {

    public String search(byte[] probeAsBytes) {
        String probePath = "temp.bmp";
        File streamedFile = new File(probePath);
        try {
            FileOutputStream outputStream = new FileOutputStream(streamedFile);
            outputStream.write(probeAsBytes, 0, probeAsBytes.length);
            outputStream.flush();
            outputStream.close();
            return search(probePath);
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            streamedFile.delete();
            return null;
        }
    }

    public String search(String probePath) throws IOException, ClassNotFoundException {
        String templateDir = "template";
        SortedMap matches = Collections.synchronizedSortedMap(new TreeMap());
        int logicalCoreCount = Runtime.getRuntime().availableProcessors();
        int[] status = new int[logicalCoreCount];
        TemplatePathPair[] candidates = TemplateExtractor.extract(templateDir);
        Template probe = new Template(probePath);
        System.out.println("read all templates");
        ParallelSearch parallelSearch = new ParallelSearch(candidates, 0, candidates.length, matches, -1, status, probe);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        double beginningTime = System.nanoTime();
        forkJoinPool.invoke(parallelSearch);
        System.out.println("main completed in " + (System.nanoTime() - beginningTime) / 1000000000);
        System.out.println("match found with " + matches.get(matches.lastKey()));
        return (String) matches.get(matches.lastKey());

    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        new Search().search("/home/drs-hive/data/rohinga_fingers/date/10120170911203716/10120170911203716_LI.wsq");
    }
}
