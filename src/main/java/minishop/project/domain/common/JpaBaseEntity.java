package minishop.project.domain.common;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

//생성 시간 수정 시간
@EntityListeners(AuditingEntityListener.class)//ProjectApplication 에서 사용
@MappedSuperclass //JPA Entity가,, 해당(JpaBaseEntity) 클래스를 컬럼으로 인식
@Getter
public class JpaBaseEntity {

    @CreatedDate
    @Column(updatable = false) //수정 불가능
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime updatedDate;

    /*순수 JPA 시에
    @PrePersist // 저장 전
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        createdDate = now;
        updatedDate = now;
    }
    @PreUpdate //업데이트시
    public void preUpdate() {
        updatedDate = LocalDateTime.now();
    }*/
}
