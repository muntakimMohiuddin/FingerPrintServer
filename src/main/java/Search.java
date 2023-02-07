import java.io.IOException;
import java.util.Collections;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class Search extends RecursiveAction {
    int start;
    int end;
    SortedMap matches;
    int[] status;

    int id;
    TemplatePathPair[] candidates;
    IFingerprintTemplate probe;

    public Search(TemplatePathPair[] candidates, int start, int end, SortedMap matches, int id, int[] status, IFingerprintTemplate probe) {
        this.start = start;
        this.end = end;
        this.matches = matches;
        this.id = id;
        this.status = status;
        this.candidates=candidates;
        this.probe=probe;
    }

    public Search(int id) {
        this.id = id;
    }

    protected void computeDirectly() {

        for (int i = start; i < end; i++) {
            double score=probe.match(candidates[i].fingerprintTemplate);
            if(score>40){
                synchronized (matches) {
                    matches.put(score, candidates[i].path);
                }
            }
        }
        status[id]=0;
    }

    public static boolean any(int[] arr, int match) {
        for (int element : arr) {
            if (element == match) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void compute() {
        if (id == -1) {
            int logicalCoreCount = Runtime.getRuntime().availableProcessors();
            Search[] processes = new Search[logicalCoreCount];
            int split = (int) (end / logicalCoreCount);
            for (int i = 0; i < logicalCoreCount - 1; i++) {
                status[i] = -1;
                processes[i] = new Search(candidates, split * i, split * (i + 1), matches, i, status,probe);
            }
            status[logicalCoreCount - 1] = -1;
            processes[logicalCoreCount - 1] = new Search(candidates, split * (logicalCoreCount - 1), end, matches, logicalCoreCount - 1, status,probe);
            invokeAll(processes);
            System.out.println(matches.get(matches.lastKey()));

        } else {
            computeDirectly();
            status[id] = 0;
            for (int s : status)
                System.out.print(s);
            System.out.println();
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        int logicalCoreCount = Runtime.getRuntime().availableProcessors();
        String templateDir="template";
        SortedMap matches = Collections.synchronizedSortedMap(new TreeMap());
        int[] status = new int[logicalCoreCount];
        TemplatePathPair[] candidates=TemplateExtractor.extract(templateDir);
        IFingerprintTemplate probe=new SourceAFISFingerprintTemplate("/home/drs-hive/data/rohinga_fingers/date/10120170911203716/10120170911203716_LI.wsq");
        System.out.println("read all templates");
        Search search = new Search(candidates, 0, candidates.length, matches, -1, status,probe);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        double beginningTime=System.nanoTime();
        forkJoinPool.invoke(search);
        System.out.println("main completed in "+(System.nanoTime()-beginningTime)/1000000000);
    }
}
