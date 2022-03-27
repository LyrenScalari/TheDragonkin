package theDragonknight.DamageModifiers.Icons;

import com.evacipated.cardcrawl.mod.stslib.icons.AbstractCustomIcon;
import theDragonknight.DragonkinMod;
import theDragonknight.util.TextureLoader;

public class FireIcon extends AbstractCustomIcon {
    public static final String ID = DragonkinMod.makeID("Fire");
    private static FireIcon singleton;

    public FireIcon() {
        super(ID, TextureLoader.getTexture("theDragonkinResources/images/ui/Fire.png"));
    }

    public static FireIcon get()
    {
        if (singleton == null) {
            singleton = new FireIcon();
        }
        return singleton;
    }
}