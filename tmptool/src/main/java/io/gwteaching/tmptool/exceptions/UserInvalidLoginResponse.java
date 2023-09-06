package io.gwteaching.tmptool.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInvalidLoginResponse {
    private String username;
    private String password;

    public UserInvalidLoginResponse() {
        this.username = "Invalid Username";
        this.password = "Invalid Password";
    }
}
