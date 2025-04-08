package firm.gui;

import java.awt.GridLayout;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;

import javax.persistence.*;
import firm.hibernate.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.Document;


import org.apache.log4j.Logger;


import net.sf.jasperreports.engine.data.JRXmlDataSource;
//import org.w3c.dom.*;
//
//import org.w3c.dom.Attr;
//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
//import org.w3c.dom.NamedNodeMap;
//import org.w3c.dom.Node;
//import org.w3c.dom.NodeList;
//import org.xml.sax.SAXException;
//
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//import javax.xml.parsers.ParserConfigurationException;
//
//import javax.xml.transform.Transformer;
//import javax.xml.transform.TransformerConfigurationException;
//import javax.xml.transform.TransformerException;
//import javax.xml.transform.TransformerFactory;
//import javax.xml.transform.dom.DOMSource;
//import javax.xml.transform.stream.StreamResult;
import java.io.File;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.apache.log4j.Logger;

public class GUI extends JFrame {
	private JFrame main, second;
	private JButton button_Pr, button_save, button_open, button_s_XML, button_l_XML;
	private JButton button_Cl, button_pdf, button_add;
	private JTextField search_main, name_pr, deadline_pr, count_of_tasks;
	private DefaultTableModel model;
	private JScrollPane scroll;
	private JTable staff;
	private JPanel wind, sec_wind;
	private JMenuBar menu;
	private JMenuItem home, projects, clients, add_info;
	private JPanel panel, panel_table;
	private JLabel label_name, label_deadline, label_count;
	private JButton sec_button_ok, sec_button_cansel;
	
	private JButton editButton, button_load_staff;

	
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("firm_persistence");
    EntityManager em = emf.createEntityManager();
	ArrayList<Project> projects_ = (ArrayList<Project>) em.createQuery("SELECT e FROM Project e", Project.class).getResultList();
    ArrayList<StaffMember> staffmembers = (ArrayList<StaffMember>) em.createQuery("SELECT e FROM StaffMember e", StaffMember.class).getResultList();
    ArrayList<Task> tasks = (ArrayList<Task>) em.createQuery("SELECT e FROM Task e", Task.class).getResultList();
    
	
	private static final Logger log = Logger.getLogger("GUI.class");
	
