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

    public static final String UPLOAD_DIR = "C:/uploads/";

    public FileService() {
        try {
            Files.createDirectories(Paths.get(UPLOAD_DIR));
        } catch (IOException e) {
            throw new FileStorageException("Failed to create upload directory", e);
        }
    }

    public String storeFile(MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            if (fileName == null) {
                throw new FileStorageException("File name is null");
            }
            if (Files.exists(Paths.get(UPLOAD_DIR, fileName))) {
                return "File " + fileName + " already exists";
            }
            Files.copy(file.getInputStream(), Paths.get(UPLOAD_DIR, fileName), StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException e) {
            throw new FileStorageException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }
}
