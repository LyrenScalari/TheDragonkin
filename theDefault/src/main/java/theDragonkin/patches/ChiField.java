package theDragonkin.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;

@SpirePatch(
        cls = "com.megacrit.cardcrawl.core.AbstractCreature",
        method = "<class>"
)
public class ChiField {
    public static SpireField<Integer> Chi = new SpireField(() -> {
        return 0;
    });

    public ChiField() {
    }
}
