package org.murygin.archive.service;

import java.util.Date;
import java.util.List;


public interface IArchiveService {
    
    void save(Document document);
    byte[] getDocumentFile(String id);
    List<DocumentMetadata> findDocuments(String personName, Date date);
}
