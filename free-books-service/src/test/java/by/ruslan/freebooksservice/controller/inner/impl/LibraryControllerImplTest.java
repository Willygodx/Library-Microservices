package by.ruslan.freebooksservice.controller.inner.impl;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import by.ruslan.freebooksservice.dto.LibraryDto;
import by.ruslan.freebooksservice.service.impl.LibraryServiceImpl;
import java.time.LocalDate;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {LibraryControllerImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class LibraryControllerImplTest {
  @Autowired
  private LibraryControllerImpl libraryControllerImpl;

  @MockBean
  private LibraryServiceImpl libraryServiceImpl;

  @Test
  void testAddBook() throws Exception {
    LibraryDto libraryDto = new LibraryDto();
    libraryDto.setBorrowedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    libraryDto.setIsbn("Isbn");
    libraryDto.setReturnBy(LocalDate.of(1970, 1, 1).atStartOfDay());
    when(libraryServiceImpl.addBook(Mockito.<String>any())).thenReturn(libraryDto);
    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders.post("/free-books").param("isbn", "foo");

    MockMvcBuilders.standaloneSetup(libraryControllerImpl)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content()
            .string(
                "{\"isbn\":\"Isbn\",\"borrowedAt\":[1970,1,1,0,0],\"returnBy\":[1970,1,1,0,0]}"));
  }

  @Test
  void testGetFreeBooks() throws Exception {
    when(libraryServiceImpl.getFreeBooks()).thenReturn(new ArrayList<>());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/free-books");

    MockMvcBuilders.standaloneSetup(libraryControllerImpl)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content().string("[]"));
  }

  @Test
  void testDeleteBook() throws Exception {
    doNothing().when(libraryServiceImpl).deleteBook(Mockito.<String>any());
    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders.delete("/free-books").param("isbn", "foo");

    MockMvcBuilders.standaloneSetup(libraryControllerImpl)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  void testReturnBook() throws Exception {
    LibraryDto libraryDto = new LibraryDto();
    libraryDto.setBorrowedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    libraryDto.setIsbn("Isbn");
    libraryDto.setReturnBy(LocalDate.of(1970, 1, 1).atStartOfDay());
    when(libraryServiceImpl.returnBook(Mockito.<String>any())).thenReturn(libraryDto);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/free-books/return")
        .param("isbn", "foo");

    MockMvcBuilders.standaloneSetup(libraryControllerImpl)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content()
            .string(
                "{\"isbn\":\"Isbn\",\"borrowedAt\":[1970,1,1,0,0],\"returnBy\":[1970,1,1,0,0]}"));
  }

  @Test
  void testTakeBook() throws Exception {
    LibraryDto libraryDto = new LibraryDto();
    libraryDto.setBorrowedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    libraryDto.setIsbn("Isbn");
    libraryDto.setReturnBy(LocalDate.of(1970, 1, 1).atStartOfDay());
    when(libraryServiceImpl.updateBook(Mockito.<String>any())).thenReturn(libraryDto);
    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders.put("/free-books/take").param("isbn", "foo");

    MockMvcBuilders.standaloneSetup(libraryControllerImpl)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content()
            .string(
                "{\"isbn\":\"Isbn\",\"borrowedAt\":[1970,1,1,0,0],\"returnBy\":[1970,1,1,0,0]}"));
  }
}
