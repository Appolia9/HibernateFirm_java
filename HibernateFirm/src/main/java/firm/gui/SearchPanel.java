package firm.gui;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SearchPanel extends JPanel {
    private JTextField searchField;
    private JComboBox<String> suggestionsBox;
    private JComboBox<String> fieldSelector;
    private JTable table;
    private TableRowSorter<DefaultTableModel> sorter;

    public SearchPanel(JTable table) {
        this.table = table;
        setLayout(new BorderLayout());

        // Инициализация компонентов
        searchField = new JTextField();
        suggestionsBox = new JComboBox<>();
        JButton searchButton = new JButton("Поиск");
        JButton resetButton = new JButton("Сбросить");

        
        // JComboBox для выбора поля поиска
        String[] fields = {"","Surname","Name","id_data","Division", "Position"};
        fieldSelector = new JComboBox<>(fields);

        // Обработчик ввода в поле поиска
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateSuggestions();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateSuggestions();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateSuggestions();
            }
        });

        // Добавляем компоненты на панель
        JPanel searchControls = new JPanel(new BorderLayout());
        searchControls.add(fieldSelector, BorderLayout.WEST);
        searchControls.add(searchField, BorderLayout.CENTER);
        searchControls.add(suggestionsBox, BorderLayout.EAST);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(searchButton);
        buttonPanel.add(resetButton);
        
        add(buttonPanel, BorderLayout.SOUTH);

        add(searchControls, BorderLayout.NORTH);
        

        // Обработчик кнопки поиска
        searchButton.addActionListener(e -> performSearch());
        
        resetButton.addActionListener(e -> resetSearch());
    }

    private void updateSuggestions() {
        String searchText = searchField.getText().toLowerCase();
        List<String> suggestions = new ArrayList<>();

        // Получаем модель таблицы
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int selectedFieldIndex = fieldSelector.getSelectedIndex();

        // Ищем соответствия в выбранной колонке
        for (int i = 0; i < model.getRowCount(); i++) {
            String value = model.getValueAt(i, selectedFieldIndex).toString().toLowerCase();
            if (value.startsWith(searchText) && !suggestions.contains(value)) {
                suggestions.add(value);
            }
        }

        // Обновляем JComboBox с предложениями
        suggestionsBox.removeAllItems();
        for (String suggestion : suggestions) {
            suggestionsBox.addItem(suggestion);
        }

        // Если есть предложения, выбираем первое
        if (!suggestions.isEmpty()) {
            suggestionsBox.setSelectedIndex(0);
        }
    }

    private void performSearch() {
        String selectedSuggestion = (String) suggestionsBox.getSelectedItem();
        if (selectedSuggestion != null) {
            searchField.setText(selectedSuggestion);
            
            // Фильтруем таблицу по выбранному элементу
            String filterText = selectedSuggestion.toLowerCase();
            int selectedFieldIndex = fieldSelector.getSelectedIndex();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
            table.setRowSorter(sorter);

            // Устанавливаем фильтр
            sorter.setRowFilter(RowFilter.regexFilter(filterText, selectedFieldIndex));
        }
        
    }
    
    private void resetSearch() {
        // Сбрасываем текст поиска и фильтрацию
        searchField.setText("");
        suggestionsBox.removeAllItems();
        
        // Убираем фильтр и возвращаем все строки
        sorter.setRowFilter(null);
    }
}