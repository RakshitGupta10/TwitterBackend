package twitterbackend.Repository;

import twitterbackend.Entities.TwitterComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<TwitterComment, Long>{
}
