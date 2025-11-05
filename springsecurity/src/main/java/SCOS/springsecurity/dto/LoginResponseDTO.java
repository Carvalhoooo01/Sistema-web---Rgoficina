package SCOS.springsecurity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDTO {

private String token;
private String type = "Bearer";
private String id;
private String email;
private String name;

public LoginResponseDTO(String token) {
    this.token = token;
    this.id = id;
    this.email = email;
    this.name = name;
}


}
