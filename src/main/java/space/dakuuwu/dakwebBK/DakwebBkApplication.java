package space.dakuuwu.dakwebBK;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import space.dakuuwu.dakwebBK.model.Post;
import space.dakuuwu.dakwebBK.repos.PostsRepository;

import java.util.Arrays;

@SpringBootApplication
@EnableMongoRepositories
public class DakwebBkApplication implements CommandLineRunner {

	@Autowired
	PostsRepository postsRepo;

	public static void main(String[] args) {
		SpringApplication.run(DakwebBkApplication.class, args);
	}

	//CREATE
	void createPost(){
		System.out.println("Data creation started...");
		postsRepo.save(new Post(new Post.PostContent("Test Post",
				"/img.png",
				"This is a test small description!",
				"This is a test long description! In this one we will fit more text that might be used when the view allows it! Maybe I can ramble here and explain thought processes." ),
				new String[]{"Tag2", "Tag3"}));
		System.out.println("Data creation finished.");

		postsRepo.findAll().forEach(post -> System.out.println(getPostDetails(post)));
	}

	public String getPostDetails(Post p) {

		System.out.println(
				"\nPost Title: " + p.getContent().title() +
						"\nShort Desc: " + p.getContent().smalldesc() +
						"\nTags: " + Arrays.toString(p.getTags()) + "\nID: " + p.getObjectId().toString()
		);

		return "";
	}
	@Override
	public void run(String... args) throws Exception {

		createPost();
	}
}
