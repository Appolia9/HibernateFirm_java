package firm.hibernate;


import javax.persistence.*;
//import java.util.List;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;

import java.util.List;
import org.hibernate.query.Query;
//import firm.hibernate.Position;


public class AppClass {

	public static void main(String[] args) {
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("firm_persistence");
		
		EntityManager em = emf.createEntityManager();
		
		System.out.println("Start a hibernate test");
		
		//open a transaction
		em.getTransaction().begin();
		
		//create a new position
		
		//CREATE a new Project
		/*
		Project pr = new Project();
		pr.setName("Firm");
		Calendar calendar = Calendar.getInstance();
		calendar.set(2025, Calendar.MAY, 1);
		Date deadline = new Date(calendar.getTimeInMillis());
		pr.setDeadline(deadline);
		pr.setCost(1000000);
		Client cl = new Client();
		cl.setName("IP Pirogova");
		cl.setIdent_data(1125463764);
		Byte is_legal = 0;
		cl.setIs_legal(is_legal);
		em.persist(cl);
		
		pr.setClid(cl);
		em.persist(pr);
		*/
		
		//CREATE a new Client
		/*
		Client cl = new Client();
		cl.setName("Ivan");
		cl.setIdent_data(123);
		Byte is_legal = 1;
		cl.setIs_legal(is_legal);
		em.persist(cl);
	*/
		
		//CREATE a new Task
		
		Task t = new Task();
		t.setDescription("Make a project layout");
		Project pr = em.find(Project.class, 3);
		t.setProject(pr);
		Date deadline = Date.valueOf("2024-01-10");
		t.setDeadline(deadline);
		StaffMember st = em.find(StaffMember.class, 2);
		t.setStaff(st);
		em.persist(t);
		
		
		//CREATE a new Division
		/*
		Division div = new Division();
		div.setName("Development");
		em.persist(div);
		*/
		
		//CREATE a new StaffMember
		/*
		StaffMember staff = new StaffMember();
		staff.setSurname("Demidov");
		staff.setName("Egor");
		staff.setIdent_data(413452673);
		staff.setAge(40);
		staff.setSalary(120000);
		staff.setWork_exp(10);
		Position pos = em.find(Position.class, 2);
		staff.setPosition(pos);
		Division div = em.find(Division.class, 2);
		staff.setDivision(div);
		em.persist(staff);
		*/
		

		
		//find a position by id
		/*
		Position pos1 = em.find(Position.class, 3);
		System.out.println("Found! PosName = " + pos1.getName()+ ", posId = " + pos1.getPosid());
		*/
		
		//search by criteria HQL
		/*
		List<Position> poslist = em.createQuery("SELECT p FROM Position p WHERE posid > 1").getResultList();
		if (poslist == null)
			System.out.println("No rows in table");
		else {
			for(Position pos: poslist) {
				System.out.println("Position ID = " + pos.getPosid() + ", position name = " + pos.getName());
			}
		}
		*/
		
		//changing entity fields
		/*pos1.setName("Analyst");
		 */
		
		
		//making a new object persistent - save it to DB
		/*em.persist(pos);
		 */
		 
		
		//remove item
		/*
		Position pos = em.find(Position.class, 3);
		em.remove(pos);
		*/
		
		
		//HQL фишки
		
		//Вывод списка всех проектов, которые принадлежат данному клиенту
		/*
		String hql = "from Project where clid.name = :username";
		TypedQuery<Project> query = em.createQuery( hql, Project.class);
		query.setParameter("username", "Ivan");
		List<Project> resultList = query.getResultList();
		*/
		

		
		//close a transaction
		em.getTransaction().commit();
		
		//System.out.println("List  " + resultList);
		//System.out.println("Removed an entity with ID = " + pos.getPosid());
	}
	
}

