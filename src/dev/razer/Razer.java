package dev.razer;


import dev.razer.command.Command;
import dev.razer.component.Component;
import dev.razer.component.ComponentManager;
import dev.razer.event.bus.impl.EventBus;
import dev.razer.module.Module;
import dev.razer.module.api.manager.ModuleManager;
import dev.razer.ui.impl.standard.RiseClickGUI;
import dev.razer.ui.theme.ThemeManager;
import dev.razer.util.ReflectionUtil;
import dev.razer.util.file.FileManager;
import dev.razer.util.file.FileType;
import dev.razer.util.file.config.ConfigFile;
import dev.razer.util.file.config.ConfigManager;
import dev.razer.util.file.data.DataManager;
import dev.razer.util.localization.Locale;
import dev.razer.util.math.MathConst;
import jdk.nashorn.internal.objects.annotations.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.Display;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@lombok.Getter
public enum Razer {
    INSTANCE;

    public static String NAME = "Razer";
    public static String VERSION = "1.0";
    public static String VERSION_FULL = "1.0"; // Used to give more detailed build info on beta builds
    public static String VERSION_DATE = "June 5, 2023";
    public static Type CLIENT_TYPE = Type.RISE;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    public EventBus eventBus;
    public String RazerTitle = "1.8.9";
    @Setter
    private Locale locale = Locale.EN_US; // The language of the client
    private RiseClickGUI standardClickGUI;
    private ModuleManager moduleManager;
    private ThemeManager themeManager;
    private ConfigFile configFile;
    @Setter
    private DataManager dataManager;
    private FileManager fileManager;
    private ConfigManager configManager;
    private ComponentManager componentManager;

    @Getter
    public void initRazer() {
        // this method is toggled during the init phase of minecraft

        // Init
        Minecraft mc = Minecraft.getMinecraft();
        MathConst.calculate();

        // managers
        this.moduleManager = new ModuleManager();
        this.dataManager = new DataManager();
        this.fileManager = new FileManager();
        this.configManager = new ConfigManager();
        this.componentManager = new ComponentManager();

        // Compatibility
        mc.gameSettings.guiScale = 2;
        mc.gameSettings.ofFastRender = false;
        //mc.gameSettings.ofShowGlErrors = DEVELOPMENT_SWITCH;

        // Performance
        mc.gameSettings.ofSmartAnimations = true;
        mc.gameSettings.ofSmoothFps = false;
        mc.gameSettings.ofFastMath = false;
        this.themeManager = new ThemeManager();
        this.eventBus = new EventBus();


        this.dataManager.init();
        this.moduleManager.init();
        this.fileManager.init();
        this.configManager.init();

        // Register
        String[] paths = {
                "RazerOfficial.Razer.gg",
                "hackclient."
        };

        for (String path : paths) {
            if (!ReflectionUtil.dirExist(path)) {
                continue;
            }

            Class<?>[] classes = ReflectionUtil.getClassesInPackage(path);

            for (Class<?> clazz : classes) {
                try {
                    if (clazz.isAnnotationPresent(Hidden.class)) continue;

                    if (Component.class.isAssignableFrom(clazz) && clazz != Component.class) {
                        this.componentManager.add((Component) clazz.getConstructor().newInstance());
                    } else if (Module.class.isAssignableFrom(clazz) && clazz != Module.class) {
                        this.moduleManager.add((Module) clazz.getConstructor().newInstance());
                    } else if (Command.class.isAssignableFrom(clazz) && clazz != Command.class) {
                        this.commandManager.add((Command) clazz.getConstructor().newInstance());
                    } else if (Check.class.isAssignableFrom(clazz) && clazz != Check.class) {
                        this.packetLogManager.add((Check) clazz.getConstructor().newInstance());
                    }
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                         NoSuchMethodException exception) {
                    exception.printStackTrace();
                }
            }

            break;
        }

        // Init Managers
        this.dataManager.init();
        this.moduleManager.init();
        this.fileManager.init();
        this.configManager.init();

        final File file = new File(ConfigManager.CONFIG_DIRECTORY, "latest.json");
        this.configFile = new ConfigFile(file, FileType.CONFIG);
        this.configFile.allowKeyCodeLoading();
        this.configFile.read();

        this.standardClickGUI = new RiseClickGUI();

        Display.setTitle(NAME + " " + VERSION_FULL);

    }
}
