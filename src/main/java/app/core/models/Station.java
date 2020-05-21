package app.core.models;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.Objects;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import app.components.shared.StandardText;

/**
 * Class is a station in the db.
 *
 * @version 2.0
 * @since 21/05/2020
 * @author Miguel Bogota
 */
public class Station {

  // Vertical draw
  public enum OrientationY {VERTICAL, HORIZONTAL};
  // Horizontal draw
  public enum OrientationX {LEFT, RIGHT, UP, DOWN};

  // F(n) = G(n) + H(n)
  // G(n) Es el costo del camino desde el primer nodo
  // H(n) Es el heuristic que determina el costo mas barato para llegar al final

  // Coordenates
  private int x, y;
  // Previous station
  private Station previous;
  // Neighbors stations
  private LinkedList<Station> neighbors;
  // Final cost
  private int f; // G + H
  // Cost from final to started station
  private int g;
  // Distance estimated from one to a nother point
  private int h;

  // Db identifier
  private final int id;
  // Station name
  private final String name;
  // Display name
  private final String nameShow;
  // Vertiacal orientation
  private OrientationY orientacionY;
  // Horizontal orientation
  private OrientationX orientacionX;
  // Text coordinates
  private final int xText, yText;
  // Text size
  private final int wText, hText;
  // Know if is a portal
  private final boolean portal;
  // Station color
  private final Color color;

  // Contructor stacion
  public Station(int id, String name, int x, int y, Color color, OrientationY orientacionY, OrientationX orientacionX, int xText, int yText, int wText, int hText) {
    this.x = x;
    this.y = y;
    this.previous = null;
    this.neighbors = new LinkedList<>();
    this.f = 0;
    this.g = 0;
    this.h = 0;

    this.id = id;
    this.name = name;
    this.nameShow = new StandardText().getString(name, "_");
    this.orientacionY = orientacionY;
    this.orientacionX = orientacionX;
    this.xText = xText;
    this.yText = yText;
    this.wText = wText;
    this.hText = hText;
    this.portal = false;
    this.color = color;
  }

  // Constructor portal
  public Station(int id, String name, int x, int y, Color color, int xText, int yText, int wText, int hText) {
    this.x = x;
    this.y = y;
    this.previous = null;
    this.neighbors = new LinkedList<>();
    this.f = 0;
    this.g = 0;
    this.h = 0;

    this.id = id;
    this.name = name;
    this.nameShow = new StandardText().getString(name, "_");
    this.xText = xText;
    this.yText = yText;
    this.wText = wText;
    this.hText = hText;
    this.portal = true;
    this.color = color;
  }

  // Get x
  public int getX() {
    return x;
  }

  // Get y
  public int getY() {
    return y;
  }

  // Add neighbors
  public void addNeighbor(Station neighbor) {
    neighbors.addLast(neighbor);
  }

  // Deletes neighbors
  public void removeNeighbor(Station neighbor) {
    neighbors.remove(neighbor);
  }

  // Get neighbors
  public LinkedList<Station> getNeighbors() {
    return neighbors;
  }

  // Get previous station
  public Station getPrevious() {
    return previous;
  }

  // Asign previous station
  public void setPrevious(Station previous) {
    this.previous = previous;
  }

  // Get F
  public int getF() {
    return f;
  }

  // Set F
  public void setF() {
    f = g + h;
  }

  // Get G
  public int getG() {
    return g;
  }

  // Set G
  public void setG(int g) {
    this.g = g;
  }

  // Get H
  public int getH() {
    return h;
  }

  // Set H - is the distance between the first and last station
  public void setH(Station last) {
    this.h = (int) Math.abs(Math.abs(last.getX() - getX()) + Math.abs(last.getY() - getY()));
  }



  // Get station id
  public int getId() {
    return id;
  }

  // Get station name
  public String getName() {
    return name;
  }

  // Get station display name
  public String getNameShow() {
    return nameShow;
  }

  // Get station color
  public Color getColor() {
    return color;
  }

  // Get if the station is a portal
  public boolean isPortal() {
    return portal;
  }

  // Get text from the station
  public int getxText() {
    return xText;
  }

  // Get text position of the station
  public int getyText() {
    return yText;
  }

  // Get text width
  public int getwText() {
    return wText;
  }

  // Get text height
  public int gethText() {
    return hText;
  }

  // Get vertical orientation
  public OrientationY getOrientacionY() {
    return orientacionY;
  }

  // Get horizontal orientation
  public OrientationX getOrientacionX() {
    return orientacionX;
  }

