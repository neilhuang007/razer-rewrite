package dev.razer.managers;

import dev.razer.Razer;
import dev.razer.modules.Module;
import dev.razer.modules.impl.chat.TickEventTester;
import dev.razer.modules.utils.Settings;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class ModuleManager{
    // modules and sorting out?????
    public ArrayList<Module> modules = new ArrayList();
    public List<Module> sortedModules = new ArrayList<Module>();
    public List<String> sortedModulesABC = new ArrayList<String>();



    // called on init for modules that needs to be disabled when loading ex: Killaura, scaffold and etc.
    public void init() {
        this.modules.add(new TickEventTester());
    }






}

