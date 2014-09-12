package org.murygin.archive.rest;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.murygin.archive.service.Document;
import org.murygin.archive.service.DocumentMetadata;
import org.murygin.archive.service.IArchiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value = "/archive")
public class ArchiveController {

    private static final Logger LOG = Logger.getLogger(ArchiveController.class);
    
    @Autowired
    IArchiveService archiveService;
    
    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public @ResponseBody String provideUploadInfo() {
        return "You can upload a file by posting to this same URL.";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public @ResponseBody String handleFileUpload(
            @RequestParam(value="file", required=true) MultipartFile file ,
            @RequestParam(value="name", required=true) String name,
            @RequestParam(value="date", required=true) @DateTimeFormat(pattern="yyyy-MM-dd") Date date) {
        if (!file.isEmpty()) {
            try {
                Document document = new Document(file.getBytes(), file.getOriginalFilename(), date, name );
                getArchiveService().save(document);
                return "You successfully uploaded " + name + " into " + name + "-uploaded !";
            } catch (Exception e) {
                LOG.error("Error while uploading.", e);
                return "You failed to upload " + name + " => " + e.getMessage();
            }
        } else {
            return "You failed to upload " + name + " because the file was empty.";
        }
    }
    
    @RequestMapping(value = "/document/{id}", method = RequestMethod.GET)
    public HttpEntity<byte[]> getDocument(@PathVariable String id) {         
        // send it back to the client
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<byte[]>(getArchiveService().getDocumentFile(id), httpHeaders, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/documents", method = RequestMethod.GET)
    public List<DocumentMetadata> findDocument(
            @RequestParam(value="person") String personName,
            @RequestParam(value="date") @DateTimeFormat(pattern="yyyy-MM-dd") Date date) {
        return getArchiveService().findDocuments(personName,date);
    }

    public IArchiveService getArchiveService() {
        return archiveService;
    }

    public void setArchiveService(IArchiveService archiveService) {
        this.archiveService = archiveService;
    }

}
