package views.tools;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

/**
 * Creates Scroll Pane.
 *
 * @version 1.0
 * @since 24/11/2019
 * @author Miguel Bogota
 */
public class ScrollPane extends JScrollPane {

  private final int SCROLL_BAR_ALPHA_ROLLOVER = 100;
  private final int SCROLL_BAR_ALPHA = 50;
  private final int THUMB_SIZE = 8;
  private final int SB_SIZE = 10;
  private final Color THUMB_COLOR = Color.BLACK;

  /**
   * Empty constructor
   */
  public ScrollPane() { this(null, VERTICAL_SCROLLBAR_AS_NEEDED, HORIZONTAL_SCROLLBAR_AS_NEEDED); }

  /**
   * Constructor with panel
   *
   * @param view Panel that will be inside the scrollpane.
   */
  public ScrollPane(Component view) { this(view, VERTICAL_SCROLLBAR_AS_NEEDED, HORIZONTAL_SCROLLBAR_AS_NEEDED); }

  /**
   * Constructor with scrollbars policies
   *
   * @param vsbPolicy Vertical policy for scrollbar.
   * @param hsbPolicy Horizontal policy for scrollbar.
   */
  public ScrollPane(int vsbPolicy, int hsbPolicy) { this(null, vsbPolicy, hsbPolicy); }

  /**
   * Constructor with panel scrollbar policies
   *
   * @param view Panel that will be inside the scrollpane.
   * @param vsbPolicy Vertical policy for scrollbar.
   * @param hsbPolicy Horizontal policy for scrollbar.
   */
  public ScrollPane(Component view, int vsbPolicy, int hsbPolicy) {

    setOpaque(false);
    setBorder(null);
    getHorizontalScrollBar().setUnitIncrement(16);
    getVerticalScrollBar().setUnitIncrement(16);

    setVerticalScrollBarPolicy(vsbPolicy);
    setHorizontalScrollBarPolicy(hsbPolicy);

    JScrollBar verticalScrollBar = getVerticalScrollBar();
    verticalScrollBar.setOpaque(false);
    verticalScrollBar.setUI(new ScrollBar(this));

    JScrollBar horizontalScrollBar = getHorizontalScrollBar();
    horizontalScrollBar.setOpaque(false);
    horizontalScrollBar.setUI(new ScrollBar(this));

    setLayout(new ScrollPaneLayout() {
      @Override
      public void layoutContainer(Container parent) {
        Rectangle availR = ((JScrollPane) parent).getBounds();
        availR.x = availR.y = 0;

        // viewport
        Insets insets = parent.getInsets();
        availR.x = insets.left;
        availR.y = insets.top;
        availR.width -= insets.left + insets.right;
        availR.height -= insets.top + insets.bottom;
        if (viewport != null) {
          viewport.setBounds(availR);
        }

        boolean vsbNeeded = isVerticalScrollBarfNecessary();
        boolean hsbNeeded = isHorizontalScrollBarNecessary();

        // vertical scroll bar
        if (vsbNeeded) {
          Rectangle vsbR = new Rectangle();
          vsbR.width = SB_SIZE;
          vsbR.height = availR.height - (hsbNeeded ? vsbR.width : 0);
          vsbR.x = availR.x + availR.width - vsbR.width;
          vsbR.y = availR.y;
          if (vsb != null) vsb.setBounds(vsbR);
        }

        // horizontal scroll bar
        if (hsbNeeded) {
          Rectangle hsbR = new Rectangle();
          hsbR.height = SB_SIZE;
          hsbR.width = availR.width - (vsbNeeded ? hsbR.height : 0);
          hsbR.x = availR.x;
          hsbR.y = availR.y + availR.height - hsbR.height;
          if (hsb != null) hsb.setBounds(hsbR);
        }
      }
    });

    setComponentZOrder(getVerticalScrollBar(), 0);
    setComponentZOrder(getHorizontalScrollBar(), 1);
    setComponentZOrder(getViewport(), 2);

    viewport.setView(view);

  }

  // Add drag
  public void addDrag(Component view) {
    DragListener listener = new DragListener(view);
    view.addMouseListener(listener);
    view.addMouseMotionListener(listener);
  }

