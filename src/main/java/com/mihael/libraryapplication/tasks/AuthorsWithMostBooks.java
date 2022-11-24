package com.mihael.libraryapplication.tasks;

import com.mihael.libraryapplication.entity.Author;
import com.mihael.libraryapplication.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@Component
public class AuthorsWithMostBooks {

    private final AuthorService authorService;

    @Autowired
    public AuthorsWithMostBooks(AuthorService authorService) {
        this.authorService = authorService;
    }
    @Scheduled(cron = "0 0 09 * * *")
    public void authorsWithMostBooks() throws IOException {
        File file = new File("./authorsWithMostBooks.txt");
        FileWriter fw = null;
        try {
            String lineSeparator = System.getProperty("line.separator");
            fw = new FileWriter(file);
            List<Author> authorList = new ArrayList<>(this.authorService.getAllAuthors());
            Collections.sort(authorList);
            int n;
            if (authorList.size() > 5) n = 5;
            else n = authorList.size();

            for(int i = 0; i<n;i++){
                fw.write(authorList.get(i).toString() + lineSeparator);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            fw.flush();
            fw.close();
        }
    }
}
