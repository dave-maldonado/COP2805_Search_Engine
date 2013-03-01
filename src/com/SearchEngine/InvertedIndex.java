package com.SearchEngine;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Builds an Inverted Index with supplied String list of files with entries
 * in following format: { filepath , status, last accessed }, see FileIndex class
 * @author dave maldonado 2013
 */
public class InvertedIndex {
	
	
	private int size;
	final static String DELIMITER = ",";
	
	// list of files to be parsed
	private List<String> filePaths = new ArrayList<String>();
	
	// data structure for inverted index 
	// outer HashMap - keys are parsed words from docs, values are inner HashMap
	// inner HashMap - keys are document names, values are position in document
	private HashMap<String,HashMap<String,Integer>> foo = new HashMap<String,HashMap<String,Integer>>();
	
	
	/**
	 * Constructor that calls telescoped constructor with additional size argument
	 * @param file
	 */
	public InvertedIndex(List<String> files) {
		new InvertedIndex (files, files.size());
	}
	
	/**
	 * Constructor that reads in String list of files and size of list then parses files
	 * and builds inverted index
	 * @param file, size
	 */
	private InvertedIndex(List<String> files, int size) {
		
		// copies file path info from input list to filePaths,
		// NOTE TO SELF: make sure this isn't a memory hog (looks kinda bad with all the created arrays)
		for (String s : files) {
			String[] tokens = s.split(DELIMITER);
			filePaths.add(tokens[0]);
		}
		
		//for testing
		System.out.println("size of passed array:" + size);
		System.out.println("contents of passed array   :" + files);
		System.out.println("contents of processed array:" + filePaths);
	}
}
