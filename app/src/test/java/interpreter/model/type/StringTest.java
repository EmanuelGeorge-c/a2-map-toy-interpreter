package interpreter.model.type;

import interpreter.model.value.StringValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringTest {
    private StringType stringType;
    private StringValue stringValue;

    @BeforeEach
    void setUp() {
        stringType = new StringType();
        stringValue = new StringValue("hello");
    }

    @Test
    void testStringTypeDefaultValue() {
        assertEquals(new StringValue(""), stringType.defaultValue());
    }

    @Test
    void testStringValueType() {
        assertEquals(stringType, stringValue.getType());
    }

    @Test
    void testStringValueToString() {
        assertEquals("\"hello\"", stringValue.toString());
    }

    @Test
    void testStringValueEquality() {
        StringValue sameValue = new StringValue("hello");
        StringValue differentValue = new StringValue("world");

        assertEquals(stringValue, sameValue);
        assertNotEquals(stringValue, differentValue);
    }
}