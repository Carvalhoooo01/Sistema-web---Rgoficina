package SCOS.springsecurity.dto;

import SCOS.springsecurity.entities.UserRole;

public record RegisterDTO (String login, String password, UserRole role){
}
