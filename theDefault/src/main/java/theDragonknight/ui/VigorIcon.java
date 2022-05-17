package theDragonknight.ui;

import com.evacipated.cardcrawl.mod.stslib.icons.AbstractCustomIcon;
import theDragonknight.DragonknightMod;
import theDragonknight.util.TextureLoader;

public class VigorIcon extends AbstractCustomIcon {
    public static final String ID = DragonknightMod.makeID("Vigor");
    private static VigorIcon singleton;

    public VigorIcon() {
        super(ID, TextureLoader.getTexture("theDragonknightResources/images/ui/powerIcons/Vigor.png"));
    }

    public static VigorIcon get()
    {
        if (singleton == null) {
            singleton = new VigorIcon();
        }
        return singleton;
    }
}