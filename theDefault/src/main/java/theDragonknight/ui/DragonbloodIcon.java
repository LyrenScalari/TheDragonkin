package theDragonknight.ui;

import com.evacipated.cardcrawl.mod.stslib.icons.AbstractCustomIcon;
import theDragonknight.DragonknightMod;
import theDragonknight.util.TextureLoader;

public class DragonbloodIcon  extends AbstractCustomIcon {
    public static final String ID = DragonknightMod.makeID("Dragonblood");
    private static DragonbloodIcon singleton;

    public DragonbloodIcon() {
        super(ID, TextureLoader.getTexture("theDragonknightResources/images/ui/powerIcons/Dragonblood.png"));
    }

    public static DragonbloodIcon get()
    {
        if (singleton == null) {
            singleton = new DragonbloodIcon();
        }
        return singleton;
    }
}