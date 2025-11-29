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


}