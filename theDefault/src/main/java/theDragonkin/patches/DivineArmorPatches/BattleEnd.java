package theDragonkin.patches.DivineArmorPatches;

import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theDragonkin.DragonkinMod;

@SpirePatch(
        clz = AbstractRoom.class,
        method = "endBattle"
)
public class BattleEnd {
    public BattleEnd() {
    }

    public static void Prefix(AbstractRoom __instance) {DivineArmorField.DivineArmor.set(AbstractDungeon.player, 0);
        DragonkinMod.Seals.clear();
    }
}
