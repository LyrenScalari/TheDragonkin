package theDragonkin.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.DragonkinMod;

@SpirePatch(
        clz = AbstractCard.class,
        method = "canUse"
)
public class HeatCheckPatch {
    public static boolean Postfix(boolean __result, AbstractCard __instance, AbstractPlayer p, AbstractMonster m) {
        if (__instance.type == AbstractCard.CardType.STATUS && DragonkinMod.HeatValid(__instance)) {
            return true;
        } else return __result;
    }
}
