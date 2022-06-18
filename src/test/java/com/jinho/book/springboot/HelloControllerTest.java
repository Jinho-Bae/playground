package com.jinho.book.springboot;

import com.jinho.book.springboot.web.HelloController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)    // SpringBoot test와 JUnit 사이에 연결자 역할을 하는 runner
@WebMvcTest(controllers = HelloController.class)    // Web에 집중할 수 있는 annotation.
public class HelloControllerTest {

    @Autowired  // Spring이 관리하는 Bean 주입 받음
    private MockMvc mvc;    // web API를 테스트할 때 사용
    @Test
    public void returnHello() throws Exception {
        String hello = "hello";

        // MockMvc를 통해 /hello 주소로 HTTP GET 요청
        // .andExcept : mv.perform 결과 HTTP Header의 Status, body의 content 검증
        mvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(hello));
    }

    @Test
    public void returnHelloDto() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(get("/hello/dto").param("name", name).param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.amount", is(amount)));   // jsonPath: JSON 응답값을 필드별로 검증
    }
}
