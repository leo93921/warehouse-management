package gestionale.DAO;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import gestionale.helper.HibernateConnector;
import gestionale.Product;

public class ProductDAO {
	
	public static Product findByID(Integer ID){
		Session session = null;
		
		try{
			session = HibernateConnector.getInstance().getSession();
			Query query = session.createQuery(" FROM Product p WHERE p.ID = :id ");
			query.setParameter("id", ID);
			
			List<Product> list = query.getResultList();
			
			if (list != null && list.isEmpty())
				return null;
			else
				return (Product)list.get(0);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally{
			session.close();
		}
	}

}
