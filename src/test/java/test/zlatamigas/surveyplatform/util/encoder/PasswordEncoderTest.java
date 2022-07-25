package test.zlatamigas.surveyplatform.util.encoder;

import epam.zlatamigas.surveyplatform.util.encoder.PasswordEncoder;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.security.NoSuchAlgorithmException;

import static org.testng.Assert.*;

public class PasswordEncoderTest {


    @DataProvider(name = "dataProvider")
    public Object[][] createDataPassword() {
        return new Object[][]{
                {"", 40},
                {"someValue1209234Here", 40}
        };
    }
    
    @Test (dataProvider = "dataProvider")
    public void testEncode(String strToEncode, int expectedLength) throws NoSuchAlgorithmException {
        PasswordEncoder encoder = new PasswordEncoder();
        String encodedStr = encoder.encode(strToEncode);

        assertEquals(encodedStr.length(), expectedLength);
    }
}