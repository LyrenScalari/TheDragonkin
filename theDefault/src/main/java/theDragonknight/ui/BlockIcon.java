package theDragonknight.ui;

import com.evacipated.cardcrawl.mod.stslib.icons.AbstractCustomIcon;
import theDragonknight.DragonknightMod;
import theDragonknight.util.TextureLoader;

public class BlockIcon extends AbstractCustomIcon {
    public static final String ID = DragonknightMod.makeID("Block");
    private static BlockIcon singleton;

    public BlockIcon() {
        super(ID, TextureLoader.getTexture("theDragonknightResources/images/ui/Shield.png"));
    }

    public static BlockIcon get()
    {
        if (singleton == null) {
            singleton = new BlockIcon();
        }
        return singleton;
    }
}