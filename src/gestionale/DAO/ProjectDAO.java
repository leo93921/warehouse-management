package gestionale.DAO;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import gestionale.helper.HibernateConnector;
import gestionale.Project;
import gestionale.User;

public class ProjectDAO {

	public static List<Project> listProjects(){
		
		Session session = null;
		
		try{
			
			session = HibernateConnector.getInstance().getSession();
			Query query = session.createQuery(" FROM Project ");
			List<Project> projects = query.getResultList();
			return projects;
			
			
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally{
			if(session != null)
				session.close();
		}
		
	}
	
	public static Project findByID(int ID){
		Session session = null;
		
		try{
			
			session = HibernateConnector.getInstance().getSession();
			Query query = session.createQuery(" FROM Project p WHERE p.ID = :id ");
			query.setParameter("id", ID);
			List<Project> projects = query.getResultList();
			
			if(projects == null || projects.isEmpty())
				return null;
			
			return (Project)projects.get(0);
			
			
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally{
			if(session != null)
				session.close();
		}
	}
	
	public static List<Project> listProjectsByProjectLeader(User user){
		
		Session session = null;
		
		try{
			session = HibernateConnector.getInstance().getSession();
			Query query = session.createQuery(" FROM Project p WHERE p.leadership = :id");
			query.setParameter("id", user);
			List<Project> projects = query.getResultList();
			return projects;
			
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally{
			if(session != null)
				session.close();
		}
		
	}
	
}
