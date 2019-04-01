package team101.RegistrationModule.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@Table(name = "join_requests")
public class JoinRequests {

    private int requestid;
    private User sender;

    @Override
    public String toString() {
        return "JoinRequests{" +
                "id=" + requestid +
                ", sender=" + sender.getUsername() +
                ", group=" + group.getGroupName() +
                '}';
    }

    private Group group;

    private int isinvite;

    public JoinRequests(User sender, Group group, int isinvite) {
        this.sender = sender;
        this.group = group;
        this.isinvite = isinvite;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idjoin_requests")
    public int getRequestid() {
        return requestid;
    }

    public void setRequestid(int idjoin_group) {
        this.requestid = idjoin_group;
    }

    @JoinColumn(name = "senderid", referencedColumnName = "userid")
    @ManyToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL,targetEntity = User.class)
    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
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

    public JoinRequests() {

    }
    @Column(name = "is_invite")
    public int getIsinvite() {
        return isinvite;
    }

    public void setIsinvite(int isinvite){
        this.isinvite = isinvite;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JoinRequests)) return false;
        JoinRequests that = (JoinRequests) o;
        return requestid == that.requestid &&
                Objects.equals(sender, that.sender) &&
                Objects.equals(group, that.group);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestid, sender, group);
    }

}
