package space.dakuuwu.dakwebBK.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Objects;

//This class is here for legacy purposes. Not instrumental to the project, it was used to test the API functionality.

@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/test")
    @ResponseBody
    public String JWTTest(Principal principal){
        return "omg hi " + principal.getName();
    }
//        public TestResponse reply(){
//        return new TestResponse("t-1", new TestResponseContent("Test Post",
//                "/img.png",
//                "This is a test small description!",
//                "This is a test long description! In this one we will fit more text that might be used when the view allows it! Maybe I can ramble here and explain thought processes." ),
//                new String[]{"Tag1", "Tag2", "Tag3"});
//    }


    public record TestResponse(String id, TestResponseContent content, String[] tags) {
        public TestResponse {
            Objects.requireNonNull(id);
            Objects.requireNonNull(content);
            Objects.requireNonNull(tags);
        }
    }

    private record TestResponseContent(String title, String imageurl, String smalldesc, String longdesc) {
        private TestResponseContent {
            Objects.requireNonNull(title);
            Objects.requireNonNull(imageurl);
            Objects.requireNonNull(smalldesc);
        }
    }
}
