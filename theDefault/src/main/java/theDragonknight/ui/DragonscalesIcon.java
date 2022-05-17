package theDragonknight.ui;

import com.evacipated.cardcrawl.mod.stslib.icons.AbstractCustomIcon;
import theDragonknight.DragonknightMod;
import theDragonknight.util.TextureLoader;

public class DragonscalesIcon extends AbstractCustomIcon {
    public static final String ID = DragonknightMod.makeID("Dragonscales");
    private static DragonscalesIcon singleton;

    public DragonscalesIcon() {
        super(ID, TextureLoader.getTexture("theDragonknightResources/images/ui/powerIcons/Dragonscales.png"));
    }

    public static DragonscalesIcon get()
    {
        if (singleton == null) {
            singleton = new DragonscalesIcon();
        }
        return singleton;
    }
}