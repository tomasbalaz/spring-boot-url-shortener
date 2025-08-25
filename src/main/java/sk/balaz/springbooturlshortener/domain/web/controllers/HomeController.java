package sk.balaz.springbooturlshortener.domain.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sk.balaz.springbooturlshortener.ApplicationProperties;
import sk.balaz.springbooturlshortener.domain.exception.ShortUrlNotFoundException;
import sk.balaz.springbooturlshortener.domain.models.CreateShortUrlCmd;
import sk.balaz.springbooturlshortener.domain.models.ShortUrlDto;
import sk.balaz.springbooturlshortener.domain.services.ShortUrlService;
import sk.balaz.springbooturlshortener.domain.web.controllers.dtos.CreateShortUrlForm;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

  private final ShortUrlService shortUrlService;

  private final ApplicationProperties properties;

  public HomeController(ShortUrlService shortUrlService, ApplicationProperties properties) {
    this.shortUrlService = shortUrlService;
    this.properties = properties;
  }

  @GetMapping("/")
  public String index(Model model) {
    List<ShortUrlDto> shortUrls = shortUrlService.getAllShortUrls();
    model.addAttribute("shortUrls", shortUrls);
    model.addAttribute("baseUrl", properties.baseUrl());
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
      model.addAttribute("baseUrl", properties.baseUrl());
      return "index";
    }
    // TODO implement to logic
    try {
      var shortUrlDto = shortUrlService.createShortUrl(new CreateShortUrlCmd(form.originalUrl()));
      redirectAttributes.addFlashAttribute("successMessage", "Short URL created successfully" +
        properties.baseUrl()+"/s/" + shortUrlDto.shortKey());

    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("errorMessage", "Failed to create short URL");
    }
    return  "redirect:/";
  }

  @GetMapping("/s/{shortKey}")
  String redirectToOriginalUrl(@PathVariable String shortKey) {
    Optional<ShortUrlDto> shortUrlDtoOptional = shortUrlService.accessShortUrl(shortKey);
    if(shortUrlDtoOptional.isEmpty()) {
      throw new ShortUrlNotFoundException("Invalid short key: "+shortKey);
    }
    ShortUrlDto shortUrlDto = shortUrlDtoOptional.get();
    return "redirect:"+shortUrlDto.originalUrl();
  }

}
