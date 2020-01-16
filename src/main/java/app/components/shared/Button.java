package app.components.shared;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

/**
 * Creates button.
 *
 * @version 1.0
 * @since 26/11/2019
 * @author Miguel Bogota
 */
public class Button extends JButton {

  private boolean isMousePressed = false; // Property for when the button is pressed
  private boolean isMouseHover = false; // Property for when the button is hover

  /**
   * Constructor
   */
  public Button() {
    setOpaque(false); // Set button opaque to false
    setCursor(new Cursor(Cursor.HAND_CURSOR)); // Default cursor for the button
    // Add mouse listener to know when the button is pressed, hover and repaint effects
    addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent mouseEvent) {
        isMousePressed = true;
      }
      @Override
      public void mouseReleased(MouseEvent mouseEvent) {
        isMousePressed = false;
        repaint();
      }
      @Override
      public void mouseEntered(MouseEvent e) {
        isMouseHover = true;
        repaint();
      }
      @Override
      public void mouseExited(MouseEvent e) {
        isMouseHover = false;
        repaint();
      }
    });
  }

  @Override
  protected void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

    // Paint enable button
    if (isEnabled()) {
      g2.setColor(getBackground());
      g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 5*2, 5*2));

      g2.setColor(new Color(Color.decode("#8c8c8c").getRed() / 255f, Color.decode("#8c8c8c").getBlue() / 255f, Color.decode("#8c8c8c").getBlue() / 255f, 0.12f));
      if (isMouseHover || isFocusOwner()) {
        g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 5*2, 5*2));
      }
    }

    // Paint disable button
    else {
      Color bg = getBackground();
      g2.setColor(new Color(bg.getRed() / 255f, bg.getGreen() / 255f, bg.getBlue() / 255f, 0.6f));
      g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 5*2, 5*2));
    }

    // Center text
    FontMetrics metrics = g.getFontMetrics(getFont());
    int x = (getWidth() - metrics.stringWidth(getText())) / 2;
    int y = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
    g2.setFont(new Font("Segoe UI", Font.PLAIN, 14).deriveFont(16f));

    // Paint text
    if (isEnabled()) {
      g2.setColor(getForeground());
    }
    else {
      Color fg = getForeground();
      g2.setColor(new Color(fg.getRed() / 255f, fg.getGreen() / 255f, fg.getBlue() / 255f, 0.6f));
    }
    g2.drawString(getText(), x, y);

  }

  @Override
  protected void paintBorder(Graphics g) {
    //intentionally left blank
  }

}