	public List<Task> getTasksByStaffMember(int staffMemberId) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("firm_persistence");
        EntityManager em = emf.createEntityManager();
	    String hql = "SELECT t FROM Task t JOIN t.staffmembers s WHERE s.smid = :staffMemberId";
	    TypedQuery<Task> query = em.createQuery(hql, Task.class);
	    query.setParameter("staffMemberId", staffMemberId);
	    List<Task> tasks = query.getResultList();
	    em.close();
	    return tasks;
	}
	
	private void loadStaff(JTable staffTable) {
        int selectedRow = staffTable.getSelectedRow();
        if (selectedRow != -1) {
            StaffMember selectedStaff = staffmembers.get(selectedRow);
            List<Task> associatedTask = new ArrayList<>();
            int i = 0;
            for (Task task : tasks) {
                if (task.getStaff().equals(selectedStaff)) {
                    associatedTask.add(task);
                    i = i+1;
                }
            }

            if (!associatedTask.isEmpty()) {
            	
            	JDialog load = new JDialog(this, "Tasks and Projects",true);
            	load.setLayout(new BoxLayout(load.getContentPane(), BoxLayout.Y_AXIS));
            	
            	JPanel headerPanel = new JPanel();
                headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.X_AXIS));
                headerPanel.add(new JLabel("Task"));
                headerPanel.add(Box.createHorizontalGlue());
                headerPanel.add(new JLabel("Project"));
                headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 30, 20));
                load.add(headerPanel);
            	 
            	 
                 
                 for(Project pr:projects_) {
	                 for (Task task : associatedTask) {
	                	 if (task.getProject().equals(pr)) {
	                		 JPanel taskPanel = new JPanel();
	                         taskPanel.setLayout(new BoxLayout(taskPanel, BoxLayout.X_AXIS));
	                         taskPanel.add(new JLabel(task.getDescription())); // Название задачи
	                         taskPanel.add(Box.createHorizontalGlue()); // Пробел между названиями
	                         taskPanel.add(new JLabel(task.getProject() != null ? task.getProject().getName() : "N/A"));
	                         taskPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
	                         load.add(taskPanel);
	                	 }
	                 }
                 }
                 
                 load.pack();
                 load.setSize(300, 200);// Упаковка диалога по содержимому
                 load.setLocationRelativeTo(this); // Центрирование относительно родительского окна
                 load.setVisible(true);
                
                } else {
                JOptionPane.showMessageDialog(this, "No tasks and projects associated with this staffmember.", "No Tasks and Projects", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a staffmember.", "No Selection", JOptionPane.WARNING_MESSAGE);
        }
    }
	
	private String[] departments = new String[] {"Quality Assurance", "Development", "Back-end"};
	
	private void checkDepartment(JTextField b) throws Exception_button_ok, Exception_null_pointer{
		String sDep = b.getText().trim();
		boolean isValidDepartment = false;
		if(sDep.length() == 0) { 
			throw new Exception_null_pointer();
		}
			
		for(String department : departments) {
			if(sDep.equalsIgnoreCase(department)) {
				isValidDepartment = true;
				break;
			}
		}
		if(!isValidDepartment) throw new Exception_button_ok();
	}
	
	public void saveToFile(String fileName) throws IOException {
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
	        for (int i = 0; i < model.getRowCount(); i++) {
	            for (int j = 0; j < model.getColumnCount(); j++) {
	                writer.write((String) model.getValueAt(i, j));
	                writer.write("   ");
	            }
	            writer.write("\n");
	        }
	    }
	}
	
	
	
	
	public static void print(String datasource, String template, String outputPath) {
	    try {
	        // Указание источника XML-данных
	        JRDataSource ds = new JRXmlDataSource(datasource, "/staff_persons/person");
	        
	       
	        JasperReport jasperReport = JasperCompileManager.compileReport(template);
	        
	       
	        HashMap<String, Object> parameterMap = new HashMap<>();
	        
	        // Генерация отчета
	        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameterMap, ds);
	        
	        // Создание выходного файла
	        File outputFile = new File(outputPath);
	        
	        // Экспорт в PDF
	        JasperExportManager.exportReportToPdfFile(jasperPrint, outputFile.getAbsolutePath());
	        
	        System.out.println("Отчет успешно сохранен по пути: " + outputFile.getAbsolutePath());
	    } catch (JRException e) {
	        e.printStackTrace();
	    }
	}
	
	
	public GUI() {
		super();
		log.info("Открытие главной экранной формы");
		setSize(1000,1000);
		setLocation(10,10);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("firm_persistence");
        EntityManager em = emf.createEntityManager();

        // Получение данных из базы данных
        ArrayList<StaffMember> staff = (ArrayList<StaffMember>) em.createQuery("SELECT e FROM StaffMember e", StaffMember.class).getResultList();
        ArrayList<Task> task = (ArrayList<Task>) em.createQuery("SELECT e FROM Task e", Task.class).getResultList();

		
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		MyMenu Mmenu = new MyMenu(this);
		
		panel.add(Mmenu.getMenuBar(), BorderLayout.WEST);
		
		
		Color customColor = new Color(0xFFF0F5);
		Color customColor_1 = new Color(0x735f5f);
		
		
		button_Pr = new JButton("Projects");
		button_Cl = new JButton("Clients");
		
		button_add = new JButton("Add");
		button_open = new JButton("Open file");
		button_save = new JButton("Save to file");
		button_s_XML = new JButton("Save XML");
		button_l_XML = new JButton("Load XML");
		button_pdf = new JButton("PDF");
		panel_table = new JPanel();
		
		button_load_staff = new JButton("Load StaffMember");
		
		
		String[] columns = {"id","Surname","Name", "Identification Data", "Division", "Position","Age", "Work Experience", "Salary", ""};

		DefaultTableModel model = new DefaultTableModel(columns, 0);
		for (StaffMember member : staff) {
            model.addRow(new Object[]{
                member.getSmId(),
                member.getSurname(),
                member.getName(),
                member.getIdent_data(),
                member.getPosition().getName(),
                member.getDivision().getName(),
                member.getAge(),
                member.getWork_exp(),
                member.getSalary(),
                "Delete"
            });
        }
        JTable staff_table = new JTable(model) {
        	
        	public Class<?> getColumnClass(int columnIndex){
        		if(columnIndex == 9) {
        			return JButton.class;
        		}
        		return super.getColumnClass(columnIndex);
        	}
        };
        
        staff_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        staff_table.setDefaultEditor(Object.class, null); // Делаем таблицу не редактируемой


        staff_table.getColumnModel().getColumn(9).setCellRenderer(new ButtonRenderer_del());
        staff_table.getColumnModel().getColumn(9).setCellEditor(new ButtonEditor(new JCheckBox(), staff_table, 1));
        //Введение значений в ячейку с подсказкой
        
        ArrayList<String> list = new ArrayList<String>();
        list.add("Ivanov");
        list.add("Inabof");
        list.add("Surikov");

        String[] find = {"id","Surname","Name", "Identification Data", "Division", "Position"};
		
        

	        // Создаем панель поиска и передаем таблицу
	    //SearchPanel searchPanel = new SearchPanel(staff_table);
        Find_2 searchPanel = new Find_2(GUI.this, columns,find, staff_table, scroll, 0);
        
        button_load_staff.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadStaff(staff_table);
            }
        });
        
        editButton = new JButton("Редактировать");
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = staff_table.getSelectedRow();
                if (selectedRow != -1) {
                	StaffMember s = em.find(StaffMember.class, model.getValueAt(selectedRow, 0));
                    String surname = (String) model.getValueAt(selectedRow, 1);
                    String name = (String) model.getValueAt(selectedRow, 2);
                    String id_data = String.valueOf(model.getValueAt(selectedRow, 3));
                    String pos = (String) model.getValueAt(selectedRow, 4);
                    String div = (String)model.getValueAt(selectedRow, 5);
                    
                    String age = String.valueOf(model.getValueAt(selectedRow, 6));
                    String work_exp = String.valueOf(model.getValueAt(selectedRow, 7));
                    String sal = String.valueOf(model.getValueAt(selectedRow, 8));
                   // Integer age = Integer.parseInt(ageStr);
                    new EditDialog(GUI.this, model, s, list, selectedRow, surname, name, id_data,
                    		pos, div, age, work_exp, sal);
                } else {
                    JOptionPane.showMessageDialog(GUI.this, "Выберите строку для редактирования.");
                }
            }
        });
        
        
		        
		        
		scroll = new JScrollPane(staff_table);
		staff_table.setBackground(customColor);
		
		wind = new JPanel();

		
		wind.add(button_add);
		wind.add(editButton);
		wind.add(button_load_staff);

		wind.setBackground(Color.GRAY);
		

		button_add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				new AddDialog(GUI.this, model,1);
			}
		});
		
		
		button_save.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent s) {
		        FileDialog save = new FileDialog(main, "Сохранение данных", FileDialog.SAVE);
		        save.setFile("*.txt");
		        save.setVisible(true);
		        String fileName = save.getDirectory() + save.getFile();
		        if (fileName == null) return;

		        try {
		            saveToFile(fileName);
		            
		        } catch (IOException e) {
		            e.printStackTrace();
		            log.error("Исключение: ", e);
		        }
		        log.info("Сохранение данных в файл");
		    }
		});
		
		
		
		button_open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent o) {
				FileDialog open = new FileDialog(main, "Загрузка данных", FileDialog.LOAD);
				open.setFile("*.txt");
				open.setVisible(true);
				String fileName = open.getDirectory() + open.getFile();
				if(fileName == null) return;
				
				try {
					BufferedReader reader = new BufferedReader(new FileReader(fileName));
					int rows = model.getRowCount();
					for(int i = 0; i < rows; i++) model.removeRow(0);
					String name;
					do {
						name = reader.readLine();
						if(name != null) {
							String department = reader.readLine();
							model.addRow(new String[] {name, department});
						}
					}while(name != null);
					reader.close();
				}
				catch(FileNotFoundException e) {e.printStackTrace();}
				catch (IOException e) {e.printStackTrace();}
				log.info("Загрузка данных из файла в экранную форму");
			}
		});
		
		button_pdf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					print("Staff.xml", "Blank_A4_1.jrxml", "PDF_1.pdf");
					log.debug("Вызов функции print");
				}catch(Exception s) {s.printStackTrace();}
				log.info("Создание отчёта PDF");
			}
		});
		
		add(searchPanel, BorderLayout.NORTH);
		add(wind, BorderLayout.SOUTH);
		add(scroll,BorderLayout.CENTER);
		add(panel, BorderLayout.WEST);
		getContentPane().setBackground(Color.DARK_GRAY);
		
		setVisible(true);
	}
		 
}

