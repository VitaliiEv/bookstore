package vitaliiev.bookstore.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import vitaliiev.bookstore.IntegrationTest;
import vitaliiev.bookstore.entity.Author;
import vitaliiev.bookstore.entity.Book;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@IntegrationTest
@AutoConfigureMockMvc
class AuthorRepositoryTest {

    private static final String API_PREFIX = "/api/authors";

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void author_add() throws Exception {
        Author author = createAuthor("test", null);
        ResultMatcher[] matchers = new ResultMatcher[]{
                status().isCreated(),
                jsonPath("$.uuid").exists(),
                jsonPath("$.name", is("test")),
                jsonPath("$.books").doesNotExist()
        };

        MvcResult mvcResult = mockMvc.perform(post(API_PREFIX)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(author)))
                .andExpectAll(matchers)
                .andReturn();
        String uuid = JsonPath.parse(mvcResult.getResponse().getContentAsString()).read("$.uuid", String.class);

        mockMvc.perform(get(API_PREFIX + "/" + uuid))
                .andExpectAll(status().isOk(), content().contentType("application/hal+json"));

    }

    private Author createAuthor(String name, List<Book> books) {
        Author author = new Author();
        author.setName(name);
        author.setBooks(books);
        return author;
    }
}