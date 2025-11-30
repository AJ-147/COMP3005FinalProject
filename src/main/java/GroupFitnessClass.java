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

    @ManyToOne
    @JoinColumn(name="trainer_id", nullable=false)
    private Trainer trainer;

    @ManyToOne
    @JoinColumn(name="room_id", nullable=false)
    private Room room;

    @OneToMany(mappedBy = "groupFitnessClass", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClassRegistration> registrations = new ArrayList<>();

    public GroupFitnessClass() {}

    public GroupFitnessClass(String className, LocalDateTime classTime, int capacity,Room room) {//Trainer trainer
    this.className = className;
    this.classTime = classTime;
    this.capacity = capacity;
    this.room = room;
        //this.trainer= trainer;
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

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public List<ClassRegistration> getRegistrations() {
        return registrations;
    }

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
