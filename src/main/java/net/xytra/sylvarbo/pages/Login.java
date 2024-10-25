package net.xytra.sylvarbo.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.PasswordField;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.annotations.Inject;

import net.xytra.common.cayenne.persistent.User;
import net.xytra.common.tapestry.session.Session;

public class Login {

    private static final Logger logger = LogManager.getLogger(Login.class);

    @Inject
    private AlertManager alertManager;

    @InjectComponent
    private Form login;

    @InjectComponent("username")
    private TextField usernameField;

    @InjectComponent("password")
    private PasswordField passwordField;

    @Property
    private String username;

    @Property
    private String password;

    private User user;

    @SessionState
    private Session session;

    void onValidateFromLogin()
    {
        user = User.getUserForUsernameAndPassword(username, password);
        System.err.println("user="+user);
        if (user == null) {
            login.recordError("Invalid username or password");
        }
    }

    Object onSuccessFromLogin()
    {
//        logger.info("Login successful!");
        session = new Session(user);

        return PersonList.class;
    }

    void onFailureFromLogin()
    {
        logger.warn("Login error!");
        alertManager.error("Invalid username or password");
    }
}
