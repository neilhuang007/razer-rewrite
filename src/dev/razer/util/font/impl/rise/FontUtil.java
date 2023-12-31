package dev.razer.util.font.impl.rise;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.io.IOException;

public class FontUtil {

    static Minecraft mc = Minecraft.getMinecraft();

    private static final IResourceManager RESOURCE_MANAGER = mc.getResourceManager();

    /**
     * Method which gets a font by a resource name
     *
     * @param resource resource name
     * @param size     font size
     * @return font by resource
     */
    public static Font getResource(final String resource, final int size) {
        try {
            //System.out.println(Font.createFont(Font.TRUETYPE_FONT, RESOURCE_MANAGER.getResource(new ResourceLocation(resource)).getInputStream()).deriveFont((float) size));
            return Font.createFont(Font.TRUETYPE_FONT, RESOURCE_MANAGER.getResource(new ResourceLocation(resource)).getInputStream()).deriveFont((float) size);
        } catch (final FontFormatException ignored) {
            System.out.println("FontFormatException");
            return null;
        } catch (final IOException ignored) {
            System.out.println("IOException");
            return null;
        }
    }
}
