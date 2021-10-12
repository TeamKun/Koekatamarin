package net.kunmc.lab.koekatamarin.command;

import dev.kotx.flylib.command.Command;

public class ConfigCommand extends Command {
    public ConfigCommand() {
        super("config");
        children(new ConfigSetCommand(), new ConfigShowCommand());
    }
}
