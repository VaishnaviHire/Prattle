package edu.northeastern.ccs.im.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class defining group in the server
 */
@Entity
@Table(name = "group")
public class Group {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "groupid")
  private Integer groupId;

  @Column(name = "group_name")
  private String groupName;

  @Column(name = "is_private")
  private boolean isPrivate;

//  @OneToMany(targetEntity = User.class,cascade = CascadeType.ALL,fetch = FetchType.LAZY)
//  @JoinTable(
//          name = "group_member",
//          joinColumns = @JoinColumn(name = "groupid"),
//          inverseJoinColumns = @JoinColumn(name = "userid")
//  )
//  private List<User> members;
//
//  @OneToMany(targetEntity = User.class, cascade = CascadeType.ALL,fetch = FetchType.LAZY)
//  @JoinTable(
//          name = "group_admin",
//          joinColumns = @JoinColumn(name = "groupid"),
//          inverseJoinColumns = @JoinColumn(name = "userid")
//  )
//  private List<User> moderators;

  public Group(){

  }

  /**
   * @param groupName name of the group
   * @param admin     User responsible for creation of the group
   */
//  public Group(String groupName, User admin) {
//
//    this.groupName = groupName;
//    members = new ArrayList<>();
//    this.members.add(admin);
//  }


  /**
   * Function to get all users in the group : active and inactive
   *
   * @return List of users (groups and user objects)
   */
//  @Override
//  public List<Unifier> getMembers() {
//    return new ArrayList<>(members);
//  }

  /**
   * @return the group name
   */
//  @Override
  public String getName() {
    return groupName;
  }

  /**
   * @param name name of the group
   */
//  @Override
  public void setName(String name) {
    this.groupName = name;

  }
//
//  /**
//   * @return the unifier type
//   */
//  @Override
//  public boolean isGroup() {
//    return true;
//  }
//
//  public Integer getGroupId() {
//    return groupId;
//  }
//
//  public void setGroupId(Integer groupId) {
//    this.groupId = groupId;
//  }
//
//  public String getGroupName() {
//    return groupName;
//  }
//
//  public void setGroupName(String groupName) {
//    this.groupName = groupName;
//  }
//
//  public boolean isPrivate() {
//    return isPrivate;
//  }
//
//  public void setPrivate(boolean aPrivate) {
//    isPrivate = aPrivate;
//  }
//
//  public void setMembers(List<User> members) {
//    this.members = members;
//  }
//
//  public List<User> getModerators() {
//    return moderators;
//  }
//
//  public void setModerators(List<User> moderators) {
//    this.moderators = moderators;
//  }
}
