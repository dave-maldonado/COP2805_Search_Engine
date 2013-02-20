package com.SearchEngine;

import java.awt.EventQueue;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class SearchEngine {

	private JFrame frmSearchEngine;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SearchEngine window = new SearchEngine();
					window.frmSearchEngine.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SearchEngine() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSearchEngine = new JFrame();
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0};
		gridBagLayout.columnWeights = new double[]{0.0};
		frmSearchEngine.getContentPane().setLayout(gridBagLayout);
		JPanel panel = new JPanel();
		panel.add(new JLabel("ENTER SEARCH TERMS BELOW"));
		//panel.setBorder(new LineBorder(Color.BLACK));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		frmSearchEngine.getContentPane().add(panel, gbc_panel);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 0;
		gbc_textField.gridy = 1;
		frmSearchEngine.getContentPane().add(textField, gbc_textField);
		textField.setColumns(10);
		
		String[] comboOpts = { "ALL search terms", "ANY search terms", "EXACT phrase" };
		
		JComboBox comboBox = new JComboBox(comboOpts);
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 0;
		gbc_comboBox.gridy = 2;
		frmSearchEngine.getContentPane().add(comboBox, gbc_comboBox);
		frmSearchEngine.setSize(400, 400);
		frmSearchEngine.setLocationRelativeTo(null);
		
		frmSearchEngine.setTitle("Search Engine");
		frmSearchEngine.setBounds(100, 100, 450, 300);
		frmSearchEngine.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frmSearchEngine.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmAddremoveFile = new JMenuItem("Add/Remove File");
		mnFile.add(mntmAddremoveFile);
		mntmAddremoveFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddRemoveFileGUI.main(null);
			}
		});
		
		JMenuItem mntmAbout = new JMenuItem("About");
		menuBar.add(mntmAbout);
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aboutDialog();
			}
		});
	}
	
	private static void aboutDialog() {
		
		ImageIcon icon = new ImageIcon("images/SearchGuy.jpeg");
				
		String message = "Search Engine 1.0\nSolution for COP-280 Project 3-5\nAuthors:\nAndrew Medeiros\nDave Maldonado\nKris Zawalski\nShawn Smith";
		
		JOptionPane.showMessageDialog(null,
			    message,
			    "About Search Engine",
			    JOptionPane.PLAIN_MESSAGE,
			    icon);
	}
}
