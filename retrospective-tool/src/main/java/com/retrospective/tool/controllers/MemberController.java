package com.retrospective.tool.controllers;

import com.retrospective.tool.models.Member;
import com.retrospective.tool.repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberRepository memberRepository;

    @PostMapping(value="/add",consumes={"application/json","application/xml"},
            produces={"application/json","application/xml"})
    public ResponseEntity<Member> addNewMember(
            @RequestParam String firstName,
            @RequestParam String surname,
            @RequestParam String email,
            @RequestParam String password
    ) {
        if (memberRepository.findByEmail(email) != null) {
            Member member = new Member(firstName, surname, email, password);
            memberRepository.save(member);
            return ResponseEntity.ok().body(member);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value="/all",consumes={"application/json","application/xml"},
            produces={"application/json","application/xml"})
    public ResponseEntity<Collection<Member>> getAllMembers() {
        Collection<Member> members = memberRepository.findAll();
        return ResponseEntity.ok().body(members);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable int id) {
        if (memberRepository.findById(id) != null) {
            Member member = memberRepository.getById(id);
            return ResponseEntity.ok().body(member);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity removeMember(@PathVariable int id) {
        if (memberRepository.findById(id) == null) {
            return ResponseEntity.notFound().build();
        } else {
            System.out.println("Member Deleted with Id " + id);
            memberRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
    }





}
