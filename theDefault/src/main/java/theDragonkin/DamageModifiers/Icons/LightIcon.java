package theDragonkin.DamageModifiers.Icons;

import com.evacipated.cardcrawl.mod.stslib.icons.AbstractCustomIcon;
import theDragonkin.DragonkinMod;
import theDragonkin.util.TextureLoader;

public class LightIcon extends AbstractCustomIcon {
    public static final String ID = DragonkinMod.makeID("Light");
    private static LightIcon singleton;

    public LightIcon() {
        super(ID, TextureLoader.getTexture("theDragonkinResources/images/ui/DivineArmor.png"));
    }

    public static LightIcon get()
    {
        if (singleton == null) {
            singleton = new LightIcon();
        }
        return singleton;
    }
}