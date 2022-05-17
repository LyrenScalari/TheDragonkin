package theDragonknight.ui;

import com.evacipated.cardcrawl.mod.stslib.icons.AbstractCustomIcon;
import theDragonknight.DragonknightMod;
import theDragonknight.util.TextureLoader;

public class VulnerableIcon extends AbstractCustomIcon {
    public static final String ID = DragonknightMod.makeID("Vulnerable");
    private static VulnerableIcon singleton;

    public VulnerableIcon() {
        super(ID, TextureLoader.getTexture("theDragonknightResources/images/ui/powerIcons/Vulnerable.png"));
    }

    public static VulnerableIcon get()
    {
        if (singleton == null) {
            singleton = new VulnerableIcon();
        }
        return singleton;
    }
}