package de.infoteam.profile_assist.adapter.out.markdown;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.vladsch.flexmark.ext.autolink.AutolinkExtension;
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughExtension;
import com.vladsch.flexmark.ext.gfm.tasklist.TaskListExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.ext.yaml.front.matter.YamlFrontMatterBlock;
import com.vladsch.flexmark.ext.yaml.front.matter.YamlFrontMatterExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.NodeVisitor;
import com.vladsch.flexmark.util.ast.VisitHandler;
import com.vladsch.flexmark.util.data.MutableDataSet;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import de.infoteam.profile_assist.application.port.out.MarkdownPort;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.stereotype.Service;

@Service
public class FlexmarkMarkdownAdapter implements MarkdownPort {
  private static final ObjectMapper YAML_MAPPER = new ObjectMapper(new YAMLFactory());

  private final Parser parser;
  private final HtmlRenderer renderer;

  public FlexmarkMarkdownAdapter() {
    MutableDataSet options = getFlexMarkdownOptions();
    this.parser = Parser.builder(options).build();
    this.renderer = HtmlRenderer.builder(options).build();
  }

  private @NotNull MutableDataSet getFlexMarkdownOptions() {
    return new MutableDataSet()
        .set(
            Parser.EXTENSIONS,
            List.of(
                TablesExtension.create(),
                AutolinkExtension.create(),
                YamlFrontMatterExtension.create(),
                TaskListExtension.create(),
                StrikethroughExtension.create()))
        .set(TablesExtension.WITH_CAPTION, false)
        .set(TablesExtension.APPEND_MISSING_COLUMNS, true);
  }

  public String renderToHtml(String markdown) {
    Document doc = parser.parse(markdown == null ? "" : markdown);
    String unsafeHtml = renderer.render(doc);

    Safelist safelist =
        Safelist.relaxed()
            .addTags("table", "thead", "tbody", "tr", "th", "td", "del", "input", "label")
            .addAttributes("a", "href", "title", "rel", "target")
            .addAttributes("table", "class")
            .addAttributes("input", "type", "checked", "disabled")
            .addProtocols("a", "href", "http", "https", "mailto");

    return Jsoup.clean(unsafeHtml, safelist);
  }

  private Optional<String> extractYaml(Document doc) {
    StringBuilder buf = new StringBuilder();
    getNodeVisitor(buf).visit(doc);
    String yaml = buf.toString().trim();
    return yaml.isEmpty() ? Optional.empty() : Optional.of(yaml);
  }

  public Map<String, Object> extractMetadataHeader(String markdown) {
    if (markdown == null || markdown.isBlank()) {
      return Map.of();
    }
    Document doc = parser.parse(markdown);
    return extractYaml(doc)
        .map(
            yaml -> {
              try {
                return parseYamlToMap(yaml);
              } catch (IOException e) {
                throw new MetadataHeaderParseException("Invalid YAML metadata header", e);
              }
            })
        .orElseGet(Map::of);
  }

  private @NotNull NodeVisitor getNodeVisitor(StringBuilder yamlBuf) {
    return new NodeVisitor(
        new VisitHandler<>(
            YamlFrontMatterBlock.class,
            block -> {
              BasedSequence seq = block.getContentChars();
              yamlBuf.append(seq);
            }));
  }

  private Map<String, Object> parseYamlToMap(String yaml) throws IOException {
    return YAML_MAPPER.readValue(yaml, new TypeReference<>() {});
  }
}
