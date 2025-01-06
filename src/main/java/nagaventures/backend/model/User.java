package nagaventures.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user") // Specifies the table name in the database
public class User {

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore  // Prevents recursive serialization of the Posts
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Share> shares = new ArrayList<>();

    @OneToMany(mappedBy = "requester", cascade = CascadeType.ALL)
    private List<Friendship> sentRequests = new ArrayList<>();

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
    private List<Friendship> receivedRequests = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment primary key
    @Column(name = "userId")
    private Long userId;

    @Column(name = "imageName")
    private String imageName;

    @Column(name = "imageType")
    private String imageType;

    @Lob
    @Column(name = "imageData")
    private byte[] imageData;

    @Column(name = "firstName", length = 40 , nullable = false)
    private String firstName;

    @Column(name = "middleName", length = 20)
    private String middleName;

    @Column(name = "lastName", nullable = false, length = 40)
    private String lastName;

    @Column(name = "birthdate", nullable = false)
    private Date birthdate;

    @Column(name = "age", nullable = false)
    private Integer age;

    @Column(name = "gender", nullable = false, length = 6)
    private String gender;

    @Column(name = "email", unique = true, nullable = false, length = 70)
    private String email;

    @Column(name = "contactNo", unique = true, nullable = false, length = 11)
    private String contactNo;

    @Column(name = "bio", length = 100)
    private String bio;

    @Column(name = "country", length = 100)
    private String country;

    @Column(name = "province", length = 100)
    private String province;

    @Column(name = "Hometown", length = 100)
    private String Hometown;

    @Column(name = "brgy", length = 100)
    private String brgy;

    @Column(name = "password", nullable = false)
    private String password;

    public User() {
    }

    public User(List<Post> posts, List<Comment> comments, List<Share> shares, List<Friendship> sentRequests, List<Friendship> receivedRequests, Long userId, String imageName, String imageType, byte[] imageData, String firstName, String middleName, String lastName, Date birthdate, Integer age, String gender, String email, String contactNo, String bio, String country, String province, String hometown, String brgy, String password) {
        this.posts = posts;
        this.comments = comments;
        this.shares = shares;
        this.sentRequests = sentRequests;
        this.receivedRequests = receivedRequests;
        this.userId = userId;
        this.imageName = imageName;
        this.imageType = imageType;
        this.imageData = imageData;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.age = age;
        this.gender = gender;
        this.email = email;
        this.contactNo = contactNo;
        this.bio = bio;
        this.country = country;
        this.province = province;
        Hometown = hometown;
        this.brgy = brgy;
        this.password = password;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
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

    public List<Friendship> getSentRequests() {
        return sentRequests;
    }

    public void setSentRequests(List<Friendship> sentRequests) {
        this.sentRequests = sentRequests;
    }

    public List<Friendship> getReceivedRequests() {
        return receivedRequests;
    }

    public void setReceivedRequests(List<Friendship> receivedRequests) {
        this.receivedRequests = receivedRequests;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getHometown() {
        return Hometown;
    }

    public void setHometown(String hometown) {
        Hometown = hometown;
    }

    public String getBrgy() {
        return brgy;
    }

    public void setBrgy(String brgy) {
        this.brgy = brgy;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }
}
