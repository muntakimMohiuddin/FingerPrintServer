import com.machinezoo.sourceafis.FingerprintTemplate;

public class TemplatePathPair {
    String path;
    IFingerprintTemplate fingerprintTemplate;

    public TemplatePathPair(String path, IFingerprintTemplate fingerprintTemplate) {
        this.path = path;
        this.fingerprintTemplate = fingerprintTemplate;
    }
}
