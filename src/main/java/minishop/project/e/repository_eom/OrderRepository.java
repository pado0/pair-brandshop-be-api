package minishop.project.e.repository_eom;

import minishop.project.e.domain_eom.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {



}
