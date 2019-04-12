package edu.northeastern.ccs.im.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "`group`")
public class Group {
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

    public Group(){}
    public Group(String groupName){
        this.groupName = groupName;
    }

    public String getName() {
        return this.groupName;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    public List<User> getModerators() {
        return moderators;
    }

    public void setModerators(List<User> moderators) {
        this.moderators = moderators;
    }
}
