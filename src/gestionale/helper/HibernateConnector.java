package gestionale.helper;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateConnector {
	private static HibernateConnector me;
	private Configuration cfg;
	private SessionFactory sessionFactory;
	
	private HibernateConnector() throws HibernateException{
		//Build the config
		cfg = new Configuration();
		/**
		* Connection Information..
		 */
		cfg.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
		cfg.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/gestionale");
		cfg.setProperty("hibernate.connection.username", "root");
		cfg.setProperty("hibernate.connection.password", "root");
		cfg.setProperty("hibernate.show_sql", "true");
		
		/**
		 * Mapping resource
		 */
		
		cfg.addAnnotatedClass(gestionale.Product.class);
		cfg.addAnnotatedClass(gestionale.Category.class);
		cfg.addAnnotatedClass(gestionale.ExternalAgency.class);
		cfg.addAnnotatedClass(gestionale.User.class);
		cfg.addAnnotatedClass(gestionale.City.class);
		cfg.addAnnotatedClass(gestionale.Warehouse.class);
		cfg.addAnnotatedClass(gestionale.Supply.class);
		cfg.addAnnotatedClass(gestionale.HeadQuarter.class);
		cfg.addAnnotatedClass(gestionale.Location.class);
		cfg.addAnnotatedClass(gestionale.Project.class);
		cfg.addAnnotatedClass(gestionale.Order.class);
		cfg.addAnnotatedClass(gestionale.Buy.class);
		
		sessionFactory = cfg.buildSessionFactory();
	}
	
	public static synchronized HibernateConnector getInstance() throws HibernateException{
		if (me == null)
			me = new HibernateConnector();
		return me;
	}
	
	public Session getSession() throws HibernateException{
		Session session = sessionFactory.openSession();
		if(!session.isConnected())
			this.reconnect();
		return session;
	}
	
	private void reconnect() throws HibernateException{
		this.sessionFactory = cfg.buildSessionFactory();
	}
}
