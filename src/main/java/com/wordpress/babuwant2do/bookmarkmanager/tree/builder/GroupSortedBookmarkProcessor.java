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
public class GroupSortedBookmarkProcessor implements BookmarkProcessor{

	public BookmarkNodeDecorator createBookmarkNodeTree(Node doc) throws BookmarkNodeException{
		BookmarkNodeRoot parentNode = new BookmarkNodeRoot();
		if(doc != null){
			List<Node> nodes = doc.childNodes();
			for (Node node : nodes) {
				if(node.nodeName().equalsIgnoreCase("dl")){
					
					parentNode.addBookmarkNode(this.createDLNode(node));
					
				}else if(node.nodeName().equalsIgnoreCase("dt")){
					parentNode.addBookmarkNode(this.createDTNode(node));
					
				}else if(node.nodeName().equalsIgnoreCase("h1")){
					parentNode.addBookmarkItem(new BookmarkItem((Element) node));
				}
			}
		}
		return parentNode;
	}
	
	public BookmarkNodeDecorator createDLNode(Node doc) throws BookmarkNodeException{
		BookmarkNodeDL parentNode = new BookmarkNodeDL();
		if(doc != null){
			List<Node> nodes = doc.childNodes();
			for (Node node : nodes) {
				if(node.nodeName().equalsIgnoreCase("dt")){
					parentNode.addBookmarkNode(this.createDTNode(node));
				}
			}
		}
		return parentNode;
	}

	public BookmarkNodeDecorator createDTNode(Node doc) throws BookmarkNodeException{
		BookmarkNodeDT parentNode = new BookmarkNodeDT();
		if(doc != null){
			List<Node> nodes = doc.childNodes();
			for (Node node : nodes) {
				if(node.nodeName().equalsIgnoreCase("dl")){
					parentNode.addBookmarkNode(createDLNode(node));
				}else if(node.nodeName().equalsIgnoreCase("a") ||
						node.nodeName().equalsIgnoreCase("h3")){
					parentNode.addBookmarkItem(new BookmarkItem((Element) node));
				}
			}
		}
		return parentNode;
	}
	

	@Override
	public BookmarkNodeDecorator process(Node nodeToProcess) throws BookmarkNodeException {
		return this.createBookmarkNodeTree(nodeToProcess);
	}

}
