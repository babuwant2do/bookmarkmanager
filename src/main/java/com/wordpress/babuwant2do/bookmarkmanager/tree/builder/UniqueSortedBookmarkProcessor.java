package com.wordpress.babuwant2do.bookmarkmanager.tree.builder;

import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.springframework.stereotype.Component;

import com.wordpress.babuwant2do.bookmarkmanager.tree.BookmarkItem;
import com.wordpress.babuwant2do.bookmarkmanager.tree.BookmarkNodeDL;
import com.wordpress.babuwant2do.bookmarkmanager.tree.BookmarkNodeDT;
import com.wordpress.babuwant2do.bookmarkmanager.tree.BookmarkNodeDecorator;
import com.wordpress.babuwant2do.bookmarkmanager.tree.BookmarkNodeException;
import com.wordpress.babuwant2do.bookmarkmanager.tree.BookmarkNodeRoot;


@Component
public class UniqueSortedBookmarkProcessor implements BookmarkProcessor{

	private void processNodeSubTree(Node nodeToProcess, BookmarkNodeDecorator parentNode)
			throws BookmarkNodeException {
		
		List<Node> nodes = nodeToProcess.childNodes();
		for (Node node : nodes) {
			if(node.nodeName().equalsIgnoreCase("dl") || node.nodeName().equalsIgnoreCase("dt")){
				processNodeSubTree(node,  parentNode);
			}else if(node.nodeName().equalsIgnoreCase("a")){
				parentNode.addBookmarkNode(new BookmarkNodeDT(new BookmarkItem((Element) node)));
			}
		}
	}

	@Override
	public BookmarkNodeDecorator process(Node nodeToProcess) throws BookmarkNodeException{
		BookmarkNodeDecorator rootNode = new  BookmarkNodeRoot();
		this.processNodeSubTree(nodeToProcess, rootNode.addBookmarkNode(new  BookmarkNodeDL()));
		return rootNode;
	}

}
