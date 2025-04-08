package firm.hibernate;

import javax.persistence.*;

import org.hibernate.annotations.Type;

import java.lang.Byte;
//import java.util.ArrayList;

@Entity
@Table (name = "clients")
public class Client{
	//private ArrayList<Project> list_of_project;
	@Id
	@Column (name = "clid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int clid;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "identification_data")
	private int identification_data;
	
	@Column(name = "is_legal")
	@Type(type = "org.hibernate.type.ByteType")
	private Byte is_legal;
	
	public void setClid(int newval) {this.clid = newval;}
	public void setSurname(char newval) {}
	public void setName(String newval) {this.name = newval;}
	public void setIdent_data(int newval) {this.identification_data = newval;}
	public void setIs_legal(Byte newval) {this.is_legal = newval;}
	//public void setList_of_project(ArrayList<Project> newval) {}
	
	public int getClid() {return this.clid;}
	public String getName() {return this.name;}
	public int getIdent_data() {return this.identification_data;}
	public Byte getIs_legal() {return this.is_legal;}
	//public ArrayList<Project> getList_of_project(){return this.list_of_project;}
	@Override
    public String toString() {
        return name; // Это то, что будет отображаться в JComboBox
    }
}

