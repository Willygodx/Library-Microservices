package by.ruslan.project.controller.inner.impl;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import by.ruslan.project.dto.BookDto;
import by.ruslan.project.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {BookControllerImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class BookControllerImplTest {
  @Autowired
  private BookControllerImpl bookControllerImpl;

  @MockBean
  private BookService bookService;

  @Test
  void testGetBookById() throws Exception {
    BookDto bookDto = new BookDto();
    bookDto.setAuthor("JaneDoe");
    bookDto.setDescription("The characteristics of someone or something");
    bookDto.setGenre("Genre");
    bookDto.setIsbn("Isbn");
    bookDto.setName("Name");
    when(bookService.getBookById(Mockito.<Long>any())).thenReturn(bookDto);
    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders.get("/library/books/{id}", 1L);

    MockMvcBuilders.standaloneSetup(bookControllerImpl)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content()
            .string(
                "{\"isbn\":\"Isbn\",\"name\":\"Name\",\"genre\":\"Genre\",\"description\":\"The characteristics of someone or"
                    + " something\",\"author\":\"JaneDoe\"}"));
  }

  @Test
  void testGetBookByIsbn() throws Exception {
    // Arrange
    BookDto bookDto = new BookDto();
    bookDto.setAuthor("JaneDoe");
    bookDto.setDescription("The characteristics of someone or something");
    bookDto.setGenre("Genre");
    bookDto.setIsbn("Isbn");
    bookDto.setName("Name");
    when(bookService.getBookByIsbn(Mockito.<String>any())).thenReturn(bookDto);
    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders.get("/library/books/isbn/{isbn}", "Isbn");

    // Act and Assert
    MockMvcBuilders.standaloneSetup(bookControllerImpl)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content()
            .string(
                "{\"isbn\":\"Isbn\",\"name\":\"Name\",\"genre\":\"Genre\",\"description\":\"The characteristics of someone or"
                    + " something\",\"author\":\"JaneDoe\"}"));
  }

  @Test
  void testGetAllBooks() throws Exception {
    when(bookService.getAllBooks()).thenReturn(new ArrayList<>());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/library/books");

    MockMvcBuilders.standaloneSetup(bookControllerImpl)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content().string("[]"));
  }

  @Test
  void testCreateBook() throws Exception {
    doNothing().when(bookService).createBook(Mockito.<BookDto>any());

    BookDto bookDto = new BookDto();
    bookDto.setAuthor("JaneDoe");
    bookDto.setDescription("The characteristics of someone or something");
    bookDto.setGenre("Genre");
    bookDto.setIsbn("Isbn");
    bookDto.setName("Name");
    String content = (new ObjectMapper()).writeValueAsString(bookDto);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/library/books")
        .contentType(MediaType.APPLICATION_JSON)
        .content(content);

    MockMvcBuilders.standaloneSetup(bookControllerImpl)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content()
            .string(
                "{\"isbn\":\"Isbn\",\"name\":\"Name\",\"genre\":\"Genre\",\"description\":\"The characteristics of someone or"
                    + " something\",\"author\":\"JaneDoe\"}"));
  }

  @Test
  void testGetBooksByIds() throws Exception {
    when(bookService.getBooksByIds(Mockito.<List<Long>>any())).thenReturn(new ArrayList<>());
    MockHttpServletRequestBuilder contentTypeResult =
        MockMvcRequestBuilders.post("/library/books/by-ids")
            .contentType(MediaType.APPLICATION_JSON);

    ObjectMapper objectMapper = new ObjectMapper();
    MockHttpServletRequestBuilder requestBuilder = contentTypeResult
        .content(objectMapper.writeValueAsString(new ArrayList<>()));

    MockMvcBuilders.standaloneSetup(bookControllerImpl)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content().string("[]"));
  }

  @Test
  void testUpdateBook() throws Exception {
    doNothing().when(bookService).updateBook(Mockito.<Long>any(), Mockito.<BookDto>any());

    BookDto bookDto = new BookDto();
    bookDto.setAuthor("JaneDoe");
    bookDto.setDescription("The characteristics of someone or something");
    bookDto.setGenre("Genre");
    bookDto.setIsbn("Isbn");
    bookDto.setName("Name");
    String content = (new ObjectMapper()).writeValueAsString(bookDto);
    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders.put("/library/books/{id}", 1L)
            .contentType(MediaType.APPLICATION_JSON)
            .content(content);

    MockMvcBuilders.standaloneSetup(bookControllerImpl)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content()
            .string(
                "{\"isbn\":\"Isbn\",\"name\":\"Name\",\"genre\":\"Genre\",\"description\":\"The characteristics of someone or"
                    + " something\",\"author\":\"JaneDoe\"}"));
  }

  @Test
  void testDeleteBook() throws Exception {
    doNothing().when(bookService).deleteBook(Mockito.<Long>any());
    MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/library/books");
    MockHttpServletRequestBuilder requestBuilder = deleteResult.param("id", String.valueOf(1L));

    MockMvcBuilders.standaloneSetup(bookControllerImpl)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk());
  }
}
