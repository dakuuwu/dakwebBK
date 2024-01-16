package space.dakuuwu.dakwebBK.services;

import org.springframework.stereotype.Service;
import space.dakuuwu.dakwebBK.models.Post;

@Service
public class DataValidationService {
    public Boolean keyFieldValidator(Post p) {
        if (p.getContent().title().isBlank() || p.getContent().title() == null) {
            return false;
        } else if (p.getContent().imageUrl().isBlank() || p.getContent().imageUrl() == null) {
            return false;
        } else return p.getTags().length != 0 && p.getTags() != (null);
    }
}
