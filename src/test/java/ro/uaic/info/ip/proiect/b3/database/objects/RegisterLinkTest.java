package ro.uaic.info.ip.proiect.b3.database.objects;

import org.easymock.EasyMockSupport;
import org.junit.Test;
import ro.uaic.info.ip.proiect.b3.database.objects.registerlink.RegisterLink;
import ro.uaic.info.ip.proiect.b3.database.objects.registerlink.exceptions.RegisterLinkException;

import java.sql.SQLException;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class RegisterLinkTest extends EasyMockSupport {

    public static final String email = "gaby@gaby.com";
    public static final String token = "7687687";

    @Test(expected = RegisterLinkException.class)
    public void When_ValidateEmailIsCalledWithDummyEmail_ExceptionShouldBeThrowned() throws SQLException, RegisterLinkException {
        RegisterLink rl = new RegisterLink(email); // should throw exception
    }
}