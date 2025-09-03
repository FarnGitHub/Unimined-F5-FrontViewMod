package farn.frontview;

import net.lenni0451.classtransform.InjectionCallback;
import net.lenni0451.classtransform.annotations.CShadow;
import net.lenni0451.classtransform.annotations.CTarget;
import net.lenni0451.classtransform.annotations.CTransformer;
import net.lenni0451.classtransform.annotations.injection.CInject;
import net.minecraft.client.Minecraft;
import net.minecraft.src.GameSettings;
import org.lwjgl.input.Keyboard;

@CTransformer(Minecraft.class)
public class TransformMinecraft {

    @CShadow
    public GameSettings gameSettings;

    @CInject(method="startGame", target=@CTarget("TAIL"))
    public void init(InjectionCallback info) {
        FrontViewHelper.init();
    }

    @CInject(
            method = "runTick",
            target = @CTarget(value = "INVOKE", target = "Lorg/lwjgl/input/Keyboard;getEventKey()I"),
            cancellable = true
    )
    private void onKeyEvent(InjectionCallback icb) {
        if (Keyboard.getEventKeyState() && Keyboard.getEventKey() == 63) {
            FrontViewHelper.thirdPersonView++;
            if (FrontViewHelper.thirdPersonView > 2) {
                FrontViewHelper.thirdPersonView = 0;
            }
            gameSettings.thirdPersonView = FrontViewHelper.thirdPersonView != 0;
            icb.setCancelled(true);
        }
    }
}
