/*package SCOS.springsecurity.dto;

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
*/

package SCOS.springsecurity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDTO {

    private String token;
    private String type = "Bearer";
    private String id;
    private String email;
    private String name;

    // Construtor com apenas token - útil para casos onde só o token é necessário
    public LoginResponseDTO(String token) {
        this.token = token;
        this.type = "Bearer";
    }

    // Construtor completo sem type (usa o valor padrão "Bearer")
    public LoginResponseDTO(String token, String id, String email, String name) {
        this.token = token;
        this.type = "Bearer";
        this.id = id;
        this.email = email;
        this.name = name;
    }
}
