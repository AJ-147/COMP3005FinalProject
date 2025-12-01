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

    //recurring
    @Column(name = "start_time_recurr", nullable = true)
    private LocalTime startTime;

    @Column(name = "end_time_recurr", nullable = true)
    private LocalTime endTime;

    @Column(name = "day_of_week",nullable = true)
    private DayOfWeek dayOfWeek;

    //one time
    @Column(name = "start_time_one", nullable = true)
    private LocalDateTime startDateTime;

    @Column(name = "end_time_one", nullable = true)
    private LocalDateTime endDateTime;

    //type
    @Column(nullable = false)
    private boolean recurring;

    public TrainerAvailability() {}

    //one-time
    public TrainerAvailability(Trainer trainer, LocalDateTime start, LocalDateTime end) {
        this.trainer = trainer;
        this.startDateTime = start;
        this.endDateTime = end;
        this.recurring = false;

        //set dummy values for recurring columns
        this.startTime = null;
        this.endTime = null;
        this.dayOfWeek = null;
    }

    //recurring
    public TrainerAvailability(Trainer trainer, DayOfWeek day,LocalTime start, LocalTime end) {
        this.trainer = trainer;
        this.dayOfWeek = day;
        this.startTime = start;
        this.endTime = end;
        this.recurring = true;

        //dummys
        this.startDateTime = null;
        this.endDateTime = null;
    }

    public Long getId() { return id; }
    public Trainer getTrainer() { return trainer; }
    public void setTrainer(Trainer trainer) { this.trainer = trainer; }
    public boolean isRecurring() {return recurring;}

    //one-time
    public LocalDateTime getStartDateTime() { return startDateTime; }
    public LocalDateTime getEndDateTime() { return endDateTime; }

    //recurring
    public DayOfWeek getDayOfWeek() { return dayOfWeek; }
    public LocalTime getStartTime() { return startTime; }
    public LocalTime getEndTime() { return endTime; }

 }

