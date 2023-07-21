package dev.razer.util.shader.impl;

import dev.razer.util.shader.base.RazerShader;
import dev.razer.util.shader.base.RazerShaderProgram;
import dev.razer.util.shader.base.ShaderRenderType;
import dev.razer.util.shader.base.ShaderUniforms;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class MainMenuBackgroundShader extends RazerShader {

    private final RazerShaderProgram program = new RazerShaderProgram("main_menu/background.frag", "vertex.vsh");

    private Framebuffer tempFBO = new Framebuffer(mc.displayWidth, mc.displayHeight, true);

    @Override
    public void run(ShaderRenderType type, float partialTicks, List<Runnable> runnable) {
        // Prevent rendering
        if (!Display.isVisible()) {
            return;
        }

        if (type == ShaderRenderType.OVERLAY) {
            this.update();

            // program ids
            final int programID = this.program.getProgramId();
            ScaledResolution scaledResolution = new ScaledResolution(mc);

            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GlStateManager.disableAlpha();

            mc.getFramebuffer().bindFramebuffer(true);
            this.program.start();
            ShaderUniforms.uniform2f(programID, "resolution", mc.displayWidth, mc.displayHeight);
            ShaderUniforms.uniform1f(programID, "time", (System.currentTimeMillis() - mc.getStartMillisTime()) / 1000F);
            RazerShaderProgram.drawQuad();
            RazerShaderProgram.stop();
        }
    }

    @Override
    public void update() {
        // can be true since this is only called in gui screen
        this.setActive(true);

        if (mc.displayWidth != tempFBO.framebufferWidth || mc.displayHeight != tempFBO.framebufferHeight) {
            tempFBO.deleteFramebuffer();
            tempFBO = new Framebuffer(mc.displayWidth, mc.displayHeight, true);
        } else {
            tempFBO.framebufferClear();
        }
    }
}

