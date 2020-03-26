package site.yl.template;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;


public class LoggerTest extends BaseApplicationTest{
  private static final Logger LOGGER = LogManager.getLogger();

  @Test
  public void test_log(){
    for (int i = 0; i < 10000; i++) {
      LOGGER.debug("log test...");
      LOGGER.info("log test...");
      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

}
