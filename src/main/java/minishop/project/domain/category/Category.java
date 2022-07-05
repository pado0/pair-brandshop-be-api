package minishop.project.domain.category;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    // 카테고리는 하나의 부모를, 하나의 부모는 여러개의 카테고리를 가질 수 있음
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id") // category 엔티티 테이블에서 부모 카테고리에 대한 컬럼명을 parent_id로 지정. joinColumn만 지정해도 해당 클래스 pk를 찾아 매핑
    private Category parent;

    @OneToMany(mappedBy = "parent")
    @JsonIgnore
    private List<Category> child;

    public void addChildCategory(Category child) {
        this.child.add(child);
        child.setParent(this);
    }
}
