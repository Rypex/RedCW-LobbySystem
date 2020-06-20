package net.redcw.lobbysystem.particle.protocol;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class ProtocolAPI {

    public String getVersion() {
        return org.bukkit.Bukkit.getServer().getClass().getPackage()
                .getName().split("\\.")[3];
    }

    public Class<?> getCraftInstance(String path) {
        try {
            return Class.forName("org.bukkit.craftbukkit." + getVersion() + "." + path);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Class<?> getMinecraftServerInstance(String class_) {
        try {
            return Class.forName("net.minecraft.server." + getVersion() + "." + class_);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void method(Class<?> class_, String method, Class<?> parameters[], Object key, Object...objects) {
        try {
            class_.getMethod(method, parameters).invoke(key, objects);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
                | SecurityException e) {
            e.printStackTrace();
        }
    }

    public void variable(Class<?> class_, String name, Object key, Object value)
    {
        try
        {
            Field field = class_.getDeclaredField(name);
            field.setAccessible(true);
            field.set(key, value);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e)
        {
            e.printStackTrace();
        }
    }

    public Field getField(Class<?> clazz, String name) {
        try {
            Field field = clazz.getDeclaredField(name);
            field.setAccessible(true);
            return field;
        } catch (NoSuchFieldException | SecurityException e) {
            e.getStackTrace();
            return null;
        }

    }

    public Field getField(Class<?> clazz, String name, Object instance, Object value) {
        try {
            Field field = clazz.getDeclaredField(name);
            field.setAccessible(true);
            field.set(instance, value);
            return field;
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            e.getStackTrace();
            return null;
        }

    }

    public void setField(Object instanz, Class<?> clazz, String field, Object value){
        try {
            Field f = clazz.getDeclaredField(field);
            f.setAccessible(true);
            f.set(instanz, value);
        } catch(Exception ex) {
            ex.getStackTrace();
        }
    }


    public Object getClassEnum(Class<?> clazz, final String exact) {
        if (clazz != null && clazz.isEnum()) {
            for (final Object object : clazz.getEnumConstants()) {
                if (object.toString().equalsIgnoreCase(exact)) {
                    return object;
                }
            }
        }
        return null;
    }

}

