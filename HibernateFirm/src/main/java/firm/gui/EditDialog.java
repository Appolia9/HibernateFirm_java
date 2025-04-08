package firm.gui;

import org.jdesktop.swingx.JXDatePicker;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import com.mysql.fabric.xmlrpc.base.Data;

import firm.hibernate.Client;
import firm.hibernate.Project;
import firm.hibernate.StaffMember;
import firm.hibernate.Task;
import firm.hibernate.Division;
import firm.hibernate.Position;

public class EditDialog extends JDialog {
    private JTextField nameField;
    private JTextField surnameField;
    private JTextField id_dataField,  posField, divField, tField, ageField, weField, salField;
    private int rowIndex;
    
    private JComboBox<Position> posComboBox;
    private JComboBox<Division> divComboBox;
    private JComboBox<Client> clComboBox;
    private JComboBox<Project> prComboBox;
    private JComboBox<StaffMember> stComboBox;
    private JComboBox<String> is_legalComboBox;
    
    private DefaultComboBoxModel<String> is_legalModel;
    
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("firm_persistence");
    EntityManager em = emf.createEntityManager();
    
    ArrayList<Division> divisions = (ArrayList<Division>) em.createQuery("SELECT e FROM Division e", Division.class).getResultList();
    ArrayList<Position> positions = (ArrayList<Position>) em.createQuery("SELECT e FROM Position e", Position.class).getResultList();
    ArrayList<Client> clients = (ArrayList<Client>) em.createQuery("SELECT e FROM Client e", Client.class).getResultList();
    ArrayList<Project> projects = (ArrayList<Project>) em.createQuery("SELECT e FROM Project e", Project.class).getResultList();
    ArrayList<StaffMember> staffs = (ArrayList<StaffMember>) em.createQuery("SELECT e FROM StaffMember e", StaffMember.class).getResultList();
    
    String[] elements = new String[] {"Physical", "Legal"};
    
    void checkField_dig(JLabel label, JTextField b) throws Exception_null_pointer{
    	String sDep = b.getText().trim();
    	String l = label.getText();
    	String a = " Пожалуйста, введите только цифры в поле";
    	String res = l + a;
    	if(sDep.length() == 0) { 
			throw new Exception_null_pointer();
		}
    	if (!sDep.matches("\\d+")) {
            throw new IllegalArgumentException(res);
        }
    	if(l == "Identification data:" && Integer.valueOf(sDep) < 1000) {
    		throw new IllegalArgumentException("Недопустимые данные");
    	}
    	if(l == "Age:" && Integer.valueOf(sDep) < 18) {
    		throw new IllegalArgumentException("Возраст не может быть меньше 18");
    	}
    	if(l == "Age:" && Integer.valueOf(sDep) > 70) {
    		throw new IllegalArgumentException("Недопустимый возраст");
    	}
    	if(l == "Salary:" && Integer.valueOf(sDep) < 35000) {
    		throw new IllegalArgumentException("Зарплата не может быть меньше МРОТ");
    	}
    	if(l == "Cost:" && Integer.valueOf(sDep) < 35000) {
    		throw new IllegalArgumentException("Недопустимая стоимость");
    	}
    }
    
    void checkField_str(JLabel label, JTextField b) throws Exception_null_pointer{
    	String sDep = b.getText().trim();
    	String l = label.getText();
    	String a = " Пожалуйста, введите только буквы латинского алфавита в поле";
    	String c = " Недопустимые данные";
    	String res_1 = l + a;
    	String res_2 = l + c;
    	if(sDep.length() == 0) { 
			throw new Exception_null_pointer();
		}
    	
    	if(l.equals("Designation") && !sDep.matches("(?ui)[a-zA-Z]+( [a-zA-Z]+)?")) {
    		throw new IllegalArgumentException(res_1);
    	}
    	
    	if(l == "Description:" && (sDep.contains("  ")) || !sDep.matches("[a-zA-Z ]*")){
    		throw new IllegalArgumentException(res_2);
    	}
    	if (!sDep.matches("(?ui)[a-zA-Z]+") && (l != "Description:") && (l != "Designation")) {
    		throw new IllegalArgumentException(res_1);
        }
    	
    	if (sDep.length() < 3) {
    		throw new IllegalArgumentException(res_2);
        }
    }
    

