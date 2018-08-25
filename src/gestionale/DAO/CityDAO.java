package gestionale.DAO;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import gestionale.helper.HibernateConnector;
import gestionale.City;

public class CityDAO {
	
	public static City findByCAP (String CAP){
		Session session = null;
		
		try{
			
			session = HibernateConnector.getInstance().getSession();
			
			Query query = session.createQuery(" FROM City c WHERE c.CAP = :str ");
			query.setParameter("str",	CAP);
			
			List<City> list = query.getResultList();
			
			//se ho null ritorno niente
			if (list == null || list.isEmpty())
				return null;
			
			//Se ho qualcosa allora prendo il primo più vicino nella stessa citta
			return (City)list.get(0);
			
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally{
			session.close();
		}
	}

}
