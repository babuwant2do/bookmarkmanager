package com.wordpress.babuwant2do.bookmarkmanager.tree;

public class BookmarkNodeException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4083831827210887469L;
	public BookmarkNodeException(String message){
		super(message);
	}
	public BookmarkNodeException(String message, Throwable throwable){
		super(message, throwable);
	}
}
