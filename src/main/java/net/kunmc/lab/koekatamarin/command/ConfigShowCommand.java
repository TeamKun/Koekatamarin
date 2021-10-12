package net.kunmc.lab.koekatamarin.command;

import dev.kotx.flylib.command.Command;
import dev.kotx.flylib.command.CommandContext;
import net.kunmc.lab.koekatamarin.Config;

import java.lang.reflect.Field;

public class ConfigShowCommand extends Command {
    public ConfigShowCommand() {
        super("show");
    }

    @Override
    public void execute(CommandContext ctx) {
        for (Field field : Config.class.getDeclaredFields()) {
            Object target = null;
            try {
                target = field.get(null);
            } catch (Exception e) {
                e.printStackTrace();
            }

            ctx.success(field.getName() + ": " + target);
        }
    }
}
