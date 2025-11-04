package de.infoteam.profile_assist.service.markdown;

import java.util.Map;

public interface MarkdownService {
    String renderToHtml(String markdown);
    Map<String, Object> extractFrontMatter(String markdown);
}
