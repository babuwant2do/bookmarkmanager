package com.wordpress.babuwant2do.bookmarkmanager.tree.builder;

import org.jsoup.nodes.Node;

import com.wordpress.babuwant2do.bookmarkmanager.tree.BookmarkNodeDecorator;
import com.wordpress.babuwant2do.bookmarkmanager.tree.BookmarkNodeException;


public interface BookmarkProcessor {
	public BookmarkNodeDecorator process(Node nodeToProcess) throws BookmarkNodeException;
}
