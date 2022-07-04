package minishop.project.e.repository_eom;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class LikeJpaRepository {

    private final EntityManager em;
    public LikeJpaRepository(EntityManager em) {
        this.em = em;
    }



}
