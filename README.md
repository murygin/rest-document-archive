rest-document-archive
=====================

A simple document archive with REST interface.

REST API
--------

* /archive/upload?file={file}&person={person}&date={date}  Add a document  POST
..* file: A file posted in a multipart request
..* person: The name of the uploading person
..* date: The date of the document
   
* /archive/documents?person={person}&date={date}           Find documents  GET
..* person: The name of the uploading person
..* date: The date of the document
   
* /archive/document/{id}                                   Get a document  GET
..* id: The UUID of a document
