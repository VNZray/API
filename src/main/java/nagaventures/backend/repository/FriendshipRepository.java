package nagaventures.backend.repository;

import nagaventures.backend.model.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
    // Fetch all friendships for a specific receiver with a given status
    List<Friendship> findByReceiver_UserIdAndStatus(Long receiverId, Friendship.FriendshipStatus status);

    // Check if a friendship exists between a requester and receiver with a specific status
    boolean existsByRequester_UserIdAndReceiver_UserIdAndStatus(Long requesterId, Long receiverId, Friendship.FriendshipStatus status);

    // Check if a friendship exists between a requester and receiver with a specific status
    boolean existsByReceiver_UserIdAndStatus(Long receiverId, Friendship.FriendshipStatus status);

    // Optional: Fetch a specific friendship between requester and receiver
    Optional<Friendship> findByRequester_UserIdAndReceiver_UserId(Long requesterId, Long receiverId);

    Optional<Friendship> findByFriendshipId(Long friendshipId);

    Optional<Friendship> findByReceiver_UserId(Long receiverId);


}
