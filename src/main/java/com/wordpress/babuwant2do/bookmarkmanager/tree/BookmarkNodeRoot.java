package com.wordpress.babuwant2do.bookmarkmanager.tree;

import java.util.ArrayList;
import java.util.Collection;

public class BookmarkNodeRoot extends BookmarkNodeDecorator{
//	private final List<BookmarkNode> bookmarkNodes = new ArrayList<BookmarkNode>();
	
	private final Collection<BookmarkNode> bookmarkNodeDecorator;
	private BookmarkItem bookmarkItem;
	private BookmarkNodeDL defaultDlNode;
	
	
	public BookmarkNodeRoot(){
		this.bookmarkNodeDecorator = new ArrayList<>();
	}

	public String print() {
		StringBuilder sb = new StringBuilder("<!DOCTYPE NETSCAPE-Bookmark-file-1> \n"
											+"<HTML> \n"
											+"<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=UTF-8\"> \n"
											+"<Title>Clean Bookmarks</Title> \n"
											);
		
		
		if(bookmarkItem != null){
			sb.append(String.format("%s %s", System.lineSeparator(), this.bookmarkItem.print()));
		}
		StringBuilder sb2 = new StringBuilder();
		for (BookmarkNode bookmarkNode : bookmarkNodeDecorator) {
			sb2.append(String.format("%s \t %s", System.lineSeparator(), bookmarkNode.print()));
		}
		
		sb.append(sb2.toString());
		
		sb.append("\n </HTML>");
		return sb.toString();
	}
	
	@Override
	public BookmarkNodeDecorator addBookmarkNode(BookmarkNodeDecorator bookmarkNodeDecorator) {
		
		/*
		 * This part is to handle unusual behavior.
		 * usually we expect <DL> followed by <H1>.
		 * But if we get <DT> instead of <DL> at root level, here we create a 
		 * <DL> manually and insert all the <DT> in the default <DL>. 
		 */
		
		if(bookmarkNodeDecorator instanceof BookmarkNodeDT){
			if(this.defaultDlNode == null)
			{
				this.defaultDlNode = new BookmarkNodeDL();
				this.bookmarkNodeDecorator.add(defaultDlNode);
			}
			return this.defaultDlNode.addBookmarkNode(bookmarkNodeDecorator);
		}
		
		
		if(this.bookmarkNodeDecorator.add(bookmarkNodeDecorator)){
			return bookmarkNodeDecorator;
		}
		return null;
	}
	
	public BookmarkNode addBookmarkItem(BookmarkItem bookmarkItem) {
		this.bookmarkItem = bookmarkItem;
		return this.bookmarkItem;
	}
}
