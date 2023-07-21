package dev.razer;

import dev.razer.util.localization.Locale;
import dev.razer.util.math.MathConst;
import jdk.nashorn.internal.objects.annotations.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;


@lombok.Getter
public enum Razer {
    INSTANCE;

    public String RazerTitle = "1.8.9";

    public static String NAME = "Razer";
    public static String VERSION = "1.0";
    public static String VERSION_FULL = "1.0"; // Used to give more detailed build info on beta builds
    public static String VERSION_DATE = "June 5, 2023";


    //private RiseClickGUI standardClickGUI;

    @Setter
    private Locale locale = Locale.EN_US; // The language of the client

    //private EventBus eventBus;



    /**
     * The main method when the Minecraft#startGame method is about
     * finish executing our client gets called and that's where we
     * can start loading our own classes and modules.
     */

    @Getter
    public void initRazer(){

        // Init
        Minecraft mc = Minecraft.getMinecraft();
        MathConst.calculate();

        // Compatibility
        mc.gameSettings.guiScale = 1;
        mc.gameSettings.ofFastRender = false;
        //mc.gameSettings.ofShowGlErrors = DEVELOPMENT_SWITCH;

        // Performance
        mc.gameSettings.ofSmartAnimations = true;
        mc.gameSettings.ofSmoothFps = false;
        mc.gameSettings.ofFastMath = false;




    }


}
