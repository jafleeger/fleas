
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;


public class CalculatorTest {
    Calculator cal;

    @BeforeEach
    void setUp() {
        cal = new Calculator();
    }

    @Test
    @DisplayName("Basic multiplication properties should hold correctly")
    void testMultiply() {
        assertEquals(220, cal.multiply(5,44),
                    "Basic multiplication should work");
        assertEquals(0,cal.multiply(0,20),
                    "Multiplying by zero should result in zero");
        assertEquals(0,cal.multiply(20,0),
                    "Multiplying by zero should result in zero");
    }

    @Test
    @DisplayName("Basic addition properties should hold correctly")
    void testAdd() {
        assertEquals(30, cal.add(10,20),
                    "Basic addition should work");
        assertEquals(5,cal.add(10,-5),
                    "Adding a negative should result in subtraction");
    }

    @Test
    @DisplayName("Basic subtraction properties should hold correctly")
    void testSubtract() {
        assertEquals(30, cal.subtract(50,20),
                    "Basic subtraction should work");
        assertEquals(15,cal.subtract(10,-5),
                    "Subtracting a negative should result in addition");
    }
}
