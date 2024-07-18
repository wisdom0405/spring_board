package com.beyond.board.author.repository;

import com.beyond.board.author.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author,Long> {
    // SpringDataJpa 네이밍 룰 : findBy컬럼명의 규칙으로 자동으로 where 조건문을 사용한 쿼리 생성
    // 그 외 : findByNameAndEmail, findByNameOrEmail
    // findByAgeBetween(int start, int end) : start ~ end 사이의 age 찾기
    // findByAgeLessThan(int age), findByAgeGreaterThan(int age)
    // findByAgeIsNull, findByAgeIsNotNull
    // 예시 : List<Author> findByNameIsNull();
    // List<Author> findAllOrderByEmail(String email);
    // findAlllOrderByEmail();

    Optional<Author> findByEmail(String email);

}
