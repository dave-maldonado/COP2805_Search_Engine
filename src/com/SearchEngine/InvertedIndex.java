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
	 * Method to search index for word and call AddToTable with result
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
	 * Method to search index for exact phrase and call AddToTable with result
	 * @param keys
	 */
	public void searchPhrase(String[] keys) {
		
		// check if all search terms are in index
		boolean hasTerms = true;
		for (String s : keys) {
			if (!invertInd.containsKey(s)) {
				hasTerms = false;
			}
		}
				
		if (hasTerms) {
			
			// list of values for first search term
			List<Pair> valsA = new ArrayList<Pair>();
			valsA.addAll(invertInd.get(keys[0]));
			
			// lists used for comparisons
			List<Pair> valsB = new ArrayList<Pair>();
			List<Pair> valsC = new ArrayList<Pair>();
			
			// iterate thru each search term
			for (int i = 1; i < keys.length; i++) {
				valsB.clear();
				valsB.addAll(invertInd.get(keys[i]));
				// iterate thru each Pair value mapped to first search term
				for (Pair p : valsA) {
					String docA = p.getLeft().toString();
					int posA = Integer.parseInt(p.getRight().toString());
					// iterate thru each Pair value mapped to second term
					for (Pair q : valsB) {
						String docB = q.getLeft().toString();
						int posB = Integer.parseInt(q.getRight().toString());
						// compare first Pair to second Term
						if (docB.equals(docA) && posB == (posA + 1)) {
							Pair temp = new Pair(q.getLeft(), q.getRight());
							valsC.add(temp);
						}
					}
				}
				valsA.clear();
				valsA.addAll(valsC);
				valsC.clear();
			}
			if (valsA.size() > 0) { 
				for (Pair p : valsA) {
					AddToTable(p.getLeft().toString());
				}
			} else {
				AddToTable("phrase not found.");
			}
		} else {
			AddToTable("phrase not found.");
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
