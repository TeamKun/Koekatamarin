package net.kunmc.lab.koekatamarin;

import org.bukkit.Material;
import org.bukkit.block.data.BlockData;

import java.util.function.Consumer;

public class Config {
    public static Value<Boolean> enabled = new Value<>(false);
    public static Value<Double> speedPerSecond = new Value<>(5.2);
    public static Value<Float> fontSize = new Value<>(5.0F, fontSize -> {
        Koekatamarin.cachedLetterMap.keySet().forEach(x -> {
            Koekatamarin.cachedLetterMap.compute(x, (k, v) -> new Letter(k, fontSize, Koekatamarin.instance.selectFont(Config.use4x4KanaFont.value)));
        });
    });
    public static Value<Boolean> use4x4KanaFont = new Value<>(true, use4x4KanaFont -> {
        Koekatamarin.cachedLetterMap.keySet().forEach(x -> {
            Koekatamarin.cachedLetterMap.compute(x, (k, v) -> new Letter(k, Config.fontSize.value, Koekatamarin.instance.selectFont(use4x4KanaFont)));
        });
    });
    public static Value<Double> degrees = new Value<>(0.0);
    public static Value<Integer> maxLengthOfStr = new Value<>(5);
    public static Value<Boolean> limitCharTypes = new Value<>(true);
    public static Value<Boolean> enableUsingTeamColorBlock = new Value<>(true);
    public static Value<BlockData> block = new Value<>(Material.DIAMOND_BLOCK.createBlockData());

    public static class Value<T> {
        private T value;
        private final Consumer<T> afterSetValue;

        Value(T value) {
            this.value = value;
            this.afterSetValue = null;
        }

        Value(T value, Consumer<T> afterSetValue) {
            this.value = value;
            this.afterSetValue = afterSetValue;
        }

        public T value() {
            return value;
        }

        public void value(T newValue) {
            value = newValue;
            if (afterSetValue != null) {
                afterSetValue.accept(newValue);
            }
        }
    }
}
