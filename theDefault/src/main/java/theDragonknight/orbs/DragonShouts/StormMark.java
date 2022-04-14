package theDragonknight.orbs.DragonShouts;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
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
import theDragonknight.powers.FrostbitePower;
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
        BreakAmount = 3;
        PlayerAmount = BasePlayerAmount = 3;
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
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters){
            if (m.hasPower(FrostbitePower.POWER_ID)){
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new LightningEffect(m.hb.cX,m.hb.cY)));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(target,new DamageInfo(m,PlayerAmount, DamageInfo.DamageType.THORNS)));
            }
        }
        if (owner instanceof AbstractMonster){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner,owner,new FrostbitePower(owner,owner,BreakAmount)));
            AbstractDragonMark that = this;
            AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
                @Override
                public void update() {
                    that.owner = AbstractDungeon.player;
                    isDone = true;
                }
            });
        }
    }
    public void updateDescription() {
        if (owner != AbstractDungeon.player) {
            description = DESCRIPTIONS[5] + DESCRIPTIONS[6] + DESCRIPTIONS[7];
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