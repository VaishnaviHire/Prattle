package team101.RegistrationModule.model;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
@Data
@Entity
@Table(name = "`group`")
public class Group {


    private Set<GroupAdmin> groupAdmins = new HashSet();

    private Set<GroupMember> groupMembers = new HashSet();


    private int groupid;


    private String groupName;

    private int privateGroup;

    /**
     * Constructor for creating the group object
     * @param groupName name of the group
     */
    public Group(String groupName, int privateGroup) {
        this.groupName = groupName;
        this.privateGroup = privateGroup;
    }

    public Group(){

    }

    /**
     *
     * @return unique id for the group object
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "groupid")
    public Integer getGroupid() {
        return groupid;
    }

    /**
     * Method to change group id
     * @param groupid  new group id
     */
    public void setGroupid(Integer groupid) {
        this.groupid = groupid;
    }

    /**
     *
     * @return name of the group
     */
    @Column(name = "group_name")
    public String getGroupName() {
        return groupName;
    }

    /**
     * Method to change the name of the group
     * @param groupName name of the group
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     *
     * @return 1 if group is private and 0 if it is public
     */
    @Column(name = "is_private")
    public int getPrivateGroup() {
        return privateGroup;
    }

    /**
     * Method to change the privacy status of the group
     * @param privateGroup set 1 if group is private and 0 if it is public
     */
    public void setPrivateGroup(int privateGroup){
        this.privateGroup = privateGroup;
    }


    /**
     * Method to get group admins
     * @return a list of groupadmin objects
     */
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "group", orphanRemoval = true)
    public Set<GroupAdmin> getGroupAdmins() {
        return groupAdmins;
    }

    /**
     *
     * @param grpAdmin set of groupAdmins for this group
     */
    public void setGroupAdmins(Set<GroupAdmin> grpAdmin) {
        this.groupAdmins = grpAdmin;
    }

    /**
     *
     * @param grpAdmin add a group admin to the list of admins
     */
    public void addGroupAdmin(GroupAdmin grpAdmin) {
        this.groupAdmins.add(grpAdmin);
    }


    /**
     *
     * @return group members for this group
     */
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "group", orphanRemoval = true)
    public Set<GroupMember> getGroupMembers() {
        return groupMembers;
    }

    /**
     *
     * @param groupMembers set of groupMembers for this group
     */
    public void setGroupMembers(Set<GroupMember> groupMembers) {
        this.groupMembers = groupMembers;
    }


    /**
     *
     * @param groupMem add a group member to the list of members
     */
    public void addGroupMembers(GroupMember groupMem) {
        this.groupMembers.add(groupMem);
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return Objects.equals(groupName, group.groupName) && Objects.equals(groupid, group.groupid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupid,groupName);
    }


}
