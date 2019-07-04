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

    /**
     *
     * @param sender the user object that sends the invitation
     * @param group the group object
     * @param receiver the user object that receives the invitation to join
     */
    public Invites(User sender, Group group, User receiver) {
        this.sender = sender;
        this.group = group;
        this.receiver = receiver;
    }

    public Invites() {
    }

    /**
     *
     * @return the invitation id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idinvites")
    public int getInviteid() {
        return inviteid;
    }

    /**
     *
     * @param inviteid set the given value as id
     */
    public void setInviteid(int inviteid) {
        this.inviteid = inviteid;
    }

    /**
     *
     * @return the user object that sends invitation
     */
    @JoinColumn(name = "senderid", referencedColumnName = "userid")
    @ManyToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL,targetEntity = User.class)
    public User getSender() {
        return sender;
    }

    /**
     *
     * @param sender set the given value as sender
     */
    public void setSender(User sender) {
        this.sender = sender;
    }

    /**
     *
     * @return get group object associated with the invitation
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
     * @return user object that receives the invitation
     */
    @JoinColumn(name = "receiverid", referencedColumnName = "userid")
    @ManyToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL,targetEntity = User.class)
    public User getReceiver() {
        return receiver;
    }

    /**
     *
     * @param receiver set the given user object as receiver
     */
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
                '}'+"\n";
    }
}
