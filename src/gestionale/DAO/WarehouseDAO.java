package gestionale.DAO;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import gestionale.helper.HibernateConnector;
import gestionale.Location;
import gestionale.Supply;
import gestionale.User;
import gestionale.Warehouse;

public class WarehouseDAO {
	
	public static Warehouse findByID(Integer ID){
		Session session = null;
		
		try{
			session = HibernateConnector.getInstance().getSession();
			Query query = session.createQuery(" FROM Warehouse w WHERE w.ID = :id ");
			query.setParameter("id", ID);
			
			List<Warehouse> list = query.getResultList();
			
			if (list == null || list.isEmpty())
				return null;
			else
				return (Warehouse)list.get(0);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally{
			session.close();
		}
	}
	
	public static List<Warehouse> listWarehouses(){
		
		Session session = null;
		
		try{
			session = HibernateConnector.getInstance().getSession();
			Query query = session.createQuery(" FROM Warehouse ");
			
			List<Warehouse> list = query.getResultList();
			
			if (list != null && list.isEmpty())
				return null;
			else
				return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally{
			session.close();
		}
	}
	
	public static List<Supply> listSupplyForWarehouse(Warehouse w){
		Session session = null;
		
		try{
			session = HibernateConnector.getInstance().getSession();
			
			Query query = session.createQuery(" FROM Supply s WHERE s.warehouse = :id ");
			query.setParameter("id", w);
			List<Supply> list = query.getResultList();
			return list;
			
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally{
			if(session != null)
				session.close();
		}
	}
	
	public static Warehouse getNearestWarehouse(User employee){
		Session session = null;
		try{
			session = HibernateConnector.getInstance().getSession();
			
			Location l = LocationDAO.getLocationByUser(employee);
			
			//Se non ho trovato location, allora non ho magazzini in quella citta
			if(l == null)
				return null;
			
			return l.getWarehouse();
			
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally{
			if(session != null)
				session.close();
		}
	}
	
	public static Warehouse findByWarehouseman(User warehouseman){
		Session session = null;
		try{
			
			session = HibernateConnector.getInstance().getSession();
			
			Query query = session.createQuery(" FROM Warehouse w WHERE w.warehouseman = :idWarehouseman ");
			query.setParameter("idWarehouseman", warehouseman);
			List<Warehouse> list = query.getResultList();
			//Se la lista è null o non vi è nulla non torno nulla
			if(list == null || list.isEmpty())
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
