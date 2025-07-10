package com.example.PracticeProject.service;

import com.example.PracticeProject.dto.MemberForm;
import com.example.PracticeProject.entity.Member;
import com.example.PracticeProject.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    public Member create(MemberForm memberForm) {
        Member member = memberForm.toEntity();
        if(member.getId() != null) {
            return null;
        }
        return memberRepository.save(member);
    }

    public Member show(Long id) {
        return memberRepository.findById(id).orElse(null);
    }


    public Iterable<Member> index() {
        return memberRepository.findAll();
    }

    public Member update(Long id, MemberForm dto) {
        Member member = dto.toEntity();
        Member target = memberRepository.findById(id).orElse(null);
        if(target == null || id != member.getId()) {
            return null;
        }
        target.patch(member);
        Member saved = memberRepository.save(target);
        return saved;
    }


    public Member delete(Long id) {
        Member target = memberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("삭제할 id를 찾지 못함"));
        memberRepository.delete(target);
        return target;

    }
}
