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



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "groupid")
    public Integer getGroupid() {
        return groupid;
    }

    public Group(String groupName) {
        this.groupName = groupName;
    }

    public Group(){

    }
    public void setGroupid(Integer groupid) {
        this.groupid = groupid;
    }

    @Column(name = "group_name")
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Column(name = "is_private")
    public int getPrivateGroup() {
        return privateGroup;
    }

    public void setPrivateGroup(int privateGroup){
        this.privateGroup = privateGroup;
    }


    @OneToMany(fetch = FetchType.LAZY,mappedBy = "group", orphanRemoval = true)
    public Set<GroupAdmin> getGroupAdmins() {
        return groupAdmins;
    }

    public void setGroupAdmins(Set<GroupAdmin> grpMem) {
        this.groupAdmins = grpMem;
    }

    public void addGroupAdmin(GroupAdmin groupMem) {
        this.groupAdmins.add(groupMem);
    }



    @OneToMany(fetch = FetchType.LAZY,mappedBy = "group", orphanRemoval = true)
    public Set<GroupMember> getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(Set<GroupMember> groupMembers) {
        this.groupMembers = groupMembers;
    }






    public void addGroupMembers(GroupMember groupMem) {
        this.groupMembers.add(groupMem);
    }

//    @OneToMany(fetch = FetchType.LAZY,mappedBy = "group", orphanRemoval = true)
//    public Set<JoinRequests> getJoinRequests() {
//        return joinRequests;
//    }


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
