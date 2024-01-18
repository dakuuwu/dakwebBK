package space.dakuuwu.dakwebBK.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import space.dakuuwu.dakwebBK.models.Post;
import space.dakuuwu.dakwebBK.repos.PostsRepository;
import space.dakuuwu.dakwebBK.services.DataValidationService;

import java.util.List;

@RestController
@RequestMapping("/")
@Validated
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.OPTIONS, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class PostController {

    private final PostsRepository postsRepository;
    private final DataValidationService dvs;

    public PostController(PostsRepository postsRepository, DataValidationService dvs) {
        this.postsRepository = postsRepository;
        this.dvs = dvs;
    }

    //C
    @PostMapping("/newpost")
    public ResponseEntity<Post> createPost(@RequestBody Post p) {
        if (dvs.keyFieldValidator(p)) {
            Post savedPost = postsRepository.save(p);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedPost);

        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    //R
    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> postList = postsRepository.findAll();
        return ResponseEntity.ok(postList);
    }

    //U
    @PutMapping("/updatepost/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable String id, @RequestBody Post updatedPost) {
        if (dvs.keyFieldValidator(updatedPost)) {
            return postsRepository.findById(id)
                    .map(existingPost -> {
                        existingPost.setContent(updatedPost.getContent());
                        existingPost.setTags(updatedPost.getTags());
                        Post savedPost = postsRepository.save(existingPost);
                        return ResponseEntity.ok(savedPost);
                    })
                    .orElse(ResponseEntity.notFound().build());
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    //D
    @DeleteMapping("/deletepost/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable String id) {
        postsRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
