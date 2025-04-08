package firm.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.apache.log4j.Logger;

public class MyMenu {
	private JMenuBar menu;
	private JMenuItem home, projects, clients, staff, tasks;
	
	public MyMenu(JFrame p) {
		menu = new JMenuBar();
		home = new JMenuItem("Home");
		staff = new JMenuItem("Staff Members");
		projects = new JMenuItem("Projects");
		clients = new JMenuItem("Clients");
		tasks = new JMenuItem("Tasks");
		
		
		final Logger log = Logger.getLogger("p.class");
		
		staff.setBackground(Color.GRAY);
		home.setBackground(Color.GRAY);
		projects.setBackground(Color.GRAY);
		clients.setBackground(Color.GRAY);
		tasks.setBackground(Color.GRAY);
		
		menu.add(home);
		menu.add(staff);
		menu.add(projects);
		menu.add(clients);
		menu.add(tasks);
		menu.revalidate();
		menu.setBackground(Color.GRAY);
		
		menu.setLayout(new GridLayout(0,1));
		
		home.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent h) {
				log.info("Закрытие экранной формы");
				log.info("Открытие главной экранной формы");
				Welcome_GUI first = new Welcome_GUI();
				p.dispose();
				
				first.setVisible(true);
			}
		});
		
        staff.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent el) {
                if (!(p instanceof GUI)) { // Проверка на текущую форму
                    log.info("Закрытие экранной формы");
                    log.info("Открытие экранной формы сотрудников");
                    GUI second = new GUI();
                    p.dispose();
                    second.setVisible(true);
                }
            }
        });
		
		projects.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent el) {
				if(!(p instanceof GUI_form2)) {
					log.info("Закрытие экранной формы");
					log.info("Открытие экранной формы проектов");
					GUI_form2 second = new GUI_form2("Projects");
					p.dispose();
					second.setVisible(true);
				}
			}
		});
		
		clients.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent el) {
				if(!(p instanceof GUI_form3)) {
					log.info("Закрытие экранной формы");
					log.info("Открытие экранной формы клиентов");
					GUI_form3 third = new GUI_form3("Clients");
					p.dispose();
					third.setVisible(true);
				}
			}
		});
		
        tasks.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent el) {
                if (!(p instanceof GUI_form4)) { // Проверка на текущую форму
                    log.info("Закрытие экранной формы");
                    log.info("Открытие экранной формы задач");
                    GUI_form4 forth = new GUI_form4("Tasks");
                    p.dispose();
                    forth.setVisible(true);
                }
            }
        });
		
	}
	public JMenuBar getMenuBar() {
		return menu;
	}
}
