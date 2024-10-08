package by.ruslan.project.config;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import by.ruslan.project.configuration.SecurityConfig;
import by.ruslan.project.controller.outer.LibraryFeignClient;
import by.ruslan.project.dto.BookDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@AutoConfigureMockMvc
@Import(SecurityConfig.class)
@Testcontainers
public class SecurityConfigTest {

  @Container
  public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:13.3")
      .withDatabaseName("testdb")
      .withUsername("test")
      .withPassword("test");

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private LibraryFeignClient libraryFeignClient;

  @BeforeAll
  public static void setUp() {
    postgreSQLContainer.start();
    System.setProperty("spring.datasource.url", postgreSQLContainer.getJdbcUrl());
    System.setProperty("spring.datasource.username", postgreSQLContainer.getUsername());
    System.setProperty("spring.datasource.password", postgreSQLContainer.getPassword());
  }

  @Test
  public void testSwaggerEndpointsArePublic() throws Exception {
    mockMvc.perform(get("/swagger-ui.html"))
        .andExpect(status().is3xxRedirection())
        .andExpect(header().string("Location", "/swagger-ui/index.html"));

    mockMvc.perform(get("/swagger-ui/index.html")).andExpect(status().isOk());
    mockMvc.perform(get("/v3/api-docs")).andExpect(status().isOk());
  }

  @Test
  public void testUnauthenticatedAccessDenied() throws Exception {
    mockMvc.perform(get("/library/books")).andExpect(status().isUnauthorized());
  }

  @Test
  @WithMockUser(roles = "USER")
  public void testAuthenticatedUserCanAccessBooks() throws Exception {
    mockMvc.perform(get("/library/books")).andExpect(status().isOk());
  }

  @Test
  @WithMockUser(roles = "USER")
  public void testUserCannotModifyBooks() throws Exception {
    mockMvc.perform(post("/library/books").with(csrf())).andExpect(status().isForbidden());
    mockMvc.perform(put("/library/books").with(csrf())).andExpect(status().isForbidden());
    mockMvc.perform(delete("/library/books").with(csrf())).andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser(roles = "LIBRARIAN")
  public void testLibrarianCanModifyBooks() throws Exception {
    String isbn = "978-1-234-56789-3";

    BookDto bookDto = new BookDto();
    bookDto.setIsbn(isbn);
    bookDto.setName("Test Book");
    bookDto.setGenre("Test genre");
    bookDto.setDescription("Test description");
    bookDto.setAuthor("Test Author");

    String bookJson = objectMapper.writeValueAsString(bookDto);

    mockMvc.perform(post("/library/books")
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(bookJson))
        .andExpect(status().isOk());

    bookDto.setName("Updated Test Book");
    bookDto.setDescription("Updated Test description");
    String updatedBookJson = objectMapper.writeValueAsString(bookDto);

    mockMvc.perform(put("/library/books/{isbn}", isbn)
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(updatedBookJson))
        .andExpect(status().isOk());

  }
}
