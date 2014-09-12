package org.murygin.archive.service;

import java.util.Date;
import java.util.Properties;

public class Document extends DocumentMetadata {

    private byte[] fileData;
    
    public Document( byte[] fileData, String fileName, Date documentDate, String personName) {
        super(fileName, documentDate, personName);
        this.fileData = fileData;
    }

    public Document(Properties properties) {
        super(properties);
    }

    public byte[] getFileData() {
        return fileData;
    }
    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }
    
}
