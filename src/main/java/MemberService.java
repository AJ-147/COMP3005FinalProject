import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;


public class MemberService {
    public boolean emailExists(Session session, String email){
        Query<Long> query = session.createQuery(
                "SELECT COUNT(m) FROM Member m WHERE m.email =: email", Long.class);
        query.setParameter("email", email);
        Long count = query.uniqueResult();
        return count!=null && count > 0;
    }

    public boolean registerMember(String name, String email, LocalDate dob){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx =  session.beginTransaction();

        try{
            if(emailExists(session, email)){
                System.out.println("Email already exists");
                tx.rollback();
                return false;
            }
            Member member = new Member(name, email, dob);
            session.persist(member);
            tx.commit();
            session.close();
            System.out.println("Member successfully registered!");
            return true;
        }catch(Exception e){
            tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    public Member findMemberByEmail(String email) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query<Member> query = session.createQuery(
                "FROM Member WHERE email = :email", Member.class);
        query.setParameter("email", email);

        Member member = query.uniqueResult();
        session.close();
        return member;
    }

    public void updatePersonalDetails(Member member, String newName, String newEmail, LocalDate newDob) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        member.setName(newName);
        member.setEmail(newEmail);
        member.setDateOfBirth(newDob);

        session.merge(member);

        tx.commit();
        session.close();

        System.out.println("Personal details updated!");
    }

    public void addFitnessGoal(Member member, String goalDescription, double targetValue) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        member = session.merge(member);

        FitnessGoal goal = new FitnessGoal(goalDescription, targetValue, member);
        member.getFitnessGoals().add(goal);

        session.persist(goal);

        tx.commit();
        session.close();

        System.out.println("Fitness goal added!");
    }

    public void addHealthMetric(Member member, double height, double weight, int heartRate) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        member = session.merge(member);

        HealthMetric metric = new HealthMetric(height, weight, heartRate, member);
        member.getHealthMetrics().add(metric);

        session.persist(metric);

        tx.commit();
        session.close();

        System.out.println("Health metric recorded!");
    }


    public Member loadMemberWithHealthHistory(Long memberId) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Member member = session.createQuery(
                        "SELECT m FROM Member m LEFT JOIN FETCH m.healthMetrics WHERE m.id = :id",
                        Member.class
                )
                .setParameter("id", memberId)
                .uniqueResult();

        session.close();
        return member;
    }

    public boolean registerForClass(String email, int classId){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try{
            tx =  session.beginTransaction();

            Member member = session.createQuery(
                    "FROM Member WHERE email = :email", Member.class)
                    .setParameter("email", email)
                    .uniqueResult();
            if(member == null){
                System.out.println("Member not found!");
                return false;
            }

            GroupFitnessClass gfc = session.get(GroupFitnessClass.class, classId);

            if(gfc == null){
                System.out.println("Class not found!");
                return false;
            }

            int currentRegistered = gfc.getRegistrations().size();
            if (currentRegistered >= gfc.getCapacity()){
                System.out.println("Class is at max capacity!");
                return false;
            }

            ClassRegistration reg = new ClassRegistration();
            reg.setMember(member);
            reg.setGroupFitnessClass(gfc);
            reg.setRegistrationDate(LocalDateTime.now());

            session.persist(reg);

            gfc.getRegistrations().add(reg);

            tx.commit();
            return true;
        } catch(Exception ex){
            if(tx != null){
                tx.rollback();
            }
            ex.printStackTrace();
            return false;
        } finally{
            session.close();
        }
    }


}
