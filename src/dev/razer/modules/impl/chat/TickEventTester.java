package dev.razer.modules.impl.chat;

import dev.razer.modules.Module;
import dev.razer.modules.utils.Settings;

public class TickEventTester extends Module {
    private final Settings<Integer> targetrange = this.register(new Settings<Integer>("target range", 8, 0, 20));


}
