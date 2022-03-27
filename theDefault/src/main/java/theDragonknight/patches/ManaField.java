package theDragonknight.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;

@SpirePatch(
        cls = "com.megacrit.cardcrawl.core.AbstractCreature",
        method = "<class>"
)
public class ManaField {
    public static SpireField<Integer> Mana = new SpireField(() -> {
        return 0;
    });

    public ManaField() {
    }
}
