package theDragonknight.orbs.DragonShouts;

import com.evacipated.cardcrawl.mod.stslib.actions.common.GainCustomBlockAction;
import com.evacipated.cardcrawl.mod.stslib.blockmods.AbstractBlockModifier;
import com.evacipated.cardcrawl.mod.stslib.blockmods.BlockModContainer;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
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
import theDragonknight.powers.RotPower;
import theDragonknight.util.AbstractDragonMark;
import theDragonknight.util.AbstractNotOrb;
import theDragonknight.util.DragonBlock;

import java.util.List;

public class StormMark extends AbstractDragonMark implements OnUseCardOrb {
    public static final String ORB_ID = DragonknightMod.makeID("MarkofStorm");
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    public static final String[] DESCRIPTIONS = orbString.DESCRIPTION;
    public static AbstractMonster target;
    public BlockModContainer DragonBlock = new BlockModContainer(this, new DragonBlock(true));
    public StormMark(AbstractCreature Owner){
        super();
        Sealstrings = orbString;
        owner = Owner;
        name = orbString.DESCRIPTION[8] + orbString.NAME;
        BreakAmount = baseBreakAmount = 2;
        PlayerAmount = BasePlayerAmount = 3;
        updateAnimation();
    }
    public AbstractDragonMark MakeCopy(){
        return new StormMark(owner);
    }
    public void onEndOfTurn() {
        for (int i = 0; i < PainAmount ; i++) {
            TriggerPassive();
        }
    }
    public void TriggerPassive(){
        if (target == null){
            target = AbstractDungeon.getCurrRoom().monsters.getRandomMonster(true);
        }
        CardCrawlGame.sound.play("ORB_LIGHTNING_EVOKE", 0.0f);
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new LightningEffect(target.hb.cX, target.hb.cY)));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(target, new DamageInfo(owner, PlayerAmount, DamageInfo.DamageType.THORNS)));
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (m.hasPower(FrostbitePower.POWER_ID)) {
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new LightningEffect(m.hb.cX, m.hb.cY)));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(m, PlayerAmount, DamageInfo.DamageType.THORNS)));
            }
        }
    }
    public void TriggerPassive(AbstractMonster target){
        CardCrawlGame.sound.play("ORB_LIGHTNING_EVOKE", 0.0f);
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new LightningEffect(target.hb.cX, target.hb.cY)));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(target, new DamageInfo(owner, PlayerAmount, DamageInfo.DamageType.THORNS)));
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (m.hasPower(FrostbitePower.POWER_ID)) {
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new LightningEffect(m.hb.cX, m.hb.cY)));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(m, PlayerAmount, DamageInfo.DamageType.THORNS)));
            }
        }
    }
    public void WhenRemoved() {
        AbstractMonster target = AbstractDungeon.getCurrRoom().monsters.getRandomMonster(true);
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target,target,new FrostbitePower(target,target,BreakAmount)));
        super.WhenRemoved();
    }
    public void updateDescription() {
        ApplyModifers();
        if (target != null) {
            description = DESCRIPTIONS[1] + DESCRIPTIONS[2] + DESCRIPTIONS[3] + PlayerAmount + DESCRIPTIONS[4] + target.name + ")" + DESCRIPTIONS[5] + DESCRIPTIONS[6] + DESCRIPTIONS[7];
        } else description = DESCRIPTIONS[1] + DESCRIPTIONS[2] + DESCRIPTIONS[3] + PlayerAmount + DESCRIPTIONS[4] + DESCRIPTIONS[5] + DESCRIPTIONS[6] + DESCRIPTIONS[7];
    }

    @Override
    public void onUseCardOrb(AbstractCard card, UseCardAction action) {
        if (card.target == AbstractCard.CardTarget.ENEMY && card.type == AbstractCard.CardType.ATTACK){
            target = (AbstractMonster) action.target;
        }
    }
}