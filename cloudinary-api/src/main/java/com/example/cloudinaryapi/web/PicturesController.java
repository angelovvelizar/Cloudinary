package com.example.cloudinaryapi.web;

import com.example.cloudinaryapi.model.entity.binding.PictureBindingModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PicturesController {

    @GetMapping("/pictures/add")
    public String addPicture(){
        return "add";
    }

    @PostMapping("/pictures/add")
    public String addPicture(PictureBindingModel pictureBindingModel){
        //todo:

        return "redirect:/pictures/all";
    }

    @GetMapping("/picutres/all")
    public String all(Model model){

        return "all";
    }

}
