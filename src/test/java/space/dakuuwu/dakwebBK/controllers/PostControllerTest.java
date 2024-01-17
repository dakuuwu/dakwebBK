package space.dakuuwu.dakwebBK.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import space.dakuuwu.dakwebBK.configs.SecurityConfig;
import space.dakuuwu.dakwebBK.models.Post;
import space.dakuuwu.dakwebBK.repos.PostsRepository;
import space.dakuuwu.dakwebBK.services.DataValidationService;

@ExtendWith(SpringExtension.class)
@Import(SecurityConfig.class)
@WebMvcTest(controllers = PostController.class)
@WebAppConfiguration
class PostControllerTest {

    private MockMvc mvc;

    @MockBean
    private PostsRepository postsRepository;
    @MockBean
    private DataValidationService dvs;
    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setup() {

        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();

    }

    @Test
    @DisplayName("This test tries to create a new post without proper identification. Expected: 401")
    public void createPost() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Post p = new Post("PostControllerTest1",
                new Post.PostContent("PC Test CREATE",
                        "imgCreate.png",
                        "dakuuwu.space",
                        "shortDesc",
                        "longDesclongDesc"),
                new String[]{"PCCreate", "PCTest"});
        String jsonPost = objectMapper.writeValueAsString(p);
        mvc.perform(MockMvcRequestBuilders.post("/newpost")
                        .content(jsonPost).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @DisplayName("This test attempts to connect to a public GET API. Expected: 200")
    void getAllPosts() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/posts")).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("This test tries to update a post without proper identification. Expected: 401")
    void updatePost() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Post p = new Post("PostControllerTest3",
                new Post.PostContent("PC Test UPDATE",
                        "imgUpdate.png",
                        "dakuuwu.space",
                        "shortDesc",
                        "longDesclongDesc"),
                new String[]{"PCUpdate", "PCTest"});
        String jsonPost = objectMapper.writeValueAsString(p);
        mvc.perform(MockMvcRequestBuilders.put("/updatepost/"+p.getId())
                        .content(jsonPost).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @DisplayName("This test tries to delete a post without proper identification. Expected: 401")
    void deletePost() throws Exception {
        Post p = new Post("PostControllerTest4",
                null,
                null);
        mvc.perform(MockMvcRequestBuilders.delete("/deletepost/"+p.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }
}