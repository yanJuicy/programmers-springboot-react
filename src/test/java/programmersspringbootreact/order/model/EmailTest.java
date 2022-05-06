package programmersspringbootreact.order.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailTest {

    @Test
    void testInvalidEmail() {
        assertThrows(IllegalArgumentException.class, () -> {
            var email = new Email("accccc");
        });
    }

    @Test
    void testValidEmail() {
        var email = new Email("hello@gmail.com");
        assertTrue(email.getAddress().equals( "hello@gmail.com"));
    }

    @Test
    void testEqEmail() {
        var email = new Email("hello@gmail.com");
        var email2 = new Email("hello@gmail.com");
        assertEquals(email, email2);
    }
}