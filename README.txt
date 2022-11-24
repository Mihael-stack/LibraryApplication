Library Application

Before Starting Application:
  mvn clean compile package

DATABASE:
  I used H2 database with a file, so there is no need to setup the database. The database already has some data in it.

Starting Application:
  Build and Run "LibraryApplication" in package src/main/java/com/mihael/libraryapplication/LibraryApplication.java

Creating Book JSON format
  With Authors -{"title":"Java Fundementals","authors":[{"firstName":"Sam","lastName":"S","date":"1997-10-10"},{"firstName":"John","lastName":"S","date":"1997-10-12"}],"genre":"FICTION"}
  Without Authors {"title":"Java Fundementals","authors":[],"genre":"FICTION"}

  Creating Authors
  {"firstName":"John","lastName":"Doe","date":"1997-10-10"}
