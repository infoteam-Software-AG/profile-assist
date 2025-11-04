package de.infoteam.profile_assist.controller.markdown;

import de.infoteam.profile_assist.service.markdown.FlexmarkMarkdownService;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/markdown")
public class MarkdownController {

  private final FlexmarkMarkdownService md;

  public MarkdownController(FlexmarkMarkdownService md) {
    this.md = md;
  }

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
