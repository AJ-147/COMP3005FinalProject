import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.time.DayOfWeek;
import java.time.LocalTime;

public class TrainerService {

    public List<TrainerAvailability> getAvailability(Trainer trainer) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Trainer t = session.get(Trainer.class, trainer.getId());
            if (t == null) {
                return new ArrayList<>();
            }
            return t.getAvailability();
        }
    }

    public Trainer findTrainerByEmail(String email){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query<Trainer> query = session.createQuery(
                "FROM Trainer WHERE email = :email", Trainer.class);
        query.setParameter("email", email);

        Trainer trainer = query.uniqueResult();
        session.close();
        return trainer;
    }

    public boolean addOneTimeAvailability(Trainer t, LocalDateTime start, LocalDateTime end) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

            try {
                //for overlap
                for (TrainerAvailability a : t.getAvailability()) {

                    if (!a.isRecurring()) {
                        // Existing one-time slot
                        if (!(end.isBefore(a.getStartDateTime()) ||
                                start.isAfter(a.getEndDateTime()))) {
                            return false; // Overlaps
                        }
                    } else {
                        // Compare one-time slot against recurring: only if day matches
                        if (start.getDayOfWeek() == a.getDayOfWeek()) {
                            LocalTime st = start.toLocalTime();
                            LocalTime en = end.toLocalTime();
                            if (!(en.isBefore(a.getStartTime()) || st.isAfter(a.getEndTime()))) {
                                return false;
                            }
                        }
                    }
                }

                TrainerAvailability slot = new TrainerAvailability(t, start, end);

                session.save(slot);
                tx.commit();
                return true;
            } catch (Exception ex) {
                if (tx!= null) tx.rollback();
                ex.printStackTrace();
                return false;
            } finally {
                session.close();
            }
    }

    public boolean addRecurringAvailability(Trainer t, DayOfWeek day, LocalTime start, LocalTime end) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        try {
            // Check for overlaps with existing availability
            for (TrainerAvailability a : t.getAvailability()) {
                if (a.isRecurring()) {
                    // Existing recurring slot
                    if (a.getDayOfWeek() == day) {
                        if (!(end.isBefore(a.getStartTime()) || start.isAfter(a.getEndTime()))) {
                            return false; // Overlaps
                        }
                    }
                } else {
                    // Compare recurring slot against one-time slots on the same day
                    if (a.getStartDateTime().getDayOfWeek() == day) {
                        LocalTime st = a.getStartDateTime().toLocalTime();
                        LocalTime en = a.getEndDateTime().toLocalTime();
                        if (!(end.isBefore(st) || start.isAfter(en))) {
                            return false; // Overlaps
                        }
                    }
                }
            }

            TrainerAvailability slot = new TrainerAvailability(t, day, start, end);
            session.save(slot);
            tx.commit();
            return true;
        } catch (Exception ex) {
            if (tx!= null) tx.rollback();
            ex.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    public void removeAvailability(TrainerAvailability slot) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            TrainerAvailability managedslot = session.get(TrainerAvailability.class, slot.getId());
            if (managedslot != null) {
                Trainer trainer = managedslot.getTrainer();
                if(trainer!= null &&  trainer.getAvailability() != null) {
                    trainer.getAvailability().remove(managedslot); //remove from collection
                }
                session.remove(managedslot);
            }
            tx.commit();
        }
    }

    public List<PersonalTrainingSession> getUpcomingPtSessions (Trainer trainer){
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.createQuery(
                    "FROM PersonalTrainingSession p WHERE p.trainer.id = :tid AND p.sessionTime > CURRENT_TIMESTAMP ORDER BY p.sessionTime ASC ",
                            PersonalTrainingSession.class
            ).setParameter("tid", trainer.getId()).list();
        }
    }

    public List<GroupFitnessClass> getUpcomingClasses(Trainer trainer){
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.createQuery(
                    "FROM GroupFitnessClass g WHERE g.trainer.id = :tid AND g.classTime > CURRENT TIMESTAMP ORDER BY g.classTime ASC",
                    GroupFitnessClass.class
            ).setParameter("tid", trainer.getId()).list();
        }
    }

       // Get members that the trainer is training, filtered by name (case-insensitive)
        List<Member> searchMembers(Trainer trainer, String nameQuery) {
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                String hql = "SELECT DISTINCT m FROM Member m " +
                        "JOIN m.personalTrainingSessions pts " +
                        "WHERE pts.trainer = :trainer " +
                        "AND LOWER(m.name) LIKE :nameQuery";

                Query<Member> query = session.createQuery(hql, Member.class);
                query.setParameter("trainer", trainer);
                query.setParameter("nameQuery", "%" + nameQuery.toLowerCase() + "%");

                return query.list();
            }
        }

        //helper last metric
        public HealthMetric getLastMetric(Member member) {
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                return session.createQuery(
                                "FROM HealthMetric hm WHERE hm.member = :member ORDER BY hm.recordedAt DESC",
                                HealthMetric.class
                        ).setParameter("member", member)
                        .setMaxResults(1)
                        .uniqueResult();
            }
        }

        //get current goal
        public FitnessGoal getCurrentGoal(Member member) {
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                return session.createQuery(
                                "FROM FitnessGoal g WHERE g.member = :member ORDER BY g.createdAt DESC",
                                FitnessGoal.class
                        ).setParameter("member", member)
                        .setMaxResults(1)
                        .uniqueResult();
            }
        }




}
