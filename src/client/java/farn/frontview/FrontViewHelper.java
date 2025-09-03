package farn.frontview;

import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;

import java.lang.reflect.Field;

public class FrontViewHelper {
    public static int thirdPersonView = 0;
    private static boolean photoMode = false;
    private static Class photoModeClass;

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
            Field field = photoModeClass.getField(name);
            return field.getFloat(screen);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static void init() {
        try {
            photoModeClass = Class.forName("GuiPhotoMode");
            photoMode = photoModeClass != null;
            System.out.println("PhotoMode has been found");
        } catch (ClassNotFoundException e) {
            photoMode = false;
            System.out.println("FrontViewPhotoMode not installed");
        }
    }


}
