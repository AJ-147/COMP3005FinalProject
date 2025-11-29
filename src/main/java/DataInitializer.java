import org.hibernate.Session;

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

        session.getTransaction().commit();
        session.close();
    }
}
