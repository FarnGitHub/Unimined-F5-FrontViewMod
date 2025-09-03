package farn.frontview;

import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class FrontViewHelper {
    public static int thirdPersonView = 0;
    private static boolean photoMode = false;
    private static Class photoModeClass;
    private static final Map<String, Field> cachedFields = new HashMap<>();

    public static void handlePhotoModeCompatibility(GuiScreen screen) {
        if(isPhotoModeScreen(screen)) {
            GL11.glRotatef(getFieldValue(screen, "tilt"), 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(45.0F + 90.0F * getFieldValue(screen, "rotation"), 0.0F, 1.0F, 0.0F);
        }

    }

    public static boolean isPhotoModeScreen(GuiScreen screen) {
        if(photoMode) {
            return photoModeClass.isInstance(screen);
        }
        return false;
    }

    private static Float getFieldValue(GuiScreen screen, String name) {
        try {
            Field field = cachedFields.computeIfAbsent(name, n -> {
                try {
                    Field f = photoModeClass.getDeclaredField(n);
                    f.setAccessible(true);
                    return f;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            return field.getFloat(screen);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void init() {
        try {
            photoModeClass = Class.forName("GuiPhotoMode");
            photoMode = photoModeClass != null;
            System.out.println("FrontView: PhotoMode has been installed");
        } catch (ClassNotFoundException e) {
            photoMode = false;
            System.out.println("FrontView: PhotoMode hasn't installed");
        }
    }


}
