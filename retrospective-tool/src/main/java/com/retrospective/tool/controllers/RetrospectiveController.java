package com.retrospective.tool.controllers;

import com.retrospective.tool.models.*;
import com.retrospective.tool.repositories.MemberRepository;
import com.retrospective.tool.repositories.ItemRepository;
import com.retrospective.tool.repositories.TeamRepository;
import com.retrospective.tool.repositories.RetrospectiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/retrospectiveTool")
public class RetrospectiveController {
    @Autowired
    private RetrospectiveRepository retrospectiveRepository;
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TeamRepository teamRepository;


    @GetMapping("/")
    public String createRetrospective(Model model){
        model.addAttribute("retrospective", new Retrospective());
        return "Retrospective/CreateRetrospective";
    }

    @GetMapping("/all")
    public String  getALL(Model model) {
        List<Retrospective> list=retrospectiveRepository.findAll();
        model.addAttribute("RetroList", list);

        return "Retrospective/listRetro";
    }

    @RequestMapping("/view")
    public String view(@RequestParam ("id") Integer id, Model model){
        Optional<Team> teamop = teamRepository.findById(id);
        Team team=null;
        if(teamop.isPresent()){
            team=teamop.get();
        }else{
            return "noTeamFound";
        }
        List<Retrospective> list = new ArrayList<>();
        for(Retrospective retro : retrospectiveRepository.findAll()){
            if(retro.getTeam() == team){
                list.add(retro);
            }
        }
        model.addAttribute("RetroList", list);
        return "Retrospective/listRetro";
    }


    @PostMapping("/createRetrospective")
    public String createRetrospective(Model model,@PathParam(value="teamName") String teamName,@PathParam(value="locked") String locked) {

        Team team=new Team();
        if(teamRepository.findByName(teamName)!=null){
            team=teamRepository.findByName(teamName);
        }
        else{
            return "Retrospective/noTeamFound";
        }

        Retrospective retrospective=new Retrospective(team);
        if(locked.equals("true")){
            retrospective.setLocked(true);
        }else{
            retrospective.setLocked(false);
        }

        retrospectiveRepository.save(retrospective);
        return "Retrospective/retroCreateSuccess";
    }

    @PostMapping("/deleteAll")
    public String deleteAll(Model model){
        retrospectiveRepository.deleteAll();
        List<Retrospective> list=retrospectiveRepository.findAll();
        model.addAttribute("RetroList", list);
        return "/Retrospective/listRetro";
    }

    @PostMapping("/deleteRetro")
    public String deleteById(@PathParam(value="id") int id,Model model){
        Retrospective retroToDelete=retrospectiveRepository.findById(id);
        if(retroToDelete!=null){
            retrospectiveRepository.deleteById(id);
            List<Retrospective> list=retrospectiveRepository.findAll();
            model.addAttribute("RetroList", list);
            return "/Retrospective/listRetro";
        }
        return "/Retrospective/noRetroFound";


    }

    @PostMapping("/updateRetro")
    public String updateById(Model model, @RequestParam(value="id") int id,@PathParam(value="locked") String locked){
        Retrospective retrospective = retrospectiveRepository.findById(id);
        if(retrospective != null){
            if (locked.equals("true")){
                retrospectiveRepository.updateById(id,true);

            }
            else{
                retrospectiveRepository.updateById(id,false);
            }
            List<Retrospective> list=retrospectiveRepository.findAll();
            model.addAttribute("RetroList", list);
            return "/Retrospective/listRetro";
        }
        return "/Retrospective/noRetroFound";

    }

}
