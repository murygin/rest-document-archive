rest-document-archive
=====================

A simple document archive with REST interface.

REST API
--------

* **Add a document**
 
   /archive/upload?file={file}&person={person}&date={date} *POST*

  * file: A file posted in a multipart request
  * person: The name of the uploading person
  * date: The date of the document
   
* **Find documents**

   /archive/documents?person={person}&date={date} *GET*

  * person: The name of the uploading person
  * date: The date of the document
   
* **Get a document**  

   /archive/document/{id} *GET*                                  

  * id: The UUID of a document
