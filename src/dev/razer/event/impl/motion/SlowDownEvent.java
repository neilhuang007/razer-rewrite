package dev.razer.event.impl.motion;


import dev.razer.event.CancellableEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Strikeless
 * @since 13.03.2022
 */
@Getter
@Setter
@AllArgsConstructor
public class SlowDownEvent extends CancellableEvent {
    private float strafeMultiplier;
    private float forwardMultiplier;
}
