package theDragonknight.ui;

import com.evacipated.cardcrawl.mod.stslib.icons.AbstractCustomIcon;
import theDragonknight.DragonknightMod;
import theDragonknight.util.TextureLoader;

public class BurnIcon extends AbstractCustomIcon {
    public static final String ID = DragonknightMod.makeID("Burn");
    private static BurnIcon singleton;

    public BurnIcon() {
        super(ID, TextureLoader.getTexture("theDragonknightResources/images/ui/powerIcons/Burn.png"));
    }

    public static BurnIcon get()
    {
        if (singleton == null) {
            singleton = new BurnIcon();
        }
        return singleton;
    }
}