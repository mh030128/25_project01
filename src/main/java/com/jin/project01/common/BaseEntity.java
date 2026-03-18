package com.jin.project01.common;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

import java.time.LocalDateTime;

@MappedSuperclass
/*
* @MappedSuperclass : BaseEntity를 상속한 엔터티들이 BaseEntity의 필드들을 칼럼으로 인식.
* */
@Getter
public class BaseEntity {

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
