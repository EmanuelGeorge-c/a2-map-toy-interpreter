package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import interpreter.model.value.IntValue;
import interpreter.model.value.IValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyDictionaryTest {
    private IDictionary<String, IValue> dictionary;

    @BeforeEach
    void setUp() {
        dictionary = new MyDictionary<>();
    }

    @Test
    void putAndLookupWorkCorrectly() throws ADTException {
        dictionary.put("x", new IntValue(10));
        assertTrue(dictionary.isDefined("x"));
        assertEquals(new IntValue(10), dictionary.lookup("x"));
    }

    @Test
    void lookupMissingKeyThrowsException() {
        var ex = assertThrows(ADTException.class, () -> dictionary.lookup("y"));
        assertEquals("key y is not defined in dictionary", ex.getMessage());
    }

    @Test
    void updateExistingKeyWorksCorrectly() throws ADTException {
        dictionary.put("a", new IntValue(5));
        dictionary.update("a", new IntValue(20));
        assertEquals(new IntValue(20), dictionary.lookup("a"));
    }

    @Test
    void updateMissingKeyThrowsException() {
        var ex = assertThrows(ADTException.class, () -> dictionary.update("nonExisting", new IntValue(1)));
        assertEquals("cannot update: key nonExisting is not defined in dictionary", ex.getMessage());
    }
}