package com.mihael.libraryapplication.restcontrollers;
import com.mihael.libraryapplication.entity.Author;
import com.mihael.libraryapplication.representations.AuthorCollectionRepresentation;
import com.mihael.libraryapplication.representations.AuthorRepresentation;
import com.mihael.libraryapplication.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


@RestController
public class AuthorRestController {
    private final AuthorService authorService;

    @Autowired
    public AuthorRestController(AuthorService authorService){
        this.authorService = authorService;
    }

    @GetMapping("/authors/{id}")
    public AuthorRepresentation findAuthorById(@PathVariable Long id){
        AuthorRepresentation authorRepresentation = new AuthorRepresentation(this.authorService.findAuthorById(id));
        Link link = linkTo(methodOn(AuthorRestController.class).findAuthorById(id)).withSelfRel();
        authorRepresentation.add(link);
        return authorRepresentation;
    }
    @GetMapping("/authors")
    public AuthorCollectionRepresentation getAllAuthors(){
        AuthorCollectionRepresentation authors = new AuthorCollectionRepresentation(this.authorService.getAllAuthors());
        for(AuthorRepresentation author : authors.getAuthors()){
            Link link = linkTo(methodOn(AuthorRestController.class).findAuthorById(author.getId())).withSelfRel();
            author.add(link);
        }
        return authors;
    }
    @PostMapping("/authors")
    public AuthorRepresentation createNewAuthor(@RequestBody Author author){
        this.authorService.createNewAuthor(author);
        AuthorRepresentation authorRepresentation = new AuthorRepresentation(this.authorService.findAuthor(
                author.getFirstName(),
                author.getLastName(),
                author.getDate()));
        Link link = linkTo(methodOn(AuthorRestController.class).findAuthorById(authorRepresentation.getId())).withSelfRel();
        authorRepresentation.add(link);
        return authorRepresentation;
    }

}
