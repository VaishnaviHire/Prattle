package edu.northeastern.ccs.im.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

/**
 * The type Group 1.
 */
@Entity
@Table(name = "`group`")
public class Group1 {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "groupid")
    private Integer groupId;

    @Column(name = "group_name")
    private String groupName;

    @Column(name = "is_private")
    private boolean isPrivate;


    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(targetEntity = User.class,cascade = CascadeType.ALL)
    @JoinTable(
            name = "group_member",
            joinColumns = @JoinColumn(name = "groupid"),
            inverseJoinColumns = @JoinColumn(name = "userid")
    )
    private List<User> members;

    @LazyCollection(LazyCollectionOption.FALSE)
      @OneToMany(targetEntity = User.class, cascade = CascadeType.ALL,fetch = FetchType.LAZY)
     @JoinTable(
              name = "group_admin",
             joinColumns = @JoinColumn(name = "groupid"),
             inverseJoinColumns = @JoinColumn(name = "userid")
     )
  private List<User> moderators;

    /**
     * Instantiates a new Group 1.
     */
    public Group1(){}

    /**
     * Instantiates a new Group 1.
     *
     * @param groupName the group name
     */
    public Group1(String groupName){
        this.groupName = groupName;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return this.groupName;
    }

    /**
     * Gets group id.
     *
     * @return the group id
     */
    public Integer getGroupId() {
        return groupId;
    }

    /**
     * Sets group id.
     *
     * @param groupId the group id
     */
    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    /**
     * Gets group name.
     *
     * @return the group name
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * Sets group name.
     *
     * @param groupName the group name
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     * Is private boolean.
     *
     * @return the boolean
     */
    public boolean isPrivate() {
        return isPrivate;
    }

    /**
     * Sets private.
     *
     * @param aPrivate the a private
     */
    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    /**
     * Gets members.
     *
     * @return the members
     */
    public List<User> getMembers() {
        return members;
    }

    /**
     * Sets members.
     *
     * @param members the members
     */
    public void setMembers(List<User> members) {
        this.members = members;
    }

    /**
     * Gets moderators.
     *
     * @return the moderators
     */
    public List<User> getModerators() {
        return moderators;
    }

    /**
     * Sets moderators.
     *
     * @param moderators the moderators
     */
    public void setModerators(List<User> moderators) {
        this.moderators = moderators;
    }
}
