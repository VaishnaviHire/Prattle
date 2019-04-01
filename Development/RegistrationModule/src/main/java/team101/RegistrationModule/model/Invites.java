package team101.RegistrationModule.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@Table(name = "invites")
public class Invites {

    private int inviteid;
    private User sender;
    private Group group;
    private  User receiver;

    public Invites(User sender, Group group, User receiver) {
        this.sender = sender;
        this.group = group;
        this.receiver = receiver;
    }

    public Invites() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idinvites")
    public int getInviteid() {
        return inviteid;
    }

    public void setInviteid(int inviteid) {
        this.inviteid = inviteid;
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

    @JoinColumn(name = "receiverid", referencedColumnName = "userid")
    @ManyToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL,targetEntity = User.class)
    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Invites)) return false;
        Invites invites = (Invites) o;
        return inviteid == invites.inviteid &&
                Objects.equals(sender, invites.sender) &&
                Objects.equals(group, invites.group) &&
                Objects.equals(receiver, invites.receiver);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inviteid, sender, group, receiver);
    }

    @Override
    public String toString() {
        return "Invites{" +
                "inviteid=" + inviteid +
                ", sender=" + sender.getUsername() +
                ", group=" + group.getGroupName() +
                ", receiver=" + receiver.getUsername() +
                '}';
    }
}
