package com.example.PracticeProject.repository;

import com.example.PracticeProject.entity.Member;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<Member, Long> {
}
