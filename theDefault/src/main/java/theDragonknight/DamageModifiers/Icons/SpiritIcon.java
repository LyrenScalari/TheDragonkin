package theDragonknight.DamageModifiers.Icons;

import com.evacipated.cardcrawl.mod.stslib.icons.AbstractCustomIcon;
import theDragonknight.DragonkinMod;
import theDragonknight.util.TextureLoader;

public class SpiritIcon extends AbstractCustomIcon {
    public static final String ID = DragonkinMod.makeID("Spirit");
    private static SpiritIcon singleton;

    public SpiritIcon() {
        super(ID, TextureLoader.getTexture("theDragonkinResources/images/ui/Drain.png"));
    }

    public static SpiritIcon get()
    {
        if (singleton == null) {
            singleton = new SpiritIcon();
        }
        return singleton;
    }
}