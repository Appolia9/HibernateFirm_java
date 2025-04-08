package firm.gui;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class ValidatingTextFieldEditor extends AbstractCellEditor implements TableCellEditor {
    private JTextField textField;

    public ValidatingTextFieldEditor() {
        textField = new JTextField();
        textField.setToolTipText("Введите значение");

        textField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                if (textField.getText().isEmpty()) {
                    textField.setForeground(Color.BLACK);
                }
            }

            public void focusLost(FocusEvent evt) {
                if (textField.getText().isEmpty()) {
                    textField.setForeground(Color.GRAY);
                }
            }
        });
    }
    
    @Override
    public Object getCellEditorValue() {
        return textField.getText();
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        textField.setText(value != null ? value.toString() : "");
        return textField;
    }

    private void validateInput() {
        // Логика валидации ввода
        String text = textField.getText();
        if (text.isEmpty()) {
            JOptionPane.showMessageDialog(textField, "Поле не может быть пустым!", "Ошибка валидации", JOptionPane.ERROR_MESSAGE);
            textField.requestFocus();
        }
    }
}