package team101.RegistrationModule.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@Table(name = "group_member")
public class GroupMember {

    private int idgroup_member;
    User user;
    Group group;
    private int adminStatus;

    public GroupMember(User user, Group group, int adminStatus) {
        this.user = user;
        this.group = group;
        this.adminStatus = adminStatus;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idgroup_member")
    public int getIdgroup_member() {
        return idgroup_member;
    }

    public void setIdgroup_member(int idgroup_member) {
        this.idgroup_member = idgroup_member;
    }


    @JoinColumn(name = "userid")
    @ManyToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL,targetEntity = User.class)
    public User getUser() {
        return user;
    }


    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, targetEntity = Group.class)
    @JoinColumn(name = "groupid")
    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Column(name = "is_admin")
    public int getAdminStatus() {
        return adminStatus;
    }

    public void setAdminStatus(int adminStatus) {
        this.adminStatus = adminStatus;
    }

    public GroupMember() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        GroupMember that = (GroupMember) o;
        return Objects.equals(group.getGroupid(), that.group.getGroupid()) &&
                Objects.equals(user.getUserid(), that.user.getUserid()) && Objects.equals(adminStatus, that.adminStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(group.getGroupid(), user.getUserid(), adminStatus);
    }

}
