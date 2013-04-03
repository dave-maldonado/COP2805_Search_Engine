package com.SearchEngine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import javax.swing.table.DefaultTableModel;

/**
 * Builds an Inverted Index in memory 
 * @author Dave Maldonado, Andrew Medeiros 2013
 */
public final class InvertedIndex {
				
	/*
	 * Each file path supplied to this class must be a String with
	 * the file path at the beginning and marked at the end with 
	 * the DELIMITER. Any information after the DELIMITER will not be 
	 * used.
	 */
	
	final static String DELIMITER = ","; 
	private static final InvertedIndex uniqueInstance = new InvertedIndex();
	private InvertedIndex() {}

	// data structure for inverted index:
	// key is parsed word from document, value is an ArrayList of Pair Objects 
	private HashMap<String,ArrayList> invertInd = new HashMap<String,ArrayList>();

	
	/**
	 * Method to return a reference to Singleton Inverted Index
	 * @return uniqueInstance
	 */
	public static InvertedIndex getInstance() {
		return uniqueInstance;
	}
	
	/**
	 * Method for indexing single text file from supplied file path
	 * @param file
	 * @throws FileNotFoundException
	 */
	public void processFile(String file) throws FileNotFoundException {
		
		int wordPos = 1;
		
		String[] tokens = file.split(DELIMITER);
		String filePath = tokens[0];
		Scanner in = new Scanner(new File(filePath));
		while (in.hasNext()) {
			String word = in.next();
			if (!invertInd.containsKey(word)) {
				ArrayList values = new ArrayList<Pair>();
				Pair<String,Integer> pair = new Pair<String,Integer>(filePath, wordPos);
				values.add(pair);
				invertInd.put(word, values);
			} else {
				ArrayList values = invertInd.get(word);
				Pair<String,Integer> pair = new Pair<String,Integer>(filePath, wordPos);
				values.add(pair);
				invertInd.put(word, values);
			}
			wordPos++;
		}
	}
	
	/**
	 * Method to load Inverted Index from supplied list of file paths
	 * @param files
	 */
	public void loadIndex(List<String> files) throws FileNotFoundException {
		
		if (!invertInd.isEmpty())
			invertInd.clear();
		
		if (!files.isEmpty()) {
			for (String s : files) {					
				processFile(s);

			}
		}
	}
	
	/**
	 * Method to test if key is contained in index
	 * @param key
	 */
	public boolean containsKey(String key) {
		return invertInd.containsKey(key);
	}
	
	/**
	 *  Method to print Inverted Index to console
	 */
	public void printIndex() {
		Iterator iter = invertInd.keySet().iterator();
		while (iter.hasNext()) {
			String key = iter.next().toString();
			String value = invertInd.get(key).toString();
			System.out.println(key + " " + value);
		}
	}
		
	/**
	 * Method to search index and call AddToTable with result
	 * @param key
	 */
	public void searchIndex(String key) {

		if (invertInd.containsKey(key)) {
			if (FileIndex.fileIndexed.isEmpty() == false && SearchEngine.textField.getText().isEmpty() == false) {
				
				// ArrayList of Filename,Position pairs mapped to search term
				List<Pair> values = invertInd.get(key);
				
				// call AddToTable with each value found with key
				for (Pair p : values) {
					String val = p.toString();
					AddToTable(val.substring(1, val.length()-1));
				}
			} else {
				AddRemoveFileGUI.AlertWindow("No files currently indexed or missing search term");
			}
			
		} else {
			AddToTable("\""+key+"\""+" not found."); 
			
		}

	}
	
	/**
	 * Method to add information to our GUI JTable
	 * @param data
	 */
	public void AddToTable(String data) {
		// Add the file info to the JTable
		( (DefaultTableModel) SearchEngine.table.getModel() ).addRow(data.split(DELIMITER));
	}
}
