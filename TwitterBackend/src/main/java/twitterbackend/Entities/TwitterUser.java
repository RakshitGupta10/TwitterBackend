package twitterbackend.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "user_accounts") // Enclose the table name in quotes
public class TwitterUser {


    @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long ID;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = false)
    private String name;


    @JsonIgnore
    @OneToMany(mappedBy = "userID", cascade = CascadeType.ALL)
    private List<TwitterPost> posts = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "userID", cascade = CascadeType.ALL)
    private List<TwitterComment> comments = new ArrayList<>();



    @Override
    public String toString() {
        return "User{" +
                "id=" + ID +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                '}';
    }


}
