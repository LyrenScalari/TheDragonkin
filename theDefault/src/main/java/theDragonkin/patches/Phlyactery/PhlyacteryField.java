package theDragonkin.patches.Phlyactery;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;

@SpirePatch(
        cls = "com.megacrit.cardcrawl.core.AbstractCreature",
        method = "<class>"
)
public class PhlyacteryField {
    public static SpireField<Integer> BloodPoints = new SpireField(() -> {
        return 0;
    });

    public PhlyacteryField() {
    }
}
