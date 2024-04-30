package twitterbackend.Manager;

import twitterbackend.Entities.TwitterComment;
import twitterbackend.Entities.TwitterPost;
import twitterbackend.Behaviour.PostBehaviour;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Controller class for handling operations related to posts on Twitter.
 */
@RestController
public class PostManager {

    @Autowired
    private PostBehaviour uploadBehaviour;

    /**
     * Endpoint to create a new post.
     * @param structure A map containing post information (userID and postBody).
     * @return ResponseEntity indicating the status of the post creation.
     */
    @PostMapping("/post")
    public ResponseEntity<String> buildPost(@RequestBody Map<String, String> structure) {
        // Extract data from the request body
        Long userID = Long.parseLong(structure.get("userID"));
        String postStructure = structure.get("postBody");

        // Create the post using PostBehaviour
        String information = uploadBehaviour.create(userID, postStructure);

        // Return appropriate response based on the result of post creation
        if (information.equals("Post created successfully")) {
            return new ResponseEntity<>(information, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(information, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint to retrieve a post by its ID.
     * @param postID The ID of the post to retrieve.
     * @return ResponseEntity containing the retrieved post information.
     */
    @GetMapping("/post")
    public ResponseEntity<?> retrievePost(@RequestParam long postID) {
        // Retrieve the post by ID using PostBehaviour
        TwitterPost upload = uploadBehaviour.getPostById(postID);

        // If the post does not exist, return a 404 Not Found response
        if (upload == null) {
            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("Response Body", "Post does not exist");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        } else {
            // If the post exists, format its information and return it
            Map<String, Object> changedUpload = new LinkedHashMap<>();
            changedUpload.put("postID", upload.getID());
            changedUpload.put("postBody", upload.getContent());

            // Format date as "yyyy-MM-dd"
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = dateFormat.format(upload.getCreatedAt());
            changedUpload.put("date", formattedDate);

            // Format comments associated with the post
            List<Map<String, Object>> changedComments = new ArrayList<>();
            for (TwitterComment comment : upload.getComments()) {
                Map<String, Object> changedComment = new LinkedHashMap<>();
                changedComment.put("commentID", comment.getID());
                changedComment.put("commentBody", comment.getContent());

                // Format author information for the comment
                Map<String, Object> authorInfo = new LinkedHashMap<>();
                authorInfo.put("userID", comment.getUserID().getID());
                authorInfo.put("name", comment.getUserID().getName());
                changedComment.put("commentCreator", authorInfo);

                changedComments.add(changedComment);
            }

            changedUpload.put("comments", changedComments);
            return ResponseEntity.ok(changedUpload);
        }
    }

    /**
     * Endpoint to update an existing post.
     * @param structure A map containing post ID and updated post body.
     * @return ResponseEntity indicating the status of the post update.
     */
    @PatchMapping("/post")
    public ResponseEntity<String> patchPost(@RequestBody Map<String, String> structure) {
        // Extract data from the request body
        Long postID = Long.parseLong(structure.get("postID"));
        String postBody = structure.get("postBody");

        // Update the post using PostBehaviour
        String information = uploadBehaviour.updatePost(postID, postBody);

        // Return appropriate response based on the result of post update
        if (information.equals("Post edited successfully")) {
            return new ResponseEntity<>(information, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(information, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint to delete a post by its ID.
     * @param postID The ID of the post to delete.
     * @return ResponseEntity indicating the status of the post deletion.
     */
    @DeleteMapping("/post")
    public ResponseEntity<String> deletePost(@RequestParam long postID) {
        // Delete the post using PostBehaviour
        String information = uploadBehaviour.deletePost(postID);

        // Return appropriate response based on the result of post deletion
        if (information.equals("Post deleted")) {
            return new ResponseEntity<>(information, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(information, HttpStatus.NOT_FOUND);
        }
    }
}