  // Draw station
  public void drawStation(Graphics2D g2) {
    // Get x and y
    int x = this.x;
    int y = this.y;

    g2.setColor(color); // asign color
    g2.setStroke(new java.awt.BasicStroke(5));
    // If station is right and horizontal
    if ((orientacionY == OrientationY.HORIZONTAL) && (orientacionX == OrientationX.RIGHT)) g2.drawLine(x, y, x + 7, y);
      // Station is left and horizontal
    else if ((orientacionY == OrientationY.HORIZONTAL) && (orientacionX == OrientationX.LEFT)) g2.drawLine(x, y, x - 7, y);
      // Station is up and vertical
    else if ((orientacionY == OrientationY.VERTICAL) && (orientacionX == OrientationX.UP)) g2.drawLine(x, y, x, y - 7);
      // Station is down and vertical
    else if ((orientacionY == OrientationY.VERTICAL) && (orientacionX == OrientationX.DOWN)) g2.drawLine(x, y, x, y + 7);
      // Portal
    else if (portal) { g2.fillOval(x, y, 15, 15); g2.setColor(Color.WHITE); g2.fillOval(x + 4, y + 4, 7, 7); }
    // None
    else System.out.println("Error de dibujado...");
  }

  // Add name to the the draw
  public JLabel drawText(JPanel map, boolean background) {
    JLabel label = new JLabel(); // Create Jpanel
    if (background) label.setOpaque(true); // -------- Tests // Draws the background for testing
    label.setVerticalAlignment(SwingConstants.CENTER); // Centers vertically text
    label.setHorizontalAlignment(SwingConstants.CENTER); // Centers horizontally the text
    label.setFont(new Font("Segoe UI SemiBold", 0, 11)); // Asign font size
    label.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Set cursor to hand
    label.setText(new StandardText().getHTML(name, "_")); // Add text
    label.setToolTipText(nameShow); // Add Tooltip

    map.add(label); // Add label to the map
    label.setBounds(xText, yText, wText, hText); // asign coordinates and size

    return label;
  }

  @Override
  public String toString() {
    return String.format("Station{ id=%d, name=%s, x=%d, y=%s, color=#%02x%02x%02x, portal=%b, xText=%d, yText=%s, wText=%d, hText=%s, orientacionY=%s, orientacionX=%s, f=%d, g=%d, h=%d }",id, name, x, y, color.getRed(), color.getGreen(), color.getBlue(), portal, xText, yText, wText, hText, orientacionY.toString(), orientacionX.toString(), f, g, h);
  }
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;

    final Station other = (Station) obj;

    if (this.x != other.x) return false;
    if (this.y != other.y) return false;
    if (!Objects.equals(this.previous, other.previous)) return false;
    if (!Objects.equals(this.neighbors, other.neighbors)) return false;
    if (this.f != other.f) return false;
    if (this.g != other.g) return false;
    if (this.h != other.h) return false;

    if (this.id != other.id) return false;
    if (!Objects.equals(this.name, other.name)) return false;
    if (!Objects.equals(this.nameShow, other.nameShow)) return false;
    if (this.orientacionY != other.orientacionY) return false;
    if (this.orientacionX != other.orientacionX) return false;
    if (this.xText != other.xText) return false;
    if (this.yText != other.yText) return false;
    if (this.wText != other.wText) return false;
    if (this.hText != other.hText) return false;
    if (this.portal != other.portal) return false;
    if (!Objects.equals(this.color, other.color)) return false;

    return true;
  }
  @Override
  public int hashCode() {
    int hash = 3;
    hash = 13 * hash + this.x;
    hash = 13 * hash + this.y;
    hash = 13 * hash + Objects.hashCode(this.previous);
    hash = 13 * hash + Objects.hashCode(this.neighbors);
    hash = 13 * hash + this.f;
    hash = 13 * hash + this.g;
    hash = 13 * hash + this.h;

    hash = 13 * hash + this.id;
    hash = 13 * hash + Objects.hashCode(this.name);
    hash = 13 * hash + Objects.hashCode(this.nameShow);
    hash = 13 * hash + Objects.hashCode(this.orientacionY);
    hash = 13 * hash + Objects.hashCode(this.orientacionX);
    hash = 13 * hash + this.xText;
    hash = 13 * hash + this.yText;
    hash = 13 * hash + this.wText;
    hash = 13 * hash + this.hText;
    hash = 13 * hash + (this.portal ? 1 : 0);
    hash = 13 * hash + Objects.hashCode(this.color);

    return hash;
  }
}