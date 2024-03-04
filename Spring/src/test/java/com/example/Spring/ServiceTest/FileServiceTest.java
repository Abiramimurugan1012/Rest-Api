package com.example.Spring.ServiceTest;

import com.example.Spring.Exception.FileStorageException;
import com.example.Spring.Service.FileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static org.hibernate.internal.util.ExceptionHelper.doThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FileServiceTest {

    @InjectMocks
    FileService fileService;
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void fileUpload(){
        MockMultipartFile mockMultipartFile =
                new MockMultipartFile("file", "test.txt", "text/plain", "Mock file content".getBytes());
        String fileName = fileService.storeFile(mockMultipartFile);
        assertEquals("File test.txt already exists", fileName);
    }


    @Test
    void testStoreFile_Success() throws IOException {
        FileService fileService = new FileService();
        MultipartFile multipartFile = new MockMultipartFile("test.txt", "This is a test file".getBytes());
        String fileName = fileService.storeFile(multipartFile);
        Path filePath = Paths.get(FileService.UPLOAD_DIR, fileName);
        assertEquals(false, Files.exists(filePath));
        Files.deleteIfExists(filePath);
    }


   }






