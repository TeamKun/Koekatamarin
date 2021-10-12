package net.kunmc.lab.koekatamarin.command;

import dev.kotx.flylib.command.Command;

import java.lang.reflect.Field;

public class ConfigItem extends Command {
    public ConfigItem(Field configField) {
        super(configField.getName());
        configField.setAccessible(true);

        String name = configField.getName();
        Class clazz = configField.getType();

        usage(builder -> {
            if (clazz.equals(Integer.class) || clazz.equals(int.class)) {
                builder.integerArgument("IntegerValue");
            }

            if (clazz.equals(Double.class) || clazz.equals(double.class)) {
                builder.doubleArgument("DoubleValue");
            }

            if (clazz.equals(Float.class) || clazz.equals(float.class)) {
                builder.floatArgument("FloatValue");
            }

            if (clazz.equals(Boolean.class) || clazz.equals(boolean.class)) {
                builder.booleanArgument("BooleanValue", suggestionBuilder -> {
                    suggestionBuilder.suggest("");
                });
            }

            builder.executes(ctx -> {
                Object value = ctx.getTypedArgs().get(0);
                try {
                    configField.set(null, value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

                ctx.success(name + "の値を" + value + "に設定しました");
            });
        });
    }
}
