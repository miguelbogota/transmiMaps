package app.components.shared;

import core.animations.PublicSetter;
import org.jdesktop.core.animation.timing.Animator;
import org.jdesktop.core.animation.timing.interpolators.SplineInterpolator;
import org.jdesktop.swing.animation.timing.sources.SwingTimerTimingSource;

import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.text.DefaultCaret;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.util.concurrent.TimeUnit;


/**
 * Creates text Input.
 *
 * @version 1.0
 * @since 25/11/2019
 * @author Miguel Bogota
 */
public class Input extends JTextField {

  public static final int HINT_OPACITY = 0x99000000;
  public static final int LINE_OPACITY = 0x66000000;

  private FloatingText floatingLabel = new FloatingText(this);
  private BottomLine line = new BottomLine(this);
  private String hint = "";
  private Color accentColor = Color.decode("#00bcd4");

  public Input(String text) {
    this();
    setText(text);
  }

  public Input() {
    super();
    setBorder(null);
    setFont(new Font("Segoe UI", Font.PLAIN, 14).deriveFont(16f));
    floatingLabel.setText("");
    setOpaque(false);
    setBackground(new Color(0, 0, 0, 0));

    setCaret(new DefaultCaret() {
      @Override
      protected synchronized void damage(Rectangle r) {
        Input.this.repaint(); //fix caret not being removed completely
      }
    });
    getCaret().setBlinkRate(500);
  }


  public String getLabel() {
    return floatingLabel.getText();
  }

  public void setLabel(String label) {
    floatingLabel.setText(label);
    repaint();
  }

  public String getHint() {
    return hint;
  }

  public void setHint(String hint) {
    this.hint = hint;
    repaint();
  }

  public Color getAccent() {
    return accentColor;
  }

  public void setAccent(Color accentColor) {
    this.accentColor = accentColor;
    floatingLabel.setAccent(accentColor);
  }

  @Override
  public void setForeground(Color fg) {
    super.setForeground(fg);
    if (floatingLabel != null)
      floatingLabel.updateForeground();
  }

  @Override
  public void setText(String s) {
    super.setText(s);
    floatingLabel.update();
    line.update();
  }

  @Override
  protected void processFocusEvent(FocusEvent e) {
    super.processFocusEvent(e);
    floatingLabel.update();
    line.update();
  }

  @Override
  protected void processKeyEvent(KeyEvent e) {
    super.processKeyEvent(e);
    floatingLabel.update();
    line.update();
  }

  @Override
  protected void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

    g2.setColor(getBackground());
    g2.fillRect(0, 0, getWidth(), getHeight());

    g2.translate(0, 9);
    super.paintComponent(g);
    g2.translate(0, -9);

    if ((!getHint().isEmpty() && getText().isEmpty() && getLabel().isEmpty()) || (!getHint().isEmpty() && getText().isEmpty() && !getLabel().isEmpty() && floatingLabel.isFloatingAbove())) {
      g.setFont(new Font("Segoe UI", Font.PLAIN, 14).deriveFont(16f));
      g2.setColor(new Color(getForeground().getRGB() & 0x00FFFFFF | (HINT_OPACITY & 0xFF000000), true));
      FontMetrics metrics = g.getFontMetrics(g.getFont());
      g.drawString(getHint(), 0, metrics.getAscent() + 32);
    }

    floatingLabel.paint(g2);

    g2.setColor(new Color(getForeground().getRGB() & 0x00FFFFFF | (LINE_OPACITY & 0xFF000000), true));
    g2.fillRect(0, getHeight() - 9, getWidth(), 1);

