package gestionale.DAO;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import gestionale.helper.HibernateConnector;
import gestionale.City;
import gestionale.Location;
import gestionale.User;

public class LocationDAO {
	
	public static Location getLocationByUser(User employee){
		
		Session session = null;
		
		try{
			
			session = HibernateConnector.getInstance().getSession();
			
			City c = employee.getCity();
			
			//Se non ho nessuna città non torno nulla
			if (c == null)
				return null;
			
			Query query = session.createQuery(" FROM Location l WHERE l.city = :ccc ");
			query.setParameter("ccc", c);
			System.out.println(query.getParameterValue("ccc"));
			List<Location> list = query.getResultList();
			
			//se ho null ritorno niente
			if (list == null || list.isEmpty())
				return null;
			//Se ho qualcosa allora prendo il primo più vicino nella stessa citta
			return (Location)list.get(0);
			
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally{
			session.close();
		}
		
	}

}
