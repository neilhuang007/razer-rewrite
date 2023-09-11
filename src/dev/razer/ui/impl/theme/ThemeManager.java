package dev.razer.ui.impl.theme;

import dev.razer.ui.theme.Themes;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public final class ThemeManager {
    private Themes theme = Themes.WATER;
}
