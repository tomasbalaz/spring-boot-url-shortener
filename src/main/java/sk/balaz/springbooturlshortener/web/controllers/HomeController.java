package sk.balaz.springbooturlshortener.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sk.balaz.springbooturlshortener.ApplicationProperties;
import sk.balaz.springbooturlshortener.domain.exception.ShortUrlNotFoundException;
import sk.balaz.springbooturlshortener.domain.models.CreateShortUrlCmd;
import sk.balaz.springbooturlshortener.domain.models.ShortUrlDto;
import sk.balaz.springbooturlshortener.domain.services.ShortUrlService;
import sk.balaz.springbooturlshortener.web.dtos.CreateShortUrlForm;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

  private final ShortUrlService shortUrlService;

  private final ApplicationProperties properties;

  private final SecurityUtils securityUtils;

  public HomeController(
    ShortUrlService shortUrlService,
    ApplicationProperties properties,
    SecurityUtils securityUtils
  ) {
    this.shortUrlService = shortUrlService;
    this.properties = properties;
    this.securityUtils = securityUtils;
  }

  @GetMapping("/")
  public String index(
    @RequestParam(defaultValue = "0") Integer page,
    Model model) {
    List<ShortUrlDto> shortUrls = shortUrlService.getAllShortUrls(page, properties.pageSize());
    model.addAttribute("shortUrls", shortUrls);
    model.addAttribute("baseUrl", properties.baseUrl());
    model.addAttribute("createShortUrlForm", new CreateShortUrlForm("", false, null));
    return "index";
  }

  @PostMapping("/short-urls")
  String createShortUrl(
    @ModelAttribute("createShortUrlForm") @Valid CreateShortUrlForm form,
    BindingResult bindingResult,
    RedirectAttributes  redirectAttributes,
    Model model) {
    if (bindingResult.hasErrors()) {
      List<ShortUrlDto> shortUrls = shortUrlService.getAllShortUrls(1, properties.pageSize());
      model.addAttribute("shortUrls", shortUrls);
      model.addAttribute("baseUrl", properties.baseUrl());
      return "index";
    }
    try {
      Long userId = securityUtils.getCurrentUserId();
      var shortUrlDto = shortUrlService.createShortUrl(new CreateShortUrlCmd(
        form.originalUrl(),
        form.isPrivate(),
        form.expirationInDays(),
        userId
      ));
      redirectAttributes.addFlashAttribute("successMessage", "Short URL created successfully" +
        properties.baseUrl()+"/s/" + shortUrlDto.shortKey());

    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("errorMessage", "Failed to create short URL");
    }
    return  "redirect:/";
  }

  @GetMapping("/s/{shortKey}")
  String redirectToOriginalUrl(@PathVariable String shortKey) {
    Long userId = securityUtils.getCurrentUserId();
    Optional<ShortUrlDto> shortUrlDtoOptional = shortUrlService.accessShortUrl(shortKey, userId);
    if(shortUrlDtoOptional.isEmpty()) {
      throw new ShortUrlNotFoundException("Invalid short key: "+shortKey);
    }
    ShortUrlDto shortUrlDto = shortUrlDtoOptional.get();
    return "redirect:"+shortUrlDto.originalUrl();
  }

  @GetMapping("/login")
  String loginForm() {
    return "login";
  }

}
