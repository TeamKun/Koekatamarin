package net.kunmc.lab.koekatamarin.command;

import dev.kotx.flylib.command.Command;
import dev.kotx.flylib.command.UsageBuilder;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class ConfigItem extends Command {
    public ConfigItem(Field configField) {
        super(configField.getName());
        configField.setAccessible(true);

        String name = configField.getName();
        Class clazz = configField.getType();
        ArgumentType type = ArgumentType.valueOf(clazz);

        usage(builder -> {
            type.appendArgument(builder);

            builder.executes(ctx -> {
                Object argument = ctx.getTypedArgs().get(0);

                if (!type.isCollectArgument(argument)) {
                    ctx.fail("引数が不正です.");
                    return;
                }

                Object value = type.argumentToObject(argument);
                try {
                    configField.set(null, value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

                ctx.success(name + "の値を" + value + "に設定しました");
            });
        });
    }

    private enum ArgumentType {
        INT(int.class,
                b -> b.integerArgument("IntegerValue"),
                x -> true,
                x -> ((int) x),
                Integer.class),
        DOUBLE(double.class,
                b -> b.doubleArgument("DoubleValue"),
                x -> true,
                x -> ((double) x),
                Double.class),
        FLOAT(float.class,
                b -> b.floatArgument("FloatValue"),
                x -> true,
                x -> ((float) x),
                Float.class),
        BOOLEAN(boolean.class,
                b -> b.booleanArgument("BooleanValue",
                        sb -> sb.suggest("true").suggest("false")),
                x -> true,
                x -> ((boolean) x),
                Boolean.class),
        BLOCKDATA(BlockData.class,
                b -> b.textArgument("BlockName", sb -> {
                    Arrays.stream(Material.values())
                            .filter(Material::isBlock)
                            .map(Material::name)
                            .map(String::toLowerCase)
                            .forEach(sb::suggest);
                }),
                x -> {
                    return Arrays.stream(Material.values())
                            .filter(Material::isBlock)
                            .anyMatch(m -> m.name().equals(x.toString().toUpperCase()));
                },
                x -> {
                    return Arrays.stream(Material.values())
                            .filter(m -> m.name().equals(x.toString().toUpperCase()))
                            .map(Material::createBlockData)
                            .findFirst()
                            .get();
                });

        private final List<Class> classList;
        private final Consumer<UsageBuilder> appendArgument;
        private final Predicate<Object> isCollectArgument;
        private final Function<Object, ?> argumentToObject;

        <T> ArgumentType(Class<T> clazz, Consumer<UsageBuilder> appendArgument, Predicate<Object> isCollectArgument, Function<Object, T> argumentToObject, Class... additionalClasses) {
            this.classList = new ArrayList<>() {{
                add(clazz);
                addAll(Arrays.asList(additionalClasses));
            }};
            this.appendArgument = appendArgument;
            this.isCollectArgument = isCollectArgument;
            this.argumentToObject = argumentToObject;
        }

        public static ArgumentType valueOf(Class clazz) {
            return Arrays.stream(values())
                    .filter(x -> x.classList.contains(clazz))
                    .findFirst().orElse(null);
        }

        public void appendArgument(UsageBuilder builder) {
            appendArgument.accept(builder);
        }

        public boolean isCollectArgument(Object argument) {
            return isCollectArgument.test(argument);
        }

        public Object argumentToObject(Object argument) {
            return argumentToObject.apply(argument);
        }
    }
}
