package minishop.project.domain.order.repository;

import minishop.project.domain.order.entity.Order;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
@Repository
public class OrderJpaRepository {

    private final EntityManager em;


    public OrderJpaRepository(EntityManager em) {
        this.em = em;
    }

    // todo : 제일 중요한거 다시공부하기 (준승님 코드) join fetch를 언제 사용하는지, 페이징이 될때와 안될때 차이
    // to one 인 건 패치조인을 사용한다. to many는 패치조인을 사용하면 안된다 -> 일일히 초기화 해야함. 이렇게 하면 페이징 가능.
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
    }

}
