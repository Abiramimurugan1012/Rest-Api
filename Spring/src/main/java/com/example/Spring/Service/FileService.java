package com.example.Spring.Service;

import com.example.Spring.Exception.FileStorageException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileService {

    public static final String UPLOAD_DIR =  "C:/uploads/";

    public FileService() {
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
    }

    public String storeFile(MultipartFile file) {
        try {
            Path targetpath=Paths.get(UPLOAD_DIR,file.getOriginalFilename());
            if(Files.exists(targetpath)){
                return "File " + file.getOriginalFilename() + " already exists";
            }
            Files.copy(file.getInputStream(), Paths.get(UPLOAD_DIR)
                    .resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
            return file.getOriginalFilename();
        } catch (IOException e) {
            throw new FileStorageException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }
}
