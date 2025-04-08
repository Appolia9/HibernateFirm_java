package firm.gui;

import org.jdesktop.swingx.JXDatePicker; // Импортируйте необходимый класс для JXDatePicker
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.Date;

public class Find_projects extends JPanel {
    JButton find, back_table;
    JComboBox<String> info;
    JTextField search;
    JScrollPane scrollfind;
    JXDatePicker datePicker; // Добавляем JXDatePicker
    TableRowSorter<TableModel> rowSorter;

    public Find_projects(JFrame frame, String[] columnNames, String[] fields, JTable positionTable, JScrollPane scroll, int flag) {
        setLayout(new BorderLayout());

        info = new JComboBox<>(fields);
        search = new JTextField(15);
        find = new JButton("Find");
        back_table = new JButton("Back");

        rowSorter = new TableRowSorter<>(positionTable.getModel());
        positionTable.setRowSorter(rowSorter);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Specify a word to match:"));
        panel.add(Box.createRigidArea(new Dimension(5, 0)));
        panel.add(search);
        panel.add(Box.createRigidArea(new Dimension(5, 0)));
        panel.add(info);

        // Инициализация JXDatePicker
        datePicker = new JXDatePicker();
        panel.add(Box.createRigidArea(new Dimension(5, 0)));
        panel.add(new JLabel("Select Date:"));
        panel.add(datePicker);

        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(positionTable), BorderLayout.CENTER);

        // Добавляем слушателя для текстового поля
        search.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                filterTable(positionTable);
            }

            public void removeUpdate(DocumentEvent e) {
                filterTable(positionTable);
            }

            public void changedUpdate(DocumentEvent e) {
                filterTable(positionTable);
            }
        });

        // Добавляем слушателя для выбора даты
        datePicker.addActionListener(e -> filterTable(positionTable));
    }

    /*
    private void filterTable(JTable positionTable) {
        String text = search.getText();
        String selectedColumn = (String) info.getSelectedItem();
        int columnIndex = getColumnIndex(selectedColumn, positionTable);

        if (text.trim().length() == 0 && datePicker.getDate() == null) {
            rowSorter.setRowFilter(null);
        } else {
            RowFilter<TableModel, Object> combinedFilter = null;

            if (text.trim().length() > 0 && columnIndex != -1) {
                combinedFilter = RowFilter.regexFilter("(?i)" + text, columnIndex);
            }

            Date selectedDate = datePicker.getDate();
            
            
            if (selectedDate == null) {
                rowSorter.setRowFilter(null);
                return;
            }
            
            
            
            long selectedTime = selectedDate.getTime();
            

            if (selectedDate != null) {
            	 RowFilter<TableModel, Object> dateFilter = new RowFilter<TableModel, Object>() {
                     @Override
                     public boolean include(Entry<? extends TableModel, ? extends Object> entry) {
                         // Предполагаем, что deadline находится в столбце с индексом 2
                         Object deadlineValue = entry.getValue(2); // Замените 2 на индекс столбца с deadline

                         if (deadlineValue instanceof Date) {
                        	 long deadlineTime = ((Date) deadlineValue).getTime();
                             return deadlineTime < selectedTime; // Проверяем, просрочен ли deadline
                         }
                         return false; // Если значение не является датой, не включаем в результат
                     }
                 };
                         
            
                 
            if (combinedFilter != null) {
                combinedFilter = RowFilter.andFilter(java.util.Arrays.asList(combinedFilter, dateFilter));
            } else {
                combinedFilter = dateFilter;
            }
            

            rowSorter.setRowFilter(combinedFilter);
            }
        }
*/

    private void filterTable(JTable positionTable) {
        String text = search.getText();
        String selectedColumn = (String) info.getSelectedItem();
        int columnIndex = getColumnIndex(selectedColumn, positionTable);

        // Получаем выбранную дату
        Date selectedDate = datePicker.getDate();

        // Создаем фильтры
        RowFilter<TableModel, Object> textFilter = null;
        RowFilter<TableModel, Object> dateFilter = null;

        // Фильтруем по текстовому полю
        if (text.trim().length() > 0 && columnIndex != -1) {
            textFilter = RowFilter.regexFilter("(?i)" + text, columnIndex);
        }

        // Фильтруем по дате
        if (selectedDate != null) {
            long selectedTime = selectedDate.getTime();
            dateFilter = new RowFilter<TableModel, Object>() {
                @Override
                public boolean include(Entry<? extends TableModel, ? extends Object> entry) {
                    // Предполагаем, что deadline находится в столбце с индексом 2
                    Object deadlineValue = entry.getValue(2); // Замените 2 на индекс столбца с deadline

                    if (deadlineValue instanceof Date) {
                        long deadlineTime = ((Date) deadlineValue).getTime();
                        return deadlineTime < selectedTime; // Проверяем, просрочен ли deadline
                    }
                    return false; // Если значение не является датой, не включаем в результат
                }
            };
        }
        RowFilter<TableModel, Object> combinedFilter = null;

        if (textFilter != null && dateFilter != null) {
            combinedFilter = RowFilter.andFilter(java.util.Arrays.asList(textFilter, dateFilter));
        } else if (textFilter != null) {
            combinedFilter = textFilter;
        } else if (dateFilter != null) {
            combinedFilter = dateFilter;
        }

        rowSorter.setRowFilter(combinedFilter);
    }
    
    private int getColumnIndex(String columnName, JTable positionTable) {
    	  for (int i = 0; i < positionTable.getColumnCount(); i++) {
              if (positionTable.getColumnName(i).equalsIgnoreCase(columnName)) {
                  return i;
              }
          }
          return -1; // Если столбец не найден
      }
 
}
