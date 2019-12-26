package proman.service.dao;

import org.springframework.stereotype.Repository;
import proman.service.entity.UserAuthTokenEntity;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository
public class UserAuthTokenDao {

    @PersistenceContext
    private EntityManager entityManager;
    public UserAuthTokenEntity create(final UserAuthTokenEntity newToken){
        entityManager.persist(newToken);
        return newToken;
    }

    public UserAuthTokenEntity getAccessTokenByString(String authorization) {
        try {
            return entityManager.createNamedQuery("userAuthTokenByAccessToken", UserAuthTokenEntity.class)
                    .setParameter("accessToken", authorization).getSingleResult();
        }catch(NoResultException e){
            return null;

        }
    }
}
