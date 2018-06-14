package com.saiyan.dropwizard;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import com.saiyan.dropwizard.resources.HelloWorldResource;
//import com.example.helloworld.health.TemplateHealthCheck;

public class DropWizardApplication extends Application<SaiyanConfig> {

    public static void main(String[] args) throws Exception {
        new DropWizardApplication().run(args);
    }

    @Override
    public void run(SaiyanConfig configuration,
                    Environment environment) {

        final HelloWorldResource resource = new HelloWorldResource(
                configuration.getTemplate(),
                configuration.getDefaultName()
        );

        environment.jersey().register(resource);
    }
}
