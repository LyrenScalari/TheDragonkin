package theDragonkin.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.CollectorCurseEffect;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import com.megacrit.cardcrawl.vfx.combat.OrbFlareEffect;

import java.util.Iterator;

public class ShadowOrbPassiveAction extends AbstractGameAction {
    private DamageInfo info;
    private AbstractOrb orb;

    public ShadowOrbPassiveAction(DamageInfo info, AbstractOrb or) {
        this.info = info;
        this.orb = orb;
        this.actionType = ActionType.DAMAGE;
        this.attackEffect = AttackEffect.NONE;
    }

    public void update() {
            AbstractCreature m = AbstractDungeon.getRandomMonster();
            if (m != null) {
                float speedTime = 0.2F / (float)AbstractDungeon.player.orbs.size();
                if (Settings.FAST_MODE) {
                    speedTime = 0.0F;
                }

                this.info.output = AbstractOrb.applyLockOn(m, this.info.base);
                this.addToTop(new DamageAction(m, this.info, AttackEffect.NONE, true));
                this.addToTop(new VFXAction(new CollectorCurseEffect(m.drawX, m.drawY), speedTime));
            }


        this.isDone = true;
    }
}