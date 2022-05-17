package theDragonknight.ui;

import com.evacipated.cardcrawl.mod.stslib.icons.AbstractCustomIcon;
import theDragonknight.DragonknightMod;
import theDragonknight.util.TextureLoader;

public class PoisonIcon extends AbstractCustomIcon {
    public static final String ID = DragonknightMod.makeID("Poison");
    private static PoisonIcon singleton;

    public PoisonIcon() {
        super(ID, TextureLoader.getTexture("theDragonknightResources/images/ui/powerIcons/Poison.png"));
    }

    public static PoisonIcon get()
    {
        if (singleton == null) {
            singleton = new PoisonIcon();
        }
        return singleton;
    }
}