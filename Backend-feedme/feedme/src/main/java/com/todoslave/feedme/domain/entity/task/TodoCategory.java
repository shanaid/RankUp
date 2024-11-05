package com.todoslave.feedme.domain.entity.task;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.todoslave.feedme.domain.entity.membership.Member;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "todocategory")
public class TodoCategory {

    // 할일 카테고리 ID
    @Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // 회원 ID
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @JsonBackReference
    private Member member;

    // 카테고리명
    @Column(nullable = false, length = 1000)
    private String name;

    //투두와 매핑
    @OneToMany(mappedBy = "todoCategory", cascade = CascadeType.ALL)
    private List<Todo> todos = new ArrayList<>();

    //==연관관계 메서드==//
    public void setMember(Member member) {
        this.member = member;
        member.getTodoCategories().add(this);
    }

}
