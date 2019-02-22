package serverTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.northeastern.ccs.im.server.ClientTimer;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class ClientTimerTest {

  private ClientTimer clientTimer;

  @BeforeEach
  public void setup() {
    clientTimer = new ClientTimer();
  }

  @Test
  public void testisBehind() {
    assertFalse(clientTimer.isBehind());
  }
}
