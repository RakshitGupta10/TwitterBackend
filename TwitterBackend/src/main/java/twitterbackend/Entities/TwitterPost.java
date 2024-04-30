package twitterbackend.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Setter
@Getter
@Entity
public class TwitterPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private Long ID;

    @Column(name="post_content", nullable = false)
    private String content;

    @Column(name="created_at", nullable = false)
    private Date createdAt;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private TwitterUser userID;

    // Setup relation between Post and Comment
    @OneToMany(mappedBy = "postID", cascade = CascadeType.ALL)
    private List<TwitterComment> comments = new ArrayList<>();



    @Override
    public String toString() {
        return "Post{" +
                "id=" + ID +
                ", content='" + content + '\'' +
                ", userId=" + userID +
                '}';
    }
}
