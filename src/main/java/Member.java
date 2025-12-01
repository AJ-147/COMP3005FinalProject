import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="members")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private int id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String email;

    @Column(name="date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    //One member can have many goals
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FitnessGoal> fitnessGoals = new ArrayList<>();

    //One member can have many metrics
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HealthMetric> healthMetrics = new ArrayList<>();

    //One member can register for many classes
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClassRegistration> classRegistrations = new ArrayList<>();

    //One member can have many goals
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PersonalTrainingSession> personalTrainingSessions = new ArrayList<>();

    public Member() {}

    public Member(String name, String email, LocalDate dateOfBirth) {
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
    }

    public int getId() {return id;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public LocalDate getDateOfBirth() {return dateOfBirth;}
    public void setDateOfBirth(LocalDate localDate) {this.dateOfBirth = localDate;}

    public List<FitnessGoal> getFitnessGoals() {
        return fitnessGoals;
    }

    public List<HealthMetric> getHealthMetrics() {
        return healthMetrics;
    }

    public List<ClassRegistration> getClassRegistrations() {
        return classRegistrations;
    }

    public List<PersonalTrainingSession> getPersonalTrainingSessions() {
        return personalTrainingSessions;
    }

    public void addGoal(FitnessGoal goal){
        goal.setMember(this);
        fitnessGoals.add(goal);
    }

    public void addHealthMetric(HealthMetric metric){
        metric.setMember(this);
        healthMetrics.add(metric);
    }
}
