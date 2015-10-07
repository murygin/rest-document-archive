REST Document Archive
=====================

A simple document archive with REST interface. You can read more about this project in my blog post: [A REST web service, file uploads & Spring Boot](https://murygin.wordpress.com/2014/10/13/rest-web-service-file-uploads-spring-boot/).

Build and run
-------------

```bash
git clone https://github.com/murygin/rest-document-archive.git
cd rest-document-archive
mvn package
java -jar target/rest-document-archive-0.1.0.jar
```

Open [http://localhost:8080/](http://localhost:8080/) to see the AngularJS frontend.

REST API
--------

* **Add a document**
 
   */archive/upload?file={file}&person={person}&date={date} POST*

  * file: A file posted in a multipart request
  * person: The name of the uploading person
  * date: The date of the document
   
* **Find documents**

   */archive/documents?person={person}&date={date} GET*

  * person: The name of the uploading person
  * date: The date of the document
   
* **Get a document**  

   */archive/document/{id} GET*                                  

  * id: The UUID of a document

Documentation
-------------

* REST Service Controller
  * [ArchiveController.java](https://github.com/murygin/rest-document-archive/blob/master/src/main/java/org/murygin/archive/rest/ArchiveController.java)
   
   Executes incoming request and defines URL to service method mappings. All remote call are delegated to the archive service.
* Service
  * Interface: [IArchiveService.java](https://github.com/murygin/rest-document-archive/blob/master/src/main/java/org/murygin/archive/service/IArchiveService.java)
  * Implementation: [ArchiveService.java](https://github.com/murygin/rest-document-archive/blob/master/src/main/java/org/murygin/archive/service/ArchiveService.java)
   
   A service to save, find and get documents from an archive. 
* Data access object
  * Interface: [IDocumentDao.java](https://github.com/murygin/rest-document-archive/blob/master/src/main/java/org/murygin/archive/dao/IDocumentDao.java)
  * Implementation: [FileSystemDocumentDao.java](https://github.com/murygin/rest-document-archive/blob/master/src/main/java/org/murygin/archive/dao/FileSystemDocumentDao.java)  
   
   Data access object to insert, find and load documents. FileSystemDocumentDao saves documents in the file system. No database in involved. For each document a folder is created. The folder contains the document and a properties files with the meta data of the document. Each document in the archive has a Universally Unique Identifier (UUID). The name of the documents folder is the UUID of the document.

* Client
   
   [ArchiveServiceClient.java](https://github.com/murygin/rest-document-archive/blob/master/src/main/java/org/murygin/archive/client/ArchiveServiceClient.java)
   
   A client for the document archive which is using the REST service.

* Web client
   
   [src/main/resources/static](https://github.com/murygin/rest-document-archive/tree/master/src/main/resources/static)

   A web client made with [AngularJS](https://angularjs.org/).
