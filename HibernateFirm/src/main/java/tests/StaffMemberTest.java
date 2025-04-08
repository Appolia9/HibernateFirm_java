package tests;
//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Assert;
//import org.junit.Assert.*;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import firm.hibernate.StaffMember;
//import firm.hibernate.Task;
//import firm.hibernate.Position;
//
//
//public class StaffMemberTest {
//
//	@Test
//	public void testSetAndGetSmId() {
//		StaffMember staff = new StaffMember();
//		int expectedId = 1;
//		
//		staff.setSmId(expectedId);
//		int actualId = staff.getSmId();
//		
//		Assert.assertEquals(expectedId, actualId);
//	}
//	
//	@Test
//	public void testSetAndGetSurname() {
//		StaffMember staff = new StaffMember();
//		String expectedSurname = "Ivanov";
//		
//		staff.setSurname(expectedSurname);
//		String actualSurname = staff.getSurname();
//		
//		Assert.assertEquals(expectedSurname, actualSurname);
//	}
//	
//	@Test
//	public void testSetAndGetName() {
//		StaffMember staff = new StaffMember();
//		String expectedName = "Ivan";
//		
//		staff.setName(expectedName);
//		String actualName = staff.getName();
//		
//		Assert.assertEquals(expectedName, actualName);
//	}
//	
//	@Test
//	public void testSetAndGetSalary() {
//		StaffMember staff = new StaffMember();
//		int expectedSalary = 120000;
//		
//		staff.setSalary(expectedSalary);
//		int actualSalary = staff.getSalary();
//		
//		Assert.assertEquals(expectedSalary, actualSalary);
//	}
//	
//
//	
//	@Test
//	public void testSetAndGetPosition() {
//		StaffMember staff = new StaffMember();
//		Position expectedPosition = new Position();
//		
//		staff.setPosition(expectedPosition);
//		Position actualPosition = staff.getPosition();
//		
//		Assert.assertEquals(expectedPosition, actualPosition);
//	}
//	
//	@Test
//	public void testSetAndGetWork_exp() {
//		StaffMember staff = new StaffMember();
//		int expectedWork_exp = 5;
//		
//		staff.setWork_exp(expectedWork_exp);
//		int actualWork_exp = staff.getWork_exp();
//		
//		Assert.assertEquals(expectedWork_exp, actualWork_exp);
//	}
//	
//	@BeforeClass
//	public static void allTestsStarted() {
//		System.out.println("Начало тестирования\n");
//	}
//	
//	@AfterClass
//	public static void allTestsFinished() {
//		System.out.println("Конец тестирования");
//	}
//	
//	@Before
//	public void testStarted() {
//		System.out.println("Запуск теста");
//	}
//	
//	@After
//	public void testFinished() {
//		System.out.println("Завершение теста\n");
//	}
//
//}