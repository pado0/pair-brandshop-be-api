package minishop.project.domain.like.service;

import lombok.RequiredArgsConstructor;
import minishop.project.domain.item.entity.Item;
import minishop.project.domain.like.Like;
import minishop.project.domain.item.repository.ItemRepository;
import minishop.project.domain.like.repository.LikeRepository;
import minishop.project.domain.member.entity.Member;
import minishop.project.exception.CUserNotFoundException;
import minishop.project.domain.member.repository.MemberRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService{

    private final LikeRepository likeRepository;
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;

    @Override
    public void pushLike(Long itemId){

        //회원 정보 확인
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getName();

        //memeber찾고
        Member member = memberRepository.findByLoginEmail(id).orElseThrow(CUserNotFoundException::new);

        //:todo optional 처리 해줘야함
        //Item 찾고
        Item item = itemRepository.findById(itemId).get();

        Optional<Like> findLike = likeRepository.findByMemberAndItem(member, item);
        if(findLike.isPresent()){
            //다운시킴
            likeRepository.delete(findLike.get());
        }else{
            //업 시킴
            Like like = new Like();
            like.setItem(item);
            like.setMember(member);
            likeRepository.save(like);
        }
    }
}
