package br.ifba.edu.inf011.command;

import java.util.ArrayList;
import java.util.List;

public class MacroCommand implements Command {
    private List<Command> commands;
    private String nome;
    
    public MacroCommand(String nome) {
        this.commands = new ArrayList<>();
        this.nome = nome;
    }
    
    public void addCommand(Command command) {
        this.commands.add(command);
    }
    
    @Override
    public void execute() {
        for (Command cmd : commands) {
            cmd.execute();
        }
    }
    
    @Override
    public void undo() {
        for (int i = commands.size() - 1; i >= 0; i--) {
            commands.get(i).undo();
        }
    }
    
    @Override
    public String getDescription() {
        return "Macro: " + nome + " (" + commands.size() + " operações)";
    }
}