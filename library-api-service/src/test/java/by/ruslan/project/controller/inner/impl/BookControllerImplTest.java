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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {BookControllerImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
@ActiveProfiles("Book controller tests")
class BookControllerImplTest {
  @Autowired
  private BookControllerImpl bookControllerImpl;

  @MockBean
  private BookService bookService;

  @Test
  void testGetBookByIsbn() throws Exception {
    when(bookService.getBookByIsbn(Mockito.<String>any()))
        .thenReturn(
            new BookDto("Isbn", "Name", "Genre", "The characteristics of someone or something",
                "JaneDoe"));
    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders.get("/library/books/get/{isbn}", "Isbn");

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

    ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(bookControllerImpl)
        .build()
        .perform(requestBuilder);

    actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
  }

  @Test
  void testCreateBook2() throws Exception {
    doNothing().when(bookService).createBook(Mockito.<BookDto>any());

    BookDto bookDto = new BookDto();
    bookDto.setAuthor("JaneDoe");
    bookDto.setDescription("The characteristics of someone or something");
    bookDto.setGenre("Genre");
    bookDto.setIsbn("978-9-999-99999-9");
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
                "{\"isbn\":\"978-9-999-99999-9\",\"name\":\"Name\",\"genre\":\"Genre\",\"description\":\"The characteristics of someone"
                    + " or something\",\"author\":\"JaneDoe\"}"));
  }

  @Test
  void testGetBooksByIsbns() throws Exception {
    when(bookService.getBooksByIsbns(Mockito.<List<String>>any())).thenReturn(new ArrayList<>());
    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders.get("/library/books/{isbns}",
            new ArrayList<>());

    MockMvcBuilders.standaloneSetup(bookControllerImpl)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content().string("[]"));
  }

  @Test
  void testGetBooksByIsbns2() throws Exception {
    when(bookService.getAllBooks()).thenReturn(new ArrayList<>());
    when(bookService.getBooksByIsbns(Mockito.<List<String>>any())).thenReturn(new ArrayList<>());
    new ArrayList<>();
    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders.get("/library/books/{isbns}", "",
            "Uri Variables");

    MockMvcBuilders.standaloneSetup(bookControllerImpl)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content().string("[]"));
  }

  @Test
  void testUpdateBook2() throws Exception {
    doNothing().when(bookService).updateBook(Mockito.<String>any(), Mockito.<BookDto>any());

    BookDto bookDto = new BookDto();
    bookDto.setAuthor("JaneDoe");
    bookDto.setDescription("The characteristics of someone or something");
    bookDto.setGenre("Genre");
    bookDto.setIsbn("978-9-999-99999-9");
    bookDto.setName("Name");
    String content = (new ObjectMapper()).writeValueAsString(bookDto);
    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders.put("/library/books/{isbn}", "Isbn")
            .contentType(MediaType.APPLICATION_JSON)
            .content(content);

    MockMvcBuilders.standaloneSetup(bookControllerImpl)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content()
            .string(
                "{\"isbn\":\"978-9-999-99999-9\",\"name\":\"Name\",\"genre\":\"Genre\",\"description\":\"The characteristics of someone"
                    + " or something\",\"author\":\"JaneDoe\"}"));
  }

  @Test
  void testDeleteBook() throws Exception {
    doNothing().when(bookService).deleteBook(Mockito.<String>any());
    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders.delete("/library/books").param("isbn", "foo");

    MockMvcBuilders.standaloneSetup(bookControllerImpl)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  void testUpdateBook() throws Exception {
    BookDto bookDto = new BookDto();
    bookDto.setAuthor("JaneDoe");
    bookDto.setDescription("The characteristics of someone or something");
    bookDto.setGenre("Genre");
    bookDto.setIsbn("Isbn");
    bookDto.setName("Name");
    String content = (new ObjectMapper()).writeValueAsString(bookDto);
    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders.put("/library/books/{isbn}", "Isbn")
            .contentType(MediaType.APPLICATION_JSON)
            .content(content);

    ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(bookControllerImpl)
        .build()
        .perform(requestBuilder);

    actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
  }
}
