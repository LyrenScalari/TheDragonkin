package theDragonkin.DamageModifiers.Icons;

import com.evacipated.cardcrawl.mod.stslib.icons.AbstractCustomIcon;
import theDragonkin.DragonkinMod;
import theDragonkin.util.TextureLoader;

public class IceIcon extends AbstractCustomIcon {
    public static final String ID = DragonkinMod.makeID("Ice");
    private static IceIcon singleton;

    public IceIcon() {
        super(ID, TextureLoader.getTexture("theDragonkinResources/images/ui/Ice.png"));
    }

    public static IceIcon get()
    {
        if (singleton == null) {
            singleton = new IceIcon();
        }
        return singleton;
    }
}