package com.SearchEngine;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTable;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;

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
		frmAddOrRemove = new JFrame();
		frmAddOrRemove.setTitle("Add or remove files");
		frmAddOrRemove.setBounds(100, 100, 450, 300);
		frmAddOrRemove.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		scrollPane = new JScrollPane();
		frmAddOrRemove.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		String[] columnNames = { "Filename", "Status" };
		String[][] data = {};
		
		table = new JTable(data, columnNames);
		
		scrollPane.add(table);
		
		JLabel lblAddOrRemove = new JLabel("Add Or Remove Files From The Index");
		frmAddOrRemove.getContentPane().add(lblAddOrRemove, BorderLayout.NORTH);
		
		bottomSplitPane = new JSplitPane();
		bottomSplitPane.setResizeWeight(0.5);
		frmAddOrRemove.getContentPane().add(bottomSplitPane, BorderLayout.SOUTH);
		
		btnAddFile = new JButton("Add File");
		bottomSplitPane.setLeftComponent(btnAddFile);
		btnAddFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				fc.showOpenDialog(null);
				File selectedFile = fc.getSelectedFile();
			}
		});
		
		btnRemoveFile = new JButton("Remove File");
		bottomSplitPane.setRightComponent(btnRemoveFile);
		
	}

}
