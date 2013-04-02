/*
 * Search Engine GUI
 * 
 * Authors:
 * Andrew Medeiros
 * Dave Maldonado
 * Kris Zawalski
 * Shawn Smith
 * 
 */

package com.SearchEngine;

import java.awt.EventQueue;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.FileNotFoundException;

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
		
		// Shut down hook run any methods calls here before shutdown
		Runtime.getRuntime().addShutdownHook(new Thread() {
		    public void run() {
		        FileIndex.WriteFileIndex();
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
		
		frmSearchEngine.setTitle("Search Engine");
		frmSearchEngine.setBounds(100, 100, 450, 300);
		frmSearchEngine.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0};
		gridBagLayout.columnWeights = new double[]{0.0};
		frmSearchEngine.getContentPane().setLayout(gridBagLayout);
		
		// Create a new panel and add a new JLabel
		JPanel panel = new JPanel();
		panel.add(new JLabel("ENTER SEARCH TERMS BELOW"));
		
		// Create a new GBC Panel and add it to the content pane
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		frmSearchEngine.getContentPane().add(panel, gbc_panel);
		
		// Create a new text field for searching
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 0;
		gbc_textField.gridy = 1;
		frmSearchEngine.getContentPane().add(textField, gbc_textField);
		textField.setColumns(10);
		
		
		// Combo box options
		String[] comboOpts = { "ALL search terms", "ANY search terms", "EXACT phrase" };	
		
		// Create and add the combo box
		JComboBox comboBox = new JComboBox(comboOpts);
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 0;
		gbc_comboBox.gridy = 2;
		frmSearchEngine.getContentPane().add(comboBox, gbc_comboBox);
		
		// Add a new button to the GBC layout
		JButton btnSearch = new JButton("Search");
		GridBagConstraints gbc_btnSearch = new GridBagConstraints();
		gbc_btnSearch.gridx = 0;
		gbc_btnSearch.gridy = 3;
		frmSearchEngine.getContentPane().add(btnSearch, gbc_btnSearch);
		frmSearchEngine.setSize(400, 400);
		frmSearchEngine.setLocationRelativeTo(null);
		
		// Search button action performed method
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Stubbed out a message for now
				textField.setText("Show Inverted Index!");
				InvertedIndex.getInstance().printIndex();
			}
		});
		
		// Add a JMenuBar
		JMenuBar menuBar = new JMenuBar();
		frmSearchEngine.setJMenuBar(menuBar);
		
		// Add a file menu option to the JMenuBar
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		// Add a menu option for adding and removing files
		JMenuItem mntmAddremoveFile = new JMenuItem("Add/Remove File");
		mnFile.add(mntmAddremoveFile);
		mntmAddremoveFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Call the AddRemoveFileGUI main method to show
				// the new application window for adding and removing files
				AddRemoveFileGUI.main(null);
			}
		});
		
		// Add a menu option for the about dialog
		JMenuItem mntmAbout = new JMenuItem("About");
		menuBar.add(mntmAbout);
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Show the about dialog when the menu option is clicked
				aboutDialog();
			}
		});
		
		// Set the file index list
		FileIndex.SetFileIndexed();
		
		// Load initial Inverted Index
		try {
			InvertedIndex.getInstance().loadIndex(FileIndex.fileIndexed);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	private static void aboutDialog() {
		// Icon for the about dialog
		ImageIcon icon = new ImageIcon("images/SearchGuy.jpeg");
		
		String message = "Search Engine 1.0\nSolution for COP-2805 Project 3\nAuthors:\nAndrew Medeiros\nDave Maldonado\nKris Zawalski\nShawn Smith";
		
		// Show the dialog with our icon and message
		JOptionPane.showMessageDialog(null,
			    message,
			    "About Search Engine",
			    JOptionPane.PLAIN_MESSAGE,
			    icon);
	}
}
