package com.SearchEngine;

import java.awt.EventQueue;
import javax.swing.*;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;
import net.miginfocom.swing.MigLayout;

public class SearchEngine {

	private JFrame frmSearchEngine;
	private JFrame frmAddRemoveWindow;
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
				AddRemoveFilesWindow();
			}
		});
		
		JMenuItem mntmAbout = new JMenuItem("About");
		menuBar.add(mntmAbout);
		frmSearchEngine.getContentPane().setLayout(new MigLayout("", "[grow][][][][][][][][grow]", "[][][][][][]"));
		
		JLabel lblEnterSearchTerms = new JLabel("ENTER SEARCH TERMS:");
		frmSearchEngine.getContentPane().add(lblEnterSearchTerms, "cell 0 1 8 1");
		
		textField = new JTextField();
		frmSearchEngine.getContentPane().add(textField, "cell 0 2 9 2,growx");
		textField.setColumns(10);
		
		JComboBox comboBox = new JComboBox();
		frmSearchEngine.getContentPane().add(comboBox, "cell 0 4,growx");
		
		JButton btnSearch = new JButton("SEARCH");
		frmSearchEngine.getContentPane().add(btnSearch, "cell 3 5");
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aboutDialog();
			}
		});
	}

	private static void addPopup(Component component, final JPopupMenu popup) {
	}
	
	private static void aboutDialog() {
		
		ImageIcon icon = new ImageIcon("images/SearchGuy.jpeg");
				
		String message = "Search Engine 1.0\nSolution for COP-280 Project 3-5\nAuthors:\nAndrew\nDave\nTwo Other People?";
		
		JOptionPane.showMessageDialog(null,
			    message,
			    "About Search Engine",
			    JOptionPane.PLAIN_MESSAGE,
			    icon);
	}
	
	private static void AddRemoveFilesWindow() {
		/*frmAddRemoveWindow = new JFrame();
		frmAddRemoveWindow.setTitle("Add or Remove Files");
		frmAddRemoveWindow.setBounds(100, 100, 450, 300);*/
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SearchEngine window = new SearchEngine();
					window.frmAddRemoveWindow.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
