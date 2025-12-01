import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDateTime;
import java.util.List;

public class RoomBookingService {
    public List<Room> getAllRooms(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Room> rooms = session.createQuery("FROM Room", Room.class).list();
        session.close();
        return rooms;
    }

    public List<GroupFitnessClass> getAllGroupFitnessClasses(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<GroupFitnessClass> gfc = session.createQuery("FROM GroupFitnessClass", GroupFitnessClass.class).list();
        session.close();
        return gfc;
    }

    public List<PersonalTrainingSession> getAllPersonalTrainingSessions(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<PersonalTrainingSession> sessions = session.createQuery("FROM PersonalTrainingSession", PersonalTrainingSession.class).list();
        session.close();
        return sessions;
    }

    public boolean roomIsBooked(Session session, Room room, LocalDateTime time){
        String gfcCheck = "SELECT COUNT(c) FROM GroupFitnessClass c WHERE c.room = :room AND c.classTime = :time";
        String ptsCheck = "SELECT COUNT(p) FROM PersonalTrainingSession p WHERE p.room = :room AND p.sessionTime = :time";

        long count1 = (long) session.createQuery(gfcCheck).setParameter("room", room).setParameter("time", time).uniqueResult();
        long count2 = (long) session.createQuery(ptsCheck).setParameter("room", room).setParameter("time", time).uniqueResult();

        return (count1+count2) > 0;
    }

    public boolean assignRoomToClass(int classId, int roomNumber){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        GroupFitnessClass gfc = session.get(GroupFitnessClass.class, classId);
        Room room = session.get(Room.class, roomNumber);

        if(gfc == null|| room == null){
            session.close();
            return false;
        }

        if(roomIsBooked(session,room,gfc.getClassTime())){
            session.close();
            return false;
        }

        gfc.setRoom(room);
        session.persist(gfc);
        tx.commit();
        session.close();
        return true;
    }

    public boolean assignRoomToSession(int sessionId, int roomNumber){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        PersonalTrainingSession pst  = session.get(PersonalTrainingSession.class, sessionId);
        Room room = session.get(Room.class, roomNumber);
        if(pst == null||room == null){
            session.close();
            return false;
        }

        if(roomIsBooked(session,room,pst.getSessionTime())){
            session.close();
            return false;
        }

        pst.setRoom(room);
        session.persist(pst);
        tx.commit();
        session.close();
        return true;
    }
}
