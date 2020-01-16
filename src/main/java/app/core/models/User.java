package app.core.models;

public class User {

  // Properties
  private long id;
  private String firstName;
  private String lastName;
  private String email;
  private String password;

  // Constructor
  public User(String firstName, String lastName, String email, String password) {
    this.id = this.generateId();
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
  }

  // Getter & Setters
  public long generateId() { return (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L; }
  public long getId() { return this.id; }

  public String getFirstName() { return firstName; }
  public void setFirstName(String name) { this.firstName = name; }

  public String getLastName() { return lastName; }
  public void setLastName(String name) { this.lastName = name; }

  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }

  public String getPassword() { return password; }
  public void setPassword(String password) { this.password = password; }
}
