package twitterbackend.Manager;

import twitterbackend.Entities.TwitterComment;
import twitterbackend.Entities.TwitterPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import twitterbackend.Behaviour.PostBehaviour;
import twitterbackend.Entities.TwitterUser;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class FeedManager {

    @Autowired
    private PostBehaviour postBehaviour;

    // Wrapper function to handle requests for user feed
    @GetMapping("/")
    public ResponseEntity<?> getUserFeed() {
        // Retrieve posts sorted by date descending
        List<TwitterPost> posts = getPostsSortedByDate();

        // Prepare updated response with formatted posts
        Map<String, Object> responseBody = prepareResponse(posts);

        // Return response entity
        return ResponseEntity.ok(responseBody);
    }

    // Function to retrieve posts sorted by date in descending order
    private List<TwitterPost> getPostsSortedByDate() {
        return postBehaviour.getAllPostsSortedByDateDesc();
    }

    // Function to prepare response body with formatted posts
    private Map<String, Object> prepareResponse(List<TwitterPost> posts) {
        List<Map<String, Object>> updatedUploads = new ArrayList<>();
        for (TwitterPost upload : posts) {
            // Prepare details of each post
            Map<String, Object> updatedUpload = preparePostDetails(upload);
            updatedUploads.add(updatedUpload);
        }
        // Prepare final response body
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("posts", updatedUploads);
        return responseBody;
    }

    // Function to prepare details of each post
    private Map<String, Object> preparePostDetails(TwitterPost upload) {
        Map<String, Object> updatedUpload = new LinkedHashMap<>(); // Use LinkedHashMap to maintain insertion order

        updatedUpload.put("postID", upload.getID());
        updatedUpload.put("postBody", upload.getContent());

        // Format date as "yyyy-MM-dd"
        SimpleDateFormat dateBody = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateBody.format(upload.getCreatedAt());
        updatedUpload.put("date", formattedDate);

        // Prepare comments for the post
        List<Map<String, Object>> updatedComments = prepareComments(upload.getComments());
        updatedUpload.put("comments", updatedComments);

        return updatedUpload;
    }

    // Function to prepare comments for a post
    private List<Map<String, Object>> prepareComments(List<TwitterComment> comments) {
        List<Map<String, Object>> updatedComments = new ArrayList<>();
        for (TwitterComment message : comments) {
            // Prepare details of each comment
            Map<String, Object> updatedComment = prepareCommentDetails(message);
            updatedComments.add(updatedComment);
        }
        return updatedComments;
    }

    // Function to prepare details of each comment
    private Map<String, Object> prepareCommentDetails(TwitterComment message) {
        Map<String, Object> updatedComment = new LinkedHashMap<>();
        updatedComment.put("commentID", message.getID());
        updatedComment.put("commentBody", message.getContent());

        // Prepare details of comment creator
        Map<String, Object> commentMaker = prepareCommentCreatorDetails(message.getUserID());
        updatedComment.put("commentCreator", commentMaker);

        return updatedComment;
    }

    // Function to prepare details of comment creator
    private Map<String, Object> prepareCommentCreatorDetails(TwitterUser userID) {
        Map<String, Object> commentMaker = new LinkedHashMap<>();
        commentMaker.put("userID", userID.getID());
        commentMaker.put("name", userID.getName());
        return commentMaker;
    }
}
