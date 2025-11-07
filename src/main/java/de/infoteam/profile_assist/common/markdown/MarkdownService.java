package de.infoteam.profile_assist.common.markdown;

import java.util.Map;

public interface MarkdownService {
  String renderToHtml(String markdown);

  Map<String, Object> extractMetadataHeader(String markdown);
}
