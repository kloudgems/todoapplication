package com.application.todoapplication.controller;

import com.application.todoapplication.dto.TokenWithUser;
import com.application.todoapplication.dto.UserGetDto;
import com.application.todoapplication.dto.UserPostDto;
import com.application.todoapplication.entities.User;
import com.application.todoapplication.dto.UserTokenState;
import com.application.todoapplication.security.TokenHelper;
import com.application.todoapplication.security.auth.JwtAuthenticationRequest;
import com.application.todoapplication.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

@Api(
        tags = {"Authentication"},
        description = " "
)
@RestController
@RequestMapping( value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE )
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    TokenHelper tokenHelper;
    @Autowired
    UserService userService;
    @ApiOperation("Create a new JWT token")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public TokenWithUser createAuthenticationToken(
            @RequestBody JwtAuthenticationRequest authenticationRequest,
            HttpServletResponse response
    ) throws AuthenticationException, IOException {

        // Perform the security
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );

        // Inject into security context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // token creation
        User user = (User)authentication.getPrincipal();
        String jws = tokenHelper.generateToken(user.getUsername(),user.getId());
        int expiresIn = tokenHelper.getExpiredIn();
        // Return the token
        return new TokenWithUser(userService.fromUser(user),new UserTokenState(jws, expiresIn));
    }
    @ApiOperation("Refresh JWT token")
    @RequestMapping(value = "/refresh", method = RequestMethod.POST)
    public ResponseEntity<?> refreshAuthenticationToken(
            HttpServletRequest request,
            HttpServletResponse response,
            Principal principal
    ) {

        String authToken = tokenHelper.getToken( request );


        if (authToken != null && principal != null) {

            // TODO check user password last update
            String refreshedToken = tokenHelper.refreshToken(authToken);
            int expiresIn = tokenHelper.getExpiredIn();

            return ResponseEntity.ok(new UserTokenState(refreshedToken, expiresIn));
        } else {
            UserTokenState userTokenState = new UserTokenState();
            return ResponseEntity.accepted().body(userTokenState);
        }
    }
    @ApiOperation("Register new user")
    @PostMapping(value = "register")
    public TokenWithUser save(
            @RequestBody UserPostDto userRegister,
            final BindingResult bindingResult) {
        UserGetDto userGetDto = userService.register(userRegister);
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userRegister.getUsername(),
                        userRegister.getPassword()
                )
        );

        // Inject into security context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // token creation
        User user = (User)authentication.getPrincipal();
        String jws = tokenHelper.generateToken(user.getUsername(),user.getId());
        int expiresIn = tokenHelper.getExpiredIn();
        // Return the token
        return new TokenWithUser(userService.fromUser(user),new UserTokenState(jws, expiresIn));
    }
}
