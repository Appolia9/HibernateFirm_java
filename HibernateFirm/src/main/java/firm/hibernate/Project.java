package firm.hibernate;

import java.util.Date;


import javax.persistence.*;


@Entity
@Table(name = "projects")
public class Project{
	@Id
	@Column (name = "prid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int prid;
	
	@Column (name = "name")
	private String name;
	
	@Column(name = "deadline")
	private Date deadline;
	
	@Column (name = "cost")
	private long cost;
	
	@ManyToOne
	@JoinColumn (name = "client_id")
	private Client clid;
	
	public void setPrid(int newval) {this.prid = newval;}
	public void setName(String newval) {this.name = newval;}
	public void setDeadline(Date newval) {this.deadline = newval;}
	public void setCost(long newval) {this.cost = newval;}
	public void setClid(Client newval) {this.clid = newval;}

	public int getPrid() {return this.prid;}
	public String getName() {return this.name;}
	public Date getDeadline() {return this.deadline;}
	public long getCost() {return this.cost;}
	public Client getClid() {return this.clid;}

	@Override
    public String toString() {
        return name; // Это то, что будет отображаться в JComboBox
    }

}

