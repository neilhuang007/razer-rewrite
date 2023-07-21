package dev.razer.ui.impl.intro;

import dev.razer.managers.ColorManager;
import dev.razer.managers.RenderManager;
import dev.razer.ui.impl.menu.Mainmenu;

import dev.razer.util.Timers.StopWatch;
import dev.razer.util.animation.Animation;
import dev.razer.util.animation.Easing;
import dev.razer.util.font.Font;
import dev.razer.util.font.FontManager;
import dev.razer.util.font.impl.rise.FontRenderer;
import dev.razer.util.interfaces.InstanceAccess;
import dev.razer.util.shader.RazerShaders;
import dev.razer.util.shader.base.ShaderRenderType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public final class IntroSequence extends GuiScreen{
    private static TextureManager ctm;

    static int PROGRESS = 0;
    private static ResourceLocation splash;

    static final Color bgcolor = new Color(18, 18, 18);

    public static int x;
    public static int y = 0;




    public static void update() {
        if (Minecraft.getMinecraft() == null || Minecraft.getMinecraft().getLanguageManager() == null) return;
        drawIntroSequence(Minecraft.getMinecraft().getTextureManager());
    }

    // main def to draw the intro sequence
    public static void drawIntroSequence(final TextureManager tm) {
        // Draw the progress bar
        drawProgress();

        // Update the users screen
        Minecraft.getMinecraft().updateDisplay();
    }

    private static void drawProgress() {
        final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

        RenderManager.roundedRectangle((sr.getScaledWidth() - 160)/2, (sr.getScaledHeight()  + 75)/2, 160,2,0.75,new Color(160,160,160));
        // Render the progress bar
        RenderManager.roundedRectangle((sr.getScaledWidth() - 160)/2, (sr.getScaledHeight()  + 75)/2, 160/16 * PROGRESS,2,0.75,new Color(255,255,255));
    }


    public static void setProgress(int stage, String phase) {
        PROGRESS = stage;
        update();
        System.out.println("stage: " + stage + ", phase : " + phase);
        if(stage == 16){
            InstanceAccess.mc.displayGuiScreen(new Mainmenu());
        }
    }
}