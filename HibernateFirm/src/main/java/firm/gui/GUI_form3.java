package firm.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import org.apache.log4j.Logger;

import firm.hibernate.Client;
import firm.hibernate.Project;

public class GUI_form3 extends JFrame {
	private JPanel panel, panel_b;
	private JMenuBar menu;
	private DefaultTableModel model;
	private JTable clients_table;
	private JScrollPane scroll;
	private JButton button_back, button_add;
	
	private static final Logger log = Logger.getLogger("GUI_form3.class");
	
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("firm_persistence");
    EntityManager em = emf.createEntityManager();
    ArrayList<Client> clients = (ArrayList<Client>) em.createQuery("SELECT e FROM Client e", Client.class).getResultList();

	
	public GUI_form3(String name) {
	
		super(name);
		setSize(1000,1000);
		setLocation(10,10);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		
		Color customColor = new Color(0xf0f8ff);
		Color customColor_1 = new Color(0xFFC0CB);
		
        ArrayList<String> list = new ArrayList<String>();
        list.add("Ivanov");
        list.add("Inabof");
        list.add("Surikov");
		
        panel = new JPanel();
		JPanel panel_m = new JPanel();
		panel.setLayout(new BorderLayout());
		panel_m.setLayout(new BorderLayout());
		
		
		MyMenu Mmenu = new MyMenu(this);
        panel_m.add(Mmenu.getMenuBar(), BorderLayout.WEST);
		
		String[] columns = {"id","Designation","id_data", "Entity",""};
		model = new DefaultTableModel(columns, 0);
		
		for (Client member : clients) {
			String e;
			if( member.getIs_legal() == 1) e = "Legal";
			else {e = "Physical";}
            model.addRow(new Object[]{
                member.getClid(),
                member.getName(),
                member.getIdent_data(),
                e,
                "Delete"
            });
        }
		
		clients_table = new JTable(model) {
        	public Class<?> getColumnClass(int columnIndex){
        		if(columnIndex == 4) {
        			return JButton.class;
        		}
        		return super.getColumnClass(columnIndex);
        	}
		};
		
		 clients_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	     clients_table.setDefaultEditor(Object.class, null);
	        
	     clients_table.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer_del());
	     clients_table.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor(new JCheckBox(), clients_table, 3));
			
		scroll = new JScrollPane(clients_table);
		scroll.setBackground(customColor);
		clients_table.setBackground(customColor);
		scroll.setSize(500, 200);
		
		String[] find = {"id","Designation","id_data", "Entity"};

		Find_2 searchPanel = new Find_2(GUI_form3.this,columns,find, clients_table, scroll, 0);
		
		
		JButton edit_button = new JButton("Редактировать");
        edit_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = clients_table.getSelectedRow();
                if (selectedRow != -1) {
                	Client p = em.find(Client.class, model.getValueAt(selectedRow, 0));
                    String name = (String) model.getValueAt(selectedRow, 1);
                    String ident_data = String.valueOf(model.getValueAt(selectedRow, 2));
                    String is_legal = (String) model.getValueAt(selectedRow, 3);
                    new EditDialog(GUI_form3.this, model, p, list, selectedRow, name, ident_data,
                    		is_legal);
                } else {
                    JOptionPane.showMessageDialog(GUI_form3.this, "Выберите строку для редактирования.");
                }
            }
        });
		
		button_back = new JButton("Back");
		button_back.setBackground(Color.GRAY);
		
		button_add = new JButton("Add");
		button_add.setBackground(customColor_1);
		
		panel_b = new JPanel();
		panel_b.add(button_add);
		panel_b.add(edit_button);
		
		button_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent el) {
				GUI first = new GUI();
				dispose();
				log.info("Закрытие экранной формы клиентов");
				first.setVisible(true);
				
			}
		});
		
		button_add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				new AddDialog(GUI_form3.this, model,3);
			}
		});
		

		
		add(panel_b, BorderLayout.SOUTH);
		add(panel_m, BorderLayout.WEST);
		
		
		panel.add(searchPanel, BorderLayout.NORTH);
		
        panel.add(scroll, BorderLayout.CENTER);
        add(panel, BorderLayout.CENTER);
		getContentPane().setBackground(customColor);
		
		setVisible(true);
	}
}
