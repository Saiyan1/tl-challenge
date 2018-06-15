package com.saiyan.dropwizard.resources;

import app.jooq.tables.records.BookRecord;
import com.saiyan.dropwizard.api.Book;
import com.codahale.metrics.annotation.Timed;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;


import static app.jooq.Tables.BOOK;

@Path("/findBooks")
@Produces(MediaType.APPLICATION_JSON)
public class FindBooksResource {

    private final String template;
    private final String defaultName;
    private final AtomicLong counter;
    private String title;
    private String description;


    public FindBooksResource(String template, String defaultName) {
        this.template = template;
        this.defaultName = defaultName;
        this.counter = new AtomicLong();
        this.title = "";
        this.description = "";
    }


    @GET
    @Timed
    public List<Book> sayHello(@QueryParam("name") String search) {

        String userName = "phpmyadmin";
        String password = "1536020a";
        String url = "jdbc:mysql://localhost:3306/library";

        Result<BookRecord> result = null;
        List<Book> list = new ArrayList<>();

        try {
            Connection conn = DriverManager.getConnection(url, userName, password);

            try {
                DSLContext create = DSL.using(conn, SQLDialect.MYSQL);

                result = create
                        .selectFrom(BOOK)
                        .where(BOOK.TITLE.like("%"+ search +"%"))
                        .or(BOOK.DESCRIPTION.like("%"+ search +"%"))
                        .fetch();


                for (Record r : result) {
                    title = r.getValue(BOOK.TITLE);
                    description = r.getValue(BOOK.DESCRIPTION);
                    Book book = new Book(counter.incrementAndGet(), title, description);
                    list.add(book);
                }

            } finally {
                conn.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}