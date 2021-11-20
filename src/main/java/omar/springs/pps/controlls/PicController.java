package omar.spring.pps.controlls;

import lombok.extern.slf4j.Slf4j;
import omar.spring.pps.data.entities.Pic;
import omar.spring.pps.data.entities.PicStatus;
import omar.spring.pps.data.entities.User;
import omar.spring.pps.exceptions.NotFoundException;
import omar.spring.pps.service.PicService;
import omar.spring.pps.utils.ImageMapper;
import omar.spring.pps.utils.PropertiesExtractor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Slf4j
@Controller
public class PicController {
    private  final PicService picService ;
    private  final ImageMapper imageMapper ;
    @Autowired

    public PicController(PicService picService, ImageMapper imageMapper) {
        this.picService = picService;
        this.imageMapper = imageMapper;
    }@RequestMapping(value = "/pics/pending")
    public String getPendingPicsFragment(Model model){
        log.info("{}:{}",getClass().getSimpleName(),"/pics");
        List<Pic>list=picService.getAllByStatus(List.of(PicStatus.PENDING));

        log.info("{}:{}",getClass().getSimpleName(),list.size());

        model.addAttribute("pics",list);
        return "/home/pendingPics";
    }  @PostMapping(value = "/pics/add")
    public RedirectView addPic(@ModelAttribute("pic")@Valid Pic pic, @RequestParam ("image") MultipartFile multipartFile
            , Model model)throws IOException {
        log.info("{}:{}", getClass().getSimpleName(), "/pics/add");
        String fileName = null;
        if (multipartFile.getOriginalFilename() != null) {
            fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            log.info("{}:{}", getClass().getSimpleName(), fileName);
        }
        imageMapper.saveFile(PropertiesExtractor.FILE_SERVER_PATH, fileName, multipartFile);
        pic.setStatus(PicStatus.PENDING);
        pic.setPath(fileName);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        pic.setUser(user);

        picService.save(pic);
        return new RedirectView("/index", true);
    }
    @GetMapping("/pic/image/{id}")
    public void showProductImage(@PathVariable Long id, HttpServletResponse response)
            throws IOException, NotFoundException {
        response.setContentType("image/jpeg");
        Pic product = picService.getById(id).orElseThrow(()->new NotFoundException(id));
        InputStream is = new ByteArrayInputStream(imageMapper.getImageUnWrapped(product.getPath()));
        IOUtils.copy(is,response.getOutputStream());
    }@GetMapping("/pics/pendingPics/approve/{id}")
    public String approvePic(@PathVariable("id") Long id, Model model){
        Pic product = picService.getById(id).orElseThrow(()->new NotFoundException(id));
        product.setStatus(PicStatus.APPROVED);
        picService.save(product);

        model.addAttribute("pics",picService.getAllByStatus(List.of(PicStatus.PENDING)));

        return "/home/pendingPics";
    }
    @GetMapping("/pics/pendingPics/decline/{id}")
    public String declinePic(@PathVariable("id") Long id, Model model){
        Pic product = picService.getById(id).orElseThrow(()->new NotFoundException(id));
        product.setStatus(PicStatus.DECLINED);
        picService.save(product);
        model.addAttribute("pics",picService.getAllByStatus(List.of(PicStatus.PENDING)));
        return "/home/pendingPics";
    }


}
