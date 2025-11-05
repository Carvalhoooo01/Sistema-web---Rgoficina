package SCOS.springsecurity.dto;

import SCOS.springsecurity.model.UserRole;

public record RegisterDTO (String login, String password, UserRole role){
}
