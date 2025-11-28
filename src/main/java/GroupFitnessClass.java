import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="group_fitness_class")
public class GroupFitnessClass {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String className;

    @Column(name="class_time", nullable = false)
    private LocalDateTime classTime;

    @Column(nullable = false)
    private int capacity;

//THIS WILL BE UNCOMMENTED WHEN IMPLEMENTED
//    @ManyToOne
//    @JoinColumn(name="trainer_id", nullable=false)
//    private Trainer trainer;
//
//    @ManyToOne
//    @JoinColumn(name="room_id", nullable=false)
//    private Room room;

    @OneToMany(mappedBy = "groupClass", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClassRegistration> registrations = new ArrayList<>();

    public GroupFitnessClass() {}

    public GroupFitnessClass(String className, LocalDateTime classTime, int capacity) {//Trainer trainer, Room room) {}
    this.className = className;
    this.classTime = classTime;
    this.capacity = capacity;
    //this.trainer= trainer;
        // this.room = room;
    }

    public Long getId() {
        return id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public LocalDateTime getClassTime() {
        return classTime;
    }

    public void setClassTime(LocalDateTime classTime) {
        this.classTime = classTime;
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

    public List<ClassRegistration> getRegistrations() {
        return registrations;
    }

    // Add a registration to this class
    public void addRegistration(ClassRegistration registration) {
        registration.setGroupFitnessClass(this);
        registrations.add(registration);
    }

    @Override
    public String toString() {
        return "GroupFitnessClass{" +
                "id=" + id +
                ", className='" + className + '\'' +
                ", capacity=" + capacity +
                ", classTime=" + classTime +
                '}';
    }
}
