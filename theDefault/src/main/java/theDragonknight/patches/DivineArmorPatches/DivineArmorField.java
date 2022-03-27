package theDragonknight.patches.DivineArmorPatches;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;

@SpirePatch(
        cls = "com.megacrit.cardcrawl.core.AbstractCreature",
        method = "<class>"
)
public class DivineArmorField {
    public static SpireField<Integer> DivineArmor = new SpireField(() -> {
        return 0;
    });

    public DivineArmorField() {
    }
}
