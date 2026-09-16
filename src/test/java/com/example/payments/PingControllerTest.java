package com.example.payments;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PingController.class) // поднимаем только веб-слой с этим контроллером
class PingControllerTest {

    @Autowired
    MockMvc mockMvc; // «фейковый браузер»: шлёт HTTP-запросы без запуска сервера

    @Test
    void pingReturnsOk() throws Exception {
        mockMvc.perform(get("/api/ping"))
                .andExpect(status().isOk())            // код ответа 200
                .andExpect(jsonPath("$.status").value("ok")); // в JSON поле status = "ok"
    }
}
