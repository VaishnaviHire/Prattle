package edu.northeastern.ccs.im;
import org.apache.log4j.*;


/**
 * Logger class that handles logging of all levels of messages.
 *
 * @author Maria Jump and Riya Nadkarni
 * @version 12-20-2018
 */
public class ChatLogger {
  /** Name of the logger file. */
  private static final String LOGNAME = ChatLogger.class.getName();
  /** The logger itself. */
  public static final Logger LOGGER = Logger.getLogger(LOGNAME);

  /**
   * Private constructor. This class cannot be instantiated.
   */
   ChatLogger() {
      throw new IllegalStateException("ChatLogger not instantiable");
  }
}