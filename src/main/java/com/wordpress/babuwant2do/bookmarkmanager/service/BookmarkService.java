package com.wordpress.babuwant2do.bookmarkmanager.service;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import com.wordpress.babuwant2do.bookmarkmanager.service.util.FileUtils;
import com.wordpress.babuwant2do.bookmarkmanager.tree.BookmarkNodeDecorator;
import com.wordpress.babuwant2do.bookmarkmanager.tree.BookmarkNodeException;
import com.wordpress.babuwant2do.bookmarkmanager.tree.builder.BookmarkProcessor;
import com.wordpress.babuwant2do.bookmarkmanager.tree.builder.GroupSortedBookmarkProcessor;
import com.wordpress.babuwant2do.bookmarkmanager.tree.builder.UniqueSortedBookmarkProcessor;

@Service
public class BookmarkService {
	
	private final BookmarkProcessor GroupSortedBookmarkProcessor;
	private final BookmarkProcessor UniqueSortedBookmarkProcessor;
	
	public BookmarkService(GroupSortedBookmarkProcessor groupSortedBookmarkProcessor 
			, UniqueSortedBookmarkProcessor uniqueSortedBookmarkProcessor){
		this.UniqueSortedBookmarkProcessor = uniqueSortedBookmarkProcessor;
		this.GroupSortedBookmarkProcessor = groupSortedBookmarkProcessor;
//		Jsou
	}
	
	public String createUnique(File file){
		return this.process(file, this.UniqueSortedBookmarkProcessor);
	}
	
	public String createGrouped(File file){
		
		return this.process(file, this.GroupSortedBookmarkProcessor);
	}
	
	private String process(File file, BookmarkProcessor processor){
		
		try {
			String outFileNAme = "out_"+file.getName();
			Document doc = Jsoup.parse(file, "UTF-8");
			BookmarkNodeDecorator chromeParentNode = processor.process(doc.selectFirst("body"));
			FileUtils.writeToFile(outFileNAme , chromeParentNode.print());
			return outFileNAme;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (BookmarkNodeException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	
}
