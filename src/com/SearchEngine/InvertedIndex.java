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
				
				// ArrayList of Filename,Position pairs mapped to search term
				List<Pair> values = invertInd.get(key);
				
				// call AddToTable with each value found with key
				for (Pair p : values) {
					String val = p.toString();
					AddToTable(val.substring(1, val.length()-1));
				}
		} else {
			AddToTable("\""+key+"\""+" not found."); 			
		}
	}
	
	/**
	 * Method to search for exact phrase.
	 * @param keys
	 */
	public void searchPhrase(String input) {
		
		String[] keys = input.split(" ");
		List<Pair> vals = new ArrayList<Pair>();
		List<Pair> vals2 = new ArrayList<Pair>();
		boolean containsNext = false;
		System.out.println("containsNext is false"); //diagnostic
		
		vals.addAll(invertInd.get(keys[0]));
	
		// diagnostic console prints
		System.out.println("keys " + keys.toString());
		System.out.println("vals before loops " + vals.toString());
		System.out.println(keys[1]); 
		
		// iterate thru keys
		for (int i = 1; i < keys.length; i++) {
			System.out.println("keys loop start"); //diagnostic
			vals2.addAll(invertInd.get(keys[i]));
			System.out.println("vals2 " + vals2.toString()); //diagnostic
			System.out.println("vals before val loop" + vals.toString()); //diagnostic
			
			// iterate thru Pairs in vals
			for (Pair p1 : vals) {
				System.out.println("val loop start"); //diagnostic
				String doc1 = p1.getLeft().toString();
				String pos1 = p1.getRight().toString();
				System.out.println("looking at val element" + doc1 + " " + pos1); // diagnostic
				
				// iterate thru Pairs in vals2
				for (Pair p2 : vals2) {
					System.out.println("vals2 loop start"); //diagnostic
					String doc2 = p2.getLeft().toString();
					String pos2 = p2.getRight().toString();
					System.out.println("comparing val2 element" + doc2 + " " + pos2); //diagnostic
					
					// compare Pair in vals2 to Pair in val1 and replace if match, also switch containsNext flag to true if match
					if (doc1 == doc2 && (pos1 + 1) == pos2) {
						vals.remove(p1);
						vals.add(p2);
						System.out.println("replacing element in vals"); //diagnostic
						System.out.println("vals " + vals.toString()); //diagnostic
						containsNext = true;
						System.out.println("containsNext is true"); //diagnostic
					}				
					System.out.println("val2 loop end"); //diagnostic
				} 
				
				// continuation of vals loop
				// if containsNext is false removes element from vals
				if (containsNext == false) {
					System.out.println("containsNext tested false"); //diagnostic
					vals.remove(p1);
					System.out.println("removing word from vals"); //diagnostic
					System.out.println("vals " + vals.toString()); //diagnostic
				}
				// set containsNext to false
				containsNext = false;
				System.out.println("containsNext is now false"); //diagnostic
				System.out.println("vals at val loop end" + vals.toString()); //diagnostic
				System.out.println("val1 loop end"); //diagnostic
			} 
			
			// continuation of keys loop
			System.out.println("outside of val1 loop"); //diagnostic
			vals2.clear();
			System.out.println("vals2 cleared"); //diagnostic
			System.out.println("vals2 " + vals2.toString());
			System.out.println("keys loop end"); //diagnostic
		}
		
		// test how many elements in list and print results
		if (vals.size() == 0) { 
			System.out.println("vals " + vals.toString()); //diagnostic
			System.out.println("adding result to Jtable - not found"); //diagnostic
			AddToTable("phrase not found.");
		}
		else {
			System.out.println("vals " + vals.toString()); //diagnostic
			System.out.println("adding result to Jtable - match found"); //diagnostic
			AddToTable(vals);
		}		
	}
	
	/**
	 * Method to return list of Pair values for given key
	 * @param key
	 */
	public List getValues(String key) {
		return invertInd.get(key);
	}
	
	/**
	 * Method to add information to our GUI JTable
	 * @param data
	 */
	public void AddToTable(String data) {
		// Add the file info to the JTable
		( (DefaultTableModel) SearchEngine.table.getModel() ).addRow(data.split(DELIMITER));
	}
	
	/**
	 * Method to add list Pairs to our GUI JTable
	 * @param data
	 */
	public void AddToTable(List<Pair> data) {
		for (Pair p : data) {
			String val = p.toString();
			AddToTable(val.substring(1, val.length()-1));
		}
	}
}
