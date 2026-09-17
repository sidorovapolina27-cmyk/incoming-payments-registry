package com.example.payments.client;

import com.example.payments.client.controller.ClientController;
import com.example.payments.client.service.ClientService;
import com.example.payments.error.ApiExceptionHandler;
import com.example.payments.error.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.context.annotation.Import;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ClientController.class)
@Import(ApiExceptionHandler.class) // без него ошибки будут не через наш JSON, а как получится
class ClientControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean // заглушка: настоящий сервис (и база!) не вызывается.
    // В старых версиях Spring Boot эта аннотация называлась @MockBean
    ClientService clientService;

    @Test
    void emptyNameReturns400Json() throws Exception {
        mockMvc.perform(post("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"fullName\":\"\",\"inn\":\"123456789012\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void missingClientReturns404Json() throws Exception {
        // учим заглушку: если у сервиса спросили клиента 999999 — он кидает NotFoundException
        when(clientService.getById(999999L))
                .thenThrow(new NotFoundException("Клиент не найден"));

        mockMvc.perform(get("/api/clients/999999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Клиент не найден"));
    }
}
