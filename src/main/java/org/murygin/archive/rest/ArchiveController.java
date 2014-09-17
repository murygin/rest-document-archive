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

/**
 * REST web service for archive service {@link IArchiveService}.
 * 
 * /archive/upload?file={file}&person={person}&date={date}  Add a document  POST
 *   file: A file posted in a multipart request
 *   person: The name of the uploading person
 *   date: The date of the document
 *   
 * /archive/documents?person={person}&date={date}           Find documents  GET
 *   person: The name of the uploading person
 *   date: The date of the document
 *   
 * /archive/document/{id}                                   Get a document  GET
 *   id: The UUID of a document
 * 
 * All service calls are delegated to instances of {@link IArchiveService}.
 * 
 * @author Daniel Murygin <daniel.murygin[at]gmail[dot]com>
 */
@Controller
@RequestMapping(value = "/archive")
public class ArchiveController {

    private static final Logger LOG = Logger.getLogger(ArchiveController.class);
    
    @Autowired
    IArchiveService archiveService;

    /**
     * Adds a document to the archive.
     * 
     * Url: /archive/upload?file={file}&person={person}&date={date} [POST]
     * 
     * @param file A file posted in a multipart request
     * @param person The name of the uploading person
     * @param date The date of the document
     * @return The meta data of the added document
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public @ResponseBody DocumentMetadata handleFileUpload(
            @RequestParam(value="file", required=true) MultipartFile file ,
            @RequestParam(value="person", required=true) String person,
            @RequestParam(value="date", required=true) @DateTimeFormat(pattern="yyyy-MM-dd") Date date) {
        
        try {
            Document document = new Document(file.getBytes(), file.getOriginalFilename(), date, person );
            getArchiveService().save(document);
            return document.getMetadata();
        } catch (RuntimeException e) {
            LOG.error("Error while uploading.", e);
            throw e;
        } catch (Exception e) {
            LOG.error("Error while uploading.", e);
            throw new RuntimeException(e);
        }      
    }
    
    /**
     * Finds document in the archive. Returns a list of document meta data 
     * which does not include the file data. Use getDocument to get the file.
     * Returns an empty list if no document was found.
     * 
     * Url: /archive/documents?person={person}&date={date} [GET]
     * 
     * @param person The name of the uploading person
     * @param date The date of the document
     * @return A list of document meta data
     */
    @RequestMapping(value = "/documents", method = RequestMethod.GET)
    public HttpEntity<List<DocumentMetadata>> findDocument(
            @RequestParam(value="person", required=false) String person,
            @RequestParam(value="date", required=false) @DateTimeFormat(pattern="yyyy-MM-dd") Date date) {
        HttpHeaders httpHeaders = new HttpHeaders();
        return new ResponseEntity<List<DocumentMetadata>>(getArchiveService().findDocuments(person,date), httpHeaders,HttpStatus.OK);
    }
    
    /**
     * Returns the document file from the archive with the given UUID.
     * 
     * Url: /archive/document/{id} [GET]
     * 
     * @param id The UUID of a document
     * @return The document file
     */
    @RequestMapping(value = "/document/{id}", method = RequestMethod.GET)
    public HttpEntity<byte[]> getDocument(@PathVariable String id) {         
        // send it back to the client
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<byte[]>(getArchiveService().getDocumentFile(id), httpHeaders, HttpStatus.OK);
    }

    public IArchiveService getArchiveService() {
        return archiveService;
    }

    public void setArchiveService(IArchiveService archiveService) {
        this.archiveService = archiveService;
    }

}
