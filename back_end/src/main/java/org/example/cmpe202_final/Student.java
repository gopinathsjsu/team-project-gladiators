package org.example.cmpe202_final;

public class Student extends User {
    public Student(int id, String name, String email) {
        super(id, name, email);
    }

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
