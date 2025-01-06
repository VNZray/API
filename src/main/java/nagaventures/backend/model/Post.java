package nagaventures.backend.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "post")
public class Post {

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Share> shares = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false) // Foreign key column in the Post table
    private User user;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "postId")
    private Long postId;

    @Column(name = "imageName")
    private String imageName;

    @Column(name = "imageType")
    private String imageType;

    @Lob
    @Column(name = "imageData")
    private byte[] imageData;

    @Column(name = "reactionCount")
    private Integer reactionCount;

    @Column(name = "commentCount")
    private Integer commentCount;

    @Column(name = "caption", length = 255)
    private String caption;

    @Column(name = "commendId")
    private Long commendId;

    @Column(name = "createdAt")
    private String createdAt;

    @Column(name = "updatedAt")
    private String updatedAt;

    public Post() {
    }

    public Post(Long postId, String imageName, String imageType, byte[] imageData, Integer reactionCount, Integer commentCount, String caption, Long commendId, String createdAt, String updatedAt) {
        this.postId = postId;
        this.imageName = imageName;
        this.imageType = imageType;
        this.imageData = imageData;
        this.reactionCount = reactionCount;
        this.commentCount = commentCount;
        this.caption = caption;
        this.commendId = commendId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Share> getShares() {
        return shares;
    }

    public void setShares(List<Share> shares) {
        this.shares = shares;
    }
}
