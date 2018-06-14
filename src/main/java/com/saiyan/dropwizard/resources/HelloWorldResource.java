package com.saiyan.dropwizard.resources;

import app.jooq.tables.records.BookRecord;
import com.saiyan.dropwizard.api.Saying;
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
import java.util.concurrent.atomic.AtomicLong;
import java.util.Optional;

import static app.jooq.Tables.BOOK;

@Path("/findBooks")
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldResource {
    private final String template;
    private final String defaultName;
    private final AtomicLong counter;
    private String title;

    public HelloWorldResource(String template, String defaultName) {
        this.template = template;
        this.defaultName = defaultName;
        this.counter = new AtomicLong();
        this.title = "";
    }

    @GET
    @Timed
    public Saying sayHello(@QueryParam("name") Optional<String> name) {

        String userName = "phpmyadmin";
        String password = "1536020a";
        String url = "jdbc:mysql://localhost:3306/library";

        try {
            Connection conn = DriverManager.getConnection(url, userName, password);
            try {
                DSLContext create = DSL.using(conn, SQLDialect.MYSQL);

                Result<BookRecord> result = create.selectFrom(BOOK).where(BOOK.TITLE.like("%is%")).fetch();

                for (Record r : result) {

                    title = r.getValue(BOOK.TITLE);

                }


                /*
                AuthorRecord result = create.selectFrom(AUTHOR).where(AUTHOR.ID.eq(1)).fetchOne();
                firstName = result.getValue(AUTHOR.FIRST_NAME);
                */

            } finally {
                conn.close();
            }
        }

        // For the sake of this tutorial, let's keep exception handling simple
        catch (Exception e) {
            e.printStackTrace();
        }

        final String value = String.format(template, title);
        return new Saying(counter.incrementAndGet(), value);

    }
}