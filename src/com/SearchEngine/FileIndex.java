package com.SearchEngine;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.table.DefaultTableModel;

public class FileIndex {
	
	final static String INDEX_FILE = "tmp/fileindex.txt";
	
	public static void AddToIndex (File newFile) throws IOException {
		if ( FileIndex.IndexFileExists() ) {
			FileWriter fstream = new FileWriter(INDEX_FILE);
			BufferedWriter out = new BufferedWriter(fstream);		
			out.append(newFile.getAbsolutePath() + " indexed");			
			out.close();
			fstream.close();
		} else {
			FileIndex.CreateIndexFile();
			// Recursively call this method to add the new file to the index
			FileIndex.AddToIndex(newFile);
		}
		
		FileIndex.PopulateTable();
	}
	
	public static void PopulateTable() {
		
		try {
			
			if (IndexFileExists () ) {
				BufferedReader reader =  new BufferedReader( new FileReader(INDEX_FILE) );
				String[] currentLine;
				while (( currentLine = reader.readLine().split(" ") ) != null) {
					( (DefaultTableModel) AddRemoveFileGUI.table.getModel() ).addRow(currentLine);
				}
			} else {
				CreateIndexFile();
			}
			
		} catch (FileNotFoundException e) {
			// Print stack trace for now
			// Handle gracefully later
			// This is for creating the new buffered reader
			e.printStackTrace();
		} catch (IOException e) {
			// Print stack trace for now
			// Handle gracefully later
			// This is for reading a line
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
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
	
	private static boolean IndexFileExists() {
		// Check to see if our index file exists
		File f = new File(INDEX_FILE);
		
		if (f.exists()) {
			return true;
		}
		
		// Assume failure for default
		return false;
	}
}
