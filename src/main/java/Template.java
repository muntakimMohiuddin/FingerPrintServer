import java.io.IOException;

public class Template  {
    IFingerprintTemplate fingerprintTemplate;
    Template(String path) throws IOException {
        fingerprintTemplate=new SourceAFISFingerprintTemplate(path);
    }
    Template(byte[] arr){
        fingerprintTemplate=new SourceAFISFingerprintTemplate(arr);
    }
//
//    public double match(IFingerprintTemplate probe){
//        return  fingerprintTemplate.match((probe);
//    }

}
