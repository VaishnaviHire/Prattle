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


    private Group group;

    private int isinvite;

    /**
     *
     * @param sender user object that requests to join the group
     * @param group group object associated with request
     * @param isinvite determines if the given request is join request or an invite
     */
    public JoinRequests(User sender, Group group, int isinvite) {
        this.sender = sender;
        this.group = group;
        this.isinvite = isinvite;
    }

    public JoinRequests() {

    }

    /**
     *
     * @return id of the request
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idjoin_requests")
    public int getRequestid() {
        return requestid;
    }

    /**
     *
     * @param idjoin_group set given value of the request id
     */
    public void setRequestid(int idjoin_group) {
        this.requestid = idjoin_group;
    }

    /**
     *
     * @return the user object that requests to join the group
     */
    @JoinColumn(name = "senderid", referencedColumnName = "userid")
    @ManyToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL,targetEntity = User.class)
    public User getSender() {
        return sender;
    }

    /**
     *
     * @param sender set the given user object as sender
     */
    public void setSender(User sender) {
        this.sender = sender;
    }

    /**
     *
     * @return the group object associated with the request
     */
    @ManyToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, targetEntity = Group.class)
    @JoinColumn(name = "groupid")
    public Group getGroup() {
        return group;
    }

    /**
     *
     * @param group set the given group object
     */
    public void setGroup(Group group) {
        this.group = group;
    }


    /**
     *
     * @return 1 if the request is an invite, else return 0
     */
    @Column(name = "is_invite")
    public int getIsinvite() {
        return isinvite;
    }

    /**
     *
     * @param isinvite set the given value as invite status
     */
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

    @Override
    public String toString() {
        return "JoinRequests{" +
                "id=" + requestid +
                ", sender=" + sender.getUsername() +
                ", group=" + group.getGroupName() +
                '}' +"\n";
    }


}
