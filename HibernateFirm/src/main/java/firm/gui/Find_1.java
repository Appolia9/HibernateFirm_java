package firm.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.RowFilter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class Find_1 extends JPanel{
	JButton find, back_table;
	JComboBox<String> info;
	JTextField search;
	JScrollPane scrollfind;
	JPopupMenu popupMenu;
	JList suggestionList;
	
	public Find_1(JFrame frame, String[] columnNames, String[] fields, JTable positionTable, JScrollPane scroll, int flag) {
	    setLayout(new BorderLayout());

	    info = new JComboBox<>(fields);
	    search = new JTextField(5);
	    find = new JButton("Find");
	    back_table = new JButton("Back");
	    
	    TableRowSorter<TableModel> rowSorter
        = new TableRowSorter<>(positionTable.getModel());
	    
	    
	    DefaultTableModel originalModel = (DefaultTableModel) positionTable.getModel();
	    DefaultTableModel filteredModel = new DefaultTableModel(new Object[0][0], columnNames);
	    

	    ActionListener finding_listening = new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent arg0) {
	            // logR.info("Поиск данных введённых пользователем");

	            String SearchColumn = (String) info.getSelectedItem();

	            if (SearchColumn.equalsIgnoreCase(info.getItemAt(0))) {
	                JOptionPane.showMessageDialog(frame, "Выберите столбец для поиска", "Ошибка поиска", JOptionPane.WARNING_MESSAGE);
	            } else {
	                String SearchElem = search.getText().toLowerCase();
	                filterTable(originalModel, SearchColumn, SearchElem,columnNames,positionTable, frame);
	            }
	        }
	    };

	    find.addActionListener(finding_listening);
	    
	    back_table.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	filteredModel.setRowCount(0);
	        	positionTable.setModel(originalModel);
	        }
	    });
	    
	    this.add(info, BorderLayout.WEST);
	    this.add(search, BorderLayout.CENTER);
	    
	    
	    JPanel buttonPanel = new JPanel();
	    buttonPanel.add(find);
	    buttonPanel.add(back_table);
	    
	    add(buttonPanel, BorderLayout.SOUTH);
	}

	private void filterTable(DefaultTableModel originalModel, String searchColumn, String searchElem,String[] columnNames, JTable positionTable, JFrame frame) {
	    // Сохраняем текущее состояние модели
	    int rowCount = originalModel.getRowCount();
	    boolean[] visibleRows = new boolean[rowCount];

	    for (int row = 0; row < rowCount; row++) {
	        String valueToCheck = originalModel.getValueAt(row, getColumnIndex(searchColumn, columnNames)).toString().toLowerCase();
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
	    positionTable.revalidate();
	    positionTable.repaint();

	    if (originalModel.getRowCount() == 0) {
	        JOptionPane.showMessageDialog(frame, "По вашему запросу ничего не найдено", "Ошибка поиска", JOptionPane.WARNING_MESSAGE);
	        // logR.warn("Поиск завершён, искомые пользователем данные не найдены");
	    } else {
	        // logR.info("Поиск завершён, искомые пользователем данные найдены");
	    }
	}

	
	
	private int getColumnIndex(String columnName, String[] columnNames) {
	    // Предположим, что у вас есть массив с названиями столбцов
	    for (int i = 0; i < columnNames.length; i++) {
	        if (columnNames[i].equalsIgnoreCase(columnName)) {
	            return i;
	        }
	    }
	    return -1; // Если столбец не найден
	}
}
