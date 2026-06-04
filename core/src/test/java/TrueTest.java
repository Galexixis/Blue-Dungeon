import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TrueTest {

    @Test
    /**
     * Ce test réussit toujours
     */
    void True(){
        int x = 1;
        assertEquals(1, x);
    }
}