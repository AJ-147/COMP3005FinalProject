import org.hibernate.Session;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DataInitializer {
    public static void seed(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        long adminCount = (long) session
                .createQuery("SELECT COUNT(a) FROM AdminStaff a")
                .uniqueResult();

        if (adminCount == 0) {
            AdminStaff a1 = new AdminStaff("Alice Johnson", "alice@");
            AdminStaff a2 = new AdminStaff("Bob Smith", "bob@");

            session.persist(a1);
            session.persist(a2);
        }

        long roomCount = (long) session
                .createQuery("SELECT COUNT(r) FROM Room r")
                .uniqueResult();
        if (roomCount == 0) {
            Room r1 = new Room(101, 20);
            Room r2 = new Room(102, 15);
            Room r3 = new Room(201, 30);
            Room r4 = new Room(202, 25);

            session.persist(r1);
            session.persist(r2);
            session.persist(r3);
            session.persist(r4);
        }

        Trainer t1 = null;

        long trainerCount = (long) session
                .createQuery("SELECT COUNT(t) FROM Trainer t")
                .uniqueResult();

        if (trainerCount == 0) {
            t1 = new Trainer("John Smith", "john@");
            Trainer t2 = new Trainer("Sarah Adams", "sarah@");
            Trainer t3 = new Trainer("Mike Jones", "mike@");

            session.persist(t1);
            session.persist(t2);
            session.persist(t3);
        } else {
            // fetch John if he already exists
            t1 = (Trainer) session.createQuery("FROM Trainer t WHERE t.name = :name")
                    .setParameter("name", "John Smith")
                    .uniqueResult();
        }

        // Add one-time availability for John
        TrainerAvailability oneTime = new TrainerAvailability(
                t1,
                LocalDateTime.of(2025, 12, 5, 14, 0),
                LocalDateTime.of(2025, 12, 5, 16, 0)
        );
        session.persist(oneTime);

        // Add recurring availability for John
        TrainerAvailability recurring = new TrainerAvailability(
                t1,
                DayOfWeek.MONDAY,
                LocalTime.of(10, 0),
                LocalTime.of(12, 0)
        );
        session.persist(recurring);


        session.getTransaction().commit();
        session.close();
    }
}
