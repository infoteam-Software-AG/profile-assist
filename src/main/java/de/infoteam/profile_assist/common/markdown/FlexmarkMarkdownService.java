package de.infoteam.profile_assist.common.markdown;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.vladsch.flexmark.ext.yaml.front.matter.YamlFrontMatterBlock;
import com.vladsch.flexmark.ext.yaml.front.matter.YamlFrontMatterExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.NodeVisitor;
import com.vladsch.flexmark.util.ast.VisitHandler;
import com.vladsch.flexmark.util.data.MutableDataSet;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.stereotype.Service;

@Service
public class FlexmarkMarkdownService implements MarkdownService {
  private static final ObjectMapper YAML_MAPPER = new ObjectMapper(new YAMLFactory());

  private final Parser parser;
  private final HtmlRenderer renderer;

  public FlexmarkMarkdownService() {
    MutableDataSet options = getFlexMarkdownOptions();
    this.parser = Parser.builder(options).build();
    this.renderer = HtmlRenderer.builder(options).build();
  }

  private MutableDataSet getFlexMarkdownOptions() {
    return new MutableDataSet().set(Parser.EXTENSIONS, List.of(YamlFrontMatterExtension.create()));
  }

  public String renderToHtml(String markdown) {
    Document doc = parser.parse(markdown == null ? "" : markdown);
    String unsafeHtml = renderer.render(doc);

    Safelist safelist =
        Safelist.relaxed()
            .addTags(
                "p",
                "h1",
                "h2",
                "h3",
                "h4",
                "h5",
                "h6",
                "ul",
                "ol",
                "li",
                "blockquote",
                "pre",
                "code",
                "em",
                "strong",
                "a")
            .addAttributes("a", "href", "title", "rel", "target")
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

  private NodeVisitor getNodeVisitor(StringBuilder yamlBuf) {
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
