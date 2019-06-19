package com.wordpress.babuwant2do.bookmarkmanager.web.rest;

import java.io.File;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.wordpress.babuwant2do.bookmarkmanager.service.BookmarkService;
import com.wordpress.babuwant2do.bookmarkmanager.service.util.FileUtils;



@RestController
@RequestMapping("/api")
public class BookmarkResource {
	private final Logger log = LoggerFactory.getLogger(BookmarkResource.class);

	private final BookmarkService bookmarkService;
	public BookmarkResource(BookmarkService bookmarkService){
		this.bookmarkService = bookmarkService;
	}
	
	/**
	 * 
	 * @param file - 
	 * @param request
	 * @param isFolderMaintain - (optional )to maintain bookmark organize in folder pass "..url?folder=true
	 * @return
	 */
	@PostMapping("/bookmark-managers/process-bookmark")
    public ResponseEntity<byte[]> uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request, @RequestParam("folder") Optional<Boolean> isFolderMaintain) {
		
		File createdFile =  null;
		String outFilename=null;
		String outFilePath = null;
		try {
			createdFile =  FileUtils.saveFile(file);
			if(createdFile != null){
	        	if(isFolderMaintain.orElse(false)){
	        		outFilename = this.bookmarkService.createGrouped(createdFile);        		
	        	}else{
	        		outFilename = this.bookmarkService.createUnique(createdFile);        		
	        	}
	        	
	        	if(outFilename != null){
	        		outFilePath = "/Users/mohammedali/Downloads/bookmarks/upload_x/" + outFilename;
	        		byte[] contents  = FileUtils.getFileAsByteArr(outFilePath);
	        		
	        		HttpHeaders headers = new HttpHeaders();
	        		headers.setContentType(MediaType.parseMediaType("text/html"));
	        		
	        		 headers.setContentDispositionFormData("filename", outFilename);
	                 headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
	                 
	                 return new ResponseEntity<byte[]>(contents, headers, HttpStatus.OK);
	                 
	        	}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(createdFile != null){
				FileUtils.deleteFile(createdFile.getPath());				
			}
			if(outFilePath != null){
				FileUtils.deleteFile(outFilePath);				
			}
		}
        return new ResponseEntity<byte[]>(HttpStatus.EXPECTATION_FAILED);
    }
}
