import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="trainers")
public class Trainer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="trainer_id")
    private Long id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String email;

    //trainer availability
    @OneToMany(mappedBy = "trainer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TrainerAvailability> availability = new ArrayList<>();

    //one trainer can teach many classes
    @OneToMany(mappedBy = "trainer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GroupFitnessClass> groupFitnessClass = new ArrayList<>();

    //one trainer can teach many personal training sessions
    @OneToMany(mappedBy = "trainer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PersonalTrainingSession> personalTrainingSessions = new ArrayList<>();


    public Trainer() {}

    public Trainer(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Long getId() {return id;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public List<TrainerAvailability> getAvailability() {return availability;}

    public List<GroupFitnessClass> getGroupFitnessClass() {return groupFitnessClass;}

    public List<PersonalTrainingSession> getPersonalTrainingSessions() {return personalTrainingSessions;}

    public void addAvailabilitySlot(TrainerAvailability slot) {
        slot.setTrainer(this);
        availability.add(slot);
    }

    public void addGroupFitnessClass(GroupFitnessClass fitnessClass) {
        fitnessClass.setTrainer(this);
        groupFitnessClass.add(fitnessClass);
    }

    public void  addPTSessions(PersonalTrainingSession PTsession) {
        PTsession.setTrainer(this);
        this.personalTrainingSessions.add(PTsession);
    }

}
