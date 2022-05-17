package theDragonknight.ui;

import com.evacipated.cardcrawl.mod.stslib.icons.AbstractCustomIcon;
import theDragonknight.DragonknightMod;
import theDragonknight.util.TextureLoader;

public class RotIcon extends AbstractCustomIcon {
    public static final String ID = DragonknightMod.makeID("Rot");
    private static RotIcon singleton;

    public RotIcon() {
        super(ID, TextureLoader.getTexture("theDragonknightResources/images/ui/powerIcons/ScarletRot.png"));
    }

    public static RotIcon get()
    {
        if (singleton == null) {
            singleton = new RotIcon();
        }
        return singleton;
    }
}