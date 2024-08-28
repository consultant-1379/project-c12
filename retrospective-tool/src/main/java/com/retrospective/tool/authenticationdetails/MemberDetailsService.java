package com.retrospective.tool.authenticationdetails;

import com.retrospective.tool.models.Member;
import com.retrospective.tool.repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class MemberDetailsService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepo;

    @Override
    public MemberDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member user = memberRepo.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new MemberDetails(user);
    }

}