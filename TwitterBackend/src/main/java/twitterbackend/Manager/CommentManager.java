package twitterbackend.Manager;

import twitterbackend.Entities.TwitterComment;
import twitterbackend.Behaviour.CommentBehaviour;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class CommentManager {
    @Autowired
    private CommentBehaviour commentBehaviour;

    /**
     * Wrapper function to build a comment.
     * This function receives a JSON structure containing postID, userID, and commentBody.
     * It then invokes the CommentBehaviour to create the comment and returns an appropriate HTTP response.
     *
     * @param structure A map containing postID, userID, and commentBody.
     * @return ResponseEntity containing a success message if the comment was created successfully, or an error message if not.
     */
    @PostMapping("/comment")
    public ResponseEntity<String> buildComment(@RequestBody Map<String, String> structure) {
        Long postID = Long.parseLong(structure.get("postID"));
        Long userID = Long.parseLong(structure.get("userID"));
        String commentBody = structure.get("commentBody");
        String information = commentBehaviour.create(postID, userID, commentBody);
        if (information.equals("Comment created successfully")) {
            return ResponseEntity.ok(information);
        } else {
            return ResponseEntity.badRequest().body(information);
        }
    }

    /**
     * Retrieve a comment by its ID.
     *
     * @param commentID The ID of the comment to retrieve.
     * @return ResponseEntity containing the comment details if found, or an error message if not found.
     */
    @GetMapping("/comment")
    public ResponseEntity<?> retrieveComment(@RequestParam long commentID) {
        Optional<TwitterComment> provisionalMessage = Optional.ofNullable(commentBehaviour.getCommentById(commentID));
        if (provisionalMessage.isPresent()) {
            TwitterComment information = provisionalMessage.get();
            Map<String, Object> responseBody = new LinkedHashMap<>();

            responseBody.put("commentID", information.getID());
            responseBody.put("commentBody", information.getContent());

            Map<String, Object> messageBuilder = new LinkedHashMap<>();
            messageBuilder.put("userID", information.getUserID().getID());
            messageBuilder.put("name", information.getUserID().getName());
            responseBody.put("commentCreator", messageBuilder);

            return ResponseEntity.ok(responseBody);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment does not exist");
        }
    }

    /**
     * Update an existing comment.
     *
     * @param structure A map containing commentID and commentBody.
     * @return ResponseEntity containing a success message if the comment was updated successfully, or an error message if not.
     */
    @PatchMapping("/comment")
    public ResponseEntity<String> changeComment(@RequestBody Map<String, String> structure) {
        Long commentID = Long.parseLong(structure.get("commentID"));
        String commentBody = structure.get("commentBody");
        String information = commentBehaviour.updateComment(commentID, commentBody);
        if (information.equals("Comment updated successfully")) {
            return ResponseEntity.ok(information);
        } else {
            return ResponseEntity.badRequest().body(information);
        }
    }

    /**
     * Delete a comment by its ID.
     *
     * @param commentID The ID of the comment to delete.
     * @return ResponseEntity containing a success message if the comment was deleted successfully, or an error information if not.
     */
    @DeleteMapping("/comment")
    public ResponseEntity<String> removeComment(@RequestParam long commentID) {
        String information = commentBehaviour.deleteComment(commentID);
        if (information.equals("Comment deleted successfully")) {
            return ResponseEntity.ok(information);
        } else {
            return ResponseEntity.badRequest().body(information);
        }
    }
}
