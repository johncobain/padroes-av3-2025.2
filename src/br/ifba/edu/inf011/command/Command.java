package br.ifba.edu.inf011.command;

public interface Command {
  void execute();
  void undo();
  String getDescription();
}