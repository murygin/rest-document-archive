package org.murygin.archive.dao;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.murygin.archive.service.Document;
import org.springframework.stereotype.Service;

@Service("documentDao")
public class FileSystemDocumentDao implements IDocumentDao {

    private static final Logger LOG = Logger.getLogger(FileSystemDocumentDao.class);
    
    public static final String DIRECTORY = "archive";
    public static final String META_DATA_FILE_NAME = "metadata.properties";
    public static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd";
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(DATE_FORMAT_PATTERN);
    
    @PostConstruct
    public void init() {
        createDirectory(DIRECTORY);
    }
    
    @Override
    public void insert(Document document) {
        try {
            createDirectory(document);
            saveFileData(document);
            saveMetaData(document);
        } catch (IOException e) {
            LOG.error("Error while inserting document", e);
        }
    }
    
    private void saveFileData(Document document) throws IOException {
        String path = getDirectoryPath(document);
        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(new File(path), document.getFileName())));
        stream.write(document.getFileData());
        stream.close();
    }
    
    public void saveMetaData(Document document) throws IOException {
            String path = getDirectoryPath(document);
            Properties props = createProperties(document);
            File f = new File(new File(path), META_DATA_FILE_NAME);
            OutputStream out = new FileOutputStream( f );
            props.store(out, "Document meta data");       
    }

    private Properties createProperties(Document document) {
        Properties props = new Properties();
        props.setProperty("uuid", document.getUuid());
        props.setProperty("file-name", document.getFileName());
        props.setProperty("person-name", document.getPersonName());
        props.setProperty("document-date", DATE_FORMAT.format(document.getDocumentDate()));
        return props;
    }
    
    private String createDirectory(Document document) {
        String path = getDirectoryPath(document);
        createDirectory(path);
        return path;
    }

    private String getDirectoryPath(Document document) {
        StringBuilder sb = new StringBuilder();
        sb.append(DIRECTORY).append(File.separator).append(document.getUuid());
        String path = sb.toString();
        return path;
    }

    private void createDirectory(String path) {
        File file = new File(path);
        file.mkdirs();
    }

}
