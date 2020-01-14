package site.yl.template.module;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

  @GetMapping(value ="/")
  public String goIndex(Model model){
    model.addAttribute("name","123");
    return "index";
  }
}

