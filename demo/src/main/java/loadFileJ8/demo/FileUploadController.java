package loadFileJ8.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
@RestController
public class FileUploadController {

    private static final String VERSION = "1";
    private static final String OUTPUT_PATH = "/opt/allot/import/registration_provisioning/";

    @Value("${spring.servlet.multipart.enabled}")
    private String multipartEnabled;

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        System.out.println("hello");
        System.out.println("multipartEnabled: " + multipartEnabled);
        return ResponseEntity.status(HttpStatus.OK).body("test");
    }

//    @PostMapping(VERSION + "/csv/SubscriberProvisioning")
    @PostMapping(value   ="/uploadFile")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        // Todo - research how to add support for server side decompression
        // Todo - add code to save csv file in the default registration folder - “/opt/allot/import/registration_provisioning/”

        File outPutFile = new File(OUTPUT_PATH + file.getOriginalFilename());
        FileOutputStream fos =  null;
        try {
            outPutFile.createNewFile();
            fos = new FileOutputStream(outPutFile);
            fos.write(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String message = "Uploaded the file successfully: ";
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @PostMapping("/uploadBody")
    public ResponseEntity<String> handleFileUploadBody(@RequestBody List<CSVObj> CSVObjList) {

        CSVObjList.forEach(csv -> {
            System.out.println(csv.getId());
        });
        String message = "Uploaded the file successfully: ";
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }
}
