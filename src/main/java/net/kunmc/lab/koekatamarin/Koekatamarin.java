package net.kunmc.lab.koekatamarin;

import dev.kotx.flylib.FlyLib;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kunmc.lab.koekatamarin.command.MainCommand;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

public final class Koekatamarin extends JavaPlugin {
    public static Koekatamarin instance;
    private final Set<Character> charSet = new HashSet<>();
    private final Map<Character, Letter> cachedLetterMap = new HashMap<>();
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
            add('あ');
            add('い');
            add('う');
            add('え');
            add('お');
            add('か');
            add('が');
            add('き');
            add('ぎ');
            add('く');
            add('ぐ');
            add('け');
            add('げ');
            add('こ');
            add('ご');
            add('さ');
            add('ざ');
            add('し');
            add('じ');
            add('す');
            add('ず');
            add('せ');
            add('ぜ');
            add('そ');
            add('ぞ');
            add('た');
            add('だ');
            add('ち');
            add('ぢ');
            add('っ');
            add('つ');
            add('づ');
            add('て');
            add('で');
            add('と');
            add('ど');
            add('な');
            add('に');
            add('ぬ');
            add('ね');
            add('の');
            add('は');
            add('ば');
            add('ぱ');
            add('ひ');
            add('び');
            add('ぴ');
            add('ふ');
            add('ぶ');
            add('ぷ');
            add('へ');
            add('べ');
            add('ぺ');
            add('ほ');
            add('ぼ');
            add('ぽ');
            add('ま');
            add('み');
            add('む');
            add('め');
            add('も');
            add('ゃ');
            add('や');
            add('ゅ');
            add('ゆ');
            add('ょ');
            add('よ');
            add('ら');
            add('り');
            add('る');
            add('れ');
            add('ろ');
            add('ゎ');
            add('わ');
            add('ゐ');
            add('ゑ');
            add('を');
            add('ん');
            add('ゔ');
            add('ゕ');
            add('ゖ');
            add('ァ');
            add('ア');
            add('ィ');
            add('イ');
            add('ゥ');
            add('ウ');
            add('ェ');
            add('エ');
            add('ォ');
            add('オ');
            add('カ');
            add('ガ');
            add('キ');
            add('ギ');
            add('ク');
            add('グ');
            add('ケ');
            add('ゲ');
            add('コ');
            add('ゴ');
            add('サ');
            add('ザ');
            add('シ');
            add('ジ');
            add('ス');
            add('ズ');
            add('セ');
            add('ゼ');
            add('ソ');
            add('ゾ');
            add('タ');
            add('ダ');
            add('チ');
            add('ヂ');
            add('ッ');
            add('ツ');
            add('ヅ');
            add('テ');
            add('デ');
            add('ト');
            add('ド');
            add('ナ');
            add('ニ');
            add('ヌ');
            add('ネ');
            add('ノ');
            add('ハ');
            add('バ');
            add('パ');
            add('ヒ');
            add('ビ');
            add('ピ');
            add('フ');
            add('ブ');
            add('プ');
            add('ヘ');
            add('ベ');
            add('ペ');
            add('ホ');
            add('ボ');
            add('ポ');
            add('マ');
            add('ミ');
            add('ム');
            add('メ');
            add('モ');
            add('ャ');
            add('ヤ');
            add('ュ');
            add('ユ');
            add('ョ');
            add('ヨ');
            add('ラ');
            add('リ');
            add('ル');
            add('レ');
            add('ロ');
            add('ヮ');
            add('ワ');
            add('ヰ');
            add('ヱ');
            add('ヲ');
            add('ン');
            add('ヴ');
            add('ヵ');
            add('ヶ');
            add('ヷ');
            add('ヸ');
            add('ヹ');
            add('ヺ');
            add('・');
            add('ー');
            add('-');
            add('!');
            add('！');
            add('?');
            add('？');
        }});
    }

    @Override
    public void onEnable() {
        FlyLib.create(this, builder -> {
            builder.command(new MainCommand("koekatamarin"));

            builder.listen(AsyncChatEvent.class, e -> {
                if (!Config.enabled) {
                    return;
                }

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        Font font = Config.use4x4KanaFont ? kanaFont : ipapgothicFont;

                        cachedLetterMap.keySet().forEach(x -> {
                            cachedLetterMap.computeIfPresent(x, (k, v) -> {
                                if (v.fontSize != Config.fontSize || !font.getName().equals(v.font.getName())) {
                                    return new Letter(k, Config.fontSize, font);
                                }
                                return v;
                            });
                        });

                        String msg = ((TextComponent) e.originalMessage()).content();
                        if (Config.limitCharTypes) {
                            msg = msg.chars()
                                    .filter(x -> charSet.contains(((char) x)))
                                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                                    .toString();
                        }

                        if (msg.length() > Config.maxLengthOfStr) {
                            msg = msg.substring(0, Config.maxLengthOfStr);
                        }

                        List<Letter> letterList = msg.chars()
                                .mapToObj(x -> cachedLetterMap.computeIfAbsent(((char) x), c -> new Letter(c, Config.fontSize, font)))
                                .collect(Collectors.toUnmodifiableList());

                        new MovingString(letterList, Config.speedPerSecond, e.getPlayer().getEyeLocation(), Config.block, Config.degrees);
                    }
                }.runTask(instance);
            });
        });
    }

    @Override
    public void onDisable() {
    }
}
