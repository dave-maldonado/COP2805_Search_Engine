package com.SearchEngine;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Creates a file Index with the status of the file and last modified.
 * This is stored in a persistent file and loaded into a list on start up in the following format
 * { file path, status, last modified }
 * 
 * @author Andrew Medeiros 2013
 */

import javax.swing.table.DefaultTableModel;


/**
 * Builds the file index
 * @author Andrew Medeiros
 */
public class FileIndex {
	
	final static String INDEX_FILE = "tmp/fileindex.txt";
	final static String DELIMITER = ",";
	private static String[] statuses = { "Indexed", "Not Found" }; 
	public static List<String> fileIndexed = new ArrayList<String>();
	
	/**
	 * Takes a new file object and adds it to our fileIndexed
	 * @param newFile
	 */
	public static void AddToIndex (File newFile) {
		if (FileIndexed(newFile.getAbsolutePath()) == false) {
			String data = newFile.getAbsolutePath() + DELIMITER + statuses[0] + DELIMITER + new Date(newFile.lastModified());
			fileIndexed.add(data);
			FileIndex.AddToTable(data);
			// Add File to Inverted Index
			try {
				InvertedIndex.getInstance().processFile(newFile.getAbsolutePath());
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			AddRemoveFileGUI.AlertWindow("File " + newFile.getName() + " already added.");
		}
	}
	
	
	/**
	 * Removes a file from the index
	 * @param removeFile
	 */
	public static void RemoveFromIndex (String removeFile) {
		// If the file index is empty skip
		if ( fileIndexed.isEmpty() == false ) {
			Iterator<String> iterate = fileIndexed.iterator();
			int index = 0;
			while ( iterate.hasNext() ) {
				String next = iterate.next();
				if ( next.contains(removeFile) ) {
					fileIndexed.remove(index);
					break;
				}
				index++;
			}
			// Remove File from Inverted Index
			try {
				InvertedIndex.getInstance().loadIndex(fileIndexed);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Method to rewrite the persistent storage file on close
	 * This is how we keep status control over the file
	 */
	public static void WriteFileIndex() {
		
		if ( FileIndex.FileExists(INDEX_FILE) ) {
			try {
				BufferedWriter writer = new BufferedWriter( new FileWriter(INDEX_FILE) );
				for (String currentLine: fileIndexed) {
					writer.write(currentLine);
					writer.newLine();
				}
				
				writer.flush();
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		} else {
			// Create the index file and call this method again to write to persistent storage
			FileIndex.CreateIndexFile();
			FileIndex.WriteFileIndex();
		}
	}
	
	/**
	 * Add the file information to our GUI JTable
	 * @param data
	 */
	public static void AddToTable(String data) {
		// Add the file info to the JTable
		( (DefaultTableModel) AddRemoveFileGUI.table.getModel() ).addRow(data.split(DELIMITER));
	}
	
	/**
	 * Method to remove rows from the JTable
	 */
	public static void RemoveFromTable() {
		
		int selectedRow = AddRemoveFileGUI.table.getSelectedRow();
		
		if (selectedRow >= 0 && selectedRow <= AddRemoveFileGUI.table.getRowCount()) {
			
			// Remove the row from the indexedFile list
			String fileName = (String) AddRemoveFileGUI.table.getModel().getValueAt(selectedRow, 0);
			FileIndex.RemoveFromIndex(fileName);
			
			// Remove from the table
			( (DefaultTableModel) AddRemoveFileGUI.table.getModel() ).removeRow(selectedRow);
			( (DefaultTableModel) AddRemoveFileGUI.table.getModel() ).fireTableDataChanged();
		}
		
	}
	
	/**
	 * Set the fileIndexed from our index file 
	 */
	public static void SetFileIndexed() {
		try {
			if ( FileExists(INDEX_FILE) ) {
				BufferedReader reader =  new BufferedReader( new FileReader(INDEX_FILE) );
				String currentLine;
				while (( currentLine = reader.readLine() ) != null) {
					
					// Update the files status if it has changed
					currentLine = FileIndex.UpdateFileStatus(currentLine);
					
					// Only add the file info to our list if it has not been added
					if ( FileIndexed(currentLine) == false) {
						fileIndexed.add(currentLine);
					}
				}
				reader.close();
			} else {
				CreateIndexFile();
			}
			
		} catch (FileNotFoundException e) {
			// This is for creating the new buffered reader
			// This should never get called as we check for the file to exist first
			AddRemoveFileGUI.AlertWindow("File Not Found\n" + e.getMessage());
		} catch (IOException e) {
			// This is for reading a line
			AddRemoveFileGUI.AlertWindow("Cannot read from file\n" + e.getMessage());
		}
	}
	
	/**
	 * Populate our JTable on GUI start
	 */
	public static void PopulateTable() {
		if ( fileIndexed.isEmpty() == false ) {
			Iterator<String> iterate = fileIndexed.iterator();
			while ( iterate.hasNext() ) {
				String next = iterate.next();
				// Update the files status if it has changed
				next = FileIndex.UpdateFileStatus(next);
				AddToTable(next);
			}
		}	
	}
	
	/**
	 * Create the index file if it has not been created
	 */
	private static void CreateIndexFile() {
		final File CREATE_INDEX = new File(INDEX_FILE);
		try {
			CREATE_INDEX.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Helper method to check if a file exists or not
	 * Takes a string as a parameter and returns a boolean
	 * @param file
	 * @return
	 */
	private static boolean FileExists(String file) {
		// Check to see if the file exists
		File f = new File(file);
		
		if (f.exists()) {
			return true;
		}
		
		// Assume failure for default
		return false;
	}
	
	
	/**
	 * Helper method to check if the file is already added to the fileIndexed list
	 * Takes a string as a parameter and returns a boolean
	 * @param file
	 * @return
	 */
	// Method to check for file being added to the fileIndexed list
	private static boolean FileIndexed(String file) {
		
		// If the file index is empty skip the check and return false
		if ( fileIndexed.isEmpty() == false ) {
			Iterator<String> iterate = fileIndexed.iterator();
			while ( iterate.hasNext() ) {
				String next = iterate.next();
				if ( next.contains(file) ) {
					return true;
				}
			}
		}
		
		// Assume file not in the index default
		return false;
	}
	
	
	/**
	 * Update the status of the file if it has changed
	 * The status of the file is the second part of the string on each line
	 * Example: Filename,Status,Last Modified
	 * Update the file status and return the string
	 * @return
	 */
	private static String UpdateFileStatus(String fileData) {
		String[] data = fileData.split(DELIMITER);
		
		if ( FileExists(data[0]) == false ) {
			data[1] = statuses[1];
			fileData = data[0] + DELIMITER + data[1] + DELIMITER + data[2];
		} else if ( FileExists(data[0]) && data[1].equals(statuses[1]) ) {
			data[1] = statuses[0];
			fileData = data[0] + DELIMITER + data[1] + DELIMITER + data[2];
		}
		
		return fileData;
	}
}
