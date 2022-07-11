package minishop.project.domain.order.repository;

import minishop.project.domain.member.entity.Member;
import minishop.project.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order,Long> {

    List<Order> findByMember(Member member);

}
