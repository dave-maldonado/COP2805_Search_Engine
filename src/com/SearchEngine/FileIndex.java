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
 * 
 * Creates a file Index with the status of the file and last modified
 * 
 * @author Andrew Medeiros 2013
 * 
 */

import javax.swing.table.DefaultTableModel;

public class FileIndex {
	
	final static String INDEX_FILE = "tmp/fileindex.txt";
	final static String DELIMITER = ",";
	public static List<String> fileIndexed = new ArrayList<String>();
	
	public static void AddToIndex (File newFile) throws IOException {
		if ( FileIndex.FileExists(INDEX_FILE) ) {
			
			if (FileIndexed(newFile.getAbsolutePath()) == false) {
				String data = newFile.getAbsolutePath() + DELIMITER + "indexed" + DELIMITER + new Date(newFile.lastModified());
				BufferedWriter writer = new BufferedWriter( new FileWriter(INDEX_FILE, true) );
				writer.append(data);			
				writer.newLine();
				writer.close();
				
				AddToTable( data );
			} else {
				AddRemoveFileGUI.AlertWindow("File " + newFile.getName() + " already added.");
			}
			
		} else {
			FileIndex.CreateIndexFile();
			// Recursively call this method to add the new file to the index
			FileIndex.AddToIndex(newFile);
		}
	}
	
	public static void AddToTable(String data) {
		
		// Only add the file info to our list if it has not been added
		if ( FileIndexed(data) == false) {
			fileIndexed.add(data);
		}
		
		// Add the file info to the jtable
		( (DefaultTableModel) AddRemoveFileGUI.table.getModel() ).addRow(data.split(DELIMITER));
	}
	
	public static void SetFileIndexed() {
		try {
			if ( FileExists(INDEX_FILE) ) {
				BufferedReader reader =  new BufferedReader( new FileReader(INDEX_FILE) );
				String currentLine;
				while (( currentLine = reader.readLine() ) != null) {
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
	
	public static void PopulateTable() {
		if ( fileIndexed.isEmpty() == false ) {
			Iterator<String> iterate = fileIndexed.iterator();
			while ( iterate.hasNext() ) {
				AddToTable(iterate.next());
			}
		}	
	}
	
	private static void CreateIndexFile() {
		final File CREATE_INDEX = new File(INDEX_FILE);
		try {
			CREATE_INDEX.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// Method to check if a file exists or not
	private static boolean FileExists(String file) {
		// Check to see if the file exists
		File f = new File(file);
		
		if (f.exists()) {
			return true;
		}
		
		// Assume failure for default
		return false;
	}
	
	// Method to check for file being added to
	// the fileIndexed list
	private static boolean FileIndexed(String file) {
		
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
}
