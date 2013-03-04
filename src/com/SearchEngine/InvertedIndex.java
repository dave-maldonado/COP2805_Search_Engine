package com.SearchEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Builds an Inverted Index with supplied String list of files with entries
 * in following format: { file path , status, last accessed }, see FileIndex class
 * @author dave maldonado 2013
 */
public class InvertedIndex {
		
	private int docID;
	final static String DELIMITER = ",";
	
	// list of files to be parsed
	private List<String> files = new ArrayList<String>();
	
	// data structure for inverted index:
	// keys are parsed words from documents, values are an ArrayList of Pair Objects 
	private HashMap<String,ArrayList> invertInd = new HashMap<String,ArrayList>();

	// private inner class for Pair Objects, pairs will consist of {docID, position of word in doc}
	private class Pair<L,R> {
		
		private final L left;
		private final R right;
		
		public Pair(L left, R right) {
			this.left = left;
			this.right = right;
		}
		
		public L getLeft() { 
			return left; 
		}
		
		public R getRight() { 
			return right; 
		}
		
		@Override
		public int hashCode() {
			return left.hashCode() ^ right.hashCode(); 
		}
		
		@Override
		public boolean equals(Object o) {
			if (o == null) return false;
			if (!(o instanceof Pair)) return false;
			Pair pairo = (Pair) o;
			return this.left.equals(pairo.getLeft()) &&
				   this.right.equals(pairo.getRight());
		}
	}
	
	/**
	 * Constructor that reads in String list of files and size of list then parses files
	 * and builds inverted index
	 * @param file 
	 */
	public InvertedIndex(List<String> input) {
		
		reloadIndex(input);
		
		//for testing
		System.out.println("contents of passed list:   " + input);
		System.out.println("contents of processed list:" + files);
	}
	
	// creates String ArrayList w/ format {docID (number), file path}
	public void reloadIndex(List<String> input) {
		
		files.clear();
		invertInd.clear();
		docID = 1;
		for (String s : input) {
			String[] tokens = s.split(DELIMITER);
			files.add(docID + DELIMITER + tokens[0]);
			docID++;
		}	
	}
}
