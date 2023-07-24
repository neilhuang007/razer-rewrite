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

        // Initialize the texture manager if null
        if (ctm == null) ctm = tm;

        // Get the users screen width and height to apply
        final ScaledResolution scaledresolution = new ScaledResolution(Minecraft.getMinecraft());

        //System.out.println("width: " + scaledresolution.getScaledWidth() + "height: " + scaledresolution.getScaledHeight());

        // Create the scale factor
        final int scaleFactor = scaledresolution.getScaleFactor();

        // Bind the width and height to the framebuffer
        final Framebuffer framebuffer = new Framebuffer(scaledresolution.getScaledWidth() * scaleFactor,
                scaledresolution.getScaledHeight() * scaleFactor, true);
        framebuffer.bindFramebuffer(false);

        // Create the projected image to be rendered
        GlStateManager.matrixMode(GL11.GL_PROJECTION);
        GlStateManager.loadIdentity();
        GlStateManager.ortho(0.0D, scaledresolution.getScaledWidth(), scaledresolution.getScaledHeight(), 0.0D, 1000.0D, 3000.0D);
        GlStateManager.matrixMode(GL11.GL_MODELVIEW);
        GlStateManager.loadIdentity();
        GlStateManager.translate(0.0F, 0.0F, -2000.0F);
        GlStateManager.disableLighting();
        GlStateManager.disableFog();
        GlStateManager.disableDepth();
        GlStateManager.enableTexture2D();

        // Initialize the splash texture
        if (splash == null) {
            splash = new ResourceLocation("razer/backgrounds/splash_background.png");
        }

        // Bind the texture
        tm.bindTexture(splash);

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        // Draw the image
        Gui.drawScaledCustomSizeModalRect(0, 0, 0, 0, 1920, 1080,
                scaledresolution.getScaledWidth(), scaledresolution.getScaledHeight(), 1920, 1080);

        // Draw the progress bar
        drawProgress();

        // Unbind the width and height as it's no longer needed
        framebuffer.unbindFramebuffer();

        // Render the previously used frame buffer
        framebuffer.framebufferRender(scaledresolution.getScaledWidth() * scaleFactor, scaledresolution.getScaledHeight() * scaleFactor);

        // Update the texture to enable alpha drawing
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);

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