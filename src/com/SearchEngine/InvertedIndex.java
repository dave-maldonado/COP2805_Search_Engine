package com.SearchEngine;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import com.sun.xml.internal.bind.v2.runtime.reflect.ListIterator;

/**
 * Builds an Inverted Index with supplied String list of files in following format:
 * <filepath , status, last accessed>, see FileIndex class
 * @author dave maldonado 2013
 */
public class InvertedIndex {
	
	private int size;
	final static String DELIMITER = ",";
	private List<String> filePaths = new ArrayList<String>();
	
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
	public InvertedIndex(List<String> files, int size) {
		
		// copies file path info from input list to filePaths,
		// see FileIndex class for format of input
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