    g2.setColor(accentColor);
    g2.fillRect((int) ((getWidth() - line.getWidth()) / 2), getHeight() - 10, (int) line.getWidth(), 2);
  }

  @Override
  protected void paintBorder(Graphics g) {
    //intentionally left blank
  }

  /**
   * Animated line that shows below the input.
   */
  private static class BottomLine {

    private final SwingTimerTimingSource timer; // Timing for the animation
    private final JComponent target; // Parent component
    private Animator animator; // Animation
    private PublicSetter.Prop<Double> width; // Width save in the property

    /**
     * Construtor.
     *
     * @param parent textfield where is going to be.
     */
    private BottomLine(JComponent parent) {
      this.target = parent;
      this.timer = new SwingTimerTimingSource();
      this.timer.init();
      this.width = PublicSetter.animatableProperty(parent, 0d);
    }

    /**
     * Function update the line depending if it was already aniamted.
     */
    private void update() {
      // Check if it was already animated
      if (animator != null) { animator.stop(); }

      // Otherwise animate
      animator = new Animator.Builder(timer)
        .setDuration(200, TimeUnit.MILLISECONDS)
        .setEndBehavior(Animator.EndBehavior.HOLD)
        .setInterpolator(new SplineInterpolator(0.4, 0, 0.2, 1))
        .addTarget(PublicSetter.getTarget(width, width.getValue(), target.isFocusOwner() ? (double) target.getWidth() + 1 : 0d))
        .build();
      animator.start();
    }

    /**
     * Function returns line width.
     *
     * @return line width.
     */
    private double getWidth() { return this.width.getValue(); }
  }

  /**
   * Label floating above the input.
   */
  private static class FloatingText {

    private final SwingTimerTimingSource timer;
    private final JTextField parent;
    private Animator animator;
    private final PublicSetter.Prop<Double> y;
    private final PublicSetter.Prop<Double> fontSize;
    private final PublicSetter.Prop<Color> color;
    private String text;
    private Color accentColor = Color.decode("#00bcd4");

    /**
     * Constructor.
     *
     * @param parent parent textfield.
     */
    private FloatingText(JTextField parent) {
      this.parent = parent;
      this.timer = new SwingTimerTimingSource();
      this.timer.init();

      this.y = PublicSetter.animatableProperty(parent, 36d);
      this.fontSize = PublicSetter.animatableProperty(parent, 16d);
      this.color = PublicSetter.animatableProperty(parent, new Color(0, 0, 0, 0.26f));

      updateForeground();
    }

    private void updateForeground() {
      this.color.setValue(new Color(parent.getForeground().getRGB() & 0x00FFFFFF | (HINT_OPACITY & 0xFF000000), true));
    }

    private void update() {
      if (animator != null) { animator.stop(); }

      Animator.Builder builder = new Animator.Builder(timer)
        .setDuration(200, TimeUnit.MILLISECONDS)
        .setEndBehavior(Animator.EndBehavior.HOLD)
        .setInterpolator(new SplineInterpolator(0.4, 0, 0.2, 1));

      double targetFontSize = (parent.isFocusOwner() || !parent.getText().isEmpty()) ? 12d : 16d;
      if (fontSize.getValue() != targetFontSize) { builder.addTarget(PublicSetter.getTarget(fontSize, fontSize.getValue(), targetFontSize)); }
      double targetY = parent.isFocusOwner() || !parent.getText().isEmpty() ? 16d : 36d;
      if (Math.abs(targetY - y.getValue()) > 0.1) { builder.addTarget(PublicSetter.getTarget(y, y.getValue(), targetY)); }

      Color targetColor;
      if (parent.isFocusOwner()) { targetColor = accentColor; }
      else { targetColor = new Color(parent.getForeground().getRGB() & 0x00FFFFFF | (HINT_OPACITY & 0xFF000000), true); }

      if (!targetColor.equals(color.getValue())) { builder.addTarget(PublicSetter.getTarget(color, color.getValue(), targetColor)); }
      animator = builder.build();
      animator.start();
    }

    void paint(Graphics2D g) {
      g.setFont(new Font("Segoe UI", Font.PLAIN, 14).deriveFont(fontSize.getValue().floatValue()));
      g.setColor(color.getValue());
      FontMetrics metrics = g.getFontMetrics(g.getFont());
      g.drawString(getText(), 0, metrics.getAscent() + y.getValue().intValue());
    }

    private Color getAccent() { return accentColor; }
    private void setAccent(Color accentColor) { this.accentColor = accentColor; }
    private String getText() { return text; }
    private void setText(String text) { this.text = text; }
    boolean isFloatingAbove() { return y.getValue() < 17d; }

  }

}
