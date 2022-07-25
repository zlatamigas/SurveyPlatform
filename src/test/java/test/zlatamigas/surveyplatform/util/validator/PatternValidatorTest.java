package test.zlatamigas.surveyplatform.util.validator;

import epam.zlatamigas.surveyplatform.util.validator.PatternValidator;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class PatternValidatorTest {

    @DataProvider(name = "emailProvider")
    public Object[][] createDataEmail() {
        return new Object[][]{
                {"abc-d@mail.com",true},
                {"abc.def@mail.com",true},
                {"abc@mail.com",true},
                {"abc_def@mail.com",true},
                {"abc.def@mail.cc", true},
                {"abc.def@mail-archive.com", true},
                {"abc.def@mail.org", true},
                {"abc.def@mail.com", true},
                {"abc-@mail.com", false},
                {"abc..def@mail.com", false},
                {".abc@mail.com", false},
                {"abc#def@mail.com",false},
                {"abc.def@mail.c", false},
                {"abc.def@mail#archive.com", false},
                {"abc.def@mail", false},
                {"abc.def@mail..com", false},
                {"12345678901234567890123456789012345@gamil.com", true},
                {"123456789012345678901234567890123456@gamil.com", false},
                {"",false},
                {null, false}
        };
    }

    @DataProvider(name = "passwordProvider")
    public Object[][] createDataPassword() {
        return new Object[][]{
                {"12341234", true},
                {"12345678901234567890", true},
                {"ue6718230", true},
                {"_ewfsd12+-", true},
                {"123", false},
                {"123456789012345678901", false},
                {"invalid email", false},
                {"!\"#$%&'()*+,-./:;", true},
                {"<=>?@[\\]^_`{|}~", true},
                {" 1234567 ", false}
        };
    }

    @DataProvider(name = "themeNameProvider")
    public Object[][] createDataThemeName() {
        return new Object[][]{
                {"ёйцукенгшщзхъфывапролджэячсмитьбюЁЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮйqwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM", true},
                {"Моря и океаны", true},
                {"Theme contains two hundred symbols yes: 1234567890 !\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ qwertyuiopasdfghjklzxcvbnm йцукенгшщзхъёфывапролджэячсмитьбю.Expected correct work.Ожидаем корректное распознавание.", true},
                {"Theme contains two hundred and one symbols (invalid string for tests):  1234567890 !\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ qwertyuiopasdfghjklzxcvbnm йцукенгшщзхъёфывапролджэячсмитьбю. Expected   invalid result. ", false},
                {"Number №", false},
                {"two", false},
                {"", false},
                {null, false}
        };
    }

    @DataProvider(name = "surveyNameProvider")
    public Object[][] createDataSurveyName() {
        return new Object[][]{
                {"ёйцукенгшщзхъфывапролджэячсмитьбюЁЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮйqwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM", true},
                {"Моря и океаны", true},
                {"Theme contains two hundred symbols yes: 1234567890 !\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ qwertyuiopasdfghjklzxcvbnm йцукенгшщзхъёфывапролджэячсмитьбю.Expected correct work.Ожидаем корректное распознавание.", true},
                {"Theme contains two hundred symbols yes: 1234567890 !\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ qwertyuiopasdfghjklzxcvbnm йцукенгшщзхъёфывапролджэячсмитьбю.Expected correct work.Ожидаем корректное распознавание. Theme contains two hundred symbols yes: 1234567890 !\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ qwertyuiopasdfghjklzxcvbnm йцукенгшщзхъёфывапролджэячсмитьбю.Expected correct work.Ожидаем корректное распознавание.  ", true},
                {"Number №", false},
                {"two", false},
                {"", false},
                {null, false}
        };
    }

    @DataProvider(name = "questionFormulationProvider")
    public Object[][] createDataQuestionFormulation() {
        return new Object[][]{
                {"ёйцукенгшщзхъфывапролджэячсмитьбюЁЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮйqwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM", true},
                {"Моря и океаны", true},
                {"Theme contains two hundred symbols yes: 1234567890 !\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ qwertyuiopasdfghjklzxcvbnm йцукенгшщзхъёфывапролджэячсмитьбю.Expected correct work.Ожидаем корректное распознавание.", true},
                {"Theme contains two hundred symbols yes: 1234567890 !\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ qwertyuiopasdfghjklzxcvbnm йцукенгшщзхъёфывапролджэячсмитьбю.Expected correct work.Ожидаем корректное распознавание. Theme contains two hundred symbols yes: 1234567890 !\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ qwertyuiopasdfghjklzxcvbnm йцукенгшщзхъёфывапролджэячсмитьбю.Expected correct work.Ожидаем корректное распознавание.  ", true},
                {"Number №", false},
                {"two", false},
                {"", false},
                {null, false}
        };
    }

    @DataProvider(name = "unlimitedTextProvider")
    public Object[][] createDataUnlimitedText() {
        return new Object[][]{
                {"ёйцукенгшщзхъфывапролджэячсмитьбюЁЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮйqwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM", true},
                {"Моря и океаны", true},
                {"Theme contains two hundred symbols yes: 1234567890 !\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ qwertyuiopasdfghjklzxcvbnm йцукенгшщзхъёфывапролджэячсмитьбю.Expected correct work.Ожидаем корректное распознавание.", true},
                {"Theme contains two hundred symbols yes: 1234567890 !\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ qwertyuiopasdfghjklzxcvbnm йцукенгшщзхъёфывапролджэячсмитьбю.Expected correct work.Ожидаем корректное распознавание. Theme contains two hundred symbols yes: 1234567890 !\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ qwertyuiopasdfghjklzxcvbnm йцукенгшщзхъёфывапролджэячсмитьбю.Expected correct work.Ожидаем корректное распознавание.  ", true},
                {"Number №", false},
                {"two", true},
                {"", true},
                {null, false}
        };
    }

    private PatternValidator validator;

    @BeforeClass
    public void setUp() {
        validator = PatternValidator.getInstance();
    }

    @Test (dataProvider = "emailProvider")
    public void testValidEmail(String text, boolean expected) {
        boolean actual = validator.validEmail(text);
        assertEquals(actual, expected);
    }

    @Test (dataProvider = "passwordProvider")
    public void testValidPassword(String text, boolean expected) {
        boolean actual = validator.validPassword(text);
        assertEquals(actual, expected);
    }

    @Test (dataProvider = "themeNameProvider")
    public void testValidThemeName(String text, boolean expected) {
        boolean actual = validator.validThemeName(text);
        assertEquals(actual, expected);
    }

    @Test (dataProvider = "surveyNameProvider")
    public void testValidSurveyName(String text, boolean expected) {
        boolean actual = validator.validSurveyName(text);
        assertEquals(actual, expected);
    }

    @Test (dataProvider = "questionFormulationProvider")
    public void testValidQuestionFormulation(String text, boolean expected) {
        boolean actual = validator.validQuestionFormulation(text);
        assertEquals(actual, expected);
    }

    @Test (dataProvider = "unlimitedTextProvider")
    public void testValidUnlimitedText(String text, boolean expected) {
        boolean actual = validator.validUnlimitedText(text);
        assertEquals(actual, expected);
    }
}