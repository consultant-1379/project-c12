package com.retrospective.tool.controllers;

import com.retrospective.tool.models.Sprint;
import com.retrospective.tool.repositories.SprintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/sprint")
public class SprintController {
    @Autowired
    private SprintRepository sprintRepository;

    @GetMapping("/all")
    public String getAllSprints(Model model) {
        model.addAttribute("sprints", sprintRepository.findAll());
        return "sprints";
    }

    @GetMapping("/create")
    public String create() {
        return "createSprint";
    }

    @PostMapping("/add")
    public @ResponseBody String addNewSprint(@RequestParam String name,
                                             @RequestParam String goals) {
        Sprint sprint = new Sprint(name, goals);
        sprintRepository.save(sprint);
        return "/all";
    }
}
