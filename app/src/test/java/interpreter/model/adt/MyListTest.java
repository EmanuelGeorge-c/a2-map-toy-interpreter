package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyListTest {
    private IList<String> list;

    @BeforeEach
    void setUp() {
        list = new MyList<>();
    }

    @Test
    void addAndGetWorkCorrectly() throws ADTException {
        assertEquals(0, list.size());
        list.add("hello");
        list.add("world");

        assertEquals(2, list.size());
        assertEquals("hello", list.get(0));
        assertEquals("world", list.get(1));
    }

    @Test
    void getOutOfBoundsThrowsException() {
        list.add("test");
        var ex = assertThrows(ADTException.class, () -> list.get(5));
        assertEquals("index 5 out of bounds", ex.getMessage());
    }
}