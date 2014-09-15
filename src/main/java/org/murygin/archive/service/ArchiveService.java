package org.murygin.archive.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.murygin.archive.dao.IDocumentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("archiveService")
public class ArchiveService implements IArchiveService, Serializable {

    private static final Logger LOG = Logger.getLogger(ArchiveService.class);
    
    @Autowired
    private IDocumentDao DocumentDao;

    @Override
    public DocumentMetadata save(Document document) {
        getDocumentDao().insert(document); 
        return document.getMetadata();
    }
    
    @Override
    public byte[] getDocumentFile(String id) {
        Document document = getDocumentDao().load(id);
        if(document!=null) {
            return document.getFileData();
        } else {
            return null;
        }
    }
    
    @Override
    public List<DocumentMetadata> findDocuments(String personName, Date date) {
        return getDocumentDao().findByPersonNameDate(personName, date);
    }

    public IDocumentDao getDocumentDao() {
        return DocumentDao;
    }

    public void setDocumentDao(IDocumentDao documentDao) {
        DocumentDao = documentDao;
    }


}
