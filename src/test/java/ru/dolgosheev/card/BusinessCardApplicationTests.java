package ru.dolgosheev.card;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.emptyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.dolgosheev.card.contact.ContactMessageRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class BusinessCardApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ContactMessageRepository repository;

    @BeforeEach
    void cleanUp() {
        repository.deleteAll();
    }

    @Test
    void profileIsServedFromConfig() throws Exception {
        mockMvc.perform(get("/api/profile"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Александр Долгошеев"))
                .andExpect(jsonPath("$.contacts[*].type").value(hasItem("github")))
                .andExpect(jsonPath("$.experience[0].company").value(not(emptyString())))
                .andExpect(jsonPath("$.experience[0].highlights[0]").value(not(emptyString())))
                .andExpect(jsonPath("$.projects[0].name").value(not(emptyString())));
    }

    @Test
    void indexPageIsServed() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("index.html"));
    }

    @Test
    void validMessageIsSaved() throws Exception {
        mockMvc.perform(post("/api/contact")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"name": " Иван ", "email": "ivan@example.com", "message": "Есть вакансия"}
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber());

        assertThat(repository.findAll())
                .singleElement()
                .satisfies(m -> {
                    assertThat(m.getName()).isEqualTo("Иван");
                    assertThat(m.getEmail()).isEqualTo("ivan@example.com");
                });
    }

    @Test
    void invalidMessageReturnsFieldErrors() throws Exception {
        mockMvc.perform(post("/api/contact")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"name": "", "email": "not-an-email", "message": "Привет"}
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.name").value("Укажите имя"))
                .andExpect(jsonPath("$.errors.email").value("Некорректный email"));

        assertThat(repository.count()).isZero();
    }

    @Test
    void malformedJsonReturnsBadRequest() throws Exception {
        mockMvc.perform(post("/api/contact")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{oops"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail").value("Некорректное тело запроса"));
    }
}
