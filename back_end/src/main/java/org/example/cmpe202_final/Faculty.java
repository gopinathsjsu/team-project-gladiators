package org.example.cmpe202_final;

public class Faculty extends User {
    public Faculty(int id, String name, String email) {
        super(id, name, email);
    }

    // Additional faculty-specific methods can be added here
    // Getters and setters inherited from User
    // Assuming User class has these methods

    public int getId() {
        return super.getId();
    }

    public void setId(int id) {
        super.setId(id);
    }

    public String getName() {
        return super.getName();
    }

    public void setName(String name) {
        super.setName(name);
    }

    public String getEmail() {
        return super.getEmail();
    }

    public void setEmail(String email) {
        super.setEmail(email);
    }
}
