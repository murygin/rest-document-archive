package org.murygin.archive.service;

import java.util.Date;
import java.util.UUID;

public class Document {
    
    private String uuid;
    private byte[] fileData;
    private String fileName;
    private Date documentDate;
    private String personName;
    
    public Document( byte[] fileData, String fileName, Date documentDate, String personName) {
        super();
        this.uuid = UUID.randomUUID().toString();
        this.fileData = fileData;
        this.fileName = fileName;
        this.documentDate = documentDate;
        this.personName = personName;
    }
    
    public String getUuid() {
        return uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public byte[] getFileData() {
        return fileData;
    }
    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    public Date getDocumentDate() {
        return documentDate;
    }
    public void setDocumentDate(Date documentDate) {
        this.documentDate = documentDate;
    }
    
    public String getPersonName() {
        return personName;
    }
    public void setPersonName(String personName) {
        this.personName = personName;
    }
    
    
}
