package org.murygin.archive.service;

import java.io.Serializable;

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
    public void save(Document document) {
        getDocumentDao().insert(document);      
    }

    public IDocumentDao getDocumentDao() {
        return DocumentDao;
    }

    public void setDocumentDao(IDocumentDao documentDao) {
        DocumentDao = documentDao;
    }

}
