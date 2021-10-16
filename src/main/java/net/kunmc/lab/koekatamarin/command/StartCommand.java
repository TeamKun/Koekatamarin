package net.kunmc.lab.koekatamarin.command;

import dev.kotx.flylib.command.Command;
import dev.kotx.flylib.command.CommandContext;
import net.kunmc.lab.koekatamarin.Config;

public class StartCommand extends Command {
    public StartCommand() {
        super("start");
    }

    @Override
    public void execute(CommandContext ctx) {
        if (Config.enabled.value()) {
            ctx.fail("有効化済みです.");
            return;
        }

        Config.enabled.value(true);
        ctx.success("有効化しました.");
    }
}