  // Know if the vertical scroll is necesary
  private boolean isVerticalScrollBarfNecessary() {
    Rectangle viewRect = viewport.getViewRect();
    Dimension viewSize = viewport.getViewSize();
    return (viewSize.getHeight() > viewRect.getHeight() && (getVerticalScrollBarPolicy() == VERTICAL_SCROLLBAR_AS_NEEDED || getVerticalScrollBarPolicy() == VERTICAL_SCROLLBAR_ALWAYS));
  }

  // Know if the horizontal scroll is necesary
  private boolean isHorizontalScrollBarNecessary() {
    Rectangle viewRect = viewport.getViewRect();
    Dimension viewSize = viewport.getViewSize();
    return (viewSize.getWidth() > viewRect.getWidth() && (getHorizontalScrollBarPolicy() == HORIZONTAL_SCROLLBAR_AS_NEEDED || getHorizontalScrollBarPolicy() == HORIZONTAL_SCROLLBAR_ALWAYS));
  }

  /**
   * Private class that creates a custom scroll bar.
   */
  private class ScrollBar extends BasicScrollBarUI {

    private JScrollPane sp;

    public ScrollBar(ScrollPane sp) {
      this.sp = sp;
    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
      return new InvisibleScrollBarButton();
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
      return new InvisibleScrollBarButton();
    }

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
      int alpha = isThumbRollover() ? SCROLL_BAR_ALPHA_ROLLOVER : SCROLL_BAR_ALPHA;
      int orientation = scrollbar.getOrientation();
      int x = thumbBounds.x;
      int y = thumbBounds.y;

      int width = orientation == JScrollBar.VERTICAL ? THUMB_SIZE : thumbBounds.width;
      width = Math.max(width, THUMB_SIZE);

      int height = orientation == JScrollBar.VERTICAL ? thumbBounds.height : THUMB_SIZE;
      height = Math.max(height, THUMB_SIZE);

      Graphics2D graphics2D = (Graphics2D) g.create();
      graphics2D.setColor(new Color(THUMB_COLOR.getRed(), THUMB_COLOR.getGreen(), THUMB_COLOR.getBlue(), alpha));
      graphics2D.fillRect(x, y, width, height);
      graphics2D.dispose();
    }

    @Override
    protected void setThumbBounds(int x, int y, int width, int height) {
      super.setThumbBounds(x, y, width, height);
      sp.repaint();
    }

    private class InvisibleScrollBarButton extends JButton {

      private static final long serialVersionUID = 1552427919226628689L;

      private InvisibleScrollBarButton() {
        setOpaque(false);
        setFocusable(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setBorder(BorderFactory.createEmptyBorder());
      }
    }
  }

  /**
   * Private class that creates and drag event for the panel
   */
  private class DragListener implements MouseListener, MouseMotionListener {

    public static final int DRAGGABLE_HORIZONTAL_SCROLL_BAR = 1;
    public static final int DRAGGABLE_VERTICAL_SCROLL_BAR = 2;

    // Scroll intensity
    private int scrollingIntensity = 5;
    // Scroll end momentoum
    private double damping = 0.4;
    // Scroll animation time
    private int animationSpeed = 40;

    private javax.swing.Timer animationTimer = null;
    private long lastDragTime = 0;
    private Point lastDragPoint = null;

    private double pixelsPerMSX;
    private double pixelsPerMSY;

    private int scrollBarMask = DRAGGABLE_HORIZONTAL_SCROLL_BAR | DRAGGABLE_VERTICAL_SCROLL_BAR;
    private final Component draggableComponent;
    private JScrollPane scroller = null;
    private Cursor defaultCursor;
    private java.util.List<Point2D.Double> dragSpeeds = new ArrayList<Point2D.Double>();

