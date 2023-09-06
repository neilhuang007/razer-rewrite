package dev.razer.event.impl.client;


import dev.razer.event.Event;
import dev.razer.module.Module;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class ModuleToggleEvent implements Event {
    private Module module;
}
