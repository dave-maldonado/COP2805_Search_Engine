package com.SearchEngine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * Builds an Inverted Index in memory using supplied file paths
 * @author dave maldonado 2013
 */
public class InvertedIndex {
				
	/*
	 * Each file path supplied to this class must be a String with
	 * the file path at the beginning and marked at the end with 
	 * the DELIMITER. Any information after the DELIMITER will not be 
	 * used.
	 */
	
	final static String DELIMITER = ",";
	
	// data structure for inverted index:
	// key is parsed word from document, value is an ArrayList of Pair Objects 
	private HashMap<String,ArrayList> invertInd = new HashMap<String,ArrayList>();

	/**
	 * Constructor that builds Inverted Index from supplied list of file paths
	 * @param files
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
	 * Method to clear then re-load Inverted Index from supplied list of file paths
	 * @param files
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
