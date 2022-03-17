package theDragonkin.patches.DivineArmorPatches;

import com.evacipated.cardcrawl.mod.stslib.vfx.combat.TempDamageNumberEffect;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.DamageImpactLineEffect;
import com.megacrit.cardcrawl.vfx.combat.StrikeEffect;
import javassist.CtBehavior;
import theDragonkin.DragonkinMod;
import theDragonkin.cards.Dragonkin.interfaces.ReciveDamageEffect;
import theDragonkin.cards.Dragonkin.interfaces.ReciveDamageinHandCard;
import theDragonkin.cards.Dragonkin.interfaces.ReciveModifyDamageEffect;
import theDragonkin.util.AbstractDragonMark;
import theDragonkin.util.AbstractNotOrb;
import theDragonkin.util.AbstractSeal;

import java.util.ArrayList;

@SpirePatch(
        clz = AbstractPlayer.class,
        method = "damage"
)
public class PlayerDamage {
    static boolean hadDivineArmor;

    public PlayerDamage() {
    }

    @SpireInsertPatch(
            locator = Locator.class,
            localvars = {"damageAmount", "hadBlock"}
    )
    public static void Insert(AbstractCreature __instance, DamageInfo info, @ByRef int[] damageAmount, @ByRef boolean[] hadBlock) {
        if (!AbstractDungeon.getCurrRoom().isBattleOver) {
            DragonkinMod.damagetaken = true;
            for (AbstractPower p : AbstractDungeon.player.powers) {
                if (p instanceof ReciveDamageEffect) {
                    ((ReciveDamageEffect) p).onReciveDamage(damageAmount[0]);
                }
                if (p instanceof ReciveModifyDamageEffect) {
                    ((ReciveModifyDamageEffect) p).onReciveDamage(damageAmount[0]);
                }
            }
            for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
                if (c instanceof ReciveDamageEffect) {
                    ((ReciveDamageEffect) c).onReciveDamage(damageAmount[0]);
                }
            }
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (c instanceof ReciveDamageinHandCard) {
                    ((ReciveDamageinHandCard) c).onReciveDamage(damageAmount[0]);
                }
                if (c instanceof ReciveDamageEffect) {
                    ((ReciveDamageEffect) c).onReciveDamage(damageAmount[0]);
                }
            }
            for (AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
                if (c instanceof ReciveDamageEffect) {
                    ((ReciveDamageEffect) c).onReciveDamage(damageAmount[0]);
                }
            }
            for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
                if (c instanceof ReciveDamageEffect) {
                    ((ReciveDamageEffect) c).onReciveDamage(damageAmount[0]);
                }
            }
            for (AbstractCard c : AbstractDungeon.player.limbo.group) {
                if (c instanceof ReciveDamageEffect) {
                    ((ReciveDamageEffect) c).onReciveDamage(damageAmount[0]);
                }
            }
            for (AbstractNotOrb c : DragonkinMod.Seals) {
                if (c instanceof AbstractSeal) {
                    ((ReciveDamageEffect) c).onReciveDamage(damageAmount[0]);
                }
            }
        }
    }

    @SpireInsertPatch(
            locator = StrikeEffectLocator.class
    )
    public static SpireReturn<Void> Insert(AbstractCreature __instance, DamageInfo info) {
        if (hadDivineArmor) {
            return SpireReturn.Return();
        } else {
            return SpireReturn.Continue();
        }
    }

    private static class StrikeEffectLocator extends SpireInsertLocator {
        private StrikeEffectLocator() {
        }

        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.NewExprMatcher(StrikeEffect.class);
            int[] all = LineFinder.findAllInOrder(ctMethodToPatch, new ArrayList(), finalMatcher);
            return new int[]{all[all.length - 1]};
        }
    }

    private static class Locator extends SpireInsertLocator {
        private Locator() {
        }

        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "decrementBlock");
            return offset(LineFinder.findInOrder(ctMethodToPatch, new ArrayList(), finalMatcher),0);
        }

        private static int[] offset(int[] originalArr, int offset) {
            for(int i = 0; i < originalArr.length; ++i) {
                originalArr[i] += offset;
            }

            return originalArr;
        }
    }
}
