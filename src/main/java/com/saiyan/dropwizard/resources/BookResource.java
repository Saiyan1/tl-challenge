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
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;


import static app.jooq.Tables.BOOK;

@Path("/book")
@Produces(MediaType.APPLICATION_JSON)
public class BookResource {

    private final AtomicLong counter;
    private String isbn;
    private String title;
    private String subtitle;
    private String authors;
    private Date published;
    private String publisher;
    private Integer pages;
    private String description;
    private Byte instock;


    public BookResource() {
        this.counter = new AtomicLong();
        this.isbn = "";
        this.title = "";
        this.subtitle = "";
        this.authors = "";
        this.published = null;
        this.publisher = "";
        this.pages = 0;
        this.description = "";
        this.instock = null;
    }


    @GET
    @Timed
    public List<Book> getBooks() {

        String userName = "phpmyadmin";
        String password = "1536020a";
        String url = "jdbc:mysql://localhost:3306/bookshelf";

        Result<BookRecord> result = null;
        List<Book> list = new ArrayList<>();

        try {
            Connection conn = DriverManager.getConnection(url, userName, password);

            try {
                DSLContext create = DSL.using(conn, SQLDialect.MYSQL);

                result = create
                        .selectFrom(BOOK)
                        .fetch();

                for (Record r : result) {
                    isbn = r.getValue(BOOK.ISBN);
                    title = r.getValue(BOOK.TITLE);
                    subtitle = r.getValue(BOOK.SUBTITLE);
                    authors = r.getValue(BOOK.AUTHORS);
                    published = r.getValue(BOOK.PUBLISHED);
                    publisher = r.getValue(BOOK.PUBLISHER);
                    pages = r.getValue(BOOK.PAGES);
                    description = r.getValue(BOOK.DESCRIPTION);
                    instock = r.getValue(BOOK.INSTOCK);

                    Book book = new Book(counter.incrementAndGet(), isbn, title, subtitle, authors, published,
                            publisher, pages, description, instock);
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