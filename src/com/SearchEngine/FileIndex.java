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
		if ( FileIndex.FileExists(INDEX_FILE) ) {
			BufferedWriter writer = new BufferedWriter( new FileWriter(INDEX_FILE, true) );
			writer.append(newFile.getAbsolutePath() + ",indexed");			
			writer.newLine();
			writer.close();
		} else {
			FileIndex.CreateIndexFile();
			// Recursively call this method to add the new file to the index
			FileIndex.AddToIndex(newFile);
		}
		
		FileIndex.PopulateTable();
	}
	
	public static void PopulateTable() {
		
		try {
			
			if ( FileExists(INDEX_FILE) ) {
				BufferedReader reader =  new BufferedReader( new FileReader(INDEX_FILE) );
				String[] currentLine;
				while (( currentLine = reader.readLine().split(",") ) != null) {
					for (int i = 0; i < currentLine.length; i++) {
						System.out.println(currentLine[i]);
					}
					( (DefaultTableModel) AddRemoveFileGUI.table.getModel() ).addRow(currentLine);
				}
				
				reader.close();
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
	
	private static boolean FileExists(String file) {
		// Check to see if the file exists
		File f = new File(file);
		
		if (f.exists()) {
			return true;
		}
		
		// Assume failure for default
		return false;
	}
}
