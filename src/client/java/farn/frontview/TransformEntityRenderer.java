package farn.frontview;

import net.lenni0451.classtransform.annotations.CShadow;
import net.lenni0451.classtransform.annotations.CTransformer;
import net.lenni0451.classtransform.annotations.injection.COverride;
import net.minecraft.client.Minecraft;
import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;

@CTransformer(EntityRenderer.class)
public class TransformEntityRenderer {

    @CShadow
    Minecraft mc;

    @CShadow
    private float field_22228_r;
    @CShadow
    private float field_22227_s;
    @CShadow
    private float field_22226_t;
    @CShadow
    private float field_22225_u;
    @CShadow
    private float field_22224_v;
    @CShadow
    private float field_22223_w;
    @CShadow
    private float field_22220_z;
    @CShadow
    private float field_22230_A;

    @CShadow
    private boolean cloudFog;

    @COverride
    private void orientCamera(float f1) {
        EntityLiving entityLiving2 = this.mc.renderViewEntity;
        float f3 = entityLiving2.yOffset - 1.62F;
        double d4 = entityLiving2.prevPosX + (entityLiving2.posX - entityLiving2.prevPosX) * (double)f1;
        double d6 = entityLiving2.prevPosY + (entityLiving2.posY - entityLiving2.prevPosY) * (double)f1 - (double)f3;
        double d8 = entityLiving2.prevPosZ + (entityLiving2.posZ - entityLiving2.prevPosZ) * (double)f1;
        GL11.glRotatef(this.field_22230_A + (this.field_22220_z - this.field_22230_A) * f1, 0.0F, 0.0F, 1.0F);
        float f10 = entityLiving2.rotationPitch;
        float f11 = entityLiving2.prevRotationPitch;

        if(entityLiving2.isPlayerSleeping()) {
            f3 = (float)((double)f3 + 1.0D);
            GL11.glTranslatef(0.0F, 0.3F, 0.0F);
            if(!this.mc.gameSettings.field_22273_E) {
                int i12 = this.mc.theWorld.getBlockId(MathHelper.floor_double(entityLiving2.posX), MathHelper.floor_double(entityLiving2.posY), MathHelper.floor_double(entityLiving2.posZ));
                if(i12 == Block.blockBed.blockID) {
                    int i13 = this.mc.theWorld.getBlockMetadata(MathHelper.floor_double(entityLiving2.posX), MathHelper.floor_double(entityLiving2.posY), MathHelper.floor_double(entityLiving2.posZ));
                    int i14 = i13 & 3;
                    GL11.glRotatef((float)(i14 * 90), 0.0F, 1.0F, 0.0F);
                }

                GL11.glRotatef(entityLiving2.prevRotationYaw + (entityLiving2.rotationYaw - entityLiving2.prevRotationYaw) * f1 + 180.0F, 0.0F, -1.0F, 0.0F);
                GL11.glRotatef(f11 + (f10 - f11) * f1, -1.0F, 0.0F, 0.0F);
            }
        } else if(FrontViewHelper.thirdPersonView > 0 && this.mc.gameSettings.thirdPersonView && !FrontViewHelper.isPhotoModeScreen(this.mc.currentScreen)) {
            double d29 = (double)(this.field_22227_s + (this.field_22228_r - this.field_22227_s) * f1);
            float f15;
            float f30;
            if(this.mc.gameSettings.field_22273_E) {
                f15 = this.field_22225_u + (this.field_22226_t - this.field_22225_u) * f1;
                f30 = this.field_22223_w + (this.field_22224_v - this.field_22223_w) * f1;
                GL11.glTranslatef(0.0F, 0.0F, (float)(-d29));
                GL11.glRotatef(f30, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(f15, 0.0F, 1.0F, 0.0F);
            } else {
                f15 = entityLiving2.rotationYaw;
                f30 = f10;

                if(FrontViewHelper.thirdPersonView == 2) {
                    f30 += 180.0F;
                }

                double d16 = (double)(-MathHelper.sin(f15 / 180.0F * 3.141593F) * MathHelper.cos(f30 / 180.0F * 3.141593F)) * d29;
                double d18 = (double)(MathHelper.cos(f15 / 180.0F * 3.141593F) * MathHelper.cos(f30 / 180.0F * 3.141593F)) * d29;
                double d20 = (double)(-MathHelper.sin(f30 / 180.0F * 3.141593F)) * d29;

                for(int i22 = 0; i22 < 8; ++i22) {
                    float f23 = (float)((i22 & 1) * 2 - 1);
                    float f24 = (float)((i22 >> 1 & 1) * 2 - 1);
                    float f25 = (float)((i22 >> 2 & 1) * 2 - 1);
                    f23 *= 0.1F;
                    f24 *= 0.1F;
                    f25 *= 0.1F;
                    MovingObjectPosition movingObjectPosition26 = this.mc.theWorld.func_28105_a(Vec3D.createVector(d4 + (double)f23, d6 + (double)f24, d8 + (double)f25), Vec3D.createVector(d4 - d16 + (double)f23 + (double)f25, d6 - d20 + (double)f24, d8 - d18 + (double)f25), false, true);
                    if(movingObjectPosition26 != null) {
                        double d27 = movingObjectPosition26.hitVec.distanceTo(Vec3D.createVector(d4, d6, d8));
                        if(d27 < d29) {
                            d29 = d27;
                        }
                    }
                }

                if(FrontViewHelper.thirdPersonView == 2) {
                    GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
                }

                GL11.glRotatef(f10 - f30, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(entityLiving2.rotationYaw - f15, 0.0F, 1.0F, 0.0F);
                GL11.glTranslatef(0.0F, 0.0F, (float)(-d29));
                GL11.glRotatef(f15 - entityLiving2.rotationYaw, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(f30 - f10, 1.0F, 0.0F, 0.0F);
            }
        } else {
            GL11.glTranslatef(0.0F, 0.0F, -0.1F);
        }

        if(!FrontViewHelper.isPhotoModeScreen(this.mc.currentScreen) && !this.mc.gameSettings.field_22273_E) {
            GL11.glRotatef(f11 + (f10 - f11) * f1, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(entityLiving2.prevRotationYaw + (entityLiving2.rotationYaw - entityLiving2.prevRotationYaw) * f1 + 180.0F, 0.0F, 1.0F, 0.0F);
        }

        FrontViewHelper.handlePhotoModeCompatibility(this.mc.currentScreen);

        GL11.glTranslatef(0.0F, f3, 0.0F);
        d4 = entityLiving2.prevPosX + (entityLiving2.posX - entityLiving2.prevPosX) * (double)f1;
        d6 = entityLiving2.prevPosY + (entityLiving2.posY - entityLiving2.prevPosY) * (double)f1 - (double)f3;
        d8 = entityLiving2.prevPosZ + (entityLiving2.posZ - entityLiving2.prevPosZ) * (double)f1;
        this.cloudFog = this.mc.renderGlobal.func_27307_a(d4, d6, d8, f1);
    }
}
