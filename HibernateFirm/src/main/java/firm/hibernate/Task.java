package firm.hibernate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.Type;

import java.util.Date;
@Entity
@Table(name = "tasks")
public class Task{
	@Id
	@Column (name = "tid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int tid;
	
	@Column(name = "description")
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "project_id")
	private Project project;
	
	@Column(name = "deadline")
	private Date deadline;
	
	@ManyToOne
	@JoinColumn(name = "staff")
	private StaffMember staff;
	
	
	public void setTid(int newval) {this.tid = newval;}
	public void setDescription(String newval) {this.description = newval;}
	public void setProject(Project newval) {this.project = newval;}
	public void setDeadline(Date newval) {this.deadline = newval;}
	public void setStaff(StaffMember newval) {this.staff = newval;}
	
	public int getTid() {return this.tid;}
	public String getDescription() {return this.description;}
	public Project getProject() {return this.project;}
	public Date getDeadline() {return this.deadline;}
	public StaffMember getStaff() {return this.staff;}
	
	@Override
    public String toString() {
        return "Task ID: " + tid  + ", Description: " + description + ", Project: " + project.getName() + ", Due Date: " + deadline;
    }
}

