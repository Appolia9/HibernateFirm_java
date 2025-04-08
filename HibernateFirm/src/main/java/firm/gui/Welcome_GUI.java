package firm.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.awt.*;

import org.apache.log4j.Logger;

public class Welcome_GUI extends JFrame {
	
	JPanel mainPanel, buttonPanel;
	JLabel welcomeLabel;
	JButton staff, projects, clients, tasks;

    public Welcome_GUI() {
        setTitle("Welcome Frame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

		final Logger log = Logger.getLogger("Welcome_GUI.class");
        
        mainPanel = new JPanel(new GridLayout(0, 1));

        welcomeLabel = new JLabel("Welcome to PM APP", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Отступы между кнопками
        staff = new JButton("Staff");
        projects = new JButton("Projects");
        clients = new JButton("Clients");
        tasks = new JButton("Tasks");

        // Устанавливаем одинаковый размер для кнопок
        Dimension buttonSize = new Dimension(150, 40);
        staff.setPreferredSize(buttonSize);
        projects.setPreferredSize(buttonSize);
        clients.setPreferredSize(buttonSize);
        tasks.setPreferredSize(buttonSize);
        
        staff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent h) {
				log.info("Открытие экранной формы сотрудников");
				GUI first = new GUI();
				dispose();
			
				log.info("Закрытие главной экранной формы");
				first.setVisible(true);
			}
		});
        
		projects.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent el) {
				log.info("Открытие экранной формы проектов");
				GUI_form2 second = new GUI_form2("Projects");
				dispose();
				log.info("Закрытие главной экранной формы");
				second.setVisible(true);
				
			}
		});
		
		clients.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent el) {
				log.info("Открытие экранной формы клиентов");
				GUI_form3 third = new GUI_form3("Clients");
				dispose();
				log.info("Закрытие главной экранной формы");
				third.setVisible(true);
				
			}
		});
		
		tasks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent el) {
				log.info("Открытие экранной формы задач");
				GUI_form4 forth = new GUI_form4("Tasks");
				dispose();
				log.info("Закрытие главной экранной формы");
				forth.setVisible(true);
				
			}
		});

        buttonPanel.add(staff);
        buttonPanel.add(projects);
        buttonPanel.add(clients);
        buttonPanel.add(tasks);

        mainPanel.add(welcomeLabel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
    }

}