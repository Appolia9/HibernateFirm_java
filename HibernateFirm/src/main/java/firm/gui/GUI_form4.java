package firm.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.apache.log4j.Logger;
import org.hibernate.Query;

import firm.hibernate.Client;
import firm.hibernate.Project;
import firm.hibernate.StaffMember;
import firm.hibernate.Task;

public class GUI_form4 extends JFrame {
	private JPanel panel, panel_b, sec_wind;


	private DefaultTableModel model;
	private JTable projects_table;
	private JScrollPane scroll;
	private JFrame second;
	private JLabel label_name, label_deadline, label_count;
	private JTextField name_pr, deadline_pr, count_of_tasks;
	private JButton sec_button_ok, sec_button_cansel, button_back, button_add;
	
	private JButton editButton;
	private JTable tasks_table;
	
	private static final Logger log = Logger.getLogger("GUI_form2.class");
	
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("firm_persistence");
    EntityManager em = emf.createEntityManager();
    ArrayList<Task> tasks = (ArrayList<Task>) em.createQuery("SELECT e FROM Task e", Task.class).getResultList();

	
	public GUI_form4(String name) {
	
		super(name);
		setSize(1000,1000);
		setLocation(100,100);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		
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
		
		String[] columns = {"id","Description", "Deadline", "Project","StaffMember",""};
		model = new DefaultTableModel(columns, 0);
		
		for (Task member : tasks) {
            model.addRow(new Object[]{
            	member.getTid(),
                member.getDescription(),
                new SimpleDateFormat("yyyy-MM-dd").format(member.getDeadline()),
                member.getProject().getName(),
                member.getStaff().getSurname(),
                "Delete"
            });
        }
		
		tasks_table = new JTable(model) {
        	public Class<?> getColumnClass(int columnIndex){
        		if(columnIndex == 5) {
        			return JButton.class;
        		}
        		return super.getColumnClass(columnIndex);
        	}
		};
		
		 tasks_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	     tasks_table.setDefaultEditor(Object.class, null);
	        
	     tasks_table.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer_del());
	     tasks_table.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(new JCheckBox(), tasks_table, 4));
			
		scroll = new JScrollPane(tasks_table);
		tasks_table.setBackground(customColor);
		
		String[] find = {"id","Description", "Deadline", "Project","StaffMember"};
		
		Find_2 searchPanel = new Find_2(GUI_form4.this, columns,find, tasks_table, scroll, 0);
		
		

		JButton edit_button = new JButton("Редактировать");
        edit_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tasks_table.getSelectedRow();
                if (selectedRow != -1) {
                	Task p = em.find(Task.class, model.getValueAt(selectedRow, 0));
                    String description = (String) model.getValueAt(selectedRow, 1);
                    String deadline = String.valueOf(model.getValueAt(selectedRow, 2));
                    String projectToFind = (String) model.getValueAt(selectedRow, 3);
                    Project project = em.createQuery("SELECT p FROM Project p WHERE p.name = :name", Project.class).setParameter("name", projectToFind)
                            .getSingleResult();;
                    
                    String surnameToFind = (String) model.getValueAt(selectedRow, 4);
                    
                    StaffMember staff = em.createQuery("SELECT p FROM StaffMember p WHERE p.surname = :surname", StaffMember.class).setParameter("surname", surnameToFind)
                            .getSingleResult();;

                    new EditDialog(GUI_form4.this, model, p, list, selectedRow, description, deadline,
                    		project, staff);
                } else {
                    JOptionPane.showMessageDialog(GUI_form4.this, "Выберите строку для редактирования.");
                }
            }
        });
		
		button_back = new JButton("Back");
		button_back.setBackground(Color.GRAY);
		
		button_add = new JButton("Add");
		button_add.setBackground(customColor);
		
		editButton = new JButton("Редактировать");
		
		panel_b = new JPanel(); 
		panel_b.add(button_add);
		panel_b.add(edit_button);
		
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
				new AddDialog(GUI_form4.this, model,4);
			}
		});
		

		
		add(panel_b, BorderLayout.SOUTH);
		add(panel_m, BorderLayout.WEST);
		
		
		panel.add(searchPanel, BorderLayout.NORTH);
		
        panel.add(scroll, BorderLayout.CENTER);
        
        // Добавление панели в JFrame
        add(panel, BorderLayout.CENTER);
		
		getContentPane().setBackground(customColor);
		setVisible(true);
	}
	
}
