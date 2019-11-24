package controllers;


import views.tools.Window;

import java.awt.Dimension;

/**
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

    // Test
    Window window = new Window(); // Window creation
    window.setSize(new Dimension(800, 600)); // Set size for the window
    window.setting(false, null, true); // Configure the window

  }

}
