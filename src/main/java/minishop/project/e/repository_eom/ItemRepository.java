package minishop.project.e.repository_eom;

import minishop.project.e.domain_eom.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item,Long> {

}
