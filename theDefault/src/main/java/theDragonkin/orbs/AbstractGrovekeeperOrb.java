package theDragonkin.orbs;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.orbs.*;
import theDragonkin.powers.GroveKeeper.NaturePower;

import java.util.ArrayList;

public abstract class AbstractGrovekeeperOrb extends AbstractOrb {
    private String passiveDescription;
    private String evokeDescription;
    private static int PASSIVE_AMOUNT = 2;
    private static int EVOKE_AMOUNT = 5;
    private static int BASEEVOKE_AMOUNT = 4;
    private static int CURRENTBONUS = 0;
    private static int HarvestCounter = 0;
    public AbstractGrovekeeperOrb(String ID, String NAME, int basePassiveAmount, int baseEvokeAmount, String passiveDescription, String evokeDescription, String imgPath) {
        this.ID = ID;
        this.name = NAME;
        CURRENTBONUS = 0;
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
    }

    public void applyNaturePower() {
        if (AbstractDungeon.player.hasPower(NaturePower.POWER_ID) && CURRENTBONUS < AbstractDungeon.player.getPower(NaturePower.POWER_ID).amount) {
            CURRENTBONUS = AbstractDungeon.player.getPower(NaturePower.POWER_ID).amount;
            baseEvokeAmount = evokeAmount + CURRENTBONUS;
            evokeAmount = evokeAmount + CURRENTBONUS;
        }
    }

    public void onHarvest(int HarvestRedux){

    }

    public static AbstractOrb getRandomBloom(boolean useCardRng) {
        ArrayList<AbstractOrb> Flowers = new ArrayList();
        Flowers.add(new InvigoratingBloom());
        Flowers.add(new LifeBloom());
        Flowers.add(new ToxicBloom());
        Flowers.add(new ThornBloom());
        Flowers.add(new PrimalBloom());
        return useCardRng ? (AbstractOrb)Flowers.get(AbstractDungeon.cardRandomRng.random(Flowers.size() - 1)) : (AbstractOrb)Flowers.get(MathUtils.random(Flowers.size() - 1));
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

