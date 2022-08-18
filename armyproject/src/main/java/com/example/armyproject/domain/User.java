package com.example.armyproject.domain;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(generator = "System-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private Long mNum; // 사용자에게 고유하게 부여되는 id

    @Column(nullable = false)
    private int unitCode; // 사용자의 군번

    @Column(nullable = false)
    private String birth; // 사용자의 생일

    @Column(nullable = false)
    private int armyCode; // 사용자의 부대코드

    @Column(name = "rank_code")
    private int rankCode; // 사용자의 계급코드
}
