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


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idgroup_admin")
    public int getIdgroup_admin() {
        return idgroup_admin;
    }

    public void setIdgroup_admin(int idgroup_admin) {
        this.idgroup_admin = idgroup_admin;
    }

    @JoinColumn(name = "userid")
    @ManyToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL,targetEntity = User.class)
    public User getUser() {
        return user;
    }

    public GroupAdmin(User user, Group group) {
        this.user = user;
        this.group = group;
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

    public GroupAdmin() {
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
