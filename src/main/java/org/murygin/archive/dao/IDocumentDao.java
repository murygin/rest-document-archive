package org.murygin.archive.dao;

import java.util.Date;
import java.util.List;

import org.murygin.archive.service.Document;
import org.murygin.archive.service.DocumentMetadata;

public interface IDocumentDao {

    void insert(Document document);
    Document load(String uuid);
    List<DocumentMetadata> findByPersonNameDate(String personName, Date date);
}
