package space.dakuuwu.dakwebBK;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/test")
    @ResponseBody
        public TestResponse reply(){
        return new TestResponse("t-1", new TestResponseContent("Test Post",
                "/img.png",
                "This is a test small description!",
                "This is a test long description! In this one we will fit more text that might be used when the view allows it! Maybe I can ramble here and explain thought processes." ),
                new String[]{"Tag1", "Tag2", "Tag3"});
    }


    public static class TestResponse {

        private final String id;
        private final TestResponseContent content;
        private final String[] tags;

        public TestResponse(String id, TestResponseContent content, String[] tags) {
            this.id = id;
            this.content = content;
            this.tags = tags;
        }

        public String getId() {
            return id;
        }

        public TestResponseContent getContent() {
            return content;
        }

        public String[] getTags() {
            return tags;
        }
    }

    public static class TestResponseContent{
        private final String title;
        private final String imageurl;
        private final String smalldesc;
        private final String longdesc;

        public TestResponseContent(String title, String imageurl, String smalldesc, String longdesc) {
            this.title = title;
            this.imageurl = imageurl;
            this.smalldesc = smalldesc;
            this.longdesc = longdesc;
        }

        public String getTitle() {
            return title;
        }

        public String getImageurl() {
            return imageurl;
        }

        public String getSmalldesc() {
            return smalldesc;
        }

        public String getLongdesc() {
            return longdesc;
        }
    }
}
