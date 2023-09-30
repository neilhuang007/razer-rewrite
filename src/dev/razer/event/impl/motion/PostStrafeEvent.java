package dev.razer.event.impl.motion;


import dev.razer.event.CancellableEvent;
import dev.razer.util.interfaces.InstanceAccess;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public final class PostStrafeEvent extends CancellableEvent implements InstanceAccess {

}
