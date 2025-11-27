import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "health_metrics")
public class HealthMetric {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double height;//in cm
    private double weight;//in lbs
    private int heartRate;//BPM

    @Column(name = "recorded_at", nullable = false)
    private LocalDateTime recordedAt;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public HealthMetric() {}

    public HealthMetric(double height, double weight, int heartRate, Member member) {
        this.height = height;
        this.weight = weight;
        this.heartRate = heartRate;
        this.member = member;
        this.recordedAt = LocalDateTime.now();
    }


    public Long getId() {
        return id;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }

    public LocalDateTime getRecordedAt() {
        return recordedAt;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    @Override
    public String toString() {
        return "HealthMetric{" +
                "id=" + id +
                ", height=" + height +
                ", weight=" + weight +
                ", heartRate=" + heartRate +
                ", recordedAt=" + recordedAt +
                '}';
    }
}
