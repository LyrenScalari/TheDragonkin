package theDragonknight.ui;

import com.evacipated.cardcrawl.mod.stslib.icons.AbstractCustomIcon;
import theDragonknight.DragonknightMod;
import theDragonknight.util.TextureLoader;

public class CleaveIcon extends AbstractCustomIcon {
    public static final String ID = DragonknightMod.makeID("Cleave");
    private static CleaveIcon singleton;

    public CleaveIcon() {
        super(ID, TextureLoader.getTexture("theDragonknightResources/images/ui/Slash.png"));
    }

    public static CleaveIcon get()
    {
        if (singleton == null) {
            singleton = new CleaveIcon();
        }
        return singleton;
    }
}
