package com.retrospective.tool.controllers;

import com.retrospective.tool.models.Member;
import com.retrospective.tool.repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class LoginController {
    @Autowired
    private MemberRepository memberRepository;

    @GetMapping("/login")
    public String viewLogin() {
        return "index";
    }
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("member", new Member());
        return "SignUpForm";
    }


    @PostMapping("/process_register")
    public String processRegister(
            @RequestParam String firstName,
            @RequestParam String surname,
            @RequestParam String email,
            @RequestParam String password
    )
    {
        if (memberRepository.findByEmail(email) == null) {
            Member member = new Member(firstName, surname, email, password);

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(member.getPassword());
            member.setPassword(encodedPassword);
            memberRepository.save(member);
            return "registerSuccess";
        } else{
            return "SignUpForm";
        }
    }


    @GetMapping("/users")
    public String listMembers(Model model) {
        List<Member> listMembers = memberRepository.findAll();
        model.addAttribute("listMembers", listMembers);
        //listMembers is used in members.html as the key for the member list

        return "members";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        return "admin";
    }



}
