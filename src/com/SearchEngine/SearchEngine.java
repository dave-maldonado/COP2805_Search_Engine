/**
 * Search Engine GUI
 * @author Andrew Medeiros, Dave Maldonado 2013
 */

package com.SearchEngine;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.FileNotFoundException;
import java.util.Vector;

public class SearchEngine {

	private JFrame frmSearchEngine;
	public static JTextField textField;
	public static JTable table;
	
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
		frmSearchEngine.setBounds(100, 100, 550, 500);
		frmSearchEngine.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0};
		gridBagLayout.columnWeights = new double[]{1.0};
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
		final JComboBox comboBox = new JComboBox(comboOpts);
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 0;
		gbc_comboBox.gridy = 2;
		frmSearchEngine.getContentPane().add(comboBox, gbc_comboBox);
		
		// Add a new button to the GBC layout
		JButton btnSearch = new JButton("Search");
		GridBagConstraints gbc_btnSearch = new GridBagConstraints();
		gbc_btnSearch.insets = new Insets(0, 0, 5, 0);
		gbc_btnSearch.gridx = 0;
		gbc_btnSearch.gridy = 3;
		frmSearchEngine.getContentPane().add(btnSearch, gbc_btnSearch);
		
		// New Search results label
		JLabel lblSearchResults = new JLabel("Search Results");
		GridBagConstraints gbc_lblSearchResults = new GridBagConstraints();
		gbc_lblSearchResults.insets = new Insets(0, 0, 5, 0);
		gbc_lblSearchResults.gridx = 0;
		gbc_lblSearchResults.gridy = 4;
		frmSearchEngine.getContentPane().add(lblSearchResults, gbc_lblSearchResults);
		
		
		// Column names for our JTable
		String[] headerNames = { "Filename", "Position" };
				
		// Create our new table and table headers
		@SuppressWarnings("serial")
		final
		DefaultTableModel model = new DefaultTableModel(null, headerNames){
			// Override to make the cells not editable
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
				
		// New JTable
		table = new JTable(model);
		JTableHeader header = table.getTableHeader();
		header.setBackground(Color.yellow);
		
		// New JScrollPane to hold our JTable
		JScrollPane scrollPane = new JScrollPane(table);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 5;
		frmSearchEngine.getContentPane().add(scrollPane, gbc_scrollPane);

		// Search button action performed method
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// clear JTable before new search
				model.setRowCount(0);
				
				// clean input of unnecessary whitespace
				String input = textField.getText().replaceAll("\\s+", " ").trim();
				
				// multiple words split by single whitespace
				String[] keys = input.split(" ");
				
				// used for ALL searches (not quite working- will prob remove)
				boolean hasKeys = true; 
				
				// check if file index is empty or no search terms entered
				if (FileIndex.fileIndexed.isEmpty()) {
					AddRemoveFileGUI.AlertWindow("No files in index.");
				} else if (input.length() == 0 ) {
					AddRemoveFileGUI.AlertWindow("No search terms entered.");
				} else {
					
					/*
					 * selection logic for search, may end up moving this stuff,
					 * big and unwieldy :(
					 */
					
					// type of search selected in comboBox (ALL, ANY, EXACT)
					int sel = comboBox.getSelectedIndex();
					
					// ALL search (not working right, checks to see if words
					// are contained in group of documents instead of each doc)
					if (sel == 0) { 
						InvertedIndex.getInstance().AddToTable("not working, try ANY search");		
					} 
					
					// ANY search
					else if (sel == 1) {
						for (String k : keys) {
								InvertedIndex.getInstance().AddToTable(k);
								InvertedIndex.getInstance().searchIndex(k);
						}
					} 
					
					// EXACT Phrase search (need to figure this out too)
					else { 
						InvertedIndex.getInstance().AddToTable("not working, try ANY search");		
					}
				}				
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
