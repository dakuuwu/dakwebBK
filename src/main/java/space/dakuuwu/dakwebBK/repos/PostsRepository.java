package space.dakuuwu.dakwebBK.repos;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import space.dakuuwu.dakwebBK.models.Post;

import java.util.List;

//Initializes mongodb repository.
public interface PostsRepository extends MongoRepository<Post, String> {

    @Query("{content.title:'?0'}")
    Post findItemByName(String name);

    @Query(value = "{tags:'?0'}", fields = "{'content' : 1 }")
    List<Post> findAll(String tags);

}
