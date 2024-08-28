package com.retrospective.tool.controllers;

import com.retrospective.tool.authenticationdetails.MemberDetails;
import com.retrospective.tool.models.*;
import com.retrospective.tool.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import javax.websocket.server.PathParam;
import java.util.List;
import java.util.ArrayList;

@Controller
@RequestMapping("/item")
public class ItemController {
    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private RetrospectiveRepository retrospectiveRepository;

    @Autowired
    private CommentRepository commentRepository;

    @RequestMapping("/create")
    public String create(@RequestParam("id") Integer id, Model model) {
        model.addAttribute("retroId", id);
        return "createItem";
    }


    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String addNewItem(@RequestParam Integer id, @RequestParam String title, @RequestParam String contents, @RequestParam Category category, Model model) {
        Item item = new Item(title, contents);
        item.setCategory(category);

        Optional<Retrospective> retrospectiveop = retrospectiveRepository.findById(id);
        if(retrospectiveop.isPresent()){
            Retrospective retrospective=retrospectiveop.get();
            retrospective.addItem(item);
            itemRepository.save(item);
            retrospectiveRepository.save(retrospective);
        }

        return view(id, model);
    }

    @RequestMapping("/upvote")
    public String upvote(@RequestParam("id") Integer id, Model model) {
        Optional<Item> itemop= itemRepository.findById(id);
        if(itemop.isPresent()){
            Item item=itemop.get();
            item.upvote();
            itemRepository.save(item);
            return view(item.getRetrospective().getId(),model);

        }
        else{
            return "ItemProgress/noItemFound";
        }

    }

    @RequestMapping("/downvote")
    public String downvote(@RequestParam("id") Integer id, Model model) {
        Optional<Item> itemop= itemRepository.findById(id);
        if(itemop.isPresent()){
            Item item=itemop.get();
            item.downvote();
            itemRepository.save(item);
            return view(item.getRetrospective().getId(),model);
        }
        else{
            return "ItemProgress/noItemFound";
        }

    }


    @RequestMapping("/view")
    public String view(@RequestParam ("id") Integer id, Model model){
        Optional<Retrospective> retrospectiveop = retrospectiveRepository.findById(id);
        if(retrospectiveop.isPresent()){
            Retrospective retrospective=retrospectiveop.get();
            List<Item> listItems = new ArrayList<>(retrospective.getItems());
            listItems.sort(Comparator.comparing(a -> -a.getVotes()));
            model.addAttribute("listItems", listItems);
            model.addAttribute("retroId", retrospective.getId());
            model.addAttribute("retrospective", retrospective);
            model.addAttribute("team", retrospective.getTeam());
        }

        return "viewItems.html";
    }

    @RequestMapping("/lock")
    public String lock(@RequestParam ("id") Integer id, Model model){
        Optional<Retrospective> retrospectiveop = retrospectiveRepository.findById(id);
        if(retrospectiveop.isPresent()){
            Retrospective retrospective=retrospectiveop.get();
            retrospective.setLocked(true);
            retrospectiveRepository.save(retrospective);
            return view(retrospective.getId(), model);
        }else{
            return "Retrospective/noRetroFound";
        }

    }

    @RequestMapping("/unlock")
    public String unlock(@RequestParam ("id") Integer id, Model model){
        Optional<Retrospective> retrospectiveop = retrospectiveRepository.findById(id);
        if(retrospectiveop.isPresent()){
            Retrospective retrospective=retrospectiveop.get();
            retrospective.setLocked(false);
            retrospectiveRepository.save(retrospective);
            return view(retrospective.getId(), model);
        }else{
            return "Retrospective/noRetroFound";
        }

    }

    @RequestMapping("/getComments")
    public String getComments(@RequestParam("id") Integer id, Model model){
        Optional<Item> itemop= itemRepository.findById(id);
        if(itemop.isPresent()){
            Item item=itemop.get();
            model.addAttribute("item", item);
            List<Comment> listComments = new ArrayList<>(item.getComments());
            model.addAttribute("listComments", listComments);
            model.addAttribute("retroId", item.getRetrospective().getId());
            model.addAttribute("retrospective", item.getRetrospective());

        }

        return "viewComments.html";
    }

    @RequestMapping("/addComment")
    public String addComment(Model model, @RequestParam("id") Integer id, @RequestParam("comment") String comment){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MemberDetails currentUser = (MemberDetails) auth.getPrincipal();
        Member member = memberRepository.findByEmail(currentUser.getUsername());
        Optional<Item> itemop= itemRepository.findById(id);
        if(itemop.isPresent()){
            Item item=itemop.get();
            Comment com = new Comment(member, comment);
            com.addItem(item);
            commentRepository.save(com);
            item.addComment(com);
            itemRepository.save(item);

        }

        return getComments(id, model);
    }

