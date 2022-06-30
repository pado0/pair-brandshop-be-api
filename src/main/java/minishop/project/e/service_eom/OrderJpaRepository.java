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

    public List<Order> findAllWithItem() {
        return em.createQuery(
                        "select distinct o from Order o" +
                                " join fetch o.orderItems oi" +
                                " join fetch oi.item i", Order.class)
                .getResultList();
    }
}
