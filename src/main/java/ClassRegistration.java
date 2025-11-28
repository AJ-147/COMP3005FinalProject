import jakarta.persistence.*;

import javax.swing.*;
import java.time.LocalDateTime;


@Entity
@Table(name="class_registration")
public class ClassRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name="registered_at", nullable = false)
    private LocalDateTime registeredAt;

    @ManyToOne
    @JoinColumn(name="member_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name="group_class_id", nullable = false)
    private GroupFitnessClass groupFitnessClass;

    public ClassRegistration() {}

    public ClassRegistration(Member member, GroupFitnessClass groupFitnessClass) {
        this.member = member;
        this.groupFitnessClass = groupFitnessClass;
        this.registeredAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public GroupFitnessClass getGroupClass() {
        return groupFitnessClass;
    }

    public void setGroupFitnessClass(GroupFitnessClass groupFitnessClass) {
        this.groupFitnessClass = groupFitnessClass;
    }

    @Override
    public String toString() {
        return "ClassRegistration{" +
                "id=" + id +
                ", registeredAt=" + registeredAt +
                '}';
    }

}
