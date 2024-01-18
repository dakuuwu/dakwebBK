package space.dakuuwu.dakwebBK.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import space.dakuuwu.dakwebBK.services.TokenService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.OPTIONS, RequestMethod.POST})
public class AuthController {
    private final TokenService tokenService;

    public AuthController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    //Calls the token generation service.
    @PostMapping("/token")

    public String token(@RequestHeader(value = "Authorization") String basicAuth, Authentication authentication) {
        return tokenService.generateToken(authentication);
    }
}
