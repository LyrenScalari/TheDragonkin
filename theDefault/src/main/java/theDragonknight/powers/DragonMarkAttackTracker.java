package theDragonknight.powers;

import com.evacipated.cardcrawl.mod.stslib.patches.NeutralPowertypePatch;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theDragonknight.DragonkinMod;
import theDragonknight.util.AbstractDragonMark;
import theDragonknight.util.AbstractNotOrb;

public class DragonMarkAttackTracker extends AbstractPower implements InvisiblePower {
    public static final String POWER_ID = DragonkinMod.makeID("DragonMark");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public DragonMarkAttackTracker() {
        name = NAME;
        ID = POWER_ID;
        this.owner = AbstractDungeon.player;
        priority = 70;
        type = NeutralPowertypePatch.NEUTRAL;
        isTurnBased = false;

        updateDescription();
    }
    @Override
    public void onAttack(DamageInfo info, int d, AbstractCreature target){
        boolean sent = false;
        for (AbstractNotOrb orb : DragonkinMod.Seals){
            if (orb instanceof AbstractDragonMark && !sent){
                if (target != null) {
                    sent = true;
                    ((AbstractDragonMark) orb).owner = target;
                }
            }
        }
    }
}
