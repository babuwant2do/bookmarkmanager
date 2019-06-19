package com.wordpress.babuwant2do.bookmarkmanager.tree;

import java.util.Objects;

public class BookmarkNodeDT extends BookmarkNodeDecorator implements Comparable<BookmarkNodeDT>{
	private BookmarkItem bookmarkItem;
	private BookmarkNodeDecorator bookmarkNodeDecorator;
	
	public BookmarkNodeDT(){}
	public BookmarkNodeDT(BookmarkItem bookmarkItem){
		this.bookmarkItem = bookmarkItem;
	}
	
	public String print() {
		StringBuilder sb = new StringBuilder();
		
		if(bookmarkItem != null){
			sb.append(String.format("<DT> %s", this.bookmarkItem.print()));
			if(this.bookmarkNodeDecorator != null){
				sb.append(String.format("%s %s", System.lineSeparator(), this.bookmarkNodeDecorator.print()));
			}
		}
		return sb.toString();
	}


	@Override
	public BookmarkNodeDecorator addBookmarkNode(BookmarkNodeDecorator bookmarkNodeDecorator) {
		this.bookmarkNodeDecorator = bookmarkNodeDecorator;
		return this.bookmarkNodeDecorator;
	}
	
	public BookmarkNode addBookmarkItem(BookmarkItem bookmarkItem) {
		this.bookmarkItem = bookmarkItem;
		return this.bookmarkItem;
	}
	
	public String getTitle(){
		if(this.bookmarkItem != null)
			return this.bookmarkItem.getTitle().toLowerCase();
		return null;
	}
	
	private String getLink(){
		if(this.bookmarkItem != null)
			return this.bookmarkItem.getLink();
		return null;
	}
	
	public String getTag(){
		if(this.bookmarkItem != null)
			return this.bookmarkItem.getTag();
		return null;
	}
	

	//@Override
	public int compareTo(BookmarkNodeDT o) {
		if(o == null) return 1;
		if(this.getTag()== null || this.getTitle() == null) return -1;
		
		int result = this.getTag().compareTo(o.getTag());
		if(result == 0)
			return this.getTitle().compareTo(o.getTitle());
		return result;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BookmarkNodeDT other = (BookmarkNodeDT) o;
        if (other.getLink() == null || getLink() == null || other.getLink().isEmpty() || getLink().isEmpty()) {
            return false;
        }
        return getLink().equals( other.getLink());
    }

    @Override
    public int hashCode() {
    	if(getLink() == null) 
    		return super.hashCode();
        return Objects.hashCode(getLink());
    }
}
