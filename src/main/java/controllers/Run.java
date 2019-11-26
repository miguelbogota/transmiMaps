package controllers;

import views.tools.Button;
import views.tools.Input;
import views.tools.ScrollPane;
import views.tools.Window;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;

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

    // Test Window
    Window window = new Window(); // Window creation
    window.setSize(new Dimension(800, 600)); // Set size for the window
    window.setting(false, null, true); // Configure the window

    // Test panel for scroll
    JPanel panel = new JPanel();
    panel.setBackground(Color.decode("#ffffff"));
    panel.setPreferredSize(new Dimension(2000, 900));
    panel.setLayout(null);

    // Test input
    Input input = new Input();
    input.setLabel("Label Test");
    input.setHint("Hint Test");
    panel.add(input);
    input.setBounds(40, 20, 150, 68);

    // Test button
    Button button = new Button();
    button.setText("Clic me!");
    panel.add(button);
    button.setBounds(40, 90, 150, 40);

    // Test scroll for the panel
    ScrollPane scroll = new ScrollPane();
    scroll.addDrag(panel);
    scroll.setViewportView(panel);
    window.add(scroll);
    scroll.setBounds(0, 0, 812, 612);

  }

}
