package minishop.project.e.repository_eom;

import minishop.project.e.domain_eom.Item;
import minishop.project.e.domain_eom.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like,Long> {

    Long countByItem(Item item);
}
