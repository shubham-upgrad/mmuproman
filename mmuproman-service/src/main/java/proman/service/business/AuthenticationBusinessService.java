package proman.service.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import proman.service.dao.UserAuthTokenDao;
import proman.service.dao.UserDao;
import proman.service.encryption.PasswordCryptographyProvider;
import proman.service.entity.UserAuthTokenEntity;
import proman.service.entity.UserEntity;
import proman.service.exceptions.AuthenticationFailedException;

import java.time.ZonedDateTime;

@Service
public class AuthenticationBusinessService {
    @Autowired
    UserDao userDao;
    @Autowired
    PasswordCryptographyProvider passwordCryptographyProvider;
    @Autowired
    UserAuthTokenDao userAuthTokenDao;
    @Transactional(propagation = Propagation.REQUIRED)
    public UserAuthTokenEntity authenticate(final String username, final String password) throws AuthenticationFailedException{
        UserEntity user=userDao.getUserByEmail(username);
        if(user==null){ // User not found by Email
            // Code to handle wrong username error
            throw new AuthenticationFailedException("USR-001","Username did not match any record");
        }
        else{ // User found by email
            // Check whether password is matching
                //1. get the password of the user(which is to be checked),i.e, password

                //2. Use the salt value of the existing(or matching) user,i.e, user.getSalt()
                //3. Use the salt value to encrypt the incoming password
           String encrypted=passwordCryptographyProvider.encrypt(password,user.getSalt());

                //4. Compare that encrypted password with the password of existing user
                        // (which is also encrypted)
            if(encrypted.equals(user.getPassword())){ // Check if the password is correct
                //Generate  JWT Token for the given user
                JwtTokenProvider jwtTokenProvider=new JwtTokenProvider(encrypted);
                UserAuthTokenEntity userAuthTokenEntity=new UserAuthTokenEntity();
                userAuthTokenEntity.setUser(user);
                ZonedDateTime now=ZonedDateTime.now();
                ZonedDateTime expiry=now.plusHours(8);
                // Set up a few things
                userAuthTokenEntity.setLoginAt(now);
                userAuthTokenEntity.setExpiresAt(expiry);
                userAuthTokenEntity.setCreatedAt(now);
                userAuthTokenEntity.setCreatedBy("api-backend");
                // Generate Access Token
                String accessToken=jwtTokenProvider.generateToken(user.getUuid(),now,expiry);
                // Persist the userAuthToken generated, in the database
                userAuthTokenEntity.setAccessToken(accessToken);
                userAuthTokenDao.create(userAuthTokenEntity);
                user.setLastLoginAt(now);
                userDao.updateUser(user);
                //return UserAuthTokenEntity so generated
                return userAuthTokenEntity;
            }
            else{
                //Code to handle wrong password error
                throw new AuthenticationFailedException("USR-002","Password did not match any record");
            }


        }

    }


}
