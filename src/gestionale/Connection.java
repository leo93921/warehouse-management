package gestionale;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import gestionale.helper.HibernateConnector;

public class Connection {
	
	public static User findUser(String username, String password){
		Session session = null;
		
		try{
			session = HibernateConnector.getInstance().getSession();
			
			Query query = session.createQuery( " FROM User u WHERE (u.username = :userr) AND (u.password = :pass) " );
			query.setParameter("userr", username);
			query.setParameter("pass", password);

			//Eseguo la query
			List<User> list = query.getResultList();
			//transaction.commit();
			if (list != null && list.isEmpty()){
				System.out.println("No users with these credentials!");
				return null;
			}else
				return (User)list.get(0);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally{
			if(session != null)
				session.close();
		}
		
		
	}
	
}
