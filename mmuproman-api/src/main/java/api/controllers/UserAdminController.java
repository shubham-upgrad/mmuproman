package api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proman.service.business.SignupBusinessService;
import proman.service.business.UserAdminBusinessService;
import proman.service.entity.UserEntity;
import proman.service.exceptions.AuthenticationFailedException;
import proman.service.exceptions.ResourceNotFoundException;
import proman.service.exceptions.UnauthorizedUserException;
import proman.service.type.UserStatus;
import promanapp.api.model.*;

import java.util.UUID;

@RestController
@RequestMapping("/")
public class UserAdminController {
    @Autowired
    UserAdminBusinessService userAdminBusinessService;
    @RequestMapping(path="/users/{id}",method= RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserDetailsResponse> signup(@PathVariable final String id) throws ResourceNotFoundException {

        UserEntity user=userAdminBusinessService.getUser(id);
        UserDetailsResponse response=new UserDetailsResponse().firstName(user.getFirstName())
                .emailAddress(user.getEmail()).lastName(user.getLastName()).status(UserStatusType.valueOf(UserStatus.getEnum(user.getStatus()).name()))
                .mobileNumber(user.getMobilePhone()).id(user.getUuid());
        return new ResponseEntity<UserDetailsResponse>(response,HttpStatus.OK);

    }
    @RequestMapping(method= RequestMethod.POST,path="users/",consumes=MediaType.APPLICATION_JSON_UTF8_VALUE,produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody final CreateUserRequest createUserRequest, @RequestHeader("authorization")final String authorization) throws AuthenticationFailedException, UnauthorizedUserException {
       UserEntity userEntity=new UserEntity();
       userEntity.setFirstName(createUserRequest.getFirstName());
       userEntity.setLastName(createUserRequest.getLastName());
       userEntity.setEmail(createUserRequest.getEmailAddress());
       userEntity.setMobilePhone(createUserRequest.getMobileNumber());
       UserEntity createdUser = userAdminBusinessService.createUser(userEntity,authorization);
       CreateUserResponse response=new CreateUserResponse()
               .status(UserStatusType.valueOf(UserStatus.getEnum(createdUser.getStatus()).name()))
               .id(createdUser.getUuid());
       return new ResponseEntity<CreateUserResponse>(response,HttpStatus.CREATED);


    }


}
