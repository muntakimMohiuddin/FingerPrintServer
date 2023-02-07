import com.machinezoo.sourceafis.FingerprintImage;
import com.machinezoo.sourceafis.FingerprintMatcher;
import com.machinezoo.sourceafis.FingerprintTemplate;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.FileHandler;

public class SourceAFISFingerprintTemplate extends FingerprintTemplate implements IFingerprintTemplate {
    private FingerprintMatcher fingerprintMatcher;

    SourceAFISFingerprintTemplate(String path) throws IOException {
        super(new FingerprintImage(Files.readAllBytes(Paths.get(path))));
    }

    SourceAFISFingerprintTemplate(byte[] arr) {
        super(arr);
    }

    @Override
    public double match(IFingerprintTemplate fingerprintTemplate) {
        if (fingerprintMatcher == null) {
            fingerprintMatcher = new FingerprintMatcher(this);
        }
        return fingerprintMatcher.match((FingerprintTemplate) fingerprintTemplate);
    }

}
