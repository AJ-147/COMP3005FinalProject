import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="personal_training_session")
public class PersonalTrainingSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="session_time", nullable = false)
    private LocalDateTime sessionTime;

    @Column(nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name="member_id", nullable=false)
    private Member member;

    //FOR NOW THESE ARE COMMENTED OUT TILL IMPLEMENTED
//    @ManyToOne
//    @JoinColumn(name="trainer_id", nullable=false)
//    private Trainer trainer;
//
    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    public PersonalTrainingSession() {}

    public PersonalTrainingSession(LocalDateTime sessionTime, Member member, Room room){//, Trainer trainer) {
        this.sessionTime = sessionTime;
        this.member = member;
        //this.trainer = trainer;
        this.room = room;
        this.status= "BOOKED";
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getSessionTime() {
        return sessionTime;
    }

    public void setSessionTime(LocalDateTime sessionTime) {
        this.sessionTime = sessionTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

//    public Trainer getTrainer() {
//        return trainer;
//    }
//
//    public void setTrainer(Trainer trainer) {
//        this.trainer = trainer;
//    }
//
//    public Room getRoom() {
//        return room;
//    }
//
//    public void setRoom(Room room) {
//        this.room = room;
//    }

    @Override
    public String toString() {
        return "PersonalTrainingSession{" +
                "id=" + id +
                ", sessionTime=" + sessionTime +
                ", status='" + status + '\'' +
                '}';
    }

}
