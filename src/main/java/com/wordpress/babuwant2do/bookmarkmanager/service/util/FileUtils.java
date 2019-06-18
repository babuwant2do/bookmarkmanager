package com.wordpress.babuwant2do.bookmarkmanager.service.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

public class FileUtils {
	 private static final Logger log = LoggerFactory.getLogger(FileUtils.class);

	
	public static synchronized File saveFile(MultipartFile file){
		try {
			
			File dir = FileUtils.createDirIfNotExists("/Users/mohammedali/Downloads/bookmarks/upload_x");
			String fileName = UUID.randomUUID().toString()+".html";
              String fileAbsulatePath =  dir.getPath() +"/" + fileName;
              File newFile = new File(fileAbsulatePath);
              file.transferTo(newFile);
			
                return newFile;
		}catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static synchronized boolean isFileExists(String path){
		File directory = new File(path);
		return directory.exists();
	}
	
	public static synchronized File createDirIfNotExists(String path){
		File directory = new File(path);
		if (! directory.exists()){
			directory.mkdir();
		}
		return directory; 
	}
	public static synchronized File createDirIfNotExists(String path, String directoryName){
		File directory = new File(path+ (path.endsWith("/")? "": "/")+directoryName);
		if (! directory.exists()){
			directory.mkdir();
		}
		return directory; 
	}
	
	public static synchronized boolean deleteFile(String filePath){
		try {
			// System.out.println("****** file delete fn called"+ filePath);
			Files.deleteIfExists(Paths.get(filePath));
			return true;
		} catch (IOException e) {
			// e.printStackTrace();
			FileUtils.log.error(e.getMessage(), e);
		}
		return false;
	}
	
	public static synchronized boolean deleteDirectory(String dirPath) {
		File directory = new File(dirPath);
		return deleteDirectory(directory);
	}

	public static synchronized boolean deleteDirectory(File directoryToBeDeleted) {
	    File[] allContents = directoryToBeDeleted.listFiles();
	    if (allContents != null) {
	        for (File file : allContents) {
	            deleteDirectory(file);
	        }
	    }
	    return directoryToBeDeleted.delete();
	}
	
}
