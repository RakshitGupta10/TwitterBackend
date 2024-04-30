package twitterbackend.Repository;


import twitterbackend.Entities.TwitterUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<TwitterUser, Long> {
    TwitterUser findByEmail(String email);
}