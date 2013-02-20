package com.SearchEngine;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTable;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;

public class AddRemoveFileGUI {

	private JFrame frmAddOrRemove;
	private JTable table;
	private JSplitPane splitPane;
	private JButton btnAddFile;
	private JButton btnRemoveFile;

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
		frmAddOrRemove = new JFrame();
		frmAddOrRemove.setTitle("Add or remove files");
		frmAddOrRemove.setBounds(100, 100, 450, 300);
		frmAddOrRemove.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		frmAddOrRemove.getContentPane().add(table, BorderLayout.CENTER);
		
		JLabel lblAddOrRemove = new JLabel("Add Or Remove Files From The Index");
		frmAddOrRemove.getContentPane().add(lblAddOrRemove, BorderLayout.NORTH);
		
		splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.5);
		frmAddOrRemove.getContentPane().add(splitPane, BorderLayout.SOUTH);
		
		btnAddFile = new JButton("Add File");
		splitPane.setLeftComponent(btnAddFile);
		
		btnRemoveFile = new JButton("Remove File");
		splitPane.setRightComponent(btnRemoveFile);
	}

}
