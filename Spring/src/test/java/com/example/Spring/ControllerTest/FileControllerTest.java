package com.example.Spring.ControllerTest;

import com.example.Spring.Controller.FileController;
import com.example.Spring.Service.FileService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FileControllerTest {


    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
   public void testUploadFileSuccess() throws Exception {
        FileService fileService = mock(FileService.class);
        when(fileService.storeFile(Mockito.any(MultipartFile.class))).thenReturn("test.txt");
        FileController fileController = new FileController(fileService);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(fileController).build();
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", MediaType.TEXT_PLAIN_VALUE,
                "This is a test file".getBytes());
        mockMvc.perform(multipart("/api/file").file(file))
                .andExpect(status().isOk());
    }

}
