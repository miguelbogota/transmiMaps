import app.App;

import java.awt.EventQueue;

/**
 * Run
 *
 * @version 1.0
 * @since 24/11/2019
 * @author Miguel Bogota
 */
public class Run {

  /**
   * Main for the app
   *
   * @param args
   */
  public static void main(String[] args) {
    // Start App
    EventQueue.invokeLater(new Runnable() {
     @Override
     public void run() { new App(); }
    });
  }
}