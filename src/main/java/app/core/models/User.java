package app.core.models;

public class User {

  // Properties
  private long id;
  private String name;
  private String email;
  private String password;

  // Constructor
  public User(String name, String email, String password) {
    this.id = this.generateId();
    this.name = name;
    this.email = email;
    this.password = password;
  }

  // Getter & Setters
  public long generateId() { return (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L; }
  public long getId() { return this.id; }

  public String getName() { return name; }
  public void setName(String name) { this.name = name; }

  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }

  public String getPassword() { return password; }
  public void setPassword(String password) { this.password = password; }
}
