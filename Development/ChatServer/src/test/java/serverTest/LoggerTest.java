package serverTest;

import edu.northeastern.ccs.im.ChatLogger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LoggerTest {
    @Test()
    public void initializeLogger(){
        Assertions.assertThrows(IllegalStateException.class, () ->  new ChatLogger());
    }
}