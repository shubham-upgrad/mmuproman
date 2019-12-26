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
import proman.service.exceptions.ResourceNotFoundException;
import proman.service.exceptions.UnauthorizedUserException;

import java.time.ZonedDateTime;
import java.util.UUID;

@Service
public class UserAdminBusinessService {
    @Autowired
    UserDao userDao;
    @Autowired
    UserAuthTokenDao userAuthTokenDao;
    @Autowired
    PasswordCryptographyProvider passwordCryptographyProvider;
    public UserEntity getUser(String id) throws ResourceNotFoundException {
        UserEntity user=userDao.getUserById(id);
        if(user==null){
            throw new ResourceNotFoundException("USR-001","User with given ID doesn't existd");
        }
        return user;
    }
    @Transactional(propagation = Propagation.REQUIRED)
    public UserEntity createUser(UserEntity userEntity,final String authorization) throws AuthenticationFailedException, UnauthorizedUserException {
      //1. Check whether the given user is able to perform this operation(authorization)
        boolean check=this.authorize(authorization);
        if(check){
            //2. Modify the user entity and persist it in the database
            userEntity.setUuid(UUID.randomUUID().toString());
            String password="Proman@123";
            String[] encrypt = passwordCryptographyProvider.encrypt(password);

            userEntity.setPassword(encrypt[1]);
            userEntity.setSalt(encrypt[0]);
            userEntity.setCreatedBy("Administrator");
            userEntity.setStatus(1);
            userEntity.setUuid(UUID.randomUUID().toString());
            ZonedDateTime now = ZonedDateTime.now();
            userEntity.setCreatedAt(now);
            return userDao.createUser(userEntity);
        }
        else{
            throw new UnauthorizedUserException("USR-090","Unauthorized User");
        }

    }

    private boolean authorize(String authorization) throws AuthenticationFailedException, UnauthorizedUserException {
        UserAuthTokenEntity uAT=userAuthTokenDao.getAccessTokenByString(authorization);
        if(uAT==null){
            throw new AuthenticationFailedException("USR-003","You are not logged in");
        }
        else{
            UserEntity user=uAT.getUser();
            if(user.getRole().getUuid()==101){
                return true;
            }
            else{
                return false;
            }
        }
    }
}
