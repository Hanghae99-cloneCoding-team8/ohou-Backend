package com.ohou.backend.entity;


import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass //멤버 변수가 컬럼이 되도록 한다.
@EntityListeners(AuditingEntityListener.class) //변경 시 자동 기록 ->
public abstract class Timestamped {
    @CreatedDate
    private LocalDateTime createdAt; //글작성 최초 시점

    @LastModifiedDate
    private LocalDateTime modifiedAt; //글작성 최초 시점
}
