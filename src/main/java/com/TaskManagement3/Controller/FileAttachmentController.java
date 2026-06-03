package com.TaskManagement3.Controller;

import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;

import com.TaskManagement3.Entity.FileAttachment;
import com.TaskManagement3.Service.FileAttachmentService;

import java.io.InputStream;
import java.net.URL;

@RestController
@RequestMapping("/api/attachments")
public class FileAttachmentController {

	private final FileAttachmentService attachmentService;

	public FileAttachmentController(FileAttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}
	
	@PostMapping("/upload/{issueId}")
	public ResponseEntity<FileAttachment>upload(@org.springframework.web.bind.annotation.PathVariable Long issueId,
							@RequestParam("file") MultipartFile file,
							@RequestParam("uploadedBy") String uploadedBy){
		
		return ResponseEntity.ok(attachmentService.upload(issueId, file, uploadedBy));
		
		
	}
	
	@GetMapping("/issue/{issueId}")
	public ResponseEntity<List<FileAttachment>> getFileByIssueId(@org.springframework.web.bind.annotation.PathVariable("issueId") Long issueId) {
		return ResponseEntity.ok(attachmentService.getFileByIssueId(issueId));
	}
	
	// Cloud download
	@GetMapping("/download/{id}")
	public ResponseEntity<Void>download(@org.springframework.web.bind.annotation.PathVariable Long id){
		FileAttachment attachments= attachmentService.getFileById(id);
		
		return ResponseEntity.status(HttpStatus.FOUND).header(HttpHeaders.LOCATION, attachments.getStoragePath()).build();
	}
	
	// Device download
	@GetMapping("/download/stream/{id}")
	public ResponseEntity<Resource>stream(@org.springframework.web.bind.annotation.PathVariable Long id) throws Exception{
		FileAttachment attachments= attachmentService.getFileById(id);
		
		URL url = new URL(attachments.getStoragePath());
		
		InputStream inputStream = url.openStream();
		
		InputStreamResource resource= new InputStreamResource(inputStream);
		
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_LOCATION, 
						"attachments;fileName=\""+attachments.getFileName()+ "\"").
					 contentType(MediaType.parseMediaType(attachments.getContentType())).body(resource);
		
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String>delete(@org.springframework.web.bind.annotation.PathVariable Long id){
		return ResponseEntity.ok("File deleted successfully");
	}

}