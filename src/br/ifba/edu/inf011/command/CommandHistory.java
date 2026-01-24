package br.ifba.edu.inf011.command;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Stack;

public class CommandHistory {
  private Stack<Command> history;
  private Stack<Command> redoStack;
  private static final String LOG_FILE = "command_history.log";

  private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  public CommandHistory() {
    this.history = new Stack<>();
    this.redoStack = new Stack<>();
  }

  public void execute(Command command){
    command.execute();
    history.push(command);
    redoStack.clear();
    log("EXECUTAR", command.getDescription());
  }

  public void undo(){
    if(!history.isEmpty()){
      Command command = history.pop();
      command.undo();
      redoStack.push(command);
      log("DESFAZER", command.getDescription());
    }
  }

  public void redo(){
    if(!redoStack.isEmpty()){
      Command command = redoStack.pop();
      command.execute();
      history.push(command);
      log("REFAZER", command.getDescription());
    }
  }

  public void consolidate(){
    history.clear();
    redoStack.clear();
    log("CONSOLIDAR", "Histórico de comandos consolidado.");
  }

  private void log(String action, String message){
    try(FileWriter writer = new FileWriter(LOG_FILE, true)){
      String timestamp = LocalDateTime.now().format(formatter);
      String logText = String.format("[%s]  %s: %s%n", timestamp, action, message);
      writer.write(logText);
      System.out.println(logText.trim());
    }catch(IOException e){
      System.out.println("Erro ao escrever no log: " + e.getMessage());
    }
  }
}
