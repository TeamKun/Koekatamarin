package net.kunmc.lab.koekatamarin;

import dev.kotx.flylib.FlyLib;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kunmc.lab.koekatamarin.command.MainCommand;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class Koekatamarin extends JavaPlugin {
    public static Koekatamarin instance;
    public static final Map<Character, Letter> letterMap = new HashMap<>();
    private final Font font;

    public Koekatamarin() throws IOException, FontFormatException {
        instance = this;
        font = Font.createFont(Font.TRUETYPE_FONT, getResource("font.ttf"));
        letterMap.putAll(new HashMap<>() {{
            put('a', new Letter('a', Config.fontSize, font));
            put('b', new Letter('b', Config.fontSize, font));
            put('c', new Letter('c', Config.fontSize, font));
            put('d', new Letter('d', Config.fontSize, font));
            put('e', new Letter('e', Config.fontSize, font));
            put('f', new Letter('f', Config.fontSize, font));
            put('g', new Letter('g', Config.fontSize, font));
            put('h', new Letter('h', Config.fontSize, font));
            put('i', new Letter('i', Config.fontSize, font));
            put('j', new Letter('j', Config.fontSize, font));
            put('k', new Letter('k', Config.fontSize, font));
            put('l', new Letter('l', Config.fontSize, font));
            put('m', new Letter('m', Config.fontSize, font));
            put('n', new Letter('n', Config.fontSize, font));
            put('o', new Letter('o', Config.fontSize, font));
            put('p', new Letter('p', Config.fontSize, font));
            put('q', new Letter('q', Config.fontSize, font));
            put('r', new Letter('r', Config.fontSize, font));
            put('s', new Letter('s', Config.fontSize, font));
            put('t', new Letter('t', Config.fontSize, font));
            put('u', new Letter('u', Config.fontSize, font));
            put('v', new Letter('v', Config.fontSize, font));
            put('w', new Letter('w', Config.fontSize, font));
            put('x', new Letter('x', Config.fontSize, font));
            put('y', new Letter('y', Config.fontSize, font));
            put('z', new Letter('z', Config.fontSize, font));
            put('A', new Letter('A', Config.fontSize, font));
            put('B', new Letter('B', Config.fontSize, font));
            put('C', new Letter('C', Config.fontSize, font));
            put('D', new Letter('D', Config.fontSize, font));
            put('E', new Letter('E', Config.fontSize, font));
            put('F', new Letter('F', Config.fontSize, font));
            put('G', new Letter('G', Config.fontSize, font));
            put('H', new Letter('H', Config.fontSize, font));
            put('I', new Letter('I', Config.fontSize, font));
            put('J', new Letter('J', Config.fontSize, font));
            put('K', new Letter('K', Config.fontSize, font));
            put('L', new Letter('L', Config.fontSize, font));
            put('M', new Letter('M', Config.fontSize, font));
            put('N', new Letter('N', Config.fontSize, font));
            put('O', new Letter('O', Config.fontSize, font));
            put('P', new Letter('P', Config.fontSize, font));
            put('Q', new Letter('Q', Config.fontSize, font));
            put('R', new Letter('R', Config.fontSize, font));
            put('S', new Letter('S', Config.fontSize, font));
            put('T', new Letter('T', Config.fontSize, font));
            put('U', new Letter('U', Config.fontSize, font));
            put('V', new Letter('V', Config.fontSize, font));
            put('W', new Letter('W', Config.fontSize, font));
            put('X', new Letter('X', Config.fontSize, font));
            put('Y', new Letter('Y', Config.fontSize, font));
            put('Z', new Letter('Z', Config.fontSize, font));
            put('あ', new Letter('あ', Config.fontSize, font));
            put('い', new Letter('い', Config.fontSize, font));
            put('う', new Letter('う', Config.fontSize, font));
            put('え', new Letter('え', Config.fontSize, font));
            put('お', new Letter('お', Config.fontSize, font));
            put('か', new Letter('か', Config.fontSize, font));
            put('が', new Letter('が', Config.fontSize, font));
            put('き', new Letter('き', Config.fontSize, font));
            put('ぎ', new Letter('ぎ', Config.fontSize, font));
            put('く', new Letter('く', Config.fontSize, font));
            put('ぐ', new Letter('ぐ', Config.fontSize, font));
            put('け', new Letter('け', Config.fontSize, font));
            put('げ', new Letter('げ', Config.fontSize, font));
            put('こ', new Letter('こ', Config.fontSize, font));
            put('ご', new Letter('ご', Config.fontSize, font));
            put('さ', new Letter('さ', Config.fontSize, font));
            put('ざ', new Letter('ざ', Config.fontSize, font));
            put('し', new Letter('し', Config.fontSize, font));
            put('じ', new Letter('じ', Config.fontSize, font));
            put('す', new Letter('す', Config.fontSize, font));
            put('ず', new Letter('ず', Config.fontSize, font));
            put('せ', new Letter('せ', Config.fontSize, font));
            put('ぜ', new Letter('ぜ', Config.fontSize, font));
            put('そ', new Letter('そ', Config.fontSize, font));
            put('ぞ', new Letter('ぞ', Config.fontSize, font));
            put('た', new Letter('た', Config.fontSize, font));
            put('だ', new Letter('だ', Config.fontSize, font));
            put('ち', new Letter('ち', Config.fontSize, font));
            put('ぢ', new Letter('ぢ', Config.fontSize, font));
            put('っ', new Letter('っ', Config.fontSize, font));
            put('つ', new Letter('つ', Config.fontSize, font));
            put('づ', new Letter('づ', Config.fontSize, font));
            put('て', new Letter('て', Config.fontSize, font));
            put('で', new Letter('で', Config.fontSize, font));
            put('と', new Letter('と', Config.fontSize, font));
            put('ど', new Letter('ど', Config.fontSize, font));
            put('な', new Letter('な', Config.fontSize, font));
            put('に', new Letter('に', Config.fontSize, font));
            put('ぬ', new Letter('ぬ', Config.fontSize, font));
            put('ね', new Letter('ね', Config.fontSize, font));
            put('の', new Letter('の', Config.fontSize, font));
            put('は', new Letter('は', Config.fontSize, font));
            put('ば', new Letter('ば', Config.fontSize, font));
            put('ぱ', new Letter('ぱ', Config.fontSize, font));
            put('ひ', new Letter('ひ', Config.fontSize, font));
            put('び', new Letter('び', Config.fontSize, font));
            put('ぴ', new Letter('ぴ', Config.fontSize, font));
            put('ふ', new Letter('ふ', Config.fontSize, font));
            put('ぶ', new Letter('ぶ', Config.fontSize, font));
            put('ぷ', new Letter('ぷ', Config.fontSize, font));
            put('へ', new Letter('へ', Config.fontSize, font));
            put('べ', new Letter('べ', Config.fontSize, font));
            put('ぺ', new Letter('ぺ', Config.fontSize, font));
            put('ほ', new Letter('ほ', Config.fontSize, font));
            put('ぼ', new Letter('ぼ', Config.fontSize, font));
            put('ぽ', new Letter('ぽ', Config.fontSize, font));
            put('ま', new Letter('ま', Config.fontSize, font));
            put('み', new Letter('み', Config.fontSize, font));
            put('む', new Letter('む', Config.fontSize, font));
            put('め', new Letter('め', Config.fontSize, font));
            put('も', new Letter('も', Config.fontSize, font));
            put('ゃ', new Letter('ゃ', Config.fontSize, font));
            put('や', new Letter('や', Config.fontSize, font));
            put('ゅ', new Letter('ゅ', Config.fontSize, font));
            put('ゆ', new Letter('ゆ', Config.fontSize, font));
            put('ょ', new Letter('ょ', Config.fontSize, font));
            put('よ', new Letter('よ', Config.fontSize, font));
            put('ら', new Letter('ら', Config.fontSize, font));
            put('り', new Letter('り', Config.fontSize, font));
            put('る', new Letter('る', Config.fontSize, font));
            put('れ', new Letter('れ', Config.fontSize, font));
            put('ろ', new Letter('ろ', Config.fontSize, font));
            put('ゎ', new Letter('ゎ', Config.fontSize, font));
            put('わ', new Letter('わ', Config.fontSize, font));
            put('ゐ', new Letter('ゐ', Config.fontSize, font));
            put('ゑ', new Letter('ゑ', Config.fontSize, font));
            put('を', new Letter('を', Config.fontSize, font));
            put('ん', new Letter('ん', Config.fontSize, font));
            put('ゔ', new Letter('ゔ', Config.fontSize, font));
            put('ゕ', new Letter('ゕ', Config.fontSize, font));
            put('ゖ', new Letter('ゖ', Config.fontSize, font));
            put('ァ', new Letter('ァ', Config.fontSize, font));
            put('ア', new Letter('ア', Config.fontSize, font));
            put('ィ', new Letter('ィ', Config.fontSize, font));
            put('イ', new Letter('イ', Config.fontSize, font));
            put('ゥ', new Letter('ゥ', Config.fontSize, font));
            put('ウ', new Letter('ウ', Config.fontSize, font));
            put('ェ', new Letter('ェ', Config.fontSize, font));
            put('エ', new Letter('エ', Config.fontSize, font));
            put('ォ', new Letter('ォ', Config.fontSize, font));
            put('オ', new Letter('オ', Config.fontSize, font));
            put('カ', new Letter('カ', Config.fontSize, font));
            put('ガ', new Letter('ガ', Config.fontSize, font));
            put('キ', new Letter('キ', Config.fontSize, font));
            put('ギ', new Letter('ギ', Config.fontSize, font));
            put('ク', new Letter('ク', Config.fontSize, font));
            put('グ', new Letter('グ', Config.fontSize, font));
            put('ケ', new Letter('ケ', Config.fontSize, font));
            put('ゲ', new Letter('ゲ', Config.fontSize, font));
            put('コ', new Letter('コ', Config.fontSize, font));
            put('ゴ', new Letter('ゴ', Config.fontSize, font));
            put('サ', new Letter('サ', Config.fontSize, font));
            put('ザ', new Letter('ザ', Config.fontSize, font));
            put('シ', new Letter('シ', Config.fontSize, font));
            put('ジ', new Letter('ジ', Config.fontSize, font));
            put('ス', new Letter('ス', Config.fontSize, font));
            put('ズ', new Letter('ズ', Config.fontSize, font));
            put('セ', new Letter('セ', Config.fontSize, font));
            put('ゼ', new Letter('ゼ', Config.fontSize, font));
            put('ソ', new Letter('ソ', Config.fontSize, font));
            put('ゾ', new Letter('ゾ', Config.fontSize, font));
            put('タ', new Letter('タ', Config.fontSize, font));
            put('ダ', new Letter('ダ', Config.fontSize, font));
            put('チ', new Letter('チ', Config.fontSize, font));
            put('ヂ', new Letter('ヂ', Config.fontSize, font));
            put('ッ', new Letter('ッ', Config.fontSize, font));
            put('ツ', new Letter('ツ', Config.fontSize, font));
            put('ヅ', new Letter('ヅ', Config.fontSize, font));
            put('テ', new Letter('テ', Config.fontSize, font));
            put('デ', new Letter('デ', Config.fontSize, font));
            put('ト', new Letter('ト', Config.fontSize, font));
            put('ド', new Letter('ド', Config.fontSize, font));
            put('ナ', new Letter('ナ', Config.fontSize, font));
            put('ニ', new Letter('ニ', Config.fontSize, font));
            put('ヌ', new Letter('ヌ', Config.fontSize, font));
            put('ネ', new Letter('ネ', Config.fontSize, font));
            put('ノ', new Letter('ノ', Config.fontSize, font));
            put('ハ', new Letter('ハ', Config.fontSize, font));
            put('バ', new Letter('バ', Config.fontSize, font));
            put('パ', new Letter('パ', Config.fontSize, font));
            put('ヒ', new Letter('ヒ', Config.fontSize, font));
            put('ビ', new Letter('ビ', Config.fontSize, font));
            put('ピ', new Letter('ピ', Config.fontSize, font));
            put('フ', new Letter('フ', Config.fontSize, font));
            put('ブ', new Letter('ブ', Config.fontSize, font));
            put('プ', new Letter('プ', Config.fontSize, font));
            put('ヘ', new Letter('ヘ', Config.fontSize, font));
            put('ベ', new Letter('ベ', Config.fontSize, font));
            put('ペ', new Letter('ペ', Config.fontSize, font));
            put('ホ', new Letter('ホ', Config.fontSize, font));
            put('ボ', new Letter('ボ', Config.fontSize, font));
            put('ポ', new Letter('ポ', Config.fontSize, font));
            put('マ', new Letter('マ', Config.fontSize, font));
            put('ミ', new Letter('ミ', Config.fontSize, font));
            put('ム', new Letter('ム', Config.fontSize, font));
            put('メ', new Letter('メ', Config.fontSize, font));
            put('モ', new Letter('モ', Config.fontSize, font));
            put('ャ', new Letter('ャ', Config.fontSize, font));
            put('ヤ', new Letter('ヤ', Config.fontSize, font));
            put('ュ', new Letter('ュ', Config.fontSize, font));
            put('ユ', new Letter('ユ', Config.fontSize, font));
            put('ョ', new Letter('ョ', Config.fontSize, font));
            put('ヨ', new Letter('ヨ', Config.fontSize, font));
            put('ラ', new Letter('ラ', Config.fontSize, font));
            put('リ', new Letter('リ', Config.fontSize, font));
            put('ル', new Letter('ル', Config.fontSize, font));
            put('レ', new Letter('レ', Config.fontSize, font));
            put('ロ', new Letter('ロ', Config.fontSize, font));
            put('ヮ', new Letter('ヮ', Config.fontSize, font));
            put('ワ', new Letter('ワ', Config.fontSize, font));
            put('ヰ', new Letter('ヰ', Config.fontSize, font));
            put('ヱ', new Letter('ヱ', Config.fontSize, font));
            put('ヲ', new Letter('ヲ', Config.fontSize, font));
            put('ン', new Letter('ン', Config.fontSize, font));
            put('ヴ', new Letter('ヴ', Config.fontSize, font));
            put('ヵ', new Letter('ヵ', Config.fontSize, font));
            put('ヶ', new Letter('ヶ', Config.fontSize, font));
            put('ヷ', new Letter('ヷ', Config.fontSize, font));
            put('ヸ', new Letter('ヸ', Config.fontSize, font));
            put('ヹ', new Letter('ヹ', Config.fontSize, font));
            put('ヺ', new Letter('ヺ', Config.fontSize, font));
            put('・', new Letter('・', Config.fontSize, font));
            put('ー', new Letter('ー', Config.fontSize, font));
            put('-', new Letter('-', Config.fontSize, font));
            put('!', new Letter('!', Config.fontSize, font));
            put('！', new Letter('！', Config.fontSize, font));
            put('?', new Letter('?', Config.fontSize, font));
            put('？', new Letter('？', Config.fontSize, font));
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
                        String msg = ((TextComponent) e.originalMessage()).content();
                        new MovingString(msg, Config.fontSize, font, Config.speedPerSecond, e.getPlayer().getEyeLocation(), Material.DIAMOND_BLOCK.createBlockData(), 0);
                    }
                }.runTask(instance);
            });
        });
    }

    @Override
    public void onDisable() {
    }
}
