package nagaventures.backend.service;

import nagaventures.backend.dto.FriendshipDTO;
import nagaventures.backend.model.Friendship;
import nagaventures.backend.model.User;
import nagaventures.backend.repository.FriendshipRepository;
import nagaventures.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FriendshipService {

    @Autowired
    private FriendshipRepository friendshipRepository;

    @Autowired
    private UserRepository userRepository;


    public FriendshipDTO sendFriendRequest(FriendshipDTO friendRequest) {
        // Validate requester and receiver
        Optional<User> requester = userRepository.findById(friendRequest.getRequesterId());
        Optional<User> receiver = userRepository.findById(friendRequest.getReceiverId());

        if (requester.isEmpty() || receiver.isEmpty()) {
            throw new IllegalArgumentException("Invalid requester or receiver ID");
        }
        // Create a new Friendship entity
        Friendship friendship = new Friendship();
        friendship.setRequester(requester.get());
        friendship.setReceiver(receiver.get());
        friendship.setStatus(Friendship.FriendshipStatus.PENDING);
        friendship.setCreatedAt(LocalDateTime.now());
        friendship.setUpdatedAt(LocalDateTime.now());

        // Save to the repository
        Friendship savedFriendship = friendshipRepository.save(friendship);

        // Convert to DTO and return
        return convertToDTO(savedFriendship);
    }

    public List<FriendshipDTO> getFriendRequests(Long receiverId) {
        List<Friendship> friendships = friendshipRepository.findByReceiver_UserIdAndStatus(receiverId, Friendship.FriendshipStatus.PENDING);

        // Map each Friendship entity to FriendshipDTO
        return friendships.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Helper method to convert Friendship entity to DTO
    public FriendshipDTO convertToDTO(Friendship friendship) {
        FriendshipDTO dto = new FriendshipDTO();
        dto.setFriendshipId(friendship.getFriendshipId());
        dto.setRequesterId(friendship.getRequester().getUserId());
        dto.setReceiverId(friendship.getReceiver().getUserId());
        dto.setStatus(friendship.getStatus().toString());
        dto.setCreatedAt(friendship.getCreatedAt());
        dto.setUpdatedAt(friendship.getUpdatedAt());
        return dto;
    }

    public boolean checkFriendshipStatus(Long requesterId, Long receiverId) {
        return friendshipRepository.existsByRequester_UserIdAndReceiver_UserIdAndStatus(
                requesterId, receiverId, Friendship.FriendshipStatus.PENDING
        );
    }

    public boolean checkFriendshipStatusByReceiverId(Long receiverId) {
        return friendshipRepository.existsByReceiver_UserIdAndStatus(receiverId, Friendship.FriendshipStatus.PENDING
        );
    }

    public Optional<Friendship> getFriendship(Long requesterId, Long receiverId) {
        return friendshipRepository.findByRequester_UserIdAndReceiver_UserId(requesterId, receiverId);
    }

    public Optional<Friendship> getFriendRequestByReceiverId(Long receiverId) {
        return friendshipRepository.findByReceiver_UserId(receiverId);
    }

    public boolean cancelFriendRequest(Long requesterId, Long receiverId) {
        Optional<Friendship> friendship = friendshipRepository.findByRequester_UserIdAndReceiver_UserId(requesterId, receiverId);

        if (friendship.isPresent()) {
            friendshipRepository.delete(friendship.get());
            return true; // Successfully deleted
        } else {
            return false; // No friend request found
        }
    }
}
