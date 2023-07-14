package com.example.springcloudaws;

import com.example.springcloudaws.domain.post.Post;
import com.example.springcloudaws.domain.post.PostRepository;
import com.example.springcloudaws.domain.user.User;
import com.example.springcloudaws.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.ArrayList;

@SpringBootApplication
@EnableJpaAuditing
public class SpringCloudAwsApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudAwsApplication.class, args);
    }

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Override
    public void run(String... args) {
        User user = User.builder()
                .nickname("kimsikhong")
                .email("foobar@gmail.com")
                .description("desc")
                .build();
        userRepository.save(user);

        ArrayList<Post> posts = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Post post = Post.builder()
                    .title("title - " + i)
                    .description("desc")
                    .userId(user.getId())
                    .build();
            posts.add(post);
        }
        postRepository.saveAll(posts);
    }
}
