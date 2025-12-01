import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;


public class AdminService {
    public boolean isAdmin(String email) {
       Session  session = HibernateUtil.getSessionFactory().openSession();

       Query<AdminStaff> query = session.createQuery("FROM AdminStaff WHERE email = :email");
       query.setParameter("email", email);

       AdminStaff adminStaff = query.uniqueResult();
       session.close();

       return adminStaff!=null;
    }

    public boolean createGroupClass(String className, int capacity, String trainerEmail, int roomNumber, LocalDateTime time){
        Session  session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Trainer trainer = session.createQuery("FROM Trainer WHERE email = :email", Trainer.class)
                .setParameter("email", trainerEmail)
                .uniqueResult();

        Room room = session.createQuery("FROM Room WHERE roomNumber = :num", Room.class)
                .setParameter("num", roomNumber)
                .uniqueResult();

        if(trainer==null || room == null){
            session.close();
            return false;
        }

        if(new RoomBookingService().roomIsBooked(session,room,time)){
            session.close();
            return false;
        }

        GroupFitnessClass gfc = new GroupFitnessClass(className,capacity,trainer,room,time);

        session.persist(gfc);
        session.getTransaction().commit();
        session.close();

        return true;
    }




}