
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class postman {
    public static void main(String args[]) throws IOException {
        String baseUrl = "http://localhost:9090/api/fingerprint";
        OkHttpClient client = new OkHttpClient();
        File file=new File("/home/drs-hive/data/rohinga_fingers/date/10120170911205531/10120170911205531_LI.wsq");
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(),
                        RequestBody.create(MediaType.parse("image/*"), file)).build();
//
//        RequestBody formBody = new FormBody.Builder()
//                .url()
//                .post(requestBody)
//                .build();

        Request request = new Request.Builder()
                .url(baseUrl + "/identify-from-fingerprint")
                .post(requestBody)
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();
        ObjectMapper objectMapper = new ObjectMapper();
        RohingaDomainModel rohingaDomainModel=objectMapper.readValue(response.body().string(),RohingaDomainModel.class);
//        System.out.println((RohingaDomainModel)response.body());
        System.out.println(rohingaDomainModel);
        File outputFile = new File("outputFile.wsq");
        try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
            outputStream.write(rohingaDomainModel.getPhoto());
        }
    }

//    void post() {
//        String baseUrl = "http://localhost:9090/api/fingerprint";
//        RestTemplate
//    }
}
