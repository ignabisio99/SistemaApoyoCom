package dto;

import lombok.Getter;

@Getter
public class LoginRequest {

    private String email;
    private String password;
    private String token;


    @Override
    public String toString() {
        return "LoginRequest{" +
                "username='" + email + '\'' +
                '}';
    }
}