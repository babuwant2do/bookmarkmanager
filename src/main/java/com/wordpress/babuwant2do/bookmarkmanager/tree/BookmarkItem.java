package com.wordpress.babuwant2do.bookmarkmanager.tree;

import java.util.Objects;
import java.util.stream.Collectors;

import org.jsoup.nodes.Element;

public class BookmarkItem implements BookmarkNode{
	private final Element element;
	
	public BookmarkItem(Element element) throws BookmarkNodeException{
		if(!(element.nodeName().equalsIgnoreCase("a") ||
				element.nodeName().equalsIgnoreCase("h1")||
				element.nodeName().equalsIgnoreCase("h3"))){
			throw new BookmarkNodeException("element must be either <A>, <H1> or <H3>");
		}
		this.element = element;
	}
	
	public String print(){
		return this.generateElement();
	}
	
	private String generateElement(){
		String attributes = this.getElement().attributes().asList().stream()
			.map(attr -> {
				String key = attr.getKey().toUpperCase();
				String val = attr.getValue();
				return  (val != null?  key+"=\""+val+"\"" : key);
			}).collect(Collectors.joining(" "));
		
		String tag = this.getTag().toUpperCase();
		StringBuilder sb = new StringBuilder(String.format("<%s %s>%s</%s>", tag, attributes, this.getTitle() , tag));
		return sb.toString();
	}

	public Element getElement() {
		return element;
	}
	
	public String getTitle(){
		if(this.element != null)
			return this.element.ownText();
		return null;
	}
	
	public String getLink(){
		if(this.element != null)
			return this.element.attributes().get("href");
		return null;
	}
	
	public String getTag(){
		if(this.element != null)
			return this.element.nodeName().toLowerCase();
		return null;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BookmarkItem other = (BookmarkItem) o;
        if (other.getLink() == null || getLink() == null || other.getLink().isEmpty() || getLink().isEmpty()) {
            return false;
        }
        return getLink().equals(other.getLink());
    }

    @Override
    public int hashCode() {
    	if(getLink() == null) 
    		return super.hashCode();
        return Objects.hashCode(getLink());
    }
}
