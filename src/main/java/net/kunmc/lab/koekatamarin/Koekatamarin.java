package net.kunmc.lab.koekatamarin;

import dev.kotx.flylib.FlyLib;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kunmc.lab.koekatamarin.command.MainCommand;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

public final class Koekatamarin extends JavaPlugin {
    public static Koekatamarin instance;
    private final Set<Character> charSet = new HashSet<>();
    public static final Map<Character, Letter> cachedLetterMap = new HashMap<>();
    private final Font kanaFont;
    private final Font ipapgothicFont;

    public Koekatamarin() throws IOException, FontFormatException {
        instance = this;
        kanaFont = Font.createFont(Font.TRUETYPE_FONT, getResource("4x4kanafont.ttf"));
        ipapgothicFont = Font.createFont(Font.TRUETYPE_FONT, getResource("ipapgothic.ttf"));
        charSet.addAll(new HashSet<>() {{
            add('a');
            add('b');
            add('c');
            add('d');
            add('e');
            add('f');
            add('g');
            add('h');
            add('i');
            add('j');
            add('k');
            add('l');
            add('m');
            add('n');
            add('o');
            add('p');
            add('q');
            add('r');
            add('s');
            add('t');
            add('u');
            add('v');
            add('w');
            add('x');
            add('y');
            add('z');
            add('A');
            add('B');
            add('C');
            add('D');
            add('E');
            add('F');
            add('G');
            add('H');
            add('I');
            add('J');
            add('K');
            add('L');
            add('M');
            add('N');
            add('O');
            add('P');
            add('Q');
            add('R');
            add('S');
            add('T');
            add('U');
            add('V');
            add('W');
            add('X');
            add('Y');
            add('Z');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('???');
            add('-');
            add('!');
            add('???');
            add('?');
            add('???');
        }});
    }

    @Override
    public void onEnable() {
        FlyLib.create(this, builder -> {
            builder.command(new MainCommand("koekatamarin"));

            builder.listen(AsyncChatEvent.class, e -> {
                if (!Config.enabled.value()) {
                    return;
                }

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        Font font = selectFont(Config.use4x4KanaFont.value());

                        String msg = ((TextComponent) e.originalMessage()).content();
                        if (Config.limitCharTypes.value()) {
                            msg = msg.chars()
                                    .filter(x -> charSet.contains(((char) x)))
                                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                                    .toString();
                        }

                        if (msg.length() > Config.maxLengthOfStr.value()) {
                            msg = msg.substring(0, Config.maxLengthOfStr.value());
                        }

                        List<Letter> letterList = msg.chars()
                                .mapToObj(x -> cachedLetterMap.computeIfAbsent(((char) x), c -> new Letter(c, Config.fontSize.value(), font)))
                                .collect(Collectors.toUnmodifiableList());

                        new MovingString(letterList, Config.speedPerSecond.value(), e.getPlayer().getEyeLocation(), selectBlockData(e.getPlayer()), Config.degrees.value());
                    }
                }.runTask(instance);
            });
        });
    }

    @Override
    public void onDisable() {
        Bukkit.getWorlds().forEach(w -> {
            w.getEntities().stream()
                    .filter(x -> x.getScoreboardTags().contains(MovingBlock.scoreboardTag))
                    .forEach(Entity::remove);
        });
    }

    private final Map<ChatColor, BlockData> colorMaterialMap = new HashMap<>() {{
        put(ChatColor.AQUA, Material.LIGHT_BLUE_WOOL.createBlockData());
        put(ChatColor.BLACK, Material.BLACK_WOOL.createBlockData());
        put(ChatColor.BLUE, Material.BLUE_WOOL.createBlockData());
        put(ChatColor.DARK_AQUA, Material.CYAN_WOOL.createBlockData());
        put(ChatColor.DARK_BLUE, Material.BLUE_WOOL.createBlockData());
        put(ChatColor.DARK_GRAY, Material.GRAY_WOOL.createBlockData());
        put(ChatColor.DARK_GREEN, Material.GREEN_WOOL.createBlockData());
        put(ChatColor.DARK_PURPLE, Material.PURPLE_WOOL.createBlockData());
        put(ChatColor.DARK_RED, Material.RED_WOOL.createBlockData());
        put(ChatColor.GOLD, Material.YELLOW_WOOL.createBlockData());
        put(ChatColor.GRAY, Material.GRAY_WOOL.createBlockData());
        put(ChatColor.GREEN, Material.GREEN_WOOL.createBlockData());
        put(ChatColor.LIGHT_PURPLE, Material.MAGENTA_WOOL.createBlockData());
        put(ChatColor.RED, Material.RED_WOOL.createBlockData());
        put(ChatColor.WHITE, Material.WHITE_WOOL.createBlockData());
        put(ChatColor.YELLOW, Material.YELLOW_WOOL.createBlockData());
    }};

    private BlockData selectBlockData(Player p) {
        if (!Config.enableUsingTeamColorBlock.value()) {
            return Config.block.value();
        }

        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        ChatColor color = scoreboard.getTeams().stream()
                .filter(x -> x.hasEntry(p.getName()))
                .map(Team::getColor)
                .findFirst().orElse(null);

        if (color == null) {
            return Config.block.value();
        }

        return colorMaterialMap.getOrDefault(color, Config.block.value());
    }

    public Font selectFont(boolean use4x4KanaFont) {
        if (use4x4KanaFont) {
            return kanaFont;
        }

        return ipapgothicFont;
    }
}
