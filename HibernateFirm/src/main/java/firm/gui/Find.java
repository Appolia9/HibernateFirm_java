package firm.gui;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import java.awt.*;
import java.awt.event.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class Find extends JPanel{
	JButton find, back_table;
	JComboBox<String> info;
	JTextField search;
	JScrollPane scrollfind;
	JPopupMenu popupMenu;
	JList suggestionList;
	DefaultListModel<String> listModel = new DefaultListModel<>();
	
	private void checkField(JTextField b) throws  Exception_null_pointer{
		String sDep = b.getText().trim();
		boolean isValidDepartment = false;
		if(sDep.length() == 0) { 
			throw new Exception_null_pointer();
		}	
	}
	
	private void updateSuggestions( DefaultTableModel originalModel) {
        String input = search.getText().toLowerCase(); // Получаем текст из поля
        listModel.clear();
        // Фильтруем данные из originalModel
        for (int row = 0; row < originalModel.getRowCount(); row++) {
            String name = originalModel.getValueAt(row, 1).toString(); // Предположим, что имя во втором столбце
            if (name.toLowerCase().contains(input)) {
                listModel.addElement(name); // Добавляем в модель списка, если совпадает
            }
        }

        suggestionList.setModel(listModel); // Устанавливаем модель в список

        if (listModel.size() > 0) {
        	if (!popupMenu.isVisible()) {
            popupMenu.show(search, 0, search.getHeight()); // Показываем меню с подсказками
        	}
        } else {
            popupMenu.setVisible(false); // Скрываем меню, если нет подсказок
        }
	}
	
	public Find(JFrame frame,String[] columnNames, String[] fields, JTable positionTable,JScrollPane scroll, int flag) {
		setLayout(new BorderLayout());
		
		info = new JComboBox<>(fields);
		search = new JTextField(5);
		find = new JButton("Find");
		back_table = new JButton("Back");
		
		DefaultTableModel originalModel = (DefaultTableModel) positionTable.getModel();
		
		popupMenu = new JPopupMenu();
        suggestionList = new JList<>();
        suggestionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        suggestionList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Двойной клик для выбора
                    String selectedValue = (String)suggestionList.getSelectedValue();
                    search.setText(selectedValue); // Вставляем выбранное значение в поле
                    popupMenu.setVisible(false); // Скрываем меню
                }
            }
        });
        
        popupMenu.add(new JScrollPane(suggestionList));
        
        search.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateSuggestions(originalModel);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateSuggestions(originalModel);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateSuggestions(originalModel);
            }
        });
        
		
		ActionListener finding_listening = new ActionListener() { 
		    @Override 
		    public void actionPerformed(ActionEvent arg0) { 
		       // logR.info("Поиск данных введённых пользователем"); 
		        
		        DefaultTableModel modelfind = new DefaultTableModel(columnNames, 0); 
		        
		        try { 
		            String SearchColumn = (String) info.getSelectedItem(); 
		            
		            if (SearchColumn.equalsIgnoreCase(info.getItemAt(0))) { 
		                JOptionPane.showMessageDialog(frame, "Выберите столбец для поиска", "Ошибка поиска", JOptionPane.WARNING_MESSAGE);
		            } else { 
		                String SearchElem = search.getText().toLowerCase(); 
		                int cnt_str = 0; 

		                String[] columns = {"id","Surname","Name", "Identification Data", "Division", "Position", "Task","Age", "Work Experience", "Salary", ""};
		        		
		                // Получаем данные из основной таблицы
		                DefaultTableModel originalModel = (DefaultTableModel) positionTable.getModel(); // positionTable - это ваша исходная JTable
		                if(flag==0) {
			                for (int row = 0; row < originalModel.getRowCount(); row++) { 
			                    String id = originalModel.getValueAt(row, 0).toString(); // Предполагаем, что ID в первом столбце
			                    String surname = originalModel.getValueAt(row, 1).toString(); // Предполагаем, что роль во втором столбце
			                    String name = originalModel.getValueAt(row, 2).toString();
			                    String id_data = originalModel.getValueAt(row, 3).toString();
			                    String division = originalModel.getValueAt(row, 4).toString();
			                    String position = originalModel.getValueAt(row, 5).toString();
			                    String task = originalModel.getValueAt(row, 6).toString();
			                    String age = originalModel.getValueAt(row, 7).toString();
			                    String work_exp = originalModel.getValueAt(row, 8).toString();
			                    String salary = originalModel.getValueAt(row, 9).toString();
			                    
			                    String surnameLower = surname.toLowerCase();
			                    String nameLower = name.toLowerCase();
			                    String id_dataLower = surname.toLowerCase();
			                    String divisionLower = surname.toLowerCase();
			                    String positionLower = surname.toLowerCase();
			                    String taskLower = surname.toLowerCase();
			                    
			                    if (SearchColumn.equals("Surname")) {  
			                        if (surnameLower.contains(SearchElem)) { 
			                            cnt_str++;  
			                            modelfind.addRow(new String[]{id, surname, name, id_data, division, position, task, age, work_exp, salary, ""}); 
			                        } 
			                    } 
			                    
			                    if (SearchColumn.equals("id")) { 
			                        try {  
			                            checkField(search); 
			                            if (Integer.parseInt(id) == Integer.parseInt(SearchElem)) { 
			                                cnt_str++;  
			                                modelfind.addRow(new String[]{id, surname, name, id_data, division, position, task, age, work_exp, salary, ""}); 
			                            } 
			                        } catch (Exception_null_pointer e) { 
			                            e.printStackTrace(); 
			                        } 
			                    } 
			                } 
		                }

		                if (cnt_str == 0) {  
		                    //logR.warn("Поиск завершён, искомые пользователем данные не найдены"); 
		                    JOptionPane.showMessageDialog(frame, "По вашему запросу ничего не найдено", "Ошибка поиска", JOptionPane.WARNING_MESSAGE); 
		                } else { 
		                   JTable positionfind = new JTable(modelfind); 
		                   scrollfind = new JScrollPane(positionfind); 

		                    if (scroll != null) { 
		                        frame.remove(scroll); 
		                        frame.revalidate(); 
		                        frame.repaint(); 
		                    } 

		                    frame.add(scrollfind, BorderLayout.CENTER); 
		                    frame.setVisible(true);  
		                    //logR.info("Поиск завершён, искомые пользователем данные найдены"); 
		                } 
		            } 
		        } catch (NullPointerException ex) { 
		            JOptionPane.showMessageDialog(frame, ex.toString()); 
		        }  
		    } 
		};
		find.addActionListener(finding_listening);		
		ActionListener back_tabling_listening = new ActionListener() { 
			   @Override 
			   public void actionPerformed(ActionEvent arg0) { 
			    if (scrollfind!=null) 
			    { 
			     //logR.info("Возврат к полной таблице"); 
			     frame.remove(scrollfind); 
			                  
			              // Обновление интерфейса 
			     frame.revalidate(); 
			     frame.repaint(); 
			    } 
			    frame.add(scroll, BorderLayout.CENTER); 
			    frame.setVisible(true); 
			   } 
		}; 
			   
			   
			  back_table.addActionListener(back_tabling_listening);
	
	this.add(info, BorderLayout.WEST);
    this.add(search, BorderLayout.CENTER);
    
    
    JPanel buttonPanel = new JPanel();
    buttonPanel.add(find);
    buttonPanel.add(back_table);
    
    add(buttonPanel, BorderLayout.SOUTH);
 }
	
	private void filterTable(JFrame frame,DefaultTableModel originalModel, String searchColumn, String searchElem, String[]columns) {
	    // Сохраняем текущее состояние модели
	    int rowCount = originalModel.getRowCount();
	    boolean[] visibleRows = new boolean[rowCount];

	    for (int row = 0; row < rowCount; row++) {
	        String valueToCheck = originalModel.getValueAt(row, getColumnIndex(searchColumn,columns)).toString().toLowerCase();
	        visibleRows[row] = valueToCheck.contains(searchElem);
	    }

	    // Обновляем модель таблицы
	    for (int row = 0; row < rowCount; row++) {
	        if (!visibleRows[row]) {
	            originalModel.removeRow(row);
	            row--; // Уменьшаем индекс, так как строка была удалена
	            rowCount--; // Уменьшаем общее количество строк
	        }
	    }
	    // Перерисовываем таблицу
	  //  positionTable.revalidate();
	 //   positionTable.repaint();

	    if (originalModel.getRowCount() == 0) {
	        JOptionPane.showMessageDialog(frame, "По вашему запросу ничего не найдено", "Ошибка поиска", JOptionPane.WARNING_MESSAGE);
	        // logR.warn("Поиск завершён, искомые пользователем данные не найдены");
	    } else {
	        // logR.info("Поиск завершён, искомые пользователем данные найдены");
	    }
	}
	
	private int getColumnIndex(String columnName, String[] columns) {
	    // Предположим, что у вас есть массив с названиями столбцов
	    for (int i = 0; i < columns.length; i++) {
	        if (columns[i].equalsIgnoreCase(columnName)) {
	            return i;
	        }
	    }
	    return -1; // Если столбец не найден
	}
}

