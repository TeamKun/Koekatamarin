package net.kunmc.lab.koekatamarin.command;

import dev.kotx.flylib.command.Command;
import net.kunmc.lab.koekatamarin.Config;

import java.lang.reflect.Field;

public class ConfigSetCommand extends Command {
    public ConfigSetCommand() {
        super("set");

        try {
            for (Field field : Config.class.getDeclaredFields()) {
                if (!field.getName().equals("enabled")) {
                    System.out.println(field.getName());
                    children(new ConfigItem(field));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
