package app.core.services;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Service for the database
 *
 * @version 1.0
 * @since 16/01/2020
 * @author Miguel Bogota
 */
public class DBService {

  // Connection attributes
  private Connection Connection;

  // DB connection data
  private final String user = "root";
  private final String pass = "";
  private final String host = "localhost";
  private final String DB = "transmidb";

  // Constructor
  public DBService() {
    Connection = null;
  }

  // Function to connect to the DB
  public boolean connect() {
    try {
      String URL = "jdbc:mysql://" + host + "/" + DB;
      Connection = DriverManager.getConnection(URL, user, pass);
      return true; // Devolver que si se hizo conexion
    } catch (Exception exc) {
      exc.printStackTrace(); // Mostrar error
      return false; // Devolver que la conexion no fue hecha
    }
  }

}
