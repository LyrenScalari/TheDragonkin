package theDragonkin.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.cardManip.CardGlowBorder;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import theDragonkin.cards.Gremory.AbstractMagicGremoryCard;

public class ReplenishMagicEffect extends AbstractGameEffect {

    // modified from base game's CampfireSmithEffect if you want to see how something works.

    private static final float DUR = 1.5f;
    private boolean openedScreen = false;
    private Color screenColor = AbstractDungeon.fadeColor.cpy();
    public boolean choseCard = false;
    public int replenishcount;
    public CardGroup Group;
    final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("ReplenishMagic");

    public ReplenishMagicEffect(int NumtoReplenish, CardGroup group) {
        this.duration = DUR;
        this.screenColor.a = 0.0f;
        replenishcount = NumtoReplenish;
        Group = group;
        AbstractDungeon.gridSelectScreen.selectedCards.clear();
        AbstractDungeon.overlayMenu.proceedButton.hide();
    }


    @Override
    public void dispose() {
    }
    @Override
    public void update() {
        if (!AbstractDungeon.isScreenUp) {
            this.duration -= Gdx.graphics.getDeltaTime();
            this.updateBlackScreenColor();
        }
        if(!AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            AbstractDungeon.gridSelectScreen.selectedCards.forEach(c -> {
                c.misc = ((AbstractMagicGremoryCard) c).baseMisc;
                c.applyPowers();
                AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy()));
                AbstractDungeon.topLevelEffects.add(new CardGlowBorder(c));
                choseCard = true;
                ((RestRoom) AbstractDungeon.getCurrRoom()).fadeIn();
            });
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            this.isDone = true;
        }
        if(this.duration < 1.0f && !this.openedScreen) {
            this.openedScreen = true;
            if (replenishcount < 2) {
                AbstractDungeon.gridSelectScreen.open(Group, replenishcount, uiStrings.TEXT[0], false, false, true, true);
            }
            else {
                AbstractDungeon.gridSelectScreen.open(Group, replenishcount, uiStrings.TEXT[1] + replenishcount + uiStrings.TEXT[2], false, false, true, true);
            }
        }
        if(this.duration < 0.0f) {
            this.isDone = true;
                // complete room and bring up continue button
                if(CampfireUI.hidden) {
                    AbstractRoom.waitTimer = 0.0f;
                    AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
                    // this is where the fire sound actually should cut off, logically.
                    ((RestRoom) AbstractDungeon.getCurrRoom()).cutFireSound();
                }
            }
        }
    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(screenColor);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0f, 0.0f, Settings.WIDTH, Settings.HEIGHT);
        if(AbstractDungeon.screen == AbstractDungeon.CurrentScreen.GRID) {
            AbstractDungeon.gridSelectScreen.render(sb);
        }
    }

    private void updateBlackScreenColor() {
        if(this.duration > 1.0f) {
            this.screenColor.a = Interpolation.fade.apply(1.0f, 0.0f, (this.duration - 1.0f) * 2.0f);
        } else {
            this.screenColor.a = Interpolation.fade.apply(0.0f, 1.0f, this.duration / 1.5f);
        }
    }

}
