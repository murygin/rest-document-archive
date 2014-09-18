REST Document Archive
=====================

A simple document archive with REST interface.

Build and run
-------------

```bash
git clone https://github.com/murygin/rest-document-archive.git
cd rest-document-archive
mvn package
java -jar target/rest-document-archive-0.1.0.jar
```

Open http://localhost:8080/ to see the AngularJS frontend.


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
