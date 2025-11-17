package de.infoteam.profile_assist.common.markdown;

import com.vladsch.flexmark.formatter.Formatter;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class MarkdownConfig {

  @Bean
  MutableDataSet flexmarkOptions() {
    return new MutableDataSet(); // Core-CommonMark
  }

  // AST output
  @Bean
  Parser flexmarkParser(MutableDataSet options) {
    return Parser.builder(options).build();
  }

  // markdown output
  @Bean
  Formatter flexmarkFormatter(MutableDataSet options) {
    return Formatter.builder(options).build();
  }
}
