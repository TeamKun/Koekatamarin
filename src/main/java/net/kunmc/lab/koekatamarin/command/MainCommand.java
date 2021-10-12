package net.kunmc.lab.koekatamarin.command;

import dev.kotx.flylib.command.Command;

public class MainCommand extends Command {
    public MainCommand(String name) {
        super(name);
        children(new StartCommand(), new StopCommand(), new ConfigCommand());
    }
}
