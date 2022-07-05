package minishop.project.domain.like.repository;

import minishop.project.domain.item.entity.Item;
import minishop.project.domain.like.Like;
import minishop.project.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like,Long> {

    Long countByItem(Item item);

    Optional<Like> findByMemberAndItem(Member member, Item item);

//    todo: 왜 안되는가
//    @Query(value = "select l from item_like l join l.member m  WHERE l.id = :item_id and m.id = :member_id")
//    Like findByIdAndUserId2(@Param("item_id") Long item_id, @Param("member_id") Long member_id);

}
