package theDragonkin.DamageModifiers.Icons;

import com.evacipated.cardcrawl.mod.stslib.icons.AbstractCustomIcon;
import theDragonkin.DragonkinMod;
import theDragonkin.util.TextureLoader;

public class ArcaneIcon extends AbstractCustomIcon {
    public static final String ID = DragonkinMod.makeID("Arcane");
    private static ArcaneIcon singleton;

    public ArcaneIcon() {
        super(ID, TextureLoader.getTexture("theDragonkinResources/images/ui/Spell.png"));
    }

    public static ArcaneIcon get()
    {
        if (singleton == null) {
            singleton = new ArcaneIcon();
        }
        return singleton;
    }
}