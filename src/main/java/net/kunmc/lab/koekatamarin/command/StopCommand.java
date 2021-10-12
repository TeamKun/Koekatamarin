package net.kunmc.lab.koekatamarin.command;

import dev.kotx.flylib.command.Command;
import dev.kotx.flylib.command.CommandContext;
import net.kunmc.lab.koekatamarin.Config;

public class StopCommand extends Command {
    public StopCommand() {
        super("stop");
    }

    @Override
    public void execute(CommandContext ctx) {
        if (!Config.enabled) {
            ctx.fail("すでに無効化されています.");
            return;
        }

        Config.enabled = false;
        ctx.success("無効化しました.");
    }
}
