package com.example.cloudinaryapi.web;

import com.example.cloudinaryapi.model.binding.PictureBindingModel;
import com.example.cloudinaryapi.repository.PictureRepository;
import com.example.cloudinaryapi.service.CloudinaryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PicturesController {
    private final CloudinaryService cloudinaryService;
    private final PictureRepository pictureRepository;

    public PicturesController(CloudinaryService cloudinaryService, PictureRepository pictureRepository) {
        //repo in controller!
        this.pictureRepository = pictureRepository;
        this.cloudinaryService = cloudinaryService;
    }

    @GetMapping("/pictures/add")
    public String addPicture(){
        return "add";
    }

    @PostMapping("/pictures/add")
    public String addPicture(PictureBindingModel pictureBindingModel){


        return "redirect:/pictures/all";
    }

    @GetMapping("/picutres/all")
    public String all(Model model){

        return "all";
    }

}
