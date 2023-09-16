package dev.razer.module.api.manager;


import dev.razer.Razer;
import dev.razer.event.Listener;
import dev.razer.event.Priorities;
import dev.razer.event.annotations.EventLink;
import dev.razer.event.impl.input.KeyboardInputEvent;
import dev.razer.module.Module;
import dev.razer.module.api.Category;
import dev.razer.module.impl.render.Interface;
import dev.razer.util.localization.Localization;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Patrick
 * @since 10/19/2021
 */
public final class ModuleManager extends ArrayList<Module> {

    @EventLink(value = Priorities.VERY_HIGH)
    public final Listener<KeyboardInputEvent> onKey = event -> {

        if (event.getGuiScreen() != null) return;

        this.stream()
                .filter(module -> module.getKeyCode() == event.getKeyCode())
                .forEach(Module::toggle);
    };

    /**
     * Called on client start and when for some reason when we reinitialize (idk custom modules?)
     */
    public void init() {
//        final Reflections reflections = new Reflections("com.riseclient.rise.module.impl");
//
//        reflections.getSubTypesOf(Module.class).forEach(clazz -> {
//            try {
//                if (!Modifier.isAbstract(clazz.getModifiers()) && clazz != Interface.class && (clazz != Test.class || Rise.DEVELOPMENT_SWITCH)) {
//                    this.add(clazz.newInstance());
//                }
//            } catch (final Exception e) {
//                e.printStackTrace();
//            }
//        });

        // Automatic initializations
        this.stream().filter(module -> module.getModuleInfo().autoEnabled()).forEach(module -> module.setEnabled(true));

        // Has to be a listener to handle the key presses
        Razer.INSTANCE.getEventBus().register(this);
    }

    public List<Module> getAll() {
        return new ArrayList<>(this);
    }

    public <T extends Module> T get(final String name) {
        // noinspection unchecked
        return (T) this.stream()
                .filter(module -> Localization.get(module.getDisplayName()).replace(" ", "").equalsIgnoreCase(name.replace(" ", "")))
                .findAny().orElse(null);
    }

    public <T extends Module> T get(final Class<T> clazz) {
        // noinspection unchecked
        return (T) this.stream()
                .filter(module -> module.getClass() == clazz)
                .findAny().orElse(null);
    }

    public List<Module> get(final Category category) {
        return this.stream()
                .filter(module -> module.getModuleInfo().category() == category)
                .collect(Collectors.toList());
    }

    @Override
    public boolean add(final Module module) {
        final boolean result = super.add(module);
        this.updateArraylistCache();
        return result;
    }

    @Override
    public void add(final int i, final Module module) {
        super.add(i, module);
        this.updateArraylistCache();
    }

    @Override
    public Module remove(final int i) {
        final Module result = super.remove(i);
        this.updateArraylistCache();
        return result;
    }

    @Override
    public boolean remove(final Object o) {
        final boolean result = super.remove(o);
        this.updateArraylistCache();
        return result;
    }


    private void updateArraylistCache() {
        final Interface interfaceModule = this.get(Interface.class);
        if (interfaceModule == null) return;

        interfaceModule.createArrayList();
    }
}