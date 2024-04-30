package twitterbackend.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class TwitterComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long ID;

    @Column(name = "content", nullable = false)
    private String content;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private TwitterPost postID;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private TwitterUser userID;




}
