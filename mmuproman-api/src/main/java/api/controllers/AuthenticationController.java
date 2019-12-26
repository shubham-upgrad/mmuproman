package api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import proman.service.business.AuthenticationBusinessService;
import proman.service.entity.UserAuthTokenEntity;
import proman.service.entity.UserEntity;
import proman.service.exceptions.AuthenticationFailedException;
import promanapp.api.model.AuthorizedUserResponse;

import java.util.Base64;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    AuthenticationBusinessService authenticationBusinessService;
    @RequestMapping(method= RequestMethod.POST,path="/login",consumes=MediaType.APPLICATION_JSON_UTF8_VALUE,produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AuthorizedUserResponse> login(@RequestHeader("authorization")final String authorization) throws AuthenticationFailedException {
        //1. Get username and password from authorization string
        // Authorization string example : Basic skjdghljshfglkjhlkjhkldjfghruknmfb
            //a. Strip Basic from the string
        String b64=authorization.split(" ")[1];
        System.out.println(b64);
        byte[] decoded = Base64.getDecoder().decode(b64);
        // decoded = "username:password"
        String decodedString=new String(decoded);
        String[] credentials=decodedString.split(":");
        UserAuthTokenEntity userAuthTokenEntity=authenticationBusinessService.authenticate(credentials[0],credentials[1]);
        UserEntity user=userAuthTokenEntity.getUser();

        HttpHeaders header=new HttpHeaders();
        header.add("access-token",userAuthTokenEntity.getAccessToken());

        return new ResponseEntity<AuthorizedUserResponse>(header,HttpStatus.OK);
    }



}
