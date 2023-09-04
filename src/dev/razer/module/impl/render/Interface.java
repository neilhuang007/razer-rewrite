package dev.razer.module.impl.render;

import dev.razer.Razer;
import dev.razer.event.Listener;
import dev.razer.event.annotations.EventLink;
import dev.razer.event.impl.motion.PreUpdateEvent;
import dev.razer.event.impl.render.Render2DEvent;
import dev.razer.module.Module;
import dev.razer.module.api.Category;
import dev.razer.module.api.ModuleInfo;
import dev.razer.module.impl.render.interfaces.ModernInterface;
import dev.razer.module.impl.render.interfaces.ModuleComponent;
import dev.razer.util.Timers.StopWatch;
import dev.razer.util.font.Font;
import dev.razer.util.localization.Localization;
import dev.razer.util.math.MathUtil;
import dev.razer.value.Value;
import dev.razer.value.impl.BooleanValue;
import dev.razer.value.impl.ModeValue;
import dev.razer.value.impl.SubMode;
import lombok.Getter;
import lombok.Setter;

import javax.vecmath.Vector2d;
import javax.vecmath.Vector2f;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

@Getter
@Setter
@ModuleInfo(name = "module.render.interface.name", description = "module.render.interface.description", category = Category.RENDER, autoEnabled = true)
public final class Interface extends Module {

    public final BooleanValue limitChatWidth = new BooleanValue("Limit Chat Width", this, false);
    public final BooleanValue smoothHotBar = new BooleanValue("Smooth Hot Bar", this, true);
    private final ModeValue mode = new ModeValue("Mode", this, () -> false) {{
        add(new ModernInterface("Modern", (Interface) this.getParent()));
        setDefault("Modern");
    }};
    private final ModeValue modulesToShow = new ModeValue("Modules to Show", this, () -> false) {{
        add(new SubMode("All"));
        add(new SubMode("Exclude render"));
        add(new SubMode("Only bound"));
        setDefault("Exclude render");
    }};
    private final StopWatch stopwatch = new StopWatch();
    private final StopWatch updateTags = new StopWatch();
    public BooleanValue suffix = new BooleanValue("Suffix", this, true, () -> false);
    public BooleanValue lowercase = new BooleanValue("Lowercase", this, false, () -> false);
    public BooleanValue removeSpaces = new BooleanValue("Remove Spaces", this, false, () -> false);
    public BooleanValue shaders = new BooleanValue("Shaders", this, true);
    public Font widthComparator = nunitoNormal;
    public float moduleSpacing = 12, edgeOffset;
    StopWatch lastUpdate = new StopWatch();
    private ArrayList<ModuleComponent> allModuleComponents = new ArrayList<>(),
            activeModuleComponents = new ArrayList<>();
    @EventLink()
    public final Listener<PreUpdateEvent> onPreUpdate = event -> {
        if (lastUpdate.finished(1000)) {
            threadPool.execute(() -> {
                for (final ModuleComponent moduleComponent : allModuleComponents) {
                    moduleComponent.setTranslatedName(Localization.get(moduleComponent.getModule().getDisplayName()));
                }
            });
        }
    };
    @EventLink()
    public final Listener<Render2DEvent> onRender2D = event -> {

        for (final ModuleComponent moduleComponent : allModuleComponents) {
            if (moduleComponent.getModule().isEnabled()) {
                moduleComponent.animationTime = Math.min(moduleComponent.animationTime + stopwatch.getElapsedTime() / 100.0F, 10);
            } else {
                moduleComponent.animationTime = Math.max(moduleComponent.animationTime - stopwatch.getElapsedTime() / 100.0F, 0);
            }
        }

        threadPool.execute(() -> {
            if (updateTags.finished(50)) {
                updateTags.reset();

                for (final ModuleComponent moduleComponent : activeModuleComponents) {
                    if (moduleComponent.animationTime == 0) {
                        continue;
                    }

                    for (final Value<?> value : moduleComponent.getModule().getValues()) {
                        if (value instanceof ModeValue) {
                            final ModeValue modeValue = (ModeValue) value;
                            moduleComponent.setTag(modeValue.getValue().getName());
                            break;
                        }

                        moduleComponent.setTag("");
                    }
                }

                this.sortArrayList();
            }

            final float screenWidth = event.getScaledResolution().getScaledWidth();
            final Vector2f position = new Vector2f(0, 0);
            for (final ModuleComponent moduleComponent : activeModuleComponents) {
                if (moduleComponent.animationTime == 0) {
                    continue;
                }

                moduleComponent.targetPosition = new Vector2d(screenWidth - moduleComponent.getNameWidth() - moduleComponent.getTagWidth(), position.getY());

                if (!moduleComponent.getModule().isEnabled() && moduleComponent.animationTime < 10) {
                    moduleComponent.targetPosition = new Vector2d(screenWidth + moduleComponent.getNameWidth() + moduleComponent.getTagWidth(), position.getY());
                } else {
                    position.setY(position.getY() + moduleSpacing);
                }

                float offsetX = edgeOffset;
                float offsetY = edgeOffset;

                moduleComponent.targetPosition.x -= offsetX;
                moduleComponent.targetPosition.y += offsetY;

                if (Math.abs(moduleComponent.getPosition().getX() - moduleComponent.targetPosition.x) > 0.5 || Math.abs(moduleComponent.getPosition().getY() - moduleComponent.targetPosition.y) > 0.5 || (moduleComponent.animationTime != 0 && moduleComponent.animationTime != 10)) {
                    for (int i = 0; i < stopwatch.getElapsedTime(); ++i) {
                        moduleComponent.position.x = MathUtil.lerp(moduleComponent.position.x, moduleComponent.targetPosition.x, 1.5E-2F);
                        moduleComponent.position.y = MathUtil.lerp(moduleComponent.position.y, moduleComponent.targetPosition.y, 1.5E-2F);
                    }
                } else {
                    moduleComponent.position = moduleComponent.targetPosition;
                }
            }

            stopwatch.reset();
        });
    };
    private SubMode lastFrameModulesToShow = (SubMode) modulesToShow.getValue();

    public Interface() {
        createArrayList();
    }

    public void createArrayList() {
        allModuleComponents.clear();
        Razer.INSTANCE.getModuleManager().getAll().stream()
                .sorted(Comparator.comparingDouble(module -> -widthComparator.width(Localization.get(module.getDisplayName()))))
                .forEach(module -> allModuleComponents.add(new ModuleComponent(module)));
    }

    public void sortArrayList() {
//        ArrayList<ModuleComponent> components = new ArrayList<>();
//        Client.INSTANCE.getModuleManager().getAll().forEach(module -> components.add(new ModuleComponent(module)));
//
        activeModuleComponents = allModuleComponents.stream()
                .filter(moduleComponent -> moduleComponent.getModule().shouldDisplay(this))
                .sorted(Comparator.comparingDouble(module -> -module.getNameWidth() - module.getTagWidth()))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}