package firm.hibernate;

import javax.persistence.*;

//import java.util.ArrayList;
@Entity
@Table(name = "lab_2.positions")
public class Position{
	@Id
	@Column(name = "posid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int posid;
	
	@Column(name = "name")
	private String name;
	
	public void setName(String newval) {this.name = newval;}
	public void setPosid(int newval) {this.posid = newval;}
	
	public String getName() {return this.name;}
	public int getPosid() {return this.posid;}
	
    @Override
    public String toString() {
        return name; // Это то, что будет отображаться в JComboBox
    }
	
}