    public EditDialog(JFrame parent, DefaultTableModel model, StaffMember sm, ArrayList<String> l_s,int rowIndex, String surname, String name, String id_data,  
    		String position, String division, String age, String work_exp, String salary) {
        super(parent, "Редактировать запись", true);			
        this.rowIndex = rowIndex;
        
        posComboBox = new JComboBox<>(positions.toArray(new Position[0]));
        divComboBox = new JComboBox<>(divisions.toArray(new Division[0]));
        
        setLayout(new GridLayout(10, 2));
        
        JLabel surnameLab = new JLabel("Surname:");
        add(surnameLab);
        surnameField = new JTextField(surname);
        add(surnameField);
        
        JLabel nameLab = new JLabel("Name:");
        add(nameLab);
        nameField = new JTextField(name);
        add(nameField);
        
        JLabel id_dataLab = new JLabel("Identification data:");
        add(id_dataLab);
        id_dataField = new JTextField(id_data);
        add(id_dataField);
        
        JLabel posLab = new JLabel("Position:");
        add(posLab);
        posField = new JTextField(position);
        add(posComboBox);
        
        JLabel divLab = new JLabel("Division:");
        add(divLab);
        divField = new JTextField(division);
        add(divComboBox);
        
        
        JLabel ageLab = new JLabel("Age:");
        add(ageLab);
        ageField = new JTextField(age);
        add(ageField);
        
        JLabel weLab = new JLabel("Work experience:");
        add(weLab);
        weField = new JTextField(work_exp);
        add(weField);
        
        JLabel salLab = new JLabel("Salary:");
        add(salLab);
        salField = new JTextField(salary);
        add(salField);
        
        //setupAutoComplete(surnameField, l_s);
        

        JButton saveButton = new JButton("Сохранить");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try {
            	
	            	Position position = (Position) posComboBox.getSelectedItem();
	                Division division = (Division) divComboBox.getSelectedItem();
	            	
	            	checkField_str(surnameLab,surnameField);
	            	checkField_str(nameLab, nameField);
	            	
	            	checkField_dig(id_dataLab,id_dataField);
	            	checkField_dig(ageLab,ageField);
	            	checkField_dig(weLab,weField);
	            	checkField_dig(salLab,salField);
	            	
	            	
	            	em.getTransaction().begin();
	            	StaffMember s = em.find(StaffMember.class, sm.getSmId());
	                model.setValueAt(surnameField.getText(), rowIndex, 1);
	                model.setValueAt(nameField.getText(), rowIndex, 2);
	                model.setValueAt(id_dataField.getText(), rowIndex, 3);
	                model.setValueAt(position.getName(), rowIndex, 4);
	                model.setValueAt(division.getName(), rowIndex, 5);
	                model.setValueAt(ageField.getText(), rowIndex, 6);
	                model.setValueAt(weField.getText(), rowIndex, 7);
	                model.setValueAt(salField.getText(), rowIndex, 8);
	                
	                s.setSurname(surnameField.getText());
	                s.setName(nameField.getText());
	                s.setIdent_data(Long.valueOf(id_dataField.getText()));
	                s.setPosition(position);
	                s.setDivision(division);
	                s.setAge(Integer.valueOf(ageField.getText()));
	                s.setWork_exp(Integer.valueOf(weField.getText()));
	                s.setSalary(Integer.valueOf(salField.getText()));
	                
	                
	                
	                em.getTransaction().commit();
	                dispose(); // Закрываем диалог
            	} catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(parent, ex.getMessage(), "Ошибка ввода", JOptionPane.ERROR_MESSAGE);
                    ageField.requestFocus();
                } 
        		catch(Exception_null_pointer c) {
        			JOptionPane.showMessageDialog(null, c.getMessage());
        		}
            }
        });
        saveButton.setSize(100,40);
        add(saveButton);

        setSize(1000, 700);
        setLocationRelativeTo(parent); // Центрируем диалог относительно родителя
        setVisible(true);
        }

        
        

    
    public EditDialog(JFrame parent, DefaultTableModel model, Project sm, ArrayList<String> l_s, int rowIndex, String name, String deadline, String cost, String client) {
    	 super(parent, "Редактировать запись", true);			
         this.rowIndex = rowIndex;
         
         clComboBox = new JComboBox<>(clients.toArray(new Client[0]));
         
         setLayout(new GridLayout(10, 2));

         JLabel nameLab = new JLabel("Designation");
         add(nameLab);
         nameField = new JTextField(name);
         add(nameField);
         
         JLabel deadlLab = new JLabel("Deadline:");
         add(deadlLab);
         
         JXDatePicker deadlinePicker = new JXDatePicker();
         if (deadline != null && !deadline.isEmpty()) {
             // Предполагаем, что deadline - это строка в формате "yyyy-MM-dd"
             SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
             try {
                 Date date = sdf.parse(deadline);
                 deadlinePicker.setDate(date);
             } catch (ParseException e) {
                 e.printStackTrace(); // Обработка исключения
             }
         }
         add(deadlinePicker);
         
         
         JLabel costLab = new JLabel("Cost:");
         add(costLab);
         JTextField costField = new JTextField(cost);
         add(costField);
         
         JLabel clLab = new JLabel("Client:");
         add(clLab);
         JTextField clField = new JTextField(client);
         //add(clField);
         add(clComboBox);
         

         
         //setupAutoComplete(nameField, l_s);

         JButton saveButton = new JButton("Сохранить");
         saveButton.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
             	try {
             		Client client = (Client) clComboBox.getSelectedItem();
             		
 	            	checkField_str(nameLab, nameField);
 	            	checkField_dig(costLab, costField);
 	            	
 	            	
 	            	
 	            	
 	            	
 	            	em.getTransaction().begin();
 	            	Project s = em.find(Project.class, sm.getPrid());
 	            	
 	            	Date selectedDate = deadlinePicker.getDate();
 	                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
 	                String formattedDate = sdf.format(selectedDate);
 	                
 	                
 	                model.setValueAt(nameField.getText(), rowIndex, 1);
 	                model.setValueAt(formattedDate, rowIndex, 2);
 	                model.setValueAt(costField.getText(), rowIndex, 3);
 	                model.setValueAt(client.getName(), rowIndex, 4);

 	                

 	                s.setName(nameField.getText());
 	                s.setCost(Integer.valueOf(costField.getText()));
 	                s.setClid(client);
 	                s.setDeadline(selectedDate);
 	               

 	                
 	                em.getTransaction().commit();
 	                dispose(); // Закрываем диалог
             	} catch (IllegalArgumentException ex) {
                     // Обработка исключения и отображение сообщения пользователю
                     JOptionPane.showMessageDialog(parent, ex.getMessage(), "Ошибка ввода", JOptionPane.ERROR_MESSAGE);
                     ageField.requestFocus();
             	}catch(Exception_null_pointer c) {
        			JOptionPane.showMessageDialog(null, c.getMessage());
        		}
             }
         });
         saveButton.setSize(100,40);
         add(saveButton);

         setSize(1000, 700);
         setLocationRelativeTo(parent); // Центрируем диалог относительно родителя
         setVisible(true);
         }
    
    
         
    
    public EditDialog(JFrame parent, DefaultTableModel model, Client sm, ArrayList<String> l_s, int rowIndex, String name, String ident_data, String is_legal) {
    	super(parent, "Редактировать запись", true);			
        this.rowIndex = rowIndex;
        
        is_legalModel = new DefaultComboBoxModel<String>();
        for (int i = 0; i < elements.length; i++) 
            is_legalModel.addElement((String)elements[i]);
        
        is_legalComboBox = new JComboBox<String>(is_legalModel);
        is_legalComboBox.setSelectedItem(is_legal);
        
        setLayout(new GridLayout(10, 2));

        JLabel nameLab = new JLabel("Designation");
        add(nameLab);
        nameField = new JTextField(name);
        add(nameField);
        
        JLabel id_dataLab = new JLabel("Identification data:");
        add(id_dataLab);
        id_dataField = new JTextField(ident_data);
        add(id_dataField);
       
        JLabel entLab = new JLabel("Entity:");
        add(entLab);
        JTextField entField = new JTextField(is_legal);
        add(is_legalComboBox);
        

        
        //setupAutoComplete(nameField, l_s);

        JButton saveButton = new JButton("Сохранить");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try {
            		String flag = (String)is_legalComboBox.getSelectedItem();
            		Byte i;
            		if ("Physical".equals(flag)) {
            			i = 0;
            		}else {i = 1;}
            		
	            	checkField_str(nameLab, nameField);
	            	checkField_dig(id_dataLab, id_dataField);
	            	
	            	

	            	em.getTransaction().begin();
	            	Client s = em.find(Client.class, sm.getClid());

	                model.setValueAt(nameField.getText(), rowIndex, 1);
	                model.setValueAt(id_dataField.getText(), rowIndex, 2);
	                model.setValueAt(flag, rowIndex, 3);

	                s.setName(nameField.getText());
	                s.setIdent_data(Integer.valueOf(id_dataField.getText()));
	                s.setIs_legal(i);
	               

	                
	                em.getTransaction().commit();
	                dispose(); // Закрываем диалог
            	} catch (IllegalArgumentException ex) {
                    // Обработка исключения и отображение сообщения пользователю
                    JOptionPane.showMessageDialog(parent, ex.getMessage(), "Ошибка ввода", JOptionPane.ERROR_MESSAGE);
                    ageField.requestFocus();
            	}catch(Exception_null_pointer c) {
       			JOptionPane.showMessageDialog(null, c.getMessage());
       		}
            }
        });
        saveButton.setSize(100,40);
        add(saveButton);

        setSize(1000, 700);
        setLocationRelativeTo(parent); // Центрируем диалог относительно родителя
        setVisible(true);
    }
    
    
    public EditDialog(JFrame parent, DefaultTableModel model, Task sm, ArrayList<String> l_s, int rowIndex, String description, String deadline, Project project, StaffMember staff) {
    	super(parent, "Редактировать запись", true);			
        this.rowIndex = rowIndex;
        
        prComboBox = new JComboBox<>(projects.toArray(new Project[0]));
        prComboBox.setSelectedItem(project);
        stComboBox = new JComboBox<>(staffs.toArray(new StaffMember[0]));
        stComboBox.setSelectedItem(staff);
        
        setLayout(new GridLayout(10, 2));

        JLabel nameLab = new JLabel("Description:");
        add(nameLab);
        nameField = new JTextField(description);
        add(nameField);
        
        JLabel deadlLab = new JLabel("Deadline:");
        add(deadlLab);
        
        JXDatePicker deadlinePicker = new JXDatePicker();
        if (deadline != null && !deadline.isEmpty()) {
            // Предполагаем, что deadline - это строка в формате "yyyy-MM-dd"
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = sdf.parse(deadline);
                deadlinePicker.setDate(date);
            } catch (ParseException e) {
                e.printStackTrace(); // Обработка исключения
            }
        }
        add(deadlinePicker);
        
        
        JLabel projectLab = new JLabel("Project:");
        add(projectLab);
        JTextField projectField = new JTextField(project.getName());
        add(prComboBox);
        
        JLabel stLab = new JLabel("Staff Member:");
        add(stLab);
        JTextField stField = new JTextField(staff.getSurname());
        add(stComboBox);
       // add(clComboBox);
        

        
        //setupAutoComplete(nameField, l_s);

        JButton saveButton = new JButton("Сохранить");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try {
            		Project project = (Project) prComboBox.getSelectedItem();
            		StaffMember staff = (StaffMember) stComboBox.getSelectedItem();
            		
	            	checkField_str(nameLab, nameField);
	            	
	            	long deadlineTime = deadlinePicker.getDate().getTime();
	 	                if(deadlineTime > project.getDeadline().getTime()) {
	 	                	Date pr_deadl  = project.getDeadline();
	 	                	String p = String.valueOf(pr_deadl);
	 	                	String a = "Дедлайн задачи позже дедлайна проекта. Дедлайн проекта: ";
	 	                	String res = a+p;
	 	                	throw new IllegalArgumentException(res);
	 	                }
	            	
	            	em.getTransaction().begin();
	            	Task s = em.find(Task.class, sm.getTid());
	            	
	            	Date selectedDate = deadlinePicker.getDate();
	                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	                String formattedDate = sdf.format(selectedDate);
	                
	                
	                model.setValueAt(nameField.getText(), rowIndex, 1);
	                model.setValueAt(formattedDate, rowIndex, 2);
	                model.setValueAt(project.getName(), rowIndex, 3);
	                model.setValueAt(staff.getSurname(), rowIndex, 4);

	                

	                s.setDescription(nameField.getText());
	                s.setDeadline(selectedDate);
	                s.setProject(project);
	                s.setStaff(staff);
	               

	                
	                em.getTransaction().commit();
	                dispose(); // Закрываем диалог
            	} catch (IllegalArgumentException ex) {
                    // Обработка исключения и отображение сообщения пользователю
                    JOptionPane.showMessageDialog(parent, ex.getMessage(), "Ошибка ввода", JOptionPane.ERROR_MESSAGE);
                    ageField.requestFocus();
            	}catch(Exception_null_pointer c) {
       			JOptionPane.showMessageDialog(null, c.getMessage());
       		}
            }
        });
        saveButton.setSize(100,40);
        add(saveButton);

        setSize(1000, 700);
        setLocationRelativeTo(parent); // Центрируем диалог относительно родителя
        setVisible(true);
        
    }

    
}
        


