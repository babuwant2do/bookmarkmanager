package com.wordpress.babuwant2do.bookmarkmanager.tree;

import org.jsoup.nodes.Node;


public interface BookmarkManager {
	public BookmarkNodeDecorator process(Node nodeToProcess) throws BookmarkNodeException;
}
