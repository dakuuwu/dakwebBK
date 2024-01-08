package space.dakuuwu.dakwebBK;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import space.dakuuwu.dakwebBK.model.Post;
import space.dakuuwu.dakwebBK.repos.PostsRepository;

import java.util.List;

@RestController
@RequestMapping("/api")
@Validated
public class PostController {

    @Autowired
    private PostsRepository postsRepo;

    //C
    @PostMapping("/newpost")
    public ResponseEntity<Post> createPost(@RequestBody Post p) {
        Post savedPost = postsRepo.save(p);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPost);
    }

    //R
    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> postList = postsRepo.findAll();
        return ResponseEntity.ok(postList);
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<Post> getPostbyID(@PathVariable String id) {
        return postsRepo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    //U
    @PutMapping("/updatepost/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable String id, @RequestBody Post updatedPost) {
        return postsRepo.findById(id)
                .map(existingPost -> {
                    existingPost.setContent(updatedPost.getContent());
                    existingPost.setTags(updatedPost.getTags());
                    Post savedPost = postsRepo.save(existingPost);
                    return ResponseEntity.ok(savedPost);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    //D
    @DeleteMapping("/deletepost/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable String id) {
        postsRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
