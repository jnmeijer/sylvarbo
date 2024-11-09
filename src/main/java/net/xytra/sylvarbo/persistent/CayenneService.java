package net.xytra.sylvarbo.persistent;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.configuration.Constants;
import org.apache.cayenne.configuration.server.ServerModule;
import org.apache.cayenne.configuration.server.ServerRuntime;
import org.apache.cayenne.configuration.server.ServerRuntimeBuilder;
import org.apache.cayenne.di.Binder;
import org.apache.cayenne.di.Module;
import org.apache.cayenne.query.SQLExec;

public class CayenneService {
    private CayenneService() {
        System.err.println("--- CayenneService being created");
    }

    private static CayenneService instance = null;
    private static Module dbConnModule = null;

    public static CayenneService getInstance() {
        if (instance == null) {
            instance = createInstance();
        }
        return instance;
    }

    private static CayenneService createInstance() {
        CayenneService cs = new CayenneService();
        cs.init();

        return cs;
    }

    public static void setup(String dbDriver, String dbUrl, String dbUsername, String dbPassword) {
        dbConnModule = new Module() {
            @Override
            public void configure(Binder binder) {
                ServerModule.contributeProperties(binder)
                    .put(Constants.JDBC_DRIVER_PROPERTY, dbDriver)
                    .put(Constants.JDBC_URL_PROPERTY, dbUrl)
                    .put(Constants.JDBC_USERNAME_PROPERTY, dbUsername)
                    .put(Constants.JDBC_PASSWORD_PROPERTY, dbPassword);
            }
        };
    }

    private ObjectContext sharedContext;
    private ServerRuntime cayenneRuntime;

    //@Autowired
    //private Environment environment;

    @PostConstruct
    void init() {
        ServerRuntimeBuilder builder = ServerRuntime.builder()
            .addConfig("cayenne-sylvarbo.xml");

        if (dbConnModule != null) {
            builder.addModule(dbConnModule);
        }

        cayenneRuntime = builder.build();

        sharedContext = cayenneRuntime.newContext();

        Runtime.getRuntime().addShutdownHook(new Thread( new Runnable() {
                @Override
                public void run() {
                        if (cayenneRuntime != null) {
                                cayenneRuntime.shutdown();
                        }
                }
        }));
        System.err.println("--- CayenneService init() completed");
        //    LOGGER.info("Cayenne setup done");
        //    LOGGER.info(this);
    }

    public ObjectContext sharedContext() {
        System.err.println("sharedContext="+sharedContext);
        return sharedContext;
    }

    public ObjectContext newObjectContext() {
        System.err.println("-- newObjectContext()!");
        ObjectContext context = cayenneRuntime.newContext();
        SQLExec.query("SET REFERENTIAL_INTEGRITY FALSE").execute(context);
        //SQLExec.query("SET SCHEMA PUBLIC").execute(context);
        return context;
    }

    public ObjectContext newChildContext(ObjectContext context) {
        System.err.println("-- newChildContext()!");
        return cayenneRuntime.newContext(context);
    }

    @PreDestroy
    public void shutdown() {
        System.err.println("--- CayenneService shutdown() called");
        if (cayenneRuntime != null)
            cayenneRuntime.shutdown();
    }
}
