import jakarta.persistence.*;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "trainer_availability")
public class TrainerAvailability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "availability_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "trainer_id", nullable = false)
    private Trainer trainer;

    //one-time availability
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    //recurring availability
    private boolean recurring;
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;

    public TrainerAvailability() {}

    //one-time
    public TrainerAvailability(Trainer trainer, LocalDateTime start, LocalDateTime end) {
        this.trainer = trainer;
        this.startDateTime = start;
        this.endDateTime = end;
        this.recurring = false;
    }

    //recurring
    public TrainerAvailability(Trainer trainer, DayOfWeek day,LocalTime start, LocalTime end) {
        this.trainer = trainer;
        this.dayOfWeek = day;
        this.startTime = start;
        this.endTime = end;
        this.recurring = true;
    }

    public boolean isRecurring() {return recurring;}

    public Long getId() { return id; }

    public Trainer getTrainer() { return trainer; }
    public void setTrainer(Trainer trainer) { this.trainer = trainer; }

    //one-time
    public LocalDateTime getStartDateTime() { return startDateTime; }
    public LocalDateTime getEndDateTime() { return endDateTime; }

    //recurring
    public DayOfWeek getDayOfWeek() { return dayOfWeek; }
    public LocalTime getStartTime() { return startTime; }
    public LocalTime getEndTime() { return endTime; }

 }

