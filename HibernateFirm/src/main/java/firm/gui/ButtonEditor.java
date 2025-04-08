package firm.gui;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import firm.hibernate.Client;
import firm.hibernate.Project;
import firm.hibernate.StaffMember;
import firm.hibernate.Task;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ButtonEditor extends DefaultCellEditor {
	
	private JButton button;
	private JTable table;
	private boolean isEditButton;
	private boolean isPushed;
	private String label;
	private Color color;
	
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("firm_persistence");
    EntityManager em = emf.createEntityManager();
	
	public ButtonEditor(JCheckBox checkBox, JTable table, int flag){
		super(checkBox);
		this.table = table;
		this.isEditButton = isEditButton;
		color = new Color(0xff2b2b);
		
		button = new JButton();

	
		
		button.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent b) {
				fireEditingStopped(); // Останавливаем редактирование
                int row = table.getSelectedRow();
                int confirm = JOptionPane.showConfirmDialog(table,
                        "Are you sure you want to delete the item?", "Confirm the delition",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION && row != -1) {
                	 int id = (int) table.getValueAt(row, 0); // Предполагается, что ID находится в первом столбце

                     // Удаляем запись из базы данных
                     em.getTransaction().begin();
                     switch (flag) {
                     case 1:
                    	 try {
	                         // Находим запись по ID
	                         StaffMember staffMember = em.find(StaffMember.class, id);
	                         if (staffMember != null) {
	                             em.remove(staffMember); // Удаляем запись
	                         }
	                         em.getTransaction().commit(); // Подтверждаем транзакцию
	                     } catch (Exception e) {
	                         em.getTransaction().rollback(); // Откатываем транзакцию в случае ошибки
	                         e.printStackTrace();
	                     }
	                    ((DefaultTableModel) table.getModel()).removeRow(row); // Удаляем строку
	                    break;
                     case 2:
                    	 try {
	                         // Находим запись по ID
	                         Project project= em.find(Project.class, id);
	                         if (project != null) {
	                             em.remove(project); // Удаляем запись
	                         }
	                         em.getTransaction().commit(); // Подтверждаем транзакцию
	                     } catch (Exception e) {
	                         em.getTransaction().rollback(); // Откатываем транзакцию в случае ошибки
	                         e.printStackTrace();
	                     }
	                    ((DefaultTableModel) table.getModel()).removeRow(row); // Удаляем строку
	                    break;
                     case 3:
                    	 try {
	                         // Находим запись по ID
	                         Client client = em.find(Client.class, id);
	                         if (client != null) {
	                             em.remove(client); // Удаляем запись
	                         }
	                         em.getTransaction().commit(); // Подтверждаем транзакцию
	                     } catch (Exception e) {
	                         em.getTransaction().rollback(); // Откатываем транзакцию в случае ошибки
	                         e.printStackTrace();
	                     }
	                    ((DefaultTableModel) table.getModel()).removeRow(row); // Удаляем строку
	                    break;
                     case 4:
                    	 try {
	                         // Находим запись по ID
	                         Task task = em.find(Task.class, id);
	                         if (task != null) {
	                             em.remove(task); // Удаляем запись
	                         }
	                         em.getTransaction().commit(); // Подтверждаем транзакцию
	                     } catch (Exception e) {
	                         em.getTransaction().rollback(); // Откатываем транзакцию в случае ошибки
	                         e.printStackTrace();
	                     }
	                    ((DefaultTableModel) table.getModel()).removeRow(row); // Удаляем строку
	                    break;
                     }
    
                }
                
			}
	
		});
	}

@Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        label = (value == null) ? "Delete" : value.toString();
        button.setText(label);
        isPushed = false;
        return button;
    }

@Override
public Object getCellEditorValue() {
    if (isPushed) {
        // Действие при нажатии на кнопку
        System.out.println("Button pressed at row: " + table.getSelectedRow());
    }
    return label;
}

@Override
public boolean stopCellEditing() {
    isPushed = false;
    return super.stopCellEditing();
}

@Override
protected void fireEditingStopped() {
    super.fireEditingStopped();
}

}
