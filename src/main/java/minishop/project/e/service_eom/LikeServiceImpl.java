package minishop.project.e.service_eom;

import lombok.RequiredArgsConstructor;
import minishop.project.e.domain_eom.Item;
import minishop.project.e.domain_eom.Like;
import minishop.project.e.repository_eom.ItemRepository;
import minishop.project.e.repository_eom.LikeRepository;
import minishop.project.entity.Member;
import minishop.project.exception.CUserNotFoundException;
import minishop.project.repository.MemberRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService{

    private final LikeRepository likeRepository;
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;

    @Override
    public void upLike(Long itemId){

        //회원 정보 확인
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getName();
        Member member = memberRepository.findByLoginEmail(id).orElseThrow(CUserNotFoundException::new);

        //:todo optional 처리 해줘야함
        Item item = itemRepository.findById(itemId).get();
        Like like = new Like();
        like.setItem(item);
        like.setMember(member);
        likeRepository.save(like);
    }

    @Override
    public void downLike(Long itemId){
        //회원 정보 확인
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getName();
        Member member = memberRepository.findByLoginEmail(id).orElseThrow(CUserNotFoundException::new);

        Item item = itemRepository.findById(itemId).get();


        Like findLike = likeRepository.findByMemberAndItem(member, item).get();
        likeRepository.delete(findLike);

    }
}
