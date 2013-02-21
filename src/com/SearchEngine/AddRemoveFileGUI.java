/*
 * Add Remove File GUI
 * 
 * Authors:
 * Andrew Medeiros
 * Dave Maldonado
 * Kris Zawalski
 * Shawn Smith
 * 
 */

package com.SearchEngine;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTable;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class AddRemoveFileGUI {

	private JFrame frmAddOrRemove;
	private JSplitPane bottomSplitPane;
	private JButton btnAddFile;
	private JButton btnRemoveFile;
	private JScrollPane scrollPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddRemoveFileGUI window = new AddRemoveFileGUI();
					window.frmAddOrRemove.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AddRemoveFileGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		// Create a new JFrame dispose on close
		frmAddOrRemove = new JFrame();
		frmAddOrRemove.setTitle("Add or remove files");
		frmAddOrRemove.setBounds(100, 100, 450, 300);
		frmAddOrRemove.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		
		// Column names for our JTable
		String[] headerNames = { "Filename", "Status" };
		// Stubbed data for our table
		String[][] data = {
							{"test.txt", "indexed"}
						  };
		
		// Create our new table and table headers
		@SuppressWarnings("serial")
		DefaultTableModel model = new DefaultTableModel(data,headerNames){
			// Override to make the cells not editable
			@Override
		    public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		};
		
		table = new JTable(model);
		JTableHeader header = table.getTableHeader();
		header.setBackground(Color.yellow);

		// Create a new scroll pane adding the table 
		scrollPane = new JScrollPane(table);
		frmAddOrRemove.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		
		// Create a new label
		JLabel lblAddOrRemove = new JLabel("Add Or Remove Files From The Index");
		frmAddOrRemove.getContentPane().add(lblAddOrRemove, BorderLayout.NORTH);
		
		// Create a new split pane
		bottomSplitPane = new JSplitPane();
		bottomSplitPane.setResizeWeight(0.5);
		frmAddOrRemove.getContentPane().add(bottomSplitPane, BorderLayout.SOUTH);
		
		// New Jbutton for adding files
		btnAddFile = new JButton("Add File");
		bottomSplitPane.setLeftComponent(btnAddFile);
		btnAddFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// New File chooser for selecting files
				final JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				fc.showOpenDialog(null);
				File selectedFile = fc.getSelectedFile();
			}
		});
		
		// New button to remove files from the index
		btnRemoveFile = new JButton("Remove File");
		bottomSplitPane.setRightComponent(btnRemoveFile);
		
	}

}



