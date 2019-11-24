package views.tools;

import javax.swing.JFrame;
import java.awt.Component;
import java.awt.Dimension;

/**
 * This class creates a window for the project
 *
 * @version 1.0
 * @since 24/11/2019
 * @author Miguel Bogota
 */
public class Window extends JFrame {

  private static Dimension SIZE;

  /**
   * Window constructor
   * Set up title to "TransmiMaps" by default.
   * Cet layout to null by default.
   */
  public Window() {
    this.SIZE = new Dimension(0, 0); // Set window size

    // Default window configuration
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setTitle("TransmiMaps");
    this.getContentPane().setLayout(null);
  }

  /**
   * Sets window size
   *
   * @param dimension window dimension.
   */
  @Override
  public void setSize(Dimension dimension) {
    // Save size
    this.SIZE = dimension;

    // Assign size to window
    this.getContentPane().setPreferredSize(this.SIZE);
    this.pack();
  }

  /**
   * Sets window size
   *
   * @param width  width for window.
   * @param height height for window
   */
  @Override
  public void setSize(int width, int height) {
    // Save size
    this.SIZE = new Dimension(width, height);

    // Assign size to window
    this.getContentPane().setPreferredSize(this.SIZE);
    this.pack();
  }

  /**
   * Sets window configuration.
   *
   * @param resizeble If window can be resizable.
   * @param location  Relative component for window.
   * @param visible   If window is visible.
   */
  public void setting(boolean resizeble, Component location, boolean visible) {
    // Window configuration
    this.setResizable(resizeble);
    this.setLocationRelativeTo(location);
    this.setVisible(visible);
  }

}