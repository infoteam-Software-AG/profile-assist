package de.infoteam.profile_assist.integration.testoutput;

import de.infoteam.profile_assist.domain.entity.Project;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ProjectMarkdownDiffCreator {

  private final String beforeFileName;
  private final String afterFileName;
  private LocalDateTime timeStamp;

  public ProjectMarkdownDiffCreator(String beforeFileName, String afterFileName){
    this.beforeFileName = beforeFileName;
    this.afterFileName = afterFileName;
    this.timeStamp = LocalDateTime.now();
  }


  private File getOrGenerateFile(String fileName){
    var path = "target" + File.separator + "diffView" + File.separator + this.timeStamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss")) + File.separator;
    var completePath = path + fileName;
    File file = new File(completePath);
    if(file.exists()) return file;
    try{
      new File(path).mkdirs();
      file.createNewFile();
      return file;
    }catch(IOException e){
      throw new RuntimeException(e);
    }

  }

  public void addDiffToFiles(Project before, Project after) throws Exception{
    MarkdownOutput beforeOutput = new ProjectMarkdownOutput(before);
    MarkdownOutput afterOutput = new ProjectMarkdownOutput(after);

    PrintStream beforeStream = new PrintStream(new FileOutputStream(getOrGenerateFile(beforeFileName), true));
    PrintStream afterStream = new PrintStream(new FileOutputStream(getOrGenerateFile(afterFileName), true));

    var beforePrintedLines = beforeOutput.output(beforeStream);
    var afterPrintedLines = afterOutput.output(afterStream);
    var lineAmountDiff = afterPrintedLines - beforePrintedLines;
    if(lineAmountDiff > 0){
      beforeOutput.printNEmptyLines(lineAmountDiff, beforeStream);
    }else{
      afterOutput.printNEmptyLines(Math.abs(lineAmountDiff), afterStream);
    }
  }
}
