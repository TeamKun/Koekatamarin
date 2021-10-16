package net.kunmc.lab.koekatamarin.command;

import dev.kotx.flylib.command.Command;
import dev.kotx.flylib.command.UsageBuilder;
import net.kunmc.lab.koekatamarin.Config;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class ConfigItem extends Command {
    public ConfigItem(Field configField) throws IllegalAccessException {
        super(configField.getName());
        configField.setAccessible(true);

        String configName = configField.getName();
        Config.Value configValue = (Config.Value) configField.get(null);
        ArgumentType type = ArgumentType.byClass(getGenericsClass(configField));

        usage(builder -> {
            type.appendArgument(builder);

            builder.executes(ctx -> {
                Object argument = ctx.getTypedArgs().get(0);

                if (!type.isCollectArgument(argument)) {
                    ctx.fail("引数が不正です.");
                    return;
                }

                Object value = type.argumentToValue(argument);
                configValue.value(value);

                ctx.success(configName + "の値を" + value + "に設定しました");
            });
        });
    }

    private static Class getGenericsClass(Field field) {
        Class genericsClass = null;
        try {
            String genericsTypeName = ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0].getTypeName();
            genericsClass = Class.forName(genericsTypeName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return genericsClass;
    }

    private static class ArgumentType<T> {
        static final ArgumentType<Integer> Integer = new ArgumentType<>(
                builder -> builder.integerArgument("IntegerArgument"),
                x -> true,
                x -> ((Integer) x)
        );
        static final ArgumentType<Double> Double = new ArgumentType<>(
                builder -> builder.doubleArgument("DoubleArgument"),
                x -> true,
                x -> ((Double) x)
        );
        static final ArgumentType<Float> Float = new ArgumentType<>(
                builder -> builder.floatArgument("FloatArgumnet"),
                x -> true,
                x -> ((Float) x)
        );
        static final ArgumentType<Boolean> Boolean = new ArgumentType<>(
                builder -> builder.booleanArgument("BooleanArgument",
                        sb -> sb.suggest("true").suggest("true")),
                x -> true,
                x -> ((Boolean) x)
        );
        static final ArgumentType<BlockData> BlockData = new ArgumentType<>(
                b -> b.textArgument("BlockName", sb -> {
                    Arrays.stream(Material.values())
                            .filter(Material::isBlock)
                            .map(Material::name)
                            .map(String::toLowerCase)
                            .forEach(sb::suggest);
                }),
                x -> Arrays.stream(Material.values())
                        .filter(Material::isBlock)
                        .anyMatch(m -> m.name().equals(x.toString().toUpperCase())),
                x -> Arrays.stream(Material.values())
                        .filter(m -> m.name().equals(x.toString().toUpperCase()))
                        .map(Material::createBlockData)
                        .findFirst()
                        .get()
        );


        private final Consumer<UsageBuilder> appendArgument;
        private final Predicate<Object> isCollectArgument;
        private final Function<Object, T> argumentToValue;

        private ArgumentType(Consumer<UsageBuilder> appendArgument, Predicate<Object> isCollectArgument, Function<Object, T> argumentToValue) {
            this.appendArgument = appendArgument;
            this.isCollectArgument = isCollectArgument;
            this.argumentToValue = argumentToValue;
        }

        public void appendArgument(UsageBuilder builder) {
            appendArgument.accept(builder);
        }

        public boolean isCollectArgument(Object argument) {
            return isCollectArgument.test(argument);
        }

        public T argumentToValue(Object argument) {
            return argumentToValue.apply(argument);
        }

        private static final Map<Class, ArgumentType> classArgumentTypeMap = new HashMap<>() {{
            Arrays.stream(ArgumentType.class.getDeclaredFields())
                    .peek(x -> x.setAccessible(true))
                    .filter(x -> Modifier.isStatic(x.getModifiers()))
                    .forEach(x -> {
                        try {
                            put(getGenericsClass(x), ((ArgumentType) x.get(null)));
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    });
        }};

        public static ArgumentType byClass(Class clazz) {
            return classArgumentTypeMap.get(clazz);
        }
    }
}
