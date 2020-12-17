package theDragonkin.orbs;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.orbs.*;

import java.util.ArrayList;

public abstract class AbstractGrovekeeperOrb extends AbstractOrb {
    private String passiveDescription;
    private String evokeDescription;

    public AbstractGrovekeeperOrb(String ID, String NAME, int basePassiveAmount, int baseEvokeAmount, String passiveDescription, String evokeDescription, String imgPath) {
        this.ID = ID;
        this.name = NAME;
        this.basePassiveAmount = basePassiveAmount;
        this.passiveAmount = this.basePassiveAmount;
        this.baseEvokeAmount = baseEvokeAmount;
        this.evokeAmount = this.baseEvokeAmount;
        this.passiveDescription = passiveDescription;
        this.evokeDescription = evokeDescription;
        this.img = ImageMaster.loadImage(imgPath);
        this.updateDescription();
    }

    public void updateDescription() {
        this.applyFocus();
        this.description = "#yPassive: " + this.passiveDescription + " NL #yEvoke: " + this.evokeDescription;
    }
    public void applyNaturePower(){

    }

    public AbstractOrb getRandomBloom(boolean useCardRng) {
        ArrayList<AbstractOrb> orbs = new ArrayList();
        orbs.add(new InvigoratingBloom());
        orbs.add(new LifeBloom());
        orbs.add(new ToxicBloom());
        orbs.add(new ThornBloom());
        orbs.add(new PrimalBloom());
        return useCardRng ? (AbstractOrb)orbs.get(AbstractDungeon.cardRandomRng.random(orbs.size() - 1)) : (AbstractOrb)orbs.get(MathUtils.random(orbs.size() - 1));
    }

    @Override
    public void applyFocus() {
        applyNaturePower();
    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.c);
        sb.draw(this.img, this.cX - 48.0F + this.bobEffect.y / 4.0F, this.cY - 48.0F + this.bobEffect.y / 4.0F, 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, false, false);
        this.renderText(sb);
        this.hb.render(sb);
    }
}

