package space.dakuuwu.dakwebBK.services;

import org.springframework.stereotype.Service;
import space.dakuuwu.dakwebBK.models.Post;

import java.util.Arrays;

@Service
public class DataValidationService {
    public Boolean titleFieldValidator(Post p) {
        return p.getContent().title() != null &&
                !p.getContent().title().isBlank();
    }

    public Boolean imageUrlFieldValidator(Post p) {
        return p.getContent().imageUrl() != null &&
                !p.getContent().imageUrl().isBlank();
    }

    public Boolean shortDescFieldValidator(Post p) {
        return p.getContent().shortDesc() != null &&
                !p.getContent().shortDesc().isBlank();
    }

    public Boolean tagFieldValidator(Post p) {
        return p.getTags() != null &&
                p.getTags().length != 0 &&
                Arrays.stream(p.getTags()).noneMatch(String::isBlank);
    }

    public boolean keyFieldValidator(Post p) {
        return titleFieldValidator(p) &&
                imageUrlFieldValidator(p) &&
                shortDescFieldValidator(p) &&
                tagFieldValidator(p);
    }
}
