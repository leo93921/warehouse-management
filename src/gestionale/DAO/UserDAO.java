package gestionale.DAO;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;

import gestionale.helper.HibernateConnector;
import gestionale.User;

public class UserDAO {
	
	public static User findByID(Integer ID){
		
		Session session = null;
		
		try{
			session = HibernateConnector.getInstance().getSession();
			Query query = session.createQuery(" FROM User u WHERE u.ID = :id ");
			query.setParameter("id", ID);
			List<User> list = query.getResultList();
			if(list.isEmpty() || list==null)
				return null;
			return list.get(0);
			
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally{
			if(session != null)
				session.close();
		}
	}

}