    @RequestMapping(value = "viewGrouped", method = RequestMethod.POST)
    public String viewGroupedItems(@RequestParam("id") Integer id, @RequestParam String type, Model model){
        Optional<Retrospective> retrospectiveop = retrospectiveRepository.findById(id);
        if(retrospectiveop.isPresent()){
            Retrospective  retrospective=retrospectiveop.get();
            model.addAttribute("retroId", id);
            model.addAttribute("retrospective", retrospective);
            List<Item> listItems = new ArrayList<>(retrospective.getItems());

            listItems.sort(Comparator.comparing(a -> -a.getVotes()));
            List<Item> listOne = new ArrayList<>();
            List<Item> listTwo = new ArrayList<>();
            List<Item> listThree = new ArrayList<>();
            List<Item> listFour = new ArrayList<>();
            if(type.equals("Progress")){
                for(Item item : listItems){
                    if(item.getProgress() == Progress.TODO){
                        listTwo.add(item);
                    }else if(item.getProgress() == Progress.INPROGRESS){
                        listThree.add(item);
                    }else if(item.getProgress() == Progress.COMPLETE){
                        listFour.add(item);
                    }else{
                        listOne.add(item);
                    }
                }
                model.addAttribute("listOne", listOne);
                model.addAttribute("listTwo", listTwo);
                model.addAttribute("listThree", listThree);
                model.addAttribute("listFour", listFour);
                model.addAttribute("titleOne", "None");
                model.addAttribute("titleTwo", "To Do");
                model.addAttribute("titleThree", "In Progress");
                model.addAttribute("titleFour", "Complete");
                return "viewGroupedItems.html";
            }else if(type.equals("Category")){
                for(Item item : listItems){
                    if(item.getCategory() == Category.MAD){
                        listTwo.add(item);
                    }else if(item.getCategory() == Category.SAD){
                        listThree.add(item);
                    }else if(item.getCategory() == Category.GLAD){
                        listFour.add(item);
                    }else{
                        listOne.add(item);
                    }
                }

                model.addAttribute("listOne", listOne);
                model.addAttribute("listTwo", listTwo);
                model.addAttribute("listThree", listThree);
                model.addAttribute("listFour", listFour);
                model.addAttribute("titleOne", "None");
                model.addAttribute("titleTwo", "Mad");
                model.addAttribute("titleThree", "Sad");
                model.addAttribute("titleFour", "Glad");
                return "viewGroupedItems.html";
            }else{
                return view(listItems.get(0).getRetrospective().getId(), model);
            }
        }else{
            return "Retrospective/noRetroFound";

        }

    }

    @GetMapping("/progressReport")
    public String progressReport(@RequestParam("id") Integer id, Model model){
        Optional<Retrospective> retrospectiveop = retrospectiveRepository.findById(id);
        Retrospective retrospective=new Retrospective();
        if(retrospectiveop.isPresent()){
            retrospective=retrospectiveop.get();
        }
        model.addAttribute("retroId", id);
        List<Item> listItems = new ArrayList<>(retrospective.getItems());
        listItems.sort(Comparator.comparing(a -> -a.getVotes()));

        List<Item> listOne = new ArrayList<>();
        List<Item> listTwo = new ArrayList<>();
        List<Item> listThree = new ArrayList<>();
        List<Item> listFour = new ArrayList<>();
        for(Item item : retrospective.getItems()){
            if(item.getProgress() == Progress.TODO){
                listTwo.add(item);
            }else if(item.getProgress() == Progress.INPROGRESS){
                listThree.add(item);
            }else if(item.getProgress() == Progress.COMPLETE){
                listFour.add(item);
            }else{
                listOne.add(item);
            }
        }

        model.addAttribute("none", listOne);
        model.addAttribute("todo", listTwo);
        model.addAttribute("inProgress", listThree);
        model.addAttribute("complete", listFour);
        model.addAttribute("listItemsSize", listTwo.size()+listThree.size()+listFour.size());
        model.addAttribute("progress", (int) Math.ceil(retrospective.getProgress()));
        return "progressReport.html";

    }

    @GetMapping("/all")
    public @ResponseBody Iterable<Item> getAllItems() { return itemRepository.findAll(); }

    @GetMapping("/{id}")
    public @ResponseBody Item getItemById(@PathVariable int id) {

        return itemRepository.findById(id);
    }

    @GetMapping("/update")
    public String updatePage(){
        return "/ItemProgress/UpdateItem";
    }
    @PostMapping("/updateProgress")
    public String updateProgress(@PathParam(value="id")int id,@PathParam(value="progress")String progress, Model model){

        Optional<Item> itemop= Optional.ofNullable(itemRepository.findById(id));
        if(itemop.isPresent()){
            Item item=itemop.get();

            if (progress.equals("TODO")){
                itemRepository.updateById(id, Progress.TODO);

            }
            else if(progress.equals("INPROGRESS")){
                itemRepository.updateById(id,Progress.INPROGRESS);
            }else{
                itemRepository.updateById(id,Progress.COMPLETE);
            }
            model.addAttribute("retroId", item.getRetrospective().getId());
            return "/ItemProgress/itemProgressUpdateSuccess";


        }

        return "/ItemProgress/noItemFound";    }


}
