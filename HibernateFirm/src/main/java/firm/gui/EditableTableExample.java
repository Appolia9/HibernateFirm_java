package firm.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class EditableTableExample {
   public EditableTableExample() {
        // Создаем фрейм
        JFrame frame = new JFrame("Editable JTable Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // Определяем названия колонок
        String[] columnNames = {"Имя", "Возраст", "Город"};

        // Создаем модель таблицы с возможностью редактирования
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);

        // Добавляем несколько пустых строк для заполнения
        model.addRow(new Object[]{"", "", ""});
        model.addRow(new Object[]{"", "", ""});

        // Создаем панель прокрутки для таблицы
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane);

        // Кнопка для добавления новой строки
        JButton addButton = new JButton("Добавить строку");
        addButton.addActionListener(e -> model.addRow(new Object[]{"", "", ""}));
        frame.add(addButton, "South");

        // Настраиваем видимость фрейма
        frame.setVisible(true);
    }
}