package com.wordpress.babuwant2do.bookmarkmanager.web.rest;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.wordpress.babuwant2do.bookmarkmanager.service.util.FileUtils;



@RestController
@RequestMapping("/api")
public class BookmarkResource {
	private final Logger log = LoggerFactory.getLogger(BookmarkResource.class);

	/**
	 * http://localhost:8080/api/bookmark-manager/sort-unique-list
	 * form data, key: file, type : file
	 * @param file
	 * @return
	 */
	@PostMapping("/bookmark-manager/sort-unique-list")
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file) {
        File createdFile =  FileUtils.saveFile(file);
        System.out.println(file.getOriginalFilename());
        if(createdFile != null){
        	System.out.println(file.getOriginalFilename());
        }
        return new ResponseEntity(String.format("{'filename': '%s'}", createdFile.getName()), HttpStatus.OK);
    }
	
	/**
	 * http://localhost:8080/api/bookmark-manager/downloadFile/be8bb945-48ae-4149-b4d3-da395b742d28.html
	 * @param fileName
	 * @param request
	 * @return
	 */
	@GetMapping("/bookmark-manager/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {

		String filePath = "/Users/mohammedali/Downloads/bookmarks/upload_x/" + fileName;
        Path path = Paths.get(filePath);
        Resource resource = null;
 
        try {
            resource = new UrlResource(path.toUri());
            String contentType = null;
            try {
                contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
            } catch (IOException ex) {
                log.info("file type undetermined.");
            }
            if(contentType == null) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
            return new ResponseEntity("File Not Found ", HttpStatus.NOT_FOUND);
	}


}
