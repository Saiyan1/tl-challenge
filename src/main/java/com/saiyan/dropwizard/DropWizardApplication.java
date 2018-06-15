package com.saiyan.dropwizard;

import com.saiyan.dropwizard.resources.BookResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import com.saiyan.dropwizard.resources.FindBooksResource;

public class DropWizardApplication extends Application<SaiyanConfig> {

    public static void main(String[] args) throws Exception {
        new DropWizardApplication().run(args);
    }

    @Override
    public void run(SaiyanConfig configuration,
                    Environment environment) {

        final FindBooksResource findBooksResource = new FindBooksResource();
        final BookResource bookResource = new BookResource();

        environment.jersey().register(findBooksResource);
        environment.jersey().register(bookResource);
    }
}
