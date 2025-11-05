package de.infoteam.profile_assist.controller;

import de.infoteam.profile_assist.service.markdown.MarkdownService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/markdown")
public class MarkdownController {
  private final MarkdownService md;

  @PostMapping(
      value = "/render",
      consumes = MediaType.TEXT_PLAIN_VALUE,
      produces = MediaType.TEXT_HTML_VALUE)
  public String render(@RequestBody String markdown) {
    return md.renderToHtml(markdown);
  }

  @PostMapping(
      value = "/front-matter",
      consumes = MediaType.TEXT_PLAIN_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public Map<String, Object> frontMatter(@RequestBody String markdown) {
    return md.extractFrontMatter(markdown);
  }
}
