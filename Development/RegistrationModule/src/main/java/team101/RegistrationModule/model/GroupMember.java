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

    /**
     * Constructor for creating a group member object
     * @param user user object associated with the member
     * @param group group that the user is part of
     * @param adminStatus boolean value for admin status of the user for the given group
     */
    public GroupMember(User user, Group group, int adminStatus) {
        this.user = user;
        this.group = group;
        this.adminStatus = adminStatus;
    }

    public GroupMember() {
    }

    /**
     *
     * @return the id of member object
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idgroup_member")
    public int getIdgroup_member() {
        return idgroup_member;
    }

    public void setIdgroup_member(int idgroup_member) {
        this.idgroup_member = idgroup_member;
    }


    /**
     *
     * @return the user object associated with the member object
     */
    @JoinColumn(name = "userid")
    @ManyToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL,targetEntity = User.class)
    public User getUser() {
        return user;
    }


    /**
     *
     * @param user set the given user value
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     *
     * @return  group the member is part of
     */
    @ManyToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, targetEntity = Group.class)
    @JoinColumn(name = "groupid")
    public Group getGroup() {
        return group;
    }

    /**
     *
     * @param group set the given group value
     */
    public void setGroup(Group group) {
        this.group = group;
    }

    /**
     *
     * @return 1 if user is admin of the group, else returns 0
     */
    @Column(name = "is_admin")
    public int getAdminStatus() {
        return adminStatus;
    }

    /**
     *
     * @param adminStatus set the given admin status
     */
    public void setAdminStatus(int adminStatus) {
        this.adminStatus = adminStatus;
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
