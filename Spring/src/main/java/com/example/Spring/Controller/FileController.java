package com.example.Spring.Controller;


import com.example.Spring.Service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class FileController {
    @Autowired
    FileService fileService;

    @PostMapping("/file")
    public ResponseEntity<String> uploadFile(@RequestParam("file")MultipartFile file){
        String fileName = fileService.storeFile(file);
        return ResponseEntity.ok().body("File " + fileName + " uploaded successfully");
    }
}
