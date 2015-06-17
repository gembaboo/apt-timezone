package com.gembaboo.aptz.resources;


import com.gembaboo.aptz.fileloader.FileLoader;
import org.springframework.beans.factory.annotation.Autowired;
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

@Controller
@RequestMapping(value = "/file/1")
public class AirportFileResource {

    private final static String SEPARATOR = System.getProperty("file.separator");
    private final static String UPLOAD_DIR = System.getProperty("user.dir") + SEPARATOR + "upload";


    @Autowired
    private FileLoader fileLoader;

    @RequestMapping(value = "/upload", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    String upload(
            @RequestParam MultipartFile file,
            @RequestParam(defaultValue = "false") Boolean overWrite,
            @RequestParam(defaultValue = "false") Boolean autoRename) {
        if (!file.isEmpty()) {
            try {
                File uploadedFile = uploadFile(file);
                processFile(uploadedFile);
                return "Successfully uploaded " + file.getName() + "";
            } catch (Exception e) {
                return "Failed to upload " + file.getName() + " => " + e.getMessage() + "";
            }
        } else {
            return "Failed to upload " + file.getName() + " because the file was empty.";
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

    private void processFile(File uploadedFile) {
        fileLoader.loadFile(uploadedFile);
    }
}
