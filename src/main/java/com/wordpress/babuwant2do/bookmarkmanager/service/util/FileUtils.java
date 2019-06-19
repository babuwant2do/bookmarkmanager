package com.wordpress.babuwant2do.bookmarkmanager.service.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

public class FileUtils {
	 private static final Logger log = LoggerFactory.getLogger(FileUtils.class);

	private static String tempfileDir =  "/Users/mohammedali/Downloads/bookmarks/upload_x/";
	 
	public static byte[] getFileAsByteArr(String filePath){
        File initialFile = new File(filePath);
	    InputStream targetStream;
		try {
			targetStream = new FileInputStream(initialFile);
			byte[] contents = org.apache.commons.io.IOUtils.toByteArray(targetStream);
			log.debug("REST request to exportPdf PdfArchive : ALL OK , JUST TO RETURN !!");
			return contents;
			
		} catch (IOException e) {
			e.printStackTrace();
        }
        return null;
    }
	
	public static synchronized File saveFile(MultipartFile file){
		try {
			
			//File dir = FileUtils.createDirIfNotExists(tempfileDir);
			String fileName = UUID.randomUUID().toString()+".html";
              String fileAbsulatePath =  tempfileDir + fileName;
              File newFile = new File(fileAbsulatePath);
              file.transferTo(newFile);
			
                return newFile;
		}catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	public static void writeToFile(String filename, String content) throws IOException{
            FileWriter fw=new FileWriter(tempfileDir+ filename);    
            fw.write(content);    
            fw.close();    
            
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
