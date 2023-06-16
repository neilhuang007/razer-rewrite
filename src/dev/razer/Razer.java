package dev.razer;


import jdk.nashorn.internal.objects.annotations.Getter;
import net.minecraft.client.Minecraft;

public enum Razer {
    INSTANCE;

    public String RazerTitle = "1.8.9";

    public static String NAME = "Razer";
    public static String VERSION = "1.0";
    public static String VERSION_FULL = "1.0"; // Used to give more detailed build info on beta builds
    public static String VERSION_DATE = "June 5, 2023";



    /**
     * The main method when the Minecraft#startGame method is about
     * finish executing our client gets called and that's where we
     * can start loading our own classes and modules.
     */

    @Getter
    public void initRazer() {

        // Init
        Minecraft mc = Minecraft.getMinecraft();

        System.out.println("init Razer");

        changetitle();






    }

    @Getter
    public String changetitle() {
        RazerTitle = "Razer v0.1";
        return RazerTitle;
    };

}
