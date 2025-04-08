package firm.hibernate;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;



@Entity
@Table(name = "staffmembers")
public class StaffMember extends Person{
	
	@Id
	@Column (name = "smid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int smid;
	
	@ManyToOne
	@JoinColumn(name = "posid")
	private Position position;
	
	@Column (name = "salary")
	private int salary;
	
	@Column(name = "work_experience")
	private int work_exp = 0;
	
	@ManyToOne
	@JoinColumn(name = "division")
	private Division division;
	

	public void setSmId(int newval) { this.smid = newval;}	
	public void setPosition(Position newval) {this.position = newval;}
	public void setSalary(int newval) {this.salary = newval;}
	public void setWork_exp(int newval) {this.work_exp = newval;}
	public void setDivision(Division newval) {this.division = newval;}
	
	
	public int getSmId() {return this.smid;}
	public Position getPosition() {return this.position;}
	public int getSalary() {return this.salary;}
	public int getWork_exp() {return this.work_exp;}
	public Division getDivision() {return this.division;}

	
	@Override
    public String toString() {
        return surname; // Это то, что будет отображаться в JComboBox
    }

}
