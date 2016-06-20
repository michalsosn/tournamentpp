package pl.lodz.p.ftims.tournamentpp.functional;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.GenericArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ExplodedImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public abstract class BaseArquillianTest {

    @Deployment
    public static WebArchive createArchive() {
        return ShrinkWrap.create(WebArchive.class)
                .addPackages(true, "pl.lodz.p.ftims.tournamentpp")
                .merge(ShrinkWrap.create(GenericArchive.class).as(ExplodedImporter.class)
                        .importDirectory("src/main/resources").as(GenericArchive.class),
                        "/", Filters.include(".*\\.properties$"));
    }

}
