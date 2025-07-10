package com.example.PracticeProject.controller;

import com.example.PracticeProject.dto.MemberForm;
import com.example.PracticeProject.entity.Member;
import com.example.PracticeProject.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
public class MemberController {
    @Autowired
    private MemberRepository memberRepository;

    @GetMapping("/signup")
    public String newMemberForm() {
        return "members/new";
    }

    @PostMapping("/join")
    public String createMember(MemberForm memberForm) {
        log.info(memberForm.toString());
        Member memberEntity = memberForm.toEntity();
        log.info(memberEntity.toString());
        Member saved = memberRepository.save(memberEntity);
        log.info(saved.toString());
        return "redirect:/members/" + saved.getId();
    }

    @GetMapping("/members/{id}")
    public String show(@PathVariable Long id, Model model) {
        log.info("id = " + id);
        Member target = memberRepository.findById(id).orElse(null);
        if(target == null || id != target.getId()) {
            return null;
        }
        model.addAttribute("member", target);
        return "members/show";
    }

    @GetMapping("/members")
    public String index(Model model) {
        Iterable<Member> members = memberRepository.findAll();
        model.addAttribute(members);
        return "members/index";
    }

    @GetMapping("/members/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        Member target = memberRepository.findById(id).orElse(null);
        model.addAttribute("member", target);
        return "members/edit";
    }

    @PostMapping("/members/update")
    public String update(MemberForm memberForm) {
        log.info("업데이트" + memberForm.toString());
        Member memberEntity = memberForm.toEntity(); // 새 데이터
        log.info(memberEntity.toString());
        // 기존 데이터
        Member target = memberRepository.findById(memberEntity.getId()).orElse(null);
        if(target != null) {
            memberRepository.save(memberEntity);
        }
        return "redirect:/members/" + memberEntity.getId();
    }

    @GetMapping("/members/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr) {
        Member target = memberRepository.findById(id).orElse(null);
        if(target != null) {
            memberRepository.delete(target);
            rttr.addFlashAttribute("member","삭제되었습니다.");      
        }
        return "redirect:/members";
    }
}
