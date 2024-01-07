package space.dakuuwu.dakwebBK.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document(collection = "posts")
public class Post {
    private final ObjectId objectId;
    private final PostContent content;
    private final String[] tags;

    public Post(PostContent content, String[] tags) {
        this.objectId = new ObjectId();
        this.content = content;
        this.tags = tags;
    }

    public ObjectId getObjectId(){
        return objectId;
    }

    public PostContent getContent() {
        return content;
    }

    public String[] getTags() {
        return tags;
    }

    public record PostContent(String title, String imageurl, String smalldesc, String longdesc) {
        public PostContent {
            Objects.requireNonNull(title);
            Objects.requireNonNull(imageurl);
            Objects.requireNonNull(smalldesc);
        }
    }

}


