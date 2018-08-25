package gestionale.DAO;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import gestionale.helper.HibernateConnector;
import gestionale.Supply;

public class SupplyDAO {
	
	public static Supply findByID(Integer ID){
		Session session = null;
		
		try{
			session = HibernateConnector.getInstance().getSession();
			Query query = session.createQuery(" FROM Supply s WHERE s.ID = :id ");
			query.setParameter("id", ID);
			
			List<Supply> list = query.getResultList();
			
			if (list == null || list.isEmpty())
				return null;
			
			return (Supply)list.get(0);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally{
			session.close();
		}
	}
	
	public static boolean updateSupply(Supply s){
		System.out.println("QUAntity: " + s.getAvailability());
		
		Session session = null;
		Transaction tran = null;
		try{
			session = HibernateConnector.getInstance().getSession();
			tran = session.beginTransaction();
			session.update(s);
			tran.commit();
			return true;
		}catch(Exception e){
			tran.rollback();
			e.printStackTrace();
			return false;
		}finally{
			session.close();
		}
	}
}
