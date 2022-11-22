package com.mihael.libraryapplication.representations;

import com.mihael.libraryapplication.entity.Author;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "authors")
public class AuthorCollectionRepresentation {
    private List<AuthorRepresentation> authors;

    public AuthorCollectionRepresentation() {
    }

    public AuthorCollectionRepresentation(List<Author> authors) {
        this.authors = new ArrayList<>();
        for(Author author : authors){
            this.authors.add(new AuthorRepresentation(author));
        }
    }

    public List<AuthorRepresentation> getAuthors() {
        return authors;
    }

    public void setAuthors(List<AuthorRepresentation> authors) {
        this.authors = authors;
    }
}
