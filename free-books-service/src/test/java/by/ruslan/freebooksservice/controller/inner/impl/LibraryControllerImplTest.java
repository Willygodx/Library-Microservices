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
    libraryDto.setBookId(1L);
    libraryDto.setBorrowedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    libraryDto.setReturnBy(LocalDate.of(1970, 1, 1).atStartOfDay());
    when(libraryServiceImpl.addBook(Mockito.<Long>any())).thenReturn(libraryDto);
    MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/free-books");
    MockHttpServletRequestBuilder requestBuilder = postResult.param("bookId", String.valueOf(1L));

    MockMvcBuilders.standaloneSetup(libraryControllerImpl)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content()
            .string("{\"bookId\":1,\"borrowedAt\":[1970,1,1,0,0],\"returnBy\":[1970,1,1,0,0]}"));
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
    doNothing().when(libraryServiceImpl).deleteBook(Mockito.<Long>any());
    MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/free-books");
    MockHttpServletRequestBuilder requestBuilder = deleteResult.param("bookId", String.valueOf(1L));

    MockMvcBuilders.standaloneSetup(libraryControllerImpl)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  void testReturnBook() throws Exception {
    LibraryDto libraryDto = new LibraryDto();
    libraryDto.setBookId(1L);
    libraryDto.setBorrowedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    libraryDto.setReturnBy(LocalDate.of(1970, 1, 1).atStartOfDay());
    when(libraryServiceImpl.returnBook(Mockito.<Long>any())).thenReturn(libraryDto);
    MockHttpServletRequestBuilder putResult = MockMvcRequestBuilders.put("/free-books/return");
    MockHttpServletRequestBuilder requestBuilder = putResult.param("bookId", String.valueOf(1L));

    MockMvcBuilders.standaloneSetup(libraryControllerImpl)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content()
            .string("{\"bookId\":1,\"borrowedAt\":[1970,1,1,0,0],\"returnBy\":[1970,1,1,0,0]}"));
  }

  @Test
  void testTakeBook() throws Exception {
    LibraryDto libraryDto = new LibraryDto();
    libraryDto.setBookId(1L);
    libraryDto.setBorrowedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    libraryDto.setReturnBy(LocalDate.of(1970, 1, 1).atStartOfDay());
    when(libraryServiceImpl.updateBook(Mockito.<Long>any())).thenReturn(libraryDto);
    MockHttpServletRequestBuilder putResult = MockMvcRequestBuilders.put("/free-books/take");
    MockHttpServletRequestBuilder requestBuilder = putResult.param("bookId", String.valueOf(1L));

    MockMvcBuilders.standaloneSetup(libraryControllerImpl)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content()
            .string("{\"bookId\":1,\"borrowedAt\":[1970,1,1,0,0],\"returnBy\":[1970,1,1,0,0]}"));
  }
}
