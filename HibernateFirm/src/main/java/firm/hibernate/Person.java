package firm.hibernate;

import javax.persistence.*;

@MappedSuperclass
public class Person{
	public String surname, name;
	public int age;
	public long identification_data;
	
	
	public void setSurname(String newval) {this.surname = newval;}
	public void setName(String newval) {this.name = newval;};
	public void setAge(int newval) {this.age = newval;}
	public void setIdent_data(long newval) {this.identification_data = newval;}
	
	public String getSurname() {return this.surname;}
	public String getName() {return this.name;}
	public int getAge() {return this.age;}
	public long getIdent_data() {return this.identification_data;}
}