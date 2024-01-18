package space.dakuuwu.dakwebBK.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.shaded.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import space.dakuuwu.dakwebBK.configs.SecurityConfig;
import space.dakuuwu.dakwebBK.models.Post;
import space.dakuuwu.dakwebBK.repos.PostsRepository;
import space.dakuuwu.dakwebBK.services.DataValidationService;

import java.util.Optional;

@Import(SecurityConfig.class)
@WebMvcTest(controllers = PostController.class)

class PostControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PostsRepository postsRepository;
    @SpyBean
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
        Gson gson = new Gson();
        Post p = new Post("PostControllerTest2",
                new Post.PostContent("PC Test CREATE",
                        "imgCreate.png",
                        "dakuuwu.space",
                        "shortDesc",
                        "longDesclongDesc"),
                new String[]{"PCCreate", "PCTest"});
        String json = gson.toJson(p);
        mvc.perform(MockMvcRequestBuilders.post("/newpost")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @DisplayName("This test tries to delete a post without proper identification. Expected: 401")
    void deletePost() throws Exception {
        Post p = new Post("PostControllerTest4",
                null,
                null);
        mvc.perform(MockMvcRequestBuilders.delete("/deletepost/" + p.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }


    @Test
    @WithMockUser
    @DisplayName("This test tries to create a new post with proper identification. Expected: 201")
    public void createPostWithCredentials() throws Exception {
        Gson gson = new Gson();
        Post p = new Post("PostControllerTest5",
                new Post.PostContent("PC Test CREATE with Credentials",
                        "imgCreate.png",
                        "dakuuwu.space",
                        "shortDesc",
                        "longDesclongDesc"),
                new String[]{"PCCreate", "PCTest"});
        String json = gson.toJson(p);
        mvc.perform(MockMvcRequestBuilders.post("/newpost")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @WithMockUser
    @DisplayName("This test tries to update a post with proper identification. Expected: 200")
    void updatePostWithCredentials() throws Exception {
        Gson gson = new Gson();
        Post p = new Post("PostControllerTest6",
                new Post.PostContent("PC Test UPDATE with Credentials",
                        "imgUpdate.png",
                        "dakuuwu.space",
                        "shortDesc",
                        "longDesclongDesc"),
                new String[]{"PCUpdate", "PCTest"});
        String jsonuP = gson.toJson(p);
        Mockito.when(postsRepository.findById(p.getId())).thenReturn(Optional.of(p));
        mvc.perform(MockMvcRequestBuilders.put("/updatepost/{id}", p.getId())
                        .content(jsonuP)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser
    @DisplayName("This test tries to delete a post with proper identification. Expected: 204")
    void deletePostWithCredentials() throws Exception {
        Post p = new Post("PostControllerTest7",
                null,
                null);
        mvc.perform(MockMvcRequestBuilders.delete("/deletepost/" + p.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}