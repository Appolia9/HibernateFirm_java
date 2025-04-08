package firm.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.EventObject;

class AutoCompleteCellEditor extends AbstractCellEditor implements TableCellEditor {
    private JComboBox<String> comboBox;
    private ArrayList<String> options;

    public AutoCompleteCellEditor(ArrayList<String> options) {

        comboBox = new JComboBox<>(options.toArray(new String[0]));
        comboBox.setEditable(true); // Делаем комбобокс редактируемым

        // Добавляем слушатель для фильтрации
        comboBox.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filterComboBox();
            }
        });
    }

    @Override
    public Object getCellEditorValue() {
        return comboBox.getSelectedItem();
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        comboBox.setSelectedItem(value);
        return comboBox;
    }

    @Override
    public boolean isCellEditable(EventObject e) {
        return true; // Можно настроить, когда ячейка редактируема
    }

    private void filterComboBox() {
        String input = (String) comboBox.getEditor().getItem();
        comboBox.removeAllItems();

        // Добавляем только те элементы, которые начинаются с введенного текста
        for (String option : options) {
            if (option.toLowerCase().startsWith(input.toLowerCase())) {
                comboBox.addItem(option);
            }
        }

        // Устанавливаем курсор в конец текста
        comboBox.getEditor().setItem(input);
        comboBox.showPopup(); // Показываем выпадающий список
    }

    public void addOption(String newOption) {
        if (!newOption.isEmpty() && !options.contains(newOption)) {
            options.add(newOption); // Добавляем новое значение в список
            comboBox.addItem(newOption); // Добавляем его в JComboBox
        }
    }
}
