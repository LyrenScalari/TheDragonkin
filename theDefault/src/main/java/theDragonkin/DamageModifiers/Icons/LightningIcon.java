package theDragonkin.DamageModifiers.Icons;

import com.evacipated.cardcrawl.mod.stslib.icons.AbstractCustomIcon;
import theDragonkin.DragonkinMod;
import theDragonkin.util.TextureLoader;

public class LightningIcon extends AbstractCustomIcon {
    public static final String ID = DragonkinMod.makeID("Lightning");
    private static LightningIcon singleton;

    public LightningIcon() {
        super(ID, TextureLoader.getTexture("theDragonkinResources/images/ui/Electric.png"));
}

    public static LightningIcon get()
    {
        if (singleton == null) {
            singleton = new LightningIcon();
        }
        return singleton;
    }
}