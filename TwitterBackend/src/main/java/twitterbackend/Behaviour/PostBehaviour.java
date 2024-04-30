package twitterbackend.Behaviour;


import twitterbackend.Entities.TwitterUser;
import twitterbackend.Repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitterbackend.Entities.TwitterPost;

import java.util.Date;
import java.util.List;

@Service
public class PostBehaviour {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserBehaviour userService;

    public String create(Long userID, String postBody){
        TwitterUser user = userService.getUserById(userID);
        if (user == null) {
            return "User does not exist";
        }else{
            TwitterPost post = new TwitterPost();
            post.setContent(postBody);
            post.setUserID(user);
            post.setCreatedAt(new Date());
            postRepository.save(post);
            return "Post created successfully";
        }
    }

    public TwitterPost getPostById(Long postID) {
        return postRepository.findById(postID).orElse(null);
    }

    public String updatePost(Long postID, String postContent) {
        TwitterPost post = postRepository.findById(postID).orElse(null);
        if (post == null) {
            return "Post does not exist";
        } else {
            post.setContent(postContent);
            postRepository.save(post);
            return "Post edited successfully";
        }
    }

    public String deletePost(long postID) {
        TwitterPost post = postRepository.findById(postID).orElse(null);
        if (post == null) {
            return "Post does not exist";
        } else {
            postRepository.delete(post);
            return "Post deleted";
        }
    }

    public List<TwitterPost> getAllPostsSortedByDateDesc() {
        return postRepository.findAllByOrderByCreatedAtDesc();
    }
}
