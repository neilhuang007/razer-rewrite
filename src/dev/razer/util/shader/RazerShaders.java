package dev.razer.util.shader;

import dev.razer.util.shader.base.RazerShader;
import dev.razer.util.shader.impl.*;

public interface RazerShaders {
    AlphaShader ALPHA_SHADER = new AlphaShader();
    RazerShader POST_BLOOM_SHADER = new BloomShader();
    RazerShader UI_BLOOM_SHADER = new BloomShader();
    RazerShader UI_POST_BLOOM_SHADER = new BloomShader();
    RazerShader GAUSSIAN_BLUR_SHADER = new GaussianBlurShader();
    //RazerShader UI_GAUSSIAN_BLUR_SHADER = new GaussianBlurShader();

    RazerShader OUTLINE_SHADER = new OutlineShader();
    RQShader RQ_SHADER = new RQShader();
    RGQShader RGQ_SHADER = new RGQShader();
    ROQShader ROQ_SHADER = new ROQShader();
    ROGQShader ROGQ_SHADER = new ROGQShader();
    RazerShader MAIN_MENU_SHADER = new MainMenuBackgroundShader();
}
