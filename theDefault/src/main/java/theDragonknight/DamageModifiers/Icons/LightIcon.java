package theDragonknight.DamageModifiers.Icons;

import com.evacipated.cardcrawl.mod.stslib.icons.AbstractCustomIcon;
import theDragonknight.DragonkinMod;
import theDragonknight.util.TextureLoader;

public class LightIcon extends AbstractCustomIcon {
    public static final String ID = DragonkinMod.makeID("Light");
    private static LightIcon singleton;

    public LightIcon() {
        super(ID, TextureLoader.getTexture("theDragonkinResources/images/ui/Light.png"));
    }

    public static LightIcon get()
    {
        if (singleton == null) {
            singleton = new LightIcon();
        }
        return singleton;
    }
}