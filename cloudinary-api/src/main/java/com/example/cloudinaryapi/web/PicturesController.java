package com.example.cloudinaryapi.web;

import com.example.cloudinaryapi.model.binding.PictureBindingModel;
import com.example.cloudinaryapi.model.entity.PictureEntity;
import com.example.cloudinaryapi.model.view.PictureViewModel;
import com.example.cloudinaryapi.repository.PictureRepository;
import com.example.cloudinaryapi.service.CloudinaryImage;
import com.example.cloudinaryapi.service.CloudinaryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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
    public String addPicture(PictureBindingModel pictureBindingModel) throws IOException {
       var picture =  createPictureEntity(pictureBindingModel.getPicture(),pictureBindingModel.getTitle());

       pictureRepository.save(picture);
        return "redirect:/pictures/all";
    }

    @GetMapping("/pictures/all")
    public String all(Model model){
        List<PictureViewModel> pictures = pictureRepository
                .findAll()
                .stream()
                .map(this::asViewModel).collect(Collectors.toList());

        model.addAttribute("pictures", pictures);

        return "all";
    }

    @Transactional
    @DeleteMapping("/pictures/delete")
    public String delete(@RequestParam("public_id") String publicId){
        if(this.cloudinaryService.delete(publicId)){
            pictureRepository.deleteAllByPublicId(publicId);
        };

        return "redirect:/pictures/all";
    }

    private PictureViewModel asViewModel(PictureEntity pictureEntity){
        PictureViewModel viewModel = new PictureViewModel();
        viewModel.setPublicId(pictureEntity.getPublicId());
        viewModel.setTitle(pictureEntity.getTitle());
        viewModel.setUrl(pictureEntity.getUrl());

        return viewModel;
    }

    private PictureEntity createPictureEntity(MultipartFile multipartFile, String title) throws IOException {
        final CloudinaryImage upload = this.cloudinaryService.upload(multipartFile);
        PictureEntity pictureEntity = new PictureEntity();
        pictureEntity.setPublicId(upload.getPublicId());
        pictureEntity.setUrl(upload.getUrl());
        pictureEntity.setTitle(title);

        return pictureEntity;
    }

}
