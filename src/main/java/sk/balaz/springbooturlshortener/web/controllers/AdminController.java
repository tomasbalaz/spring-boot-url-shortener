package sk.balaz.springbooturlshortener.web.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sk.balaz.springbooturlshortener.ApplicationProperties;
import sk.balaz.springbooturlshortener.domain.models.PagedResult;
import sk.balaz.springbooturlshortener.domain.models.ShortUrlDto;
import sk.balaz.springbooturlshortener.domain.services.ShortUrlService;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
  private final ShortUrlService shortUrlService;
  private final ApplicationProperties properties;

  public AdminController(ShortUrlService shortUrlService, ApplicationProperties properties) {
    this.shortUrlService = shortUrlService;
    this.properties = properties;
  }

  @GetMapping("/dashboard")
  public String dashboard(
    @RequestParam(defaultValue = "1") int page,
    Model model) {
    PagedResult<ShortUrlDto> allUrls = shortUrlService.findAllShortUrls(page, properties.pageSize());
    model.addAttribute("shortUrls", allUrls);
    model.addAttribute("baseUrl", properties.baseUrl());
    model.addAttribute("paginationUrl", "/admin/dashboard");
    return "admin-dashboard";
  }
}