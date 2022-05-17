package theDragonknight.ui;

import com.evacipated.cardcrawl.mod.stslib.icons.AbstractCustomIcon;
import theDragonknight.DragonknightMod;
import theDragonknight.util.TextureLoader;

public class ThornsIcon extends AbstractCustomIcon {
    public static final String ID = DragonknightMod.makeID("Thorns");
    private static ThornsIcon singleton;

    public ThornsIcon() {
        super(ID, TextureLoader.getTexture("theDragonknightResources/images/ui/powerIcons/Thorns.png"));
    }

    public static ThornsIcon get()
    {
        if (singleton == null) {
            singleton = new ThornsIcon();
        }
        return singleton;
    }
}