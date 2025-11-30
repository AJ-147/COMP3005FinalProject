import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDate;

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

    public void addAvailability(Trainer trainer){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();


    }

    public void addTrainingSession(Trainer trainer){}



}
