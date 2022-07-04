package minishop.project.e.service_eom;

import lombok.RequiredArgsConstructor;
import minishop.project.e.domain_eom.Item;
import minishop.project.e.domain_eom.Like;
import minishop.project.e.repository_eom.ItemRepository;
import minishop.project.e.repository_eom.LikeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService{

    private final LikeRepository likeRepository;
    private final ItemRepository itemRepository;

    @Override
    public void upLike(Long itemId){
        //:todo optional 처리 해줘야함
        Item item = itemRepository.findById(itemId).get();
        Like like = new Like();
        like.setItem(item);
        likeRepository.save(like);
    }

    @Override
    public void downLike(Long itemId){
//        Item item = itemRepository.findById(itemId).get();
//        itemRepository.finByIdandUser();
//        itemRepository.delete(item);
    }
}
