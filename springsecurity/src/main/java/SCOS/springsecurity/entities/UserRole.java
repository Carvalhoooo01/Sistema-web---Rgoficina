package SCOS.springsecurity.entities;
import jakarta.persistence.*;

@Entity
@Table(name = "tb_roles")
public enum UserRole {
ADMIN("ADMIN"),
USER("USER");

     private String role;
    UserRole(String role) {
    this.role = role;
}

public String getRole() {
        return role;
}
}
