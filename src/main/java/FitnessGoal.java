import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table(name = "fitness_goals")
public class FitnessGoal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String goalType;
    private double targetValue;

    private LocalDate createdAt;
    private boolean achieved;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public FitnessGoal() {}

    public FitnessGoal(String goalType, double targetValue, Member member) {
        this.goalType = goalType;
        this.targetValue = targetValue;
        this.createdAt = LocalDate.now();
        this.member = member;
        this.achieved = false;
    }

    public String getGoalType() {
        return goalType;
    }

    public void setGoalType(String goalType) {
        this.goalType = goalType;
    }

    public double getTargetValue() {
        return targetValue;
    }

    public void setTargetValue(double targetValue) {
        this.targetValue = targetValue;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public boolean isAchieved() {
        return achieved;
    }

    public void setAchieved(boolean achieved) {
        this.achieved = achieved;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    @Override
    public String toString() {
        return "FitnessGoal{" +
                "goalType='" + goalType + '\'' +
                ", targetValue=" + targetValue +
                ", createdAt=" + createdAt +
                ", achieved=" + achieved +
                '}';
    }
}