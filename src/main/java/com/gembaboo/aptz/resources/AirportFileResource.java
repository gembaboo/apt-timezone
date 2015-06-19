package com.gembaboo.aptz.resources;


import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Api(description = "Upload airport file, available at http://ourairports.com/data/airports.csv")
@Controller
@RequestMapping(value = "/file/1")
public class AirportFileResource {

    private final static String SEPARATOR = System.getProperty("file.separator");
    public final static String UPLOAD_DIR = System.getProperty("user.dir") + SEPARATOR + "upload";

    @RequestMapping(value = "/upload", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String upload(@RequestParam MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                uploadFile(file);
                return "Successfully uploaded " + file.getName() + "";
            } catch (Exception e) {
                final String message = "Failed to upload " + file.getName() + " => " + e.getMessage();
                log.error(message, e);
                return message;
            }
        } else {
            final String message = "Failed to upload " + file.getName() + " because the file was empty.";
            log.info(message);
            return message;
        }

    }

    private File uploadFile(MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        String fileName = getFileName();
        File uploadedFile = new File(fileName);
        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(uploadedFile));
        stream.write(bytes);
        stream.close();
        return uploadedFile;
    }

    private String getFileName() {
        return UPLOAD_DIR + SEPARATOR + UUID.randomUUID().toString() + ".csv";
    }
}
