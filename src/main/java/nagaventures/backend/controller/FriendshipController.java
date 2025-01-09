package nagaventures.backend.controller;

import nagaventures.backend.dto.FriendshipDTO;
import nagaventures.backend.model.Friendship;
import nagaventures.backend.model.User;
import nagaventures.backend.repository.FriendshipRepository;
import nagaventures.backend.service.FriendshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
public class FriendshipController {

    @Autowired
    private FriendshipService friendshipService;

    @Autowired
    private FriendshipRepository friendshipRepository;

    // Endpoint to send a friend request
    @PostMapping("/friendship/send")
    public ResponseEntity<FriendshipDTO> sendFriendRequest(@RequestBody FriendshipDTO friendRequest) {
        if (friendRequest.getRequesterId() == null || friendRequest.getReceiverId() == null) {
            return ResponseEntity.badRequest().body(null); // Invalid request body
        }

        // Call the service to handle the business logic
        FriendshipDTO savedFriendship = friendshipService.sendFriendRequest(friendRequest);

        // Return the created friendship as the response
        return ResponseEntity.status(HttpStatus.CREATED).body(savedFriendship);
    }

    @GetMapping("/friendship/requests/{receiverId}")
    public ResponseEntity<List<FriendshipDTO>> getFriendRequests(@PathVariable Long receiverId) {
        List<Friendship> friendRequests = friendshipRepository.findByReceiver_UserIdAndStatus(receiverId, Friendship.FriendshipStatus.PENDING);

        // Convert each Friendship object to a FriendshipDTO
        List<FriendshipDTO> friendRequestsDTO = friendRequests.stream()
                .map(friendshipService::convertToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(friendRequestsDTO);
    }

    @GetMapping("/friendship/friends/{receiverId}")
    public ResponseEntity<List<FriendshipDTO>> getFriendList(@PathVariable Long receiverId) {
        List<Friendship> friendRequests = friendshipRepository.findByReceiver_UserIdAndStatus(receiverId, Friendship.FriendshipStatus.ACCEPTED);

        // Convert each Friendship object to a FriendshipDTO
        List<FriendshipDTO> friendRequestsDTO = friendRequests.stream()
                .map(friendshipService::convertToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(friendRequestsDTO);
    }

    @GetMapping("/friendship/status/full/{requesterId}/{receiverId}")
    public ResponseEntity<Map<String, String>> getFriendshipStatus(
            @PathVariable Long requesterId,
            @PathVariable Long receiverId) {
        return friendshipRepository.findByRequester_UserIdAndReceiver_UserId(requesterId, receiverId)
                .map(friendship -> ResponseEntity.ok(Map.of("status", friendship.getStatus().toString())))
                .orElse(ResponseEntity.ok(Map.of("status", "NONE")));
    }

    // Endpoint to cancel (delete) a friend request
    @DeleteMapping("/friendship/cancel/{requesterId}/{receiverId}")
    public ResponseEntity<String> cancelFriendRequest(@PathVariable Long requesterId, @PathVariable Long receiverId) {
        try {
            boolean isCanceled = friendshipService.cancelFriendRequest(requesterId, receiverId);

            if (isCanceled) {
                return ResponseEntity.ok("Friend request canceled successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Friend request not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

    // Endpoint to remove (delete) a friend
    @DeleteMapping("/friendship/remove/{friendshipId}")
    public ResponseEntity<String> removeFriend(@PathVariable Long friendshipId) {
        try {
            // Check if the friendship record exists
            Optional<Friendship> friendship = friendshipRepository.findById(friendshipId);
            if (friendship.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Friendship record not found");
            }

            // Remove the friendship record
            friendshipRepository.deleteById(friendshipId);

            // Return a success response
            return ResponseEntity.ok("Friendship removed successfully");
        } catch (Exception e) {
            // Log the error and return a server error response
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while removing the friendship");
        }
    }


    @PutMapping(value = "/friendship/accept/{friendshipId}")
    public ResponseEntity<?> updateUserInfo(
            @PathVariable Long friendshipId) {
        try {
            Friendship friendship = friendshipService.acceptFriendRequest(friendshipId);
            return ResponseEntity.ok(friendship);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "An error occurred.");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "An error occurred.");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
