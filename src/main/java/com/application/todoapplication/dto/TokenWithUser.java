package com.application.todoapplication.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenWithUser {
    public TokenWithUser(UserGetDto userGetDto, UserTokenState userTokenState) {
        this.userGetDto = userGetDto;
        this.userTokenState = userTokenState;
    }

    private UserGetDto userGetDto;

    private UserTokenState userTokenState;
}
