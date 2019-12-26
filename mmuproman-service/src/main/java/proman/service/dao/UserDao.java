package proman.service.dao;

import org.springframework.stereotype.Repository;
import proman.service.entity.UserEntity;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository
public class UserDao {
    @PersistenceContext
    private EntityManager entityManager;
    public UserEntity createUser(UserEntity userEntity){
        entityManager.persist(userEntity);
        return userEntity;
    }

    public UserEntity getUserById(String id) {
        try {
            return entityManager.createNamedQuery("userByUuid", UserEntity.class).setParameter("uuid", id).getSingleResult();
        }catch(NoResultException e){
            return null;
        }
    }

    public UserEntity getUserByEmail(String username) {
        try {
            return entityManager.createNamedQuery("userByEmail", UserEntity.class).setParameter("email", username).getSingleResult();
        }catch(NoResultException e){
            return null;
        }
        }

    public void updateUser(UserEntity updatedUser) {
        entityManager.merge(updatedUser);
    }
}
