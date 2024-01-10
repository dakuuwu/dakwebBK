package space.dakuuwu.dakwebBK.models;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

//Structures the data to use.
@Document(collection = "posts")
public class Post {
    private String id;
    private PostContent content;
    private String[] tags;

    public Post(String id, PostContent content, String[] tags) {
        this.id = id;
        this.content = content;
        this.tags = tags;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PostContent getContent() {
        return content;
    }

    public void setContent(PostContent content) {
        this.content = content;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    //Inner class defined as a record for ease of use, requires the Post class to work. Structures the content object.
    public record PostContent(String title, String imageurl, String smalldesc, String longdesc) {
        public PostContent {
            Objects.requireNonNull(title);
            Objects.requireNonNull(imageurl);
            Objects.requireNonNull(smalldesc);
        }
    }

}


