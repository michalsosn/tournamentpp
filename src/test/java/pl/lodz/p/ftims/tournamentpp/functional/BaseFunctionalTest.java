package pl.lodz.p.ftims.tournamentpp.functional;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.GenericArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ExplodedImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import java.net.URL;

@RunWith(Arquillian.class)
public abstract class BaseFunctionalTest {

    @Deployment(testable = false)
    public static WebArchive createArchive() {
        return BaseArquillianTest.createArchive()
                .merge(ShrinkWrap.create(GenericArchive.class).as(ExplodedImporter.class)
                        .importDirectory("src/main/resources").as(GenericArchive.class),
                        "/", Filters.include(".*\\.html$"));
    }

    @Drone
    protected WebDriver browser;

    @ArquillianResource
    protected URL deploymentUrl;

}
