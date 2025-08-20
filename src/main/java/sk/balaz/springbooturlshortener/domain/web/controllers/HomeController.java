package sk.balaz.springbooturlshortener.domain.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sk.balaz.springbooturlshortener.domain.models.CreateShortUrlCmd;
import sk.balaz.springbooturlshortener.domain.models.ShortUrlDto;
import sk.balaz.springbooturlshortener.domain.services.ShortUrlService;
import sk.balaz.springbooturlshortener.domain.web.controllers.dtos.CreateShortUrlForm;

import jakarta.validation.Valid;
import java.util.List;

@Controller
public class HomeController {

  private final ShortUrlService shortUrlService;

  public HomeController(ShortUrlService shortUrlService) {
    this.shortUrlService = shortUrlService;
  }

  @GetMapping("/")
  public String index(Model model) {
    List<ShortUrlDto> shortUrls = shortUrlService.getAllShortUrls();
    model.addAttribute("shortUrls", shortUrls);
    model.addAttribute("baseUrl", "http://localhost:8080");
    model.addAttribute("createShortUrlForm", new CreateShortUrlForm(""));
    return "index";
  }

  @PostMapping("/short-urls")
  String createShortUrl(
    @ModelAttribute("createShortUrlForm") @Valid CreateShortUrlForm form,
    BindingResult bindingResult,
    RedirectAttributes  redirectAttributes,
    Model model) {
    if (bindingResult.hasErrors()) {
      List<ShortUrlDto> shortUrls = shortUrlService.getAllShortUrls();
      model.addAttribute("shortUrls", shortUrls);
      model.addAttribute("baseUrl", "http://localhost:8080");
      return "index";
    }
    // TODO implement to logic
    try {
      var shortUrlDto = shortUrlService.createShortUrl(new CreateShortUrlCmd(form.originalUrl()));
      redirectAttributes.addFlashAttribute("successMessage", "Short URL created successfully" +
        "http://localhost:8080/s/" + shortUrlDto.shortKey());

    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("errorMessage", "Failed to create short URL");
    }
    return  "redirect:/";
  }

}
