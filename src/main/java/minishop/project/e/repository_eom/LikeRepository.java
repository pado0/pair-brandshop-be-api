package minishop.project.e.repository_eom;

import minishop.project.e.domain_eom.Item;
import minishop.project.e.domain_eom.Like;
import minishop.project.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like,Long> {

    Long countByItem(Item item);

    Optional<Like> findByMemberAndItem(Member member, Item item);

//    todo: 왜 안되는가
//    @Query(value = "select l from item_like l join l.member m  WHERE l.id = :item_id and m.id = :member_id")
//    Like findByIdAndUserId2(@Param("item_id") Long item_id, @Param("member_id") Long member_id);

}
