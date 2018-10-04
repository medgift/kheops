package online.kheops.auth_server;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class KheopsPrincipalTest {

    private final static long principalDBID = 123456789L;
    private final static String principalName = "123456789";

    static private KheopsPrincipalInterface kheopsPrincipal;



    @Test
    void getDBID() {
        assertEquals(principalDBID, 123456789L);
    }

    @Test
    void getName() {
        assertEquals(principalName, "123456789");
    }

    
}