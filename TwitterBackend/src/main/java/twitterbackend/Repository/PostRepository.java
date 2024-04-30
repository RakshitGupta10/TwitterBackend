package twitterbackend.Repository;

import twitterbackend.Entities.TwitterPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<TwitterPost, Long> {

    List<TwitterPost> findAllByOrderByCreatedAtDesc();
}
