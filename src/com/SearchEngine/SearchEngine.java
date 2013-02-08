package com.SearchEngine;

import java.awt.EventQueue;
import javax.swing.*;
import java.awt.GridLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SearchEngine {

	private JFrame frmSearchEngine;

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
		
		JMenuItem mntmAbout = new JMenuItem("About");
		menuBar.add(mntmAbout);
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aboutDialog();
				// System.out.println("About Me!");
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
	
	
}
