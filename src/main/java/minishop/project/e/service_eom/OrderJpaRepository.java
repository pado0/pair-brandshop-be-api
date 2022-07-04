package minishop.project.e.service_eom;

import minishop.project.e.domain_eom.Order;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
@Repository
public class OrderJpaRepository {

    private final EntityManager em;


    public OrderJpaRepository(EntityManager em) {
        this.em = em;
    }

    public List<Order> findAllWithItemV1() {
        return em.createQuery(
                        "select distinct o from Order o" +
                                " join fetch o.orderItems oi" +
                                " join fetch oi.item i", Order.class)
                .getResultList();
    }


    public List<Order> findAllWithItemV2(int offset, int limit) {
        return em.createQuery(
                        "select o from Order o",
                                Order.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
/*
        return em.createQuery(
                        "select o from Order o "+
                        "join fetch o.ember",
                        Order.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
*/
    }
}
