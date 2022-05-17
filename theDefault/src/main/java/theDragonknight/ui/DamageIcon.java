package theDragonknight.ui;

import com.evacipated.cardcrawl.mod.stslib.icons.AbstractCustomIcon;
import theDragonknight.DragonknightMod;
import theDragonknight.util.TextureLoader;

public class DamageIcon extends AbstractCustomIcon {
    public static final String ID = DragonknightMod.makeID("Damage");
    private static DamageIcon singleton;

    public DamageIcon() {
        super(ID, TextureLoader.getTexture("theDragonknightResources/images/ui/Impact.png"));
    }

    public static DamageIcon get()
    {
        if (singleton == null) {
            singleton = new DamageIcon();
        }
        return singleton;
    }
}