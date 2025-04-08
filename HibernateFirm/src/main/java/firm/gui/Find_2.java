package firm.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class Find_2 extends JPanel{
	JButton find, back_table;
	JComboBox<String> info;
	JTextField search;
	JScrollPane scrollfind;
	JPopupMenu popupMenu;
	JList suggestionList;
	
	public Find_2(JFrame frame, String[] columnNames, String[] fields, JTable positionTable, JScrollPane scroll, int flag) {
	    setLayout(new BorderLayout());

	    info = new JComboBox<>(fields);
	    search = new JTextField(15);
	    find = new JButton("Find");
	    back_table = new JButton("Back");
	    
	    TableRowSorter<TableModel> rowSorter
        = new TableRowSorter<>(positionTable.getModel());
	

	        positionTable.setRowSorter(rowSorter);
	        
	        JPanel panel = new JPanel();
	        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS)); // Используем BoxLayout для панели
	        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Отступы вокруг панели

	        panel.add(new JLabel("Specify a word to match:"));
	        panel.add(Box.createRigidArea(new Dimension(5, 0))); // Отступ между меткой и текстовым полем
	        panel.add(search);
	        panel.add(Box.createRigidArea(new Dimension(5, 0))); // Отступ между текстовым полем и комбобоксом
	        panel.add(info);

	        
	        add(panel, BorderLayout.NORTH);
	        add(new JScrollPane(positionTable), BorderLayout.CENTER);
	       
	        
	        search.getDocument().addDocumentListener(new DocumentListener() {
	            public void insertUpdate(DocumentEvent e) {
	                filterTable(rowSorter, positionTable);
	            }

	            public void removeUpdate(DocumentEvent e) {
	                filterTable(rowSorter, positionTable);
	            }

	            public void changedUpdate(DocumentEvent e) {
	                filterTable(rowSorter, positionTable);
	            }
	        });
	    }

	    private void filterTable(TableRowSorter<TableModel> rowSorter, JTable positionTable) {
	        String text = search.getText();
	        String selectedColumn = (String) info.getSelectedItem(); // Получаем выбранный столбец
	        int columnIndex = getColumnIndex(selectedColumn, positionTable); // Получаем индекс выбранного столбца

	        if (text.trim().length() == 0) {
	            rowSorter.setRowFilter(null);
	        } else {
	            // Фильтруем по выбранному столбцу
	            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, columnIndex));
	        }
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
	        
	        /*
	        search.getDocument().addDocumentListener(new DocumentListener(){

	            public void insertUpdate(DocumentEvent e) {
	                String text = search.getText();

	                if (text.trim().length() == 0) {
	                    rowSorter.setRowFilter(null);
	                } else {
	                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
	                }
	            }

	            public void removeUpdate(DocumentEvent e) {
	                String text = search.getText();

	                if (text.trim().length() == 0) {
	                    rowSorter.setRowFilter(null);
	                } else {
	                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
	                }
	            }

	            public void changedUpdate(DocumentEvent e) {
	                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	            }

	        });
	    }
	    }
	    */
	    
	    

	    
	    
	    
	    