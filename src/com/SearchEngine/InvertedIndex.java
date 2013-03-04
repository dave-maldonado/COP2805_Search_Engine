package com.SearchEngine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * Builds an Inverted Index in memory using supplied list of file paths
 * @author dave maldonado 2013
 */
public class InvertedIndex {
		
	final static String DELIMITER = ",";
		
	// data structure for inverted index:
	// key is parsed word from document, value is an ArrayList of Pair Objects 
	private HashMap<String,ArrayList> invertInd = new HashMap<String,ArrayList>();

	/**
	 * Constructor that builds Inverted Index from supplied list of file paths
	 * supplied list must have file path BEFORE first DELIMITER
	 * @param input
	 * @throws FileNotFoundException 
	 */
	public InvertedIndex(List<String> files) throws FileNotFoundException {
		
		// processes all files in list
		if (!files.isEmpty()) {
			for (String s : files) {
				processFile(s);
			}
		}
		
		//for testing, prints Inverted Index to the console
		Iterator iter = invertInd.keySet().iterator();
		while (iter.hasNext()) {
			String key = iter.next().toString();
			String value = invertInd.get(key).toString();
			System.out.println(key + " " + value);
		}
	}
	
	/**
	 * Method for processing single text file into Inverted Index
	 * String must have file path BEFORE first DELIMITER
	 * @param file
	 * @throws FileNotFoundException
	 */
	public void processFile(String file) throws FileNotFoundException {
		
		int wordPos = 1;
		
		String[] tokens = file.split(DELIMITER);
		String filename = tokens[0];
		Scanner in = new Scanner(new File(filename));
		while (in.hasNext()) {
			String word = in.next();
			if (!invertInd.containsKey(word)) {
				ArrayList values = new ArrayList<Pair>();
				Pair<String,Integer> pair = new Pair<String,Integer>(filename, wordPos);
				values.add(pair);
				invertInd.put(word, values);
			} else {
				ArrayList values = invertInd.get(word);
				Pair<String,Integer> pair = new Pair<String,Integer>(filename, wordPos);
				values.add(pair);
				invertInd.put(word, values);
			}
			wordPos++;
		}
	}
	
	/**
	 * Method to clear then reload Inverted Index from supplied list of file paths
	 * supplied list must have file path BEFORE first DELIMITER
	 * @param input
	 */
	public void reloadIndex(List<String> files) {
		
		invertInd.clear();
		
		if (!files.isEmpty()) {
			for (String s : files) {
				try {
					processFile(s);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
