package com.wordpress.babuwant2do.bookmarkmanager.tree;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

public class BookmarkNodeDL extends BookmarkNodeDecorator{
	
	private final Collection<BookmarkNodeDecorator> bookmarkNodeDecorator;
	
	public BookmarkNodeDL(){
		this.bookmarkNodeDecorator = new HashSet<BookmarkNodeDecorator>();
//		this.bookmarkNodeDecorator = new ArrayList<BookmarkNodeDecorator>();
	}

	public String print() {
		StringBuilder sb = new StringBuilder("<DL>");
		bookmarkNodeDecorator.stream().sorted().forEach(bookmarkNode -> 
			sb.append(String.format("%s \t %s",System.lineSeparator(),  bookmarkNode.print())));
		sb.append("\n </DL>");
		return sb.toString();
		
//		List sortedList =  bookmarkNodeDecorator.stream().sorted().collect(Collectors.toList());
/*Collections.sort((List) bookmarkNodeDecorator);
		for (BookmarkNode bookmarkNode : this.bookmarkNodeDecorator) {
			sb.append(String.format("%s \t %s",System.lineSeparator(),  bookmarkNode.print()));
		}
		sb.append("\n </DL>");
		return sb.toString();*/
	}

	@Override
	public BookmarkNodeDecorator addBookmarkNode(BookmarkNodeDecorator bookmarkNodeDecorator) {
		if(this.bookmarkNodeDecorator.add(bookmarkNodeDecorator)){
			return bookmarkNodeDecorator;
		}
		return null;
	}

	protected Collection<BookmarkNodeDecorator> getBookmarkNodeDecorator() {
		return bookmarkNodeDecorator;
	}
	
//	@Override
//    public boolean equals(Object o) {
//        if (this == o) {
//            return true;
//        }
//        return Objects.equals(this, o);
////        
////        if (o == null || getClass() != o.getClass()) {
////            return false;
////        }
////        BookmarkNodeDT other = (BookmarkNodeDT) o;
////        if (other.getLink() == null || getLink() == null) {
////            return false;
////        }
////        return Objects.equals(getLink(), other.getLink());
//    }
//
//    @Override
//    public int hashCode() {
//    	return super.hashCode();
//    } 
}
