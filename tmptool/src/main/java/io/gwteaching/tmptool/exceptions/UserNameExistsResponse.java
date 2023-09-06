package io.gwteaching.tmptool.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserNameExistsResponse {
    private String responseMsg;
}
