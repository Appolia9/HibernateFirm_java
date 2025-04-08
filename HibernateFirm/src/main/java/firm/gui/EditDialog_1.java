package firm.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import firm.hibernate.StaffMember;;

public class EditDialog_1 extends JDialog {
    private JTextField nameField;
    private JTextField surnameField;
    private JTextField id_dataField, posField, divField, tField, ageField, weField, salField;
    private int rowIndex;

    private boolean surnameChanged = false;
    private boolean nameChanged = false;
    private boolean idDataChanged = false;
    private boolean posChanged = false;
    private boolean divChanged = false;
    private boolean taskChanged = false;
    private boolean ageChanged = false;
    private boolean weChanged = false;
    private boolean salChanged = false;

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("firm_persistence");
    EntityManager em = emf.createEntityManager();

    public EditDialog_1(JFrame parent, DefaultTableModel model, StaffMember s, ArrayList<String> l_s,
                      int rowIndex, String surname, String name, String id_data,
                      String position, String division, String task, String age,
                      String work_exp, String salary) {
        super(parent, "Редактировать запись", true);
        this.rowIndex = rowIndex;

        setLayout(new GridLayout(10, 2));
        add(new JLabel("Surname:"));
        surnameField = new JTextField(surname);
        surnameField.getDocument().addDocumentListener(new ChangeListener(() -> surnameChanged = true));
        add(surnameField);
        
        add(new JLabel("Name:"));
        nameField = new JTextField(name);
        nameField.getDocument().addDocumentListener(new ChangeListener(() -> nameChanged = true));
        add(nameField);
        
        add(new JLabel("Identification data:"));
        id_dataField = new JTextField(id_data);
        id_dataField.getDocument().addDocumentListener(new ChangeListener(() -> idDataChanged = true));
        add(id_dataField);
        
        add(new JLabel("Position:"));
        posField = new JTextField(position);
        posField.getDocument().addDocumentListener(new ChangeListener(() -> posChanged = true));
        add(posField);
        
        add(new JLabel("Division:"));
        divField = new JTextField(division);
        divField.getDocument().addDocumentListener(new ChangeListener(() -> divChanged = true));
        add(divField);
        
        add(new JLabel("Task:"));
        tField = new JTextField(task);
        tField.getDocument().addDocumentListener(new ChangeListener(() -> taskChanged = true));
        add(tField);
        
        add(new JLabel("Age:"));
        ageField = new JTextField(age);
        ageField.getDocument().addDocumentListener(new ChangeListener(() -> ageChanged = true));
        add(ageField);
        
        add(new JLabel("Work experience:"));
        weField = new JTextField(work_exp);
        weField.getDocument().addDocumentListener(new ChangeListener(() -> weChanged = true));
        add(weField);
        
        add(new JLabel("Salary:"));
        salField = new JTextField(salary);
        salField.getDocument().addDocumentListener(new ChangeListener(() -> salChanged = true));
        add(salField);

        setupAutoComplete(surnameField, l_s);

        JButton saveButton = new JButton("Сохранить");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                em.getTransaction().begin();
                if (surnameChanged) {
                    s.setSurname(surnameField.getText());
                    model.setValueAt(surnameField.getText(), rowIndex, 1);
                }
                if (nameChanged) {
                    s.setName(nameField.getText());
                    model.setValueAt(nameField.getText(), rowIndex, 2);
                }
                if (idDataChanged) {
                    s.setIdent_data(Long.valueOf(id_dataField.getText()));
                    model.setValueAt(id_dataField.getText(), rowIndex, 3);
                }
                if (posChanged) {
                    //s.setPosition(posField.getText());
                    model.setValueAt(posField.getText(), rowIndex, 4);
                }
                if (divChanged) {
                    //s.setDivision(divField.getText());
                    model.setValueAt(divField.getText(), rowIndex, 5);
                }
                if (taskChanged) {
                    //s.setTask(tField.getText());
                    model.setValueAt(tField.getText(), rowIndex, 6);
                }
                if (ageChanged) {
                    s.setAge(Integer.valueOf(ageField.getText()));
                    model.setValueAt(ageField.getText(), rowIndex, 7);
                }
                if (weChanged) {
                    s.setWork_exp(Integer.valueOf(weField.getText()));
                    model.setValueAt(weField.getText(), rowIndex, 8);
                }
                if (salChanged) {
                    s.setSalary(Integer.valueOf(salField.getText()));
                    model.setValueAt(salField.getText(), rowIndex, 9);
                }

                em.getTransaction().commit();
                dispose(); // Закрываем диалог
            }
        });
        
        saveButton.setSize(100, 40);
        add(saveButton);
    }
    
    private void setupAutoComplete(JTextField textField, List<String> suggestions) {
        JList<String> suggestionList = new JList<>();
        suggestionList.setVisibleRowCount(5);
        
        // Создаем всплывающее меню
        JPopupMenu popupMenu = new JPopupMenu();
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String input = textField.getText();
                if (input.isEmpty()) {
                    popupMenu.setVisible(false);
                    return;
                }
                
                // Фильтруем список на основе введенного текста
                List<String> filteredSuggestions = new ArrayList<>();
                for (String suggestion : suggestions) {
                    if (suggestion.toLowerCase().startsWith(input.toLowerCase())) {
                        filteredSuggestions.add(suggestion);
                    }
                }
                
                if (filteredSuggestions.isEmpty()) {
                    popupMenu.setVisible(false);
                } else {
                    suggestionList.setListData(filteredSuggestions.toArray(new String[0]));
                    popupMenu.removeAll();
                    popupMenu.add(new JScrollPane(suggestionList));
                    popupMenu.show(textField, 0, textField.getHeight());
                }
            }
        });

        suggestionList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String selectedValue = suggestionList.getSelectedValue();
                if (selectedValue != null) {
                    textField.setText(selectedValue);
                    popupMenu.setVisible(false); // Закрываем меню после выбора
                }
            }
        });
    }

    // Вспомогательный класс для отслеживания изменений
    private class ChangeListener implements DocumentListener {
        private Runnable onChange;

        public ChangeListener(Runnable onChange) {
            this.onChange = onChange;
        }

        @Override
        public void insertUpdate(DocumentEvent e) { onChange.run(); }
        
        @Override
        public void removeUpdate(DocumentEvent e) { onChange.run(); }
        
        @Override
        public void changedUpdate(DocumentEvent e) { onChange.run(); }
    }
}
                