    // Constructor
    public DragListener(Component c) {
      draggableComponent = c;
      defaultCursor = draggableComponent.getCursor();
      draggableComponent.addPropertyChangeListener(new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent arg0) {
          setScroller();
          defaultCursor = draggableComponent.getCursor();
        }
      });
      setScroller();
    }

    private void setScroller() {
      Component c = getParentScroller(draggableComponent);

      if (c != null) scroller = (JScrollPane) c;
      else scroller = null;
    }

    public void setDraggableElements(int mask) { scrollBarMask = mask; }

    public void setScrollingIntensity(int intensity) { scrollingIntensity = intensity; }

    public void setAnimationTiming(int timing) { animationSpeed = timing; }

    public void setDamping(double damping) { this.damping = damping; }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }

    @Override
    public void mousePressed(MouseEvent e) {

      if (animationTimer != null && animationTimer.isRunning()) animationTimer.stop();

      draggableComponent.setCursor(new Cursor(Cursor.HAND_CURSOR));
      dragSpeeds.clear();
      lastDragPoint = e.getPoint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

      draggableComponent.setCursor(defaultCursor);

      if (scroller == null) return;

      long durationSinceLastDrag = System.currentTimeMillis() - lastDragTime;

      if (durationSinceLastDrag > 20) return;

      pixelsPerMSX = 0;
      pixelsPerMSY = 0;

      int j = 0;
      for (int i = dragSpeeds.size() - 1; i >= 0 && i > dragSpeeds.size() - 6; i--, j++) {
        pixelsPerMSX += dragSpeeds.get(i).x;
        pixelsPerMSY += dragSpeeds.get(i).y;
      }

      pixelsPerMSX /= -(double) j;
      pixelsPerMSY /= -(double) j;

      if (Math.abs(pixelsPerMSX) > 0 || Math.abs(pixelsPerMSY) > 0) {
        animationTimer = new javax.swing.Timer(animationSpeed, new ScrollAnimator());
        animationTimer.start();
      }
    }

    @Override
    public void mouseClicked(MouseEvent e) { }

    @Override
    public void mouseDragged(MouseEvent e) {

      if (scroller == null) return;

      Point p = e.getPoint();
      int diffx = p.x - lastDragPoint.x;
      int diffy = p.y - lastDragPoint.y;
      lastDragPoint = e.getPoint();

      if ((scrollBarMask & DRAGGABLE_HORIZONTAL_SCROLL_BAR) != 0) getHorizontalScrollBar().setValue(getHorizontalScrollBar().getValue() - diffx);

      lastDragPoint.x = lastDragPoint.x - diffx;

      if ((scrollBarMask & DRAGGABLE_VERTICAL_SCROLL_BAR) != 0) getVerticalScrollBar().setValue(getVerticalScrollBar().getValue() - diffy);

      lastDragPoint.y = lastDragPoint.y - diffy;

      dragSpeeds.add(new Point2D.Double((e.getPoint().x - lastDragPoint.x), (e.getPoint().y - lastDragPoint.y)));

      lastDragTime = System.currentTimeMillis();
    }

    @Override
    public void mouseMoved(MouseEvent e) { }

    // Clase interna con animaciones
    private class ScrollAnimator implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent e) {
        pixelsPerMSX -= pixelsPerMSX * damping;
        pixelsPerMSY -= pixelsPerMSY * damping;

        if (Math.abs(pixelsPerMSX) < 0.01 && Math.abs(pixelsPerMSY) < 0.01) {
          animationTimer.stop();
          return;
        }

        int nValX = getHorizontalScrollBar().getValue() + (int) (pixelsPerMSX * scrollingIntensity);
        int nValY = getVerticalScrollBar().getValue() + (int) (pixelsPerMSY * scrollingIntensity);

        if (nValX <= 0) nValX = 0;
        else if (nValX >= getHorizontalScrollBar().getMaximum()) nValX = getHorizontalScrollBar().getMaximum();
        if (nValY <= 0) nValY = 0;
        else if (nValY >= getVerticalScrollBar().getMaximum()) nValY = getVerticalScrollBar().getMaximum();

        if ((nValX == 0 || nValX == getHorizontalScrollBar().getMaximum()) && Math.abs(pixelsPerMSY) < 1) {
          animationTimer.stop();
          return;
        }

        if ((nValY == 0 || nValY == getVerticalScrollBar().getMaximum()) && Math.abs(pixelsPerMSX) < 1) {
          animationTimer.stop();
          return;
        }

        if ((scrollBarMask & DRAGGABLE_HORIZONTAL_SCROLL_BAR) != 0) getHorizontalScrollBar().setValue(nValX);
        if ((scrollBarMask & DRAGGABLE_VERTICAL_SCROLL_BAR) != 0) getVerticalScrollBar().setValue(nValY);
      }
    }

    private JScrollBar getHorizontalScrollBar() { return scroller.getHorizontalScrollBar(); }

    private JScrollBar getVerticalScrollBar() { return scroller.getVerticalScrollBar(); }

    private Component getParentScroller(Component c) {
      Container parent = c.getParent();
      if (parent != null && parent instanceof Component) {
        Component parentC = (Component) parent;

        if (parentC instanceof JScrollPane) return parentC;
        else return getParentScroller(parentC);
      }
      return null;
    }
  }

}
