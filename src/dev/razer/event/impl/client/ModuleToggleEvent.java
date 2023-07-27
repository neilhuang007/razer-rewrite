package dev.razer.event.impl.client;

import dev.razer.event.types.Event;
import dev.razer.modules.Module;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class ModuleToggleEvent implements Event {
    private Module module;
}
