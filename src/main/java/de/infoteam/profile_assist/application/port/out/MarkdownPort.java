package de.infoteam.profile_assist.application.port.out;

import java.util.Map;

public interface MarkdownPort {
  String renderToHtml(String markdown);

  Map<String, Object> extractMetadataHeader(String markdown);
}
