import org.example.JsonVerifier;
import org.junit.jupiter.api.Test;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JsonVerifierTest {

    @Test
    void givenValidJsonReturnFalse(){
        URL resourceUrl = getClass().getClassLoader().getResource("ValidJson01.json");
        assert resourceUrl != null;

        assertFalse(JsonVerifier.verifyJson(resourceUrl.getPath()));
    }

    @Test
    void givenValidJsonWithMultipleStatementsReturnTrue(){
        URL resourceUrl = getClass().getClassLoader().getResource("ValidJsonWithMultipleStatements.json");
        assert resourceUrl != null;

        assertTrue(JsonVerifier.verifyJson(resourceUrl.getPath()));
    }

    @Test
    void givenValidJsonWithNestedConditionReturnTrue(){
        URL resourceUrl = getClass().getClassLoader().getResource("ValidJsonWithNestedCondition.json");
        assert resourceUrl != null;

        assertTrue(JsonVerifier.verifyJson(resourceUrl.getPath()));
    }


    @Test
    void givenValidJsonWithNoSidReturnFalse(){
        URL resourceUrl = getClass().getClassLoader().getResource("ValidJsonWithNoSid.json");
        assert resourceUrl != null;

        assertFalse(JsonVerifier.verifyJson(resourceUrl.getPath()));
    }

    @Test
    void givenValidJsonWithoutStatementReturnTrue(){
        URL resourceUrl = getClass().getClassLoader().getResource("ValidJsonWithoutStatement.json");
        assert resourceUrl != null;

        assertTrue(JsonVerifier.verifyJson(resourceUrl.getPath()));
    }

    @Test
    void givenInvalidJsonWithIncorrectEffectReturnTrue(){
        URL resourceUrl = getClass().getClassLoader().getResource("InvalidJsonWithIncorrectEffect.json");
        assert resourceUrl != null;

        assertTrue(JsonVerifier.verifyJson(resourceUrl.getPath()));
    }

    @Test
    void givenInvalidJsonWithIncorrectKeyReturnTrue(){
        URL resourceUrl = getClass().getClassLoader().getResource("InvalidJsonWithIncorrectKey.json");
        assert resourceUrl != null;

        assertTrue(JsonVerifier.verifyJson(resourceUrl.getPath()));
    }

    @Test
    void givenInvalidJsonWithNoPolicyNameReturnTrue(){
        URL resourceUrl = getClass().getClassLoader().getResource("InvalidJsonWithNoPolicyName.json");
        assert resourceUrl != null;

        assertTrue(JsonVerifier.verifyJson(resourceUrl.getPath()));
    }

    @Test
    void givenInvalidJsonWithNoVersionReturnTrue(){
        URL resourceUrl = getClass().getClassLoader().getResource("InvalidJsonWithNoVersion.json");
        assert resourceUrl != null;

        assertTrue(JsonVerifier.verifyJson(resourceUrl.getPath()));
    }
}
