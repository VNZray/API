package nagaventures.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "friendship")
public class Friendship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "friendshipId")
    private Long friendshipId;

    @ManyToOne
    @JoinColumn(name = "requesterId", nullable = false) // User who sent the request
    private User requester;

    @ManyToOne
    @JoinColumn(name = "receiverId", nullable = false) // User who received the request
    private User receiver;

    @Column(name = "status", nullable = false) // e.g., "pending", "accepted", "rejected"
    private String status;

    @Column(name = "createdAt", nullable = false)
    private String createdAt;

    @Column(name = "updatedAt")
    private String updatedAt;

    public Friendship() {
    }

    public Friendship(User requester, User receiver, String status, String createdAt) {
        this.requester = requester;
        this.receiver = receiver;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getFriendshipId() {
        return friendshipId;
    }

    public void setFriendshipId(Long friendshipId) {
        this.friendshipId = friendshipId;
    }

    public User getRequester() {
        return requester;
    }

    public void setRequester(User requester) {
        this.requester = requester;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}

