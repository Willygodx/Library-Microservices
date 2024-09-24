package by.ruslan.project.config;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import by.ruslan.project.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
class SecurityConfigTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private BookService bookService; // Mock для BookService

  @Test
  @WithMockJwtAuth(roles = "ROLE_LIBRARIAN")
  void whenLibrarianDeletesBook_thenSuccess() throws Exception {
    mockMvc.perform(delete("/library/books")
            .param("isbn", "123456789") // Передача параметра isbn
            .with(jwt().authorities(() -> "ROLE_LIBRARIAN")))
        .andExpect(status().isOk());
  }

  @Test
  @WithMockJwtAuth(roles = "ROLE_READER")
  void whenUserDeletesBook_thenForbidden() throws Exception {
    mockMvc.perform(delete("/library/books")
            .param("isbn", "123456789") // Передача параметра isbn
            .with(jwt().authorities(() -> "ROLE_USER"))) // Передача роли USER
        .andExpect(status().isForbidden()); // Ожидаем статус 403
  }
}
