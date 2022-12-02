package com.mihael.libraryapplication.tasks;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Component
public class AuthorsWithMostBooksSQL {

    @PersistenceContext
    private EntityManager em;

    @Scheduled(cron = "0 0 09 * * *")
    public void authorsWithMostBooks() throws IOException {
        File file = new File("./authorsWithMostBooks.txt");
        FileWriter fw = null;
        try {
            String lineSeparator = System.getProperty("line.separator");
            fw = new FileWriter(file);
            Query query = this.em.createQuery(
                    "SELECT author, COUNT(author.id) as writtenBooks FROM Author as author INNER JOIN author.booksWritten as books GROUP BY author.id ORDER BY writtenBooks DESC");

            List<Object[]> list = query.getResultList();
            int n;
            if (list.size() > 5) n = 5;
            else n = list.size();
            for(int i = 0; i < n; i++){
                fw.write(list.get(i)[0].toString());
                fw.write(list.get(i)[1].toString());
                fw.write(lineSeparator);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            fw.flush();
            fw.close();
        }
    }
}
