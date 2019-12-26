package api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import proman.service.business.SignupBusinessService;
import promanapp.api.model.SignupUserRequest;
import promanapp.api.model.SignupUserResponse;
import proman.service.entity.UserEntity;

import java.time.ZonedDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/signupcontroller")
public class SignupController {
    @Autowired
    SignupBusinessService signupBusinessService;
    @RequestMapping(path="/signup",method= RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SignupUserResponse> signup(SignupUserRequest signupUserRequest){
        UserEntity userEntity=new UserEntity();
        userEntity.setUuid(UUID.randomUUID().toString());
        userEntity.setFirstName(signupUserRequest.getFirstName());
        userEntity.setLastName(signupUserRequest.getLastName());
        userEntity.setEmail(signupUserRequest.getEmailAddress());
        userEntity.setPassword(signupUserRequest.getPassword());
        userEntity.setMobilePhone(signupUserRequest.getMobileNumber());
        userEntity.setStatus(4); // Registered
        userEntity.setCreatedAt(ZonedDateTime.now());
        userEntity.setCreatedBy("api-backend");
        UserEntity createdUser = signupBusinessService.signup(userEntity);
        SignupUserResponse response=new SignupUserResponse().id(createdUser.getUuid())
                .status("REGISSTERED");
        return new ResponseEntity<SignupUserResponse>(response,HttpStatus.OK);
    }
}
