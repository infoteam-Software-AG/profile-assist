package de.infoteam.profile_assist.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.infoteam.profile_assist.service.markdown.FrontMatterParseException;
import de.infoteam.profile_assist.service.markdown.MarkdownService;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
@Tag("component")
class MarkdownControllerTest {

  @Mock MarkdownService mdService;

  private MockMvc mvc;

  @BeforeEach
  void setup() {
    MarkdownController controller = new MarkdownController(mdService);
    mvc = MockMvcBuilders.standaloneSetup(controller).build();
  }

  @DisplayName("Check if POST with an invalid YAML front matter returns a http 400 error")
  @Test
  void extractFrontMatter_invalidYaml_returns400() throws Exception {
    when(mdService.extractFrontMatter(anyString()))
        .thenThrow(new FrontMatterParseException("Invalid YAML front matter", null));

    mvc.perform(
            post("/api/markdown/front-matter")
                .contentType(MediaType.TEXT_PLAIN)
                .content("---\nfoo: [unterminated\n---\n"))
        .andExpect(status().isBadRequest());
  }

  @DisplayName("Check if POST with a valid YAML front matter returns a http 200 with a valid JSON")
  @Test
  void extractFrontMatter_returns200AndJson() throws Exception {
    when(mdService.extractFrontMatter(anyString()))
        .thenReturn(Map.of("title", "Example", "draft", false));

    mvc.perform(
            post("/api/markdown/front-matter")
                .contentType(MediaType.TEXT_PLAIN)
                .content("---\ntitle: Example\ndraft: false\n---\n"))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(content().json("{\"title\":\"Example\",\"draft\":false}"));
  }

  @DisplayName("Check if POST with any String returns a valid HTML")
  @Test
  void renderToHtml_returnsHtml() throws Exception {
    when(mdService.renderToHtml(anyString())).thenReturn("<p>Hello</p>");

    mvc.perform(post("/api/markdown/render").contentType(MediaType.TEXT_PLAIN).content("Hello"))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
        .andExpect(content().string("<p>Hello</p>"));
  }
}
