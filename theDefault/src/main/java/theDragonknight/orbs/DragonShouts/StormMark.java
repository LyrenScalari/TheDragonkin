package theDragonknight.orbs.DragonShouts;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import theDragonknight.DragonknightMod;
import theDragonknight.actions.SmiteAction;
import theDragonknight.orbs.OnUseCardOrb;
import theDragonknight.util.AbstractDragonMark;
import theDragonknight.util.AbstractNotOrb;

public class StormMark extends AbstractDragonMark implements OnUseCardOrb {
    public static final String ORB_ID = DragonknightMod.makeID("MarkofStorm");
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    public static final String[] DESCRIPTIONS = orbString.DESCRIPTION;
    public static AbstractMonster target;
    public StormMark(AbstractCreature Owner){
        super();
        Sealstrings = orbString;
        owner = Owner;
        name = orbString.DESCRIPTION[8] + orbString.NAME;
        BreakAmount = 7;
        PlayerAmount = BasePlayerAmount = 2;
        updateAnimation();
    }
    public AbstractDragonMark MakeCopy(){
        return new StormMark(owner);
    }
    public void onEndOfTurn() {
        if (target != null && owner == AbstractDungeon.player){
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new LightningEffect(target.hb.cX,target.hb.cY)));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(target,new DamageInfo(owner,PlayerAmount, DamageInfo.DamageType.THORNS)));
        } else {
            AbstractDungeon.actionManager.addToBottom(new DamageRandomEnemyAction(new DamageInfo(owner,PlayerAmount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.LIGHTNING));
        }
        for (AbstractNotOrb orb : DragonknightMod.Seals){
            if (orb instanceof AbstractDragonMark){
                if (((AbstractDragonMark) orb).owner instanceof AbstractMonster){
                    AbstractDungeon.actionManager.addToBottom(new VFXAction(new LightningEffect(((AbstractDragonMark) orb).owner.hb.cX,(((AbstractDragonMark) orb).owner.hb.cY))));
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(target,new DamageInfo(owner,PlayerAmount, DamageInfo.DamageType.THORNS)));
                }
            }
        }
    }
    public void updateDescription() {
        if (owner != AbstractDungeon.player) {
            description = DESCRIPTIONS[5] + DESCRIPTIONS[6] + owner.name + DESCRIPTIONS[7];
        } else {
            description =  DESCRIPTIONS[1]+ DESCRIPTIONS[2] + DESCRIPTIONS[3] + PlayerAmount + DESCRIPTIONS[4] + target.name +")";
        }
    }

    @Override
    public void onUseCardOrb(AbstractCard card, UseCardAction action) {
        if (card.target == AbstractCard.CardTarget.ENEMY){
            target = (AbstractMonster) action.target;
        }
    }
}