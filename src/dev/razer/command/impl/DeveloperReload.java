package dev.razer.command.impl;


import dev.razer.Razer;
import dev.razer.api.Rise;
import dev.razer.command.Command;
import dev.razer.module.api.DevelopmentFeature;
import dev.razer.util.chat.ChatUtil;

/**
 * @author Alan
 * @since 10/19/2021
 */
@Rise
@DevelopmentFeature
public final class DeveloperReload extends Command {

    public DeveloperReload() {
        super("Reloads the client", "developerreload", "dr");
    }

    @Override
    public void execute(final String[] args) {
        Razer.INSTANCE.terminate();
        Razer.INSTANCE.initRise();
        ChatUtil.display("Reloaded Rise");
    }
}