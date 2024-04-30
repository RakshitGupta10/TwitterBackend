package twitterbackend.Behaviour;

import twitterbackend.Entities.TwitterComment;
import twitterbackend.Entities.TwitterPost;
import twitterbackend.Entities.TwitterUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitterbackend.Repository.CommentRepository;

@Service
public class CommentBehaviour {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostBehaviour postService;
    @Autowired
    private UserBehaviour userService;

    public String create(long postID, long userID, String commentBody){
        TwitterPost post = postService.getPostById(postID);
        if (post == null) {
            return "Post does not exist";
        }else{
            TwitterUser user = userService.getUserById(userID);
            if (user == null) {
                return "User does not exist";
            }else{
                TwitterComment comment = new TwitterComment();
                comment.setContent(commentBody);
                comment.setPostID(post);
                comment.setUserID(user);
                commentRepository.save(comment);
                return "Comment created successfully";
            }
        }
    }

    public TwitterComment getCommentById(Long commentID) {
        return commentRepository.findById(commentID).orElse(null);
    }

    public String updateComment(Long commentID, String commentContent) {
        TwitterComment comment = commentRepository.findById(commentID).orElse(null);
        if (comment == null) {
            return "Comment does not exist";
        } else {
            comment.setContent(commentContent);
            commentRepository.save(comment);
            return "Comment edited successfully";
        }
    }

    public String deleteComment(long commentID) {
        TwitterComment comment = commentRepository.findById(commentID).orElse(null);
        if (comment == null) {
            return "Comment does not exist";
        } else {
            commentRepository.delete(comment);
            return "Comment deleted";
        }
    }
}
