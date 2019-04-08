package team101.RegistrationModule.model;



import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {

    public User(String username, String password, String firstName, String lastName, String dateOfBirth) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }

    public User(){}


    private Set<GroupAdmin> groupAdmins = new HashSet<>();

    private Set<JoinRequests> joinRequests = new HashSet<>();

    private Set<GroupMember> groupMembers = new HashSet<>();

    private int userid;

    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private String dateOfBirth;

    private int enabled;


    /**
     *
     * @return the unique user id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userid")
    public int getUserid() {
        return userid;
    }

    /**
     *
     * @param userid set the given value as user id
     */
    public void setUserid(int userid) {
        this.userid = userid;
    }


    /**
     *
     * @return the associated user password as string
     */
    @Column(name = "password")
    public String getPassword(){
        return password;
    }

    /**
     *
     * @return 1 if the user is logged in, else 0
     */
    @Column(name = "enabled")
    public int getEnabled() {
            return enabled;
    }

    /**
     *
     * @param enabled set the given value as enabled
     */
    public void setEnabled(int enabled) {
            this.enabled = enabled;
    }

    //        @Column(name = "is_private")
//        @NotEmpty(message = "*Please provide a value")
//        private boolean isPrivate;


    /**
     *
     * @param password set the given string value as password
     */
    public void setPassword(String password){
            this.password = password;
    }

    /**
     *
     * @return get the display name for the user
     */
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username set the given value as username
     */
    public void setUsername(String username) {
        this.username = username;
    }


    /**
     *
     * @return get the first name for the user
     */
    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    /**
     *
     * @param firstName set the given value as first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     *
     * @return get the last name for the user
     */
    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }

    /**
     *
     * @param lastName set the given value as last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     *
     * @return get the date of birth for the user
     */
    @Column(name = "date_of_birth")
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     *
     * @param dateOfBirth set the given value as date of birth
     */
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }


    /**
     *
     * @param group
     */
    public void addGroupToAdmin(GroupAdmin group) {
        this.groupAdmins.add(group);
    }



    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user", orphanRemoval = true)
    public Set<GroupAdmin> getGroupAdmins() {
        return groupAdmins;
    }

    public void setGroupAdmins(Set<GroupAdmin> grpMem) {
        this.groupAdmins = grpMem;
    }

    public void addGroupMembers(GroupAdmin groupMem) {
        this.groupAdmins.add(groupMem);
    }

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user", orphanRemoval = true)
    public Set<GroupMember> getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(Set<GroupMember> grpMem) {
        this.groupMembers = grpMem;
    }

    public void addGroupMembers(GroupMember groupMem) {
        this.groupMembers.add(groupMem);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) && Objects.equals(userid, user.userid)
                && Objects.equals(dateOfBirth,user.dateOfBirth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userid,username,dateOfBirth);
    }


}

