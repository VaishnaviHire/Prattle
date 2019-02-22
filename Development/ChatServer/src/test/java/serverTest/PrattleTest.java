package serverTest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.northeastern.ccs.im.server.Prattle;

public class PrattleTest {

  private Thread chatServer;
  private ExecutorService executorService = Executors.newFixedThreadPool(5);

  @BeforeEach
  public void setup() {
    Runnable prattleMain = () -> Prattle.main(new String[1]);
    chatServer = new Thread(prattleMain);
    executorService.submit(chatServer);
  }

  @Test
  void test1() {
    chatServer.start();
  }

  @AfterEach
  void end() {
    chatServer.stop();
  }

}
