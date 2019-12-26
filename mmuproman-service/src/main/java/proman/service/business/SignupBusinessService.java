package proman.service.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import proman.service.dao.UserDao;
import proman.service.encryption.PasswordCryptographyProvider;
import proman.service.entity.UserEntity;

@Service
public class SignupBusinessService {
    @Autowired
    UserDao userDao;
    @Autowired
    PasswordCryptographyProvider passwordCryptographyProvider;
    @Transactional(propagation = Propagation.REQUIRED)
    public UserEntity signup(UserEntity userEntity){
        String passwd=userEntity.getPassword();
        String[] encrypted=passwordCryptographyProvider.encrypt(passwd);
        // encrypted contains : [salt,password]
        userEntity.setPassword(encrypted[1]);
        userEntity.setSalt(encrypted[0]);
        return userDao.createUser(userEntity);
    }

}
