import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TrainerService {
    public boolean emailExists(Session session, String email){
        Query<Long> query = session.createQuery(
                "SELECT COUNT(t) FROM Trainer t WHERE t.email = :email",  Long.class);
        query.setParameter("email", email);
        Long count = query.uniqueResult();
        return count !=null && count > 0;
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

    public boolean isTrainer(String email) {
        Session  session = HibernateUtil.getSessionFactory().openSession();

        Query<Trainer> query = session.createQuery("FROM Trainer WHERE email = :email");
        query.setParameter("email", email);

        Trainer trainer = query.uniqueResult();
        session.close();

        return trainer != null;
    }

    public List<Trainer> findTrainerByName(String name){
        Session session = HibernateUtil.getSessionFactory().openSession();

        List<Trainer> list = session.createQuery(
                "FROM Trainer t WHERE LOWER (t.name) LIKE LOWER(:name)", Trainer.class)
                .setParameter("name", name).getResultList();

        session.close();
        return list;
    }

    public boolean setAvailability(Trainer trainer, LocalDateTime start,  LocalDateTime end){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        try{
            trainer = session.merge(trainer);

            List<TrainerAvailability> existing = session.createQuery(
                    "FROM TrainerAvailability a WHERE a.trainer.id = :tid",
                    TrainerAvailability.class)
                    .setParameter("tid", trainer.getId())
                    .list();

            //preventing overlaps
            for (TrainerAvailability a : existing){
                boolean overlap =
                        start.isBefore(a.getEndTime()) &&
                                end.isAfter(a.getStartTime());

                if(overlap){
                    System.out.println("Overlap");
                    tx.rollback();
                    return false;
                }
            }

            TrainerAvailability newSlot = new TrainerAvailability(trainer, start, end);
            session.persist(newSlot);

            tx.commit();
            session.close();

            System.out.println("Availability Successfully added");
            return true;

        } catch (Exception e){
            tx.rollback();
            e.printStackTrace();
            return false;
        }
    }


    public Trainer loadTrainerSchedule(Long trainerId){
        Session session = HibernateUtil.getSessionFactory().openSession();

        Trainer trainer = session.createQuery(
                "SELECT t FROM Trainer t LEFT JOIN FETCH t.classRegistrations LEFT JOIN FETCH t.personalTrainingSessions WHERE t.id = :id",
                        Trainer.class)
                        .setParameter("id", trainerId)
                        .uniqueResult();

        session.close();
        return trainer;
    }

    public Member lookupMember(String memberName){
        Session session = HibernateUtil.getSessionFactory().openSession();

        //fetch member and last health metric
        Member member = session.createQuery(
                "SELECT m FROM Member m LEFT JOIN FETCH m.healthMetrics hm LEFT JOIN FETCH m.fitnessGoals fg WHERE LOWER(m.name) LIKE LOWER(:name)",
                Member.class)
                .setParameter("name", "%" + memberName + "%")
                .setMaxResults(1)
                .uniqueResult();

        session.close();
        return member;
    }



}
