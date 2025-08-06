package sk.balaz.springbooturlshortener.domain.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import sk.balaz.springbooturlshortener.domain.entities.ShortUrl;
import sk.balaz.springbooturlshortener.domain.services.ShortUrlService;

import java.util.List;

@Controller
public class HomeController {

  private final ShortUrlService shortUrlService;

  public HomeController(ShortUrlService shortUrlService) {
    this.shortUrlService = shortUrlService;
  }

  @GetMapping("/")
  public String index(Model model) {
    List<ShortUrl> shortUrls = shortUrlService.getAllShortUrls();
    model.addAttribute("shortUrls", shortUrls);
    model.addAttribute("baseUrl", "http://localhost:8080");
    return "index";
  }

}
