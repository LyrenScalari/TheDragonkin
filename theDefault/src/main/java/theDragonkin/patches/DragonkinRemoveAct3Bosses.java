package theDragonkin.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheBeyond;
import theDragonkin.Monsters.TheAcolyte;
import theDragonkin.characters.TheDefault;

@SpirePatch(clz= TheBeyond.class,
        method="initializeBoss")
public class DragonkinRemoveAct3Bosses {
    public static void Postfix(Object meObj) {
        if (AbstractDungeon.player.chosenClass == TheDefault.Enums.THE_DRAGONKIN){
            TheBeyond.bossList.clear();
            TheBeyond.bossList.add(TheAcolyte.ID);
            TheBeyond.bossList.add(TheAcolyte.ID);
        }
    }
}
