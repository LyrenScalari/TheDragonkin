package theDragonkin.actions;

import basemod.ReflectionHacks;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import com.megacrit.cardcrawl.vfx.stance.DivinityParticleEffect;
import theDragonkin.util.DivineEyeParticle;
import theDragonkin.util.SmiteEffect;
import theDragonkin.util.SmiteLightningEffect;

import java.util.function.Supplier;

public class SmiteAction extends AbstractGameAction {
    private DamageInfo info;
    public boolean doDamage = false;
    private boolean shot = false;
    public Supplier<AbstractGameAction> BonusEffect;
    public SmiteAction(AbstractCreature target, DamageInfo info, Supplier<AbstractGameAction> BonusEffect) {
        setValues(target, this.info = info);
        actionType = ActionType.DAMAGE;
        this.BonusEffect = BonusEffect;
    }
    public SmiteAction(AbstractCreature target, DamageInfo info) {
        setValues(target, this.info = info);
        actionType = ActionType.DAMAGE;
    }
    @Override
    public void update() {
        if (shouldCancelAction()) {
            isDone = true;
            return;
        }
        if (!shot) {
            AbstractDungeon.effectList.add(new SmiteEffect(target, this));
            for (int i = 0; i < AbstractDungeon.miscRng.random(20, 30); ++i) {
                AbstractDungeon.effectsQueue.add(new DivineEyeParticle());
            }
            shot = true;
        }
        if (doDamage && !target.isDeadOrEscaped()) {
            target.damage(info);
            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }
        if (doDamage || target.isDying) {
            isDone = true;
        }
    }
}