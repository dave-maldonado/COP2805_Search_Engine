package com.SearchEngine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * Builds an Inverted Index in memory with supplied Strings in following format:
 * { file path, status, last accessed }, see FileIndex class
 * @author dave maldonado 2013
 */
public class InvertedIndex {
		
	final static String DELIMITER = ",";
	
	
	// data structure for inverted index:
	// key is parsed word from document, value is an ArrayList of Pair Objects 
	private HashMap<String,ArrayList> invertInd = new HashMap<String,ArrayList>();

	
	/**
	 * Constructor that reads in String list of files, parses files
	 * and builds inverted index
	 * @param input
	 * @throws FileNotFoundException 
	 */
	public InvertedIndex(List<String> input) throws FileNotFoundException {
		
		if (!input.isEmpty()) {
			for (String s : input) {
				processFile(s);
			}
		}
		
		//for testing
		Iterator iter = invertInd.keySet().iterator();
		
		while (iter.hasNext()) {
			String key = iter.next().toString();
			String value = invertInd.get(key).toString();
			
			System.out.println(key + " " + value);
			
		}
	}
	
	/**
	 * Method for processing single file
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
}
