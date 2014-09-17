package org.murygin.archive.dao;

import java.util.Date;
import java.util.List;

import org.murygin.archive.service.Document;
import org.murygin.archive.service.DocumentMetadata;

/**
 * Data access object to insert, find and load {@link Document}s
 * 
 * @author Daniel Murygin <daniel.murygin[at]gmail[dot]com>
 */
public interface IDocumentDao {

    /**
     * Inserts a document in the data store.
     * 
     * @param document A Document
     */
    void insert(Document document);
    
    /**
     * Finds documents in the data store matching the given parameter.
     * A list of document meta data is returned which does not include the file data.
     * Use load and the id from the meta data to get the document file.
     * Returns an empty list if no document was found.
     * 
     * @param personName The name of a person, may be null
     * @param date The date of a document, may be null
     * @return A list of document meta data
     */
    List<DocumentMetadata> findByPersonNameDate(String personName, Date date);
    
    /**
     * Returns the document from the data store with the given id.
     * The document file and meta data is returned.
     * Returns null if no document was found.
     * 
     * @param uuid The id of the document
     * @return A document incl. file and meta data
     */
    Document load(String uuid);
    
}
