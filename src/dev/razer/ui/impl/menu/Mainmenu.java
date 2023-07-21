package dev.razer.ui.impl.menu;

import dev.razer.Razer;
import dev.razer.managers.ColorManager;
import dev.razer.managers.RenderManager;
import dev.razer.ui.component.button.MenuButton;
import dev.razer.ui.component.button.impl.MenuTextButton;
import dev.razer.ui.impl.Menu;
import dev.razer.util.animation.Animation;
import dev.razer.util.animation.Easing;
import dev.razer.util.font.Font;
import dev.razer.util.font.FontManager;
import dev.razer.util.interfaces.InstanceAccess;
import dev.razer.util.mouse.MouseUtil;
import dev.razer.util.shader.RazerShaders;
import dev.razer.util.shader.base.ShaderRenderType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;
import java.io.IOException;

public final class Mainmenu extends Menu {

    Minecraft mc = Minecraft.getMinecraft();

//    private static final ResourceLocation SETTINGS_ICON = new ResourceLocation("rise/icons/main_menu/SettingsIcon.png");
//    private static final ResourceLocation LANGUAGES_ICON = new ResourceLocation("rise/icons/main_menu/LanguagesIcon.png");

    // "Logo" animation
    private final Font fontRenderer = FontManager.getProductSansRegular(64);
    private Animation animation = new Animation(Easing.EASE_OUT_QUINT, 600);

    private MenuTextButton singlePlayerButton;
    private MenuTextButton multiPlayerButton;

    private MenuTextButton optionsButton;

    private MenuTextButton quitButton;
    //private MenuTextButton altManagerButton;

    private MenuButton[] menuButtons;

    private boolean vape;
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (this.singlePlayerButton == null || this.multiPlayerButton == null) {
            return;
        }

        // Renders the background
        RazerShaders.MAIN_MENU_SHADER.run(ShaderRenderType.OVERLAY, partialTicks, null);

        ScaledResolution scaledResolution = new ScaledResolution(mc);

        NORMAL_BLUR_RUNNABLES.add(() -> RenderManager.rectangle(0, 0, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight(), Color.BLACK));

        // Run blur
        RazerShaders.GAUSSIAN_BLUR_SHADER.update();
        RazerShaders.GAUSSIAN_BLUR_SHADER.run(ShaderRenderType.OVERLAY, partialTicks, InstanceAccess.NORMAL_BLUR_RUNNABLES);

        // Run bloom
        RazerShaders.POST_BLOOM_SHADER.update();
        RazerShaders.POST_BLOOM_SHADER.run(ShaderRenderType.OVERLAY, partialTicks, InstanceAccess.NORMAL_POST_BLOOM_RUNNABLES);

        // FPS counter on dev builds
        //if (Client.DEVELOPMENT_SWITCH) mc.fontRendererObj.drawStringWithShadow(Minecraft.getDebugFPS() + "", 0, 0, -1);

        // Run post shader things
        InstanceAccess.clearRunnables();

        // Renders the buttons
        this.singlePlayerButton.draw(mouseX, mouseY, partialTicks);
        this.multiPlayerButton.draw(mouseX, mouseY, partialTicks);
        this.optionsButton.draw(mouseX, mouseY, partialTicks);
        this.quitButton.draw(mouseX, mouseY, partialTicks);

        //this.altManagerButton.draw(mouseX, mouseY, partialTicks);

        // Update the animation
        final double destination = this.singlePlayerButton.getY() - this.fontRenderer.height();
        this.animation.run(destination);

        // String name
        String name = vape ? "Razer" : Razer.NAME;

        // Render the rise "logo"
        final double value = this.animation.getValue();
        final Color color = ColorManager.withAlpha(Color.WHITE, (int) (value / destination * 200));
        this.fontRenderer.drawCenteredString(name, width / 2.0F, value, color.getRGB());

        // Draw bottom right text
        Font watermarkLarge = FontManager.getProductSansRegular(24);
        String watermarkMainText = name + " " + Razer.VERSION_FULL + " (" + Razer.VERSION_DATE + ")";

        watermarkLarge.drawRightString(watermarkMainText, scaledResolution.getScaledWidth()- 5,
                scaledResolution.getScaledHeight() - watermarkLarge.height() - 2,
                ColorManager.withAlpha(TEXT_SUBTEXT, 128).getRGB());

        FontManager.getProductSansRegular(16).drawRightString("Designed and built by neilhuang007",
                scaledResolution.getScaledWidth() - 5, scaledResolution.getScaledHeight() - 40,
                ColorManager.withAlpha(TEXT_SUBTEXT, 100).getRGB());

        FontManager.getProductSansRegular(12).drawRightString("© Razer Client 2022. All Rights Reserved",
                scaledResolution.getScaledWidth() - 5, scaledResolution.getScaledHeight() - 30,
                ColorManager.withAlpha(TEXT_SUBTEXT, 100).getRGB());

        // TODO: Add the small "6.0"
        UI_BLOOM_RUNNABLES.forEach(Runnable::run);
        UI_BLOOM_RUNNABLES.clear();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (this.menuButtons == null) return;

        // If doing a left click and the mouse is hovered over a button, execute the buttons action (runnable)
        if (mouseButton == 0) {
            for (MenuButton menuButton : this.menuButtons) {
                if (MouseUtil.isHovered(menuButton.getX(), menuButton.getY(), menuButton.getWidth(), menuButton.getHeight(), mouseX, mouseY)) {
                    menuButton.runAction();
                    break;
                }
            }
        }
    }

    @Override
    public void initGui() {
        InstanceAccess.clearRunnables();
        vape = Math.random() > 0.95;
        int centerX = this.width / 2;
        int centerY = this.height / 2;
        int buttonWidth = 180;
        int buttonHeight = 24;
        int buttonSpacing = 6;
        int buttonX = centerX - buttonWidth / 2;
        int buttonY = centerY - buttonHeight / 2 - buttonSpacing / 2 - buttonHeight / 2;

        // Re-creates the buttons for not having to care about the animation reset
        this.singlePlayerButton = new MenuTextButton(buttonX, buttonY, buttonWidth, buttonHeight, () -> mc.displayGuiScreen(new GuiSelectWorld(this)), "Singleplayer");
        this.multiPlayerButton = new MenuTextButton(buttonX, buttonY + buttonHeight + buttonSpacing, buttonWidth, buttonHeight, () -> mc.displayGuiScreen(new GuiMultiplayer(this)), "Multiplayer");
        this.optionsButton = new MenuTextButton(buttonX, buttonY + buttonHeight * 2 + buttonSpacing * 2, buttonWidth / 2 - 3, buttonHeight, () -> this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings)), "options");
        this.quitButton = new MenuTextButton(buttonX + buttonSpacing / 2 + buttonWidth/2, buttonY + buttonHeight * 2 + buttonSpacing * 2, buttonWidth / 2 - 3, buttonHeight, () -> this.mc.shutdown(), "quit");

        //this.altManagerButton = new MenuTextButton(buttonX, buttonY + buttonHeight * 2 + buttonSpacing * 2, buttonWidth, buttonHeight, () -> mc.displayGuiScreen(Client.INSTANCE.getAltManagerMenu()), "AltManager(needs developement)");

        // Re-create the logo animation for not having to care about its reset
        this.animation = new Animation(Easing.EASE_OUT_QUINT, 600);

        // Putting all buttons in an array for handling mouse clicks
        this.menuButtons = new MenuButton[]{this.singlePlayerButton, this.multiPlayerButton,this.optionsButton,this.quitButton};
    }
}
