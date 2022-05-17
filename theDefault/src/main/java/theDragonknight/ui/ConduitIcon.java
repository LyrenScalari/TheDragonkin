package theDragonknight.ui;

import com.evacipated.cardcrawl.mod.stslib.icons.AbstractCustomIcon;
import theDragonknight.DragonknightMod;
import theDragonknight.util.TextureLoader;

public class ConduitIcon extends AbstractCustomIcon {
    public static final String ID = DragonknightMod.makeID("Conduit");
    private static ConduitIcon singleton;

    public ConduitIcon() {
        super(ID, TextureLoader.getTexture("theDragonknightResources/images/ui/powerIcons/ArcConduit.png"));
    }

    public static ConduitIcon get()
    {
        if (singleton == null) {
            singleton = new ConduitIcon();
        }
        return singleton;
    }
}