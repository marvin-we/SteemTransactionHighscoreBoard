package eu.bittrade.steem.steemstats;

import java.io.File;

import javax.servlet.ServletException;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.servlet.ServletContainer;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class TomcatLauncher {
    private static final Logger LOGGER = LogManager.getLogger(TomcatLauncher.class);

    /**
     * Private constructor to hide the implicit public one.
     */
    private TomcatLauncher() {
    }

    public static void main(String args[]) {

        try {
            Tomcat tomcat = new Tomcat();

            // basedir property will be set by the appassembler script.
            String basedir = System.getProperty("basedir");
            String webappDirectory = new File(basedir + "/webapp").getAbsolutePath();

            tomcat.setPort(8080);

            Context context = tomcat.addWebapp("", webappDirectory);
            Tomcat.addServlet(context, "SteemUserStatistics", new ServletContainer(new ResourceLoader()));
            
            tomcat.start();
            tomcat.getServer().await();
        } catch (ServletException e) {
            LOGGER.error("Could not add webapp.", e);
        } catch (LifecycleException e) {
            LOGGER.error("Could not start tomcat.", e);
        }
    }
}
