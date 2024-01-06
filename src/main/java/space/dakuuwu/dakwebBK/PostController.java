package space.dakuuwu.dakwebBK;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api")
public class PostController {

    @GetMapping("/post")
    public String test(){
        return "post!";
    }
}
