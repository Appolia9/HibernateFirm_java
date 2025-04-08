package firm.gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.swing.*;

import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;

import firm.hibernate.Task;

public class ButtonEditor_tasks extends DefaultCellEditor {
    private JButton button;
    private String label;
    private JTable table;
    private boolean isPushed;
    private int staffMemberId;
    private int row;// ID сотрудника

    public ButtonEditor_tasks(JCheckBox checkBox, JTable table) {
        super(checkBox);
        this.table = table; 
        
        
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
                int row = table.getSelectedRow();
                if (row != -1) {
               	 	staffMemberId = (int) table.getValueAt(row, 0);
                }
                showTasks(staffMemberId);
               // showTasksForStaffMember(staffMemberId); // Показываем задачи
            }
        });
    }

    // Метод для отображения задач
    private void showTasksForStaffMember(int staffMemberId) {
        List<Task> tasks = getTasksByStaffMember(staffMemberId); // Получаем задачи
        StringBuilder taskList = new StringBuilder("Tasks for Staff Member ID " + staffMemberId + ":\n");

        for (Task task : tasks) {
            taskList.append("Task ID: ").append(task.getTid())
                     .append(", Description: ").append(task.getDescription())
                     .append(", Deadline: ").append(task.getDeadline()).append("\n");
        }

        JOptionPane.showMessageDialog(button, taskList.toString(), "Tasks", JOptionPane.INFORMATION_MESSAGE);
    }
    
	public List<Task> getTasksByStaffMember(int staffMemberId) {
		List<Task> tasks = new ArrayList<>();
		try {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("firm_persistence");
	        EntityManager em = emf.createEntityManager();
		    String hql = "SELECT t FROM Task t JOIN t.staffmembers s WHERE s.smid = :staffMemberId";
		    TypedQuery<Task> query = em.createQuery(hql, Task.class);
		    query.setParameter("staffMemberId", staffMemberId);
		    tasks = query.getResultList();
		    em.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	    return tasks;
	}
	
	 private void showTasks(int staffId) {
	         // Получаем ID сотрудника
	        //List<Task> tasks = getTasksByStaffMember(staffId); // Загружаем задачи из БД
		 	
		 	String[] tasks  = {"Task_1", "Task_2"};
	        // Создаем диалоговое окно для отображения задач
	        JDialog dialog = new JDialog();
	        dialog.setTitle("Tasks for Staff Member ID: " + staffId);
	        dialog.setSize(400, 300);
	        
//	        JTextArea textArea = new JTextArea();
//	        for (Task task : tasks) {
//	            textArea.append(task.toString() + "\n"); // Предполагается, что Task имеет метод toString()
//	        }
	        
	        JTextArea textArea = new JTextArea();
	        for (String task : tasks) {
	            textArea.append(task + "\n"); // Предполагается, что Task имеет метод toString()
	        }
	        dialog.add(new JScrollPane(textArea));
	        dialog.setVisible(true);
	    }

    public Component getTableCellEditorComponent(JTable table, Object value,
                                                  boolean isSelected, int row) {
    	this.row = row;
        label = (value == null) ? "Tasks" : value.toString();
        button.setText(label);
        isPushed = true;
        return button;
    }

    @Override
    public Object getCellEditorValue() {

        return label;
    }
}