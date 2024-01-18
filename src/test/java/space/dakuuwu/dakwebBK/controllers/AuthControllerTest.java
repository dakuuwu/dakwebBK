package space.dakuuwu.dakwebBK.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import space.dakuuwu.dakwebBK.configs.SecurityConfig;
import space.dakuuwu.dakwebBK.configs.UserProperties;
import space.dakuuwu.dakwebBK.repos.PostsRepository;
import space.dakuuwu.dakwebBK.services.TokenService;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;

@Import(SecurityConfig.class)
@WebMvcTest(controllers = AuthController.class)
class AuthControllerTest {
    private MockMvc mvc;
    @MockBean
    private TokenService tokenService;
    @MockBean
    private PostsRepository postsRepository;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private UserProperties userProperties;

    @BeforeEach
    public void setup() {

        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();

    }

    @Test
    @DisplayName("Token generation endpoint is reached without credentials. Expected: 400")
    void fetchTokenWithoutCredentials() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .post("/token"))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .isBadRequest());

    }

    @Test
    @DisplayName("Token generation endpoint is reached with invalid credentials. Expected: 401")
    void fetchTokenWithBadCredentials() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .post("/token").with(httpBasic("user", "password")))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .isUnauthorized());
    }

    @Test
    @DisplayName("Token generation endpoint is reached with valid credentials. Expected: 200")
    void fetchTokenWithValidCredentials() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .post("/token").with(httpBasic(userProperties.username(), userProperties.password())))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .isOk());
    }
}