package theDragonknight.ui;

import com.evacipated.cardcrawl.mod.stslib.icons.AbstractCustomIcon;
import theDragonknight.DragonknightMod;
import theDragonknight.util.TextureLoader;

public class WeakIcon extends AbstractCustomIcon {
    public static final String ID = DragonknightMod.makeID("Weak");
    private static WeakIcon singleton;

    public WeakIcon() {
        super(ID, TextureLoader.getTexture("theDragonknightResources/images/ui/powerIcons/Weak.png"));
    }

    public static WeakIcon get()
    {
        if (singleton == null) {
            singleton = new WeakIcon();
        }
        return singleton;
    }
}