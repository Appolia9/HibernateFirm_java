package firm.gui;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import org.jdesktop.swingx.JXDatePicker; // Не забудьте добавить библиотеку JDatePicker
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class Find_project extends JPanel {
    private JButton back_table;
    private JXDatePicker datePicker; // Календарь для выбора даты
    private JTextField search;
    private JTable positionTable;

    public Find_project(JFrame frame, JTable positionTable, JScrollPane scroll) {
        setLayout(new BorderLayout());

        this.positionTable = positionTable;

        // Создаем JDatePicker для выбора даты
        datePicker = new JXDatePicker();
        search = new JTextField(15); // Увеличиваем ширину текстового поля
        back_table = new JButton("Back");

        TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(positionTable.getModel());
        positionTable.setRowSorter(rowSorter);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Select a date to filter overdue deadlines:"), BorderLayout.WEST);
        panel.add(datePicker, BorderLayout.EAST);
        // Добавляем календарь на панель
        add(new JScrollPane(positionTable), BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);
        

        // Добавляем слушатель для выбора даты
        datePicker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterTable(rowSorter);
            }
        });
    }

    private void filterTable(TableRowSorter<TableModel> rowSorter) {
    	Date selectedDate = datePicker.getDate();// Получаем выбранную дату

        if (selectedDate == null) {
            rowSorter.setRowFilter(null);
            return;
        }

        // Преобразуем выбранную дату в миллисекунды
        long selectedTime = selectedDate.getTime();

        // Фильтруем строки, оставляя только те, у которых deadline просрочен
        rowSorter.setRowFilter(new RowFilter<TableModel, Integer>() {
            @Override
            public boolean include(Entry<? extends TableModel, ? extends Integer> entry) {
                // Предполагаем, что deadline находится в столбце с индексом 0
                Object deadlineValue = entry.getValue(2); // Замените 0 на индекс столбца с deadline

                if (deadlineValue instanceof Date) {
                    long deadlineTime = ((Date) deadlineValue).getTime();
                    return deadlineTime < selectedTime; // Проверяем, просрочен ли deadline
                }
                return false;
            }
        });
    }
}
