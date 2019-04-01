package team101.RegistrationModule.model;

import lombok.Data;
import org.hibernate.mapping.Join;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
@Table(name = "group_admin")

public class GroupAdmin {


    private int idgroup_admin;
    private User user;
    private Group group;

    /**
     * Constructor for creating Group admin object
     * @param user admin of the group
     * @param group group object
     */
    public GroupAdmin(User user, Group group) {
        this.user = user;
        this.group = group;
    }

    public GroupAdmin() {
    }

    /**
     *
     * @return id of GroupAdmin object
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idgroup_admin")
    public int getIdgroup_admin() {
        return idgroup_admin;
    }

    /**
     *
     * @param idgroup_admin new id for groupAdmin object
     */
    public void setIdgroup_admin(int idgroup_admin) {
        this.idgroup_admin = idgroup_admin;
    }

    /**
     * Method to get user object corresponding to the group
     * @return user object representing the admin of the group
     */
    @JoinColumn(name = "userid")
    @ManyToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL,targetEntity = User.class)
    public User getUser() {
        return user;
    }


    /**
     *
     * @param user  set the given user value
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     *
     * @return the group associated with the Group Admin object
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        GroupAdmin that = (GroupAdmin) o;
        return Objects.equals(group.getGroupid(), that.group.getGroupid()) &&
                Objects.equals(user.getUserid(), that.user.getUserid());
    }

    @Override
    public int hashCode() {
        return Objects.hash(group.getGroupid(), user.getUserid());
    }




}
