package firm.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.apache.log4j.Logger;

import firm.hibernate.Project;
import firm.hibernate.StaffMember;
import firm.hibernate.Task;

public class GUI_form2 extends JFrame {

	private JPanel panel, panel_b, sec_wind;


	private DefaultTableModel model;
	private JTable projects_table;
	private JScrollPane scroll;
	private JFrame second;
	private JLabel label_name, label_deadline, label_count;
	private JTextField name_pr, deadline_pr, count_of_tasks;
	private JButton sec_button_ok, sec_button_cansel, button_back, button_add, edit_button;
	private JButton showStaffButton;
	
	private static final Logger log = Logger.getLogger("GUI_form2.class");
	
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("firm_persistence");
    EntityManager em = emf.createEntityManager();
    ArrayList<Project> projects = (ArrayList<Project>) em.createQuery("SELECT e FROM Project e", Project.class).getResultList();
    ArrayList<StaffMember> staffs = (ArrayList<StaffMember>) em.createQuery("SELECT e FROM StaffMember e", StaffMember.class).getResultList();
    ArrayList<Task> tasks = (ArrayList<Task>) em.createQuery("SELECT e FROM Task e", Task.class).getResultList();
    
	
	
	public GUI_form2(String name) {
	
		super(name);
		setSize(1000,1000);
		setLocation(100,100);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		

		
		Color customColor = new Color(0xcea262);
		Color customColor_1 = new Color(0xFFC0CB);
		
        ArrayList<String> list = new ArrayList<String>();
        list.add("Ivanov");
        list.add("Inabof");
        list.add("Surikov");
		
		panel = new JPanel();
		JPanel panel_m = new JPanel();
		panel.setLayout(new BorderLayout());
		panel_m.setLayout(new BorderLayout());
		
		
		MyMenu Mmenu = new MyMenu(this);
        panel_m.add(Mmenu.getMenuBar(), BorderLayout.WEST);
		
		String[] columns = {"id","Designation", "Deadline", "Cost", "Client",""};
		model = new DefaultTableModel(columns, 0);
		
		for (Project member : projects) {
			
            model.addRow(new Object[]{
                member.getPrid(),
                member.getName(),
                new SimpleDateFormat("yyyy-MM-dd").format(member.getDeadline()),
                member.getCost(),
                member.getClid().getName(),
                "Delete"
            });
        }
		
		projects_table = new JTable(model) {
        	public Class<?> getColumnClass(int columnIndex){
        		if(columnIndex == 5) {
        			return JButton.class;
        		}
        		return super.getColumnClass(columnIndex);
        	}
		};
		
        projects_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        projects_table.setDefaultEditor(Object.class, null);
        
        projects_table.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer_del());
        projects_table.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(new JCheckBox(), projects_table,2));
		
		
		projects_table.setBackground(customColor);
		
		String[] find = {"id","Designation", "Deadline", "Cost", "Client"};
		
		
		//Find_project searchPanel_1 = new Find_project(GUI_form2.this, projects_table, scroll);
		Find_projects searchPanel = new Find_projects(GUI_form2.this, columns,find, projects_table, scroll, 0);
		//Find_2 searchPanel = new Find_2(GUI_form2.this, columns,find, projects_table, scroll, 0);
		
		//projects_table.setDefaultEditor(Object.class, new ValidatingTextFieldEditor());
		//JPanel panel_staff = new JPanel();
		showStaffButton = new JButton("Show Staff");
        showStaffButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showStaffForSelectedProject(projects_table);
            }
        });
		
		
		 edit_button = new JButton("Редактировать");
	        edit_button.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                int selectedRow = projects_table.getSelectedRow();
	                if (selectedRow != -1) {
	                	Project p = em.find(Project.class, model.getValueAt(selectedRow, 0));
	                    String name = (String) model.getValueAt(selectedRow, 1);
	                    String deadline = String.valueOf(model.getValueAt(selectedRow, 2));
	                    String cost = String.valueOf(model.getValueAt(selectedRow, 3));
	                    String client = (String)model.getValueAt(selectedRow, 4);
	                   // Integer age = Integer.parseInt(ageStr);
	                    new EditDialog(GUI_form2.this, model, p, list, selectedRow, name, deadline,
	                    		cost, client);
	                } else {
	                    JOptionPane.showMessageDialog(GUI_form2.this, "Выберите строку для редактирования.");
	                }
	            }
	        });
		
		button_back = new JButton("Back");
		button_back.setBackground(Color.GRAY);
		
		button_add = new JButton("Add");
		button_add.setBackground(customColor);
		
		panel_b = new JPanel();
		panel_b.add(button_add);
		panel_b.add(edit_button);
		panel_b.add(showStaffButton);
		
		button_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent el) {
				GUI first = new GUI();
				dispose();
				log.info("Закрытие экранной формы проектов");
				first.setVisible(true);
				
			}
		});
		
		button_add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				new AddDialog(GUI_form2.this, model,2);
			}
		});
		
		scroll = new JScrollPane(projects_table);
		//setLayout(new BorderLayout());
		scroll.setPreferredSize(new Dimension(800, 600));
		
		//add(searchPanel, BorderLayout.NORTH);
		//add(scroll, BorderLayout.CENTER);
		add(panel_b, BorderLayout.SOUTH);
		add(panel_m, BorderLayout.WEST);
		
		
		panel.add(searchPanel, BorderLayout.NORTH);
		
        panel.add(scroll, BorderLayout.CENTER);
        
        // Добавление панели в JFrame
        add(panel, BorderLayout.CENTER);

		getContentPane().setBackground(customColor);
		
		setVisible(true);
	}
	
	 private void showStaffForSelectedProject(JTable projectsTable) {
	        int selectedRow = projectsTable.getSelectedRow();
	        if (selectedRow != -1) {
	            Project selectedProject = projects.get(selectedRow);
	            List<StaffMember> associatedStaff = new ArrayList<>();

	            for (Task task : tasks) {
	                if (task.getProject().equals(selectedProject)) {
	                    associatedStaff.add(task.getStaff());
	                }
	            }

	            if (!associatedStaff.isEmpty()) {
	                StringBuilder staffList = new StringBuilder("Staff Members:\n");
	                for (StaffMember staff : associatedStaff) {
	                    staffList.append(staff.getSurname()).append(" ").append(staff.getName()).append("\n");
	                }
	                JOptionPane.showMessageDialog(this, staffList.toString(), "Staff Members", JOptionPane.INFORMATION_MESSAGE);
	            } else {
	                JOptionPane.showMessageDialog(this, "No staff members associated with this project.", "No Staff", JOptionPane.WARNING_MESSAGE);
	            }
	        } else {
	            JOptionPane.showMessageDialog(this, "Please select a project.", "No Selection", JOptionPane.WARNING_MESSAGE);
	        }
	    }
	
}
