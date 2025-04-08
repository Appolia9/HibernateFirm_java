package firm.hibernate;

import javax.persistence.*;

import org.hibernate.mapping.Set;

import java.util.ArrayList;

@Entity
@Table(name = "divisions")
public
class Division{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int divid;
	
	@Column(name = "name")
	private String name;
	

	
	public void setName(String newval) {this.name = newval;}
	//public void setList_of_staff(ArrayList<StaffMember> newval) {}
	
	public String getName() {return this.name;}
	
    @Override
    public String toString() {
        return name; // Это то, что будет отображаться в JComboBox
    }

}