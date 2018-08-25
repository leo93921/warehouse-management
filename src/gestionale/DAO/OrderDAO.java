package gestionale.DAO;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import gestionale.helper.HibernateConnector;
import gestionale.Buy;
import gestionale.Order;
import gestionale.Project;
import gestionale.ShopperManager;
import gestionale.Supply;
import gestionale.User;
import gestionale.Warehouse;

public class OrderDAO {

	
	/**
	 * 
	 * @param purchases List of bought items
	 * @param project Project to which append the costs
	 * @return false if something wrong in the saving process on the DB
	 */
	public static Order saveOrderAndPurchases(ShopperManager sm, Project project){
		
		Session session = null;
		Transaction tx = null;
		
		try{
			
			session = HibernateConnector.getInstance().getSession();
			
			Order order = new Order();
			order.setDate(new Date());
			order.setProject(project);
			order.setState(Order.OrderState.NEW);
			order.setEmployee(sm.getEmployee());
			
			tx = session.beginTransaction();
			//Salvo ordine
			session.save(order);
			//Devo salvare tutti gli acquisti ora
			for(Buy b : sm.getPurchases()){
				//Aggiorno quantita di supply
				Supply s = b.getSupply();
				s.decrementQuantity(b.getQuantity());
				b.setOrder(order);
				session.merge(b);
				session.merge(s);
			}
			
			tx.commit();
			return order;
			
		}catch(Exception e){
			e.printStackTrace();
			if (tx != null)
				tx.rollback();
			return null;
		}finally{
			if (session != null)
				session.close();
		}
		
	}

	public static List<Order> listByProject(Project project) {
		Session session = null;
		
		try{
			
			session = HibernateConnector.getInstance().getSession();
			Query query = session.createQuery(" FROM Order o WHERE o.project = :projectID ");
			query.setParameter("projectID", project);
			List<Order> list = query.getResultList();
			return list;
			
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally{
			if (session != null)
				session.close();
		}
	}
	
	public static List<Order> listByEmployee(User employee) {
		Session session = null;
		
		try{
			
			session = HibernateConnector.getInstance().getSession();
			Query query = session.createQuery(" FROM Order o WHERE o.employee = :employeeID ");
			query.setParameter("employeeID", employee);
			List<Order> list = query.getResultList();
			return list;
			
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally{
			if (session != null)
				session.close();
		}
	}
	
	public static List<Order> listByWarehouse(Warehouse warehouse){
		
		Session session = null;
		try{
			
			session = HibernateConnector.getInstance().getSession();
			
			Query query = session.createNativeQuery("SELECT o.* FROM gestionale.supply "
					+ "JOIN buy ON buy.Supply = supply.ID "
					+ "JOIN `order` o ON o.ID = OrderID "
					+ "WHERE Warehouse = (?1) "
					+ "GROUP BY OrderID", Order.class);
			query.setParameter(1, warehouse.getID());
			
			List<Order> list = query.getResultList();

			return list;
			
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally{
			if (session != null)
				session.close();
		}
		
	}

	public static List<Order> listGroupingByEmployee(User projectLeader){
		
		Session session = null;
		
		try{
			
			session = HibernateConnector.getInstance().getSession();
			Query query = session.createNativeQuery("SELECT o.* FROM gestionale.buy "
					+ "JOIN `order` o ON o.ID = buy.OrderID "
					+ "JOIN Project p ON p.ID = o.Project "
					+ "WHERE p.Leadership = (?1) "
					+ "GROUP BY o.ID "
					+ "ORDER BY Employee", Order.class);
			query.setParameter(1, projectLeader.getID());
			List<Order> list = query.getResultList();
			return list;
			
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally{
			if (session != null)
				session.close();
		}
		
		
	}
	
	public static Order getByID(int ID) {
		Session session = null;
		
		try{
			session = HibernateConnector.getInstance().getSession();
			Query query = session.createQuery(" FROM Order o WHERE o.ID = :id ");
			query.setParameter("id", ID);
			List<Order> list = query.getResultList();
			if(list == null || list.isEmpty())
				return null;
			return list.get(0);
			
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally{
			if (session != null)
				session.close();
		}
		
	}

	public static void update(Order order) {
		Session session = null;
		Transaction transaction = null;
		
		try{
			session = HibernateConnector.getInstance().getSession();
			transaction = session.beginTransaction();
			session.update(order);
			transaction.commit();
			
		}catch(Exception e){
			e.printStackTrace();
			transaction.rollback();
		}finally{
			if (session != null)
				session.close();
		}
		
	}
	
	

}
