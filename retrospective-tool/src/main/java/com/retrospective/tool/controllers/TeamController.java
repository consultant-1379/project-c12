package com.retrospective.tool.controllers;

import com.retrospective.tool.authenticationdetails.MemberDetails;
import com.retrospective.tool.models.Member;
import com.retrospective.tool.models.Team;
import com.retrospective.tool.repositories.MemberRepository;
import com.retrospective.tool.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/team")
public class TeamController {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private MemberRepository memberRepository;

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("members", memberRepository.findAll());
        return "createTeam";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String addNewTeam(@RequestParam String name,
                             @RequestParam(value = "employeeNumbers", required = false) int[] memberIDs,
                             Model model) {
        Set<Member> members = new HashSet<>();

        Team team = new Team(name, members);
        teamRepository.save(team);

        if (members != null) {
            for (int id : memberIDs) {
                team.addMember(memberRepository.findById(id));
            }
        }
        teamRepository.save(team);
        return getAllTeams(model);
    }

    @GetMapping("/all")
    public String getAllTeams(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MemberDetails currentUser = (MemberDetails) auth.getPrincipal();
        Member member = memberRepository.findByEmail(currentUser.getUsername());

        Set<Team> currentUsersTeams = new HashSet<>();
        for (Team team : teamRepository.findAll()) {
            if (team.getMembers().contains(member))
                currentUsersTeams.add(team);
        }
        model.addAttribute("teams", currentUsersTeams);
        return "teams";
    }
}
