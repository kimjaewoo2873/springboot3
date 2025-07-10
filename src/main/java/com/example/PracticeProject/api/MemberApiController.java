package com.example.PracticeProject.api;

import com.example.PracticeProject.dto.MemberForm;
import com.example.PracticeProject.entity.Member;
import com.example.PracticeProject.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class MemberApiController {
    @Autowired
    private MemberService memberService;

    // GET
    @GetMapping("/signup")
    public String newMemberForm() {
        return "members/new";
    }

    @GetMapping("/api/members/{id}")
    public Member show(@PathVariable Long id) {
        return memberService.show(id);
    }

    @GetMapping("/api/members")
    public Iterable<Member> index() {
        return memberService.index();
    }

    // POST
    @PostMapping("/api/join")
    public ResponseEntity<Member> createMember(MemberForm memberForm) {
        Member memberEntity = memberService.create(memberForm);
        return (memberEntity != null) ?
                ResponseEntity.status(HttpStatus.OK).body(memberEntity) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // PATCH
    @PostMapping("api/members/{id}")
    public ResponseEntity<Member> update(@PathVariable Long id, @RequestBody MemberForm memberForm) {
        Member memberEntity = memberService.update(id, memberForm);
        return (memberEntity != null) ?
                ResponseEntity.status(HttpStatus.OK).body(memberEntity) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // DELETE
    @DeleteMapping("api/members/{id}")
    public ResponseEntity<Member> delete(@PathVariable Long id) {
        Member target = memberService.delete(id);
        return (target != null) ?
                ResponseEntity.status(HttpStatus.OK).body(target) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
