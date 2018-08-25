package gestionale.DAO;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import gestionale.helper.HibernateConnector;
import gestionale.Buy;
import gestionale.Order;

public class BuyDAO {

	public static List<Buy> listForOrder(Order order){
		
		Session session = null;
		
		try{
			
			session = HibernateConnector.getInstance().getSession();
			
			Query query = session.createQuery(" FROM Buy b WHERE b.order = :id ");
			query.setParameter("id", order);
			
			List<Buy> purchases = query.getResultList();
			return purchases;
			
			
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally{
			if(session != null)
				session.close();
		}
		
	}
	
}
