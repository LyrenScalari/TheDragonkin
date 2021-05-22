package theDragonkin.util;

import basemod.devcommands.relic.Relic;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
import com.evacipated.cardcrawl.modthespire.lib.SpireSuper;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rewards.chests.AbstractChest;
import com.megacrit.cardcrawl.vfx.ObtainKeyEffect;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class DragonsHoardLinkedReward extends RewardItem
{
    public List<RewardItem> relicLinks = new ArrayList<>();
    public int COMMON_CHANCE = 15;
    public int UNCOMMON_CHANCE = 10;
    public int RARE_CHANCE;
    private UIStrings uiStrings =  CardCrawlGame.languagePack.getUIString("theDragonkin:DragonsHoardRewardStrings");
    public RewardItem RelicReward;
    public DragonsHoardLinkedReward() {
        int roll = AbstractDungeon.treasureRng.random(0, 99);
        AbstractRelic Relic;
        if (roll < this.COMMON_CHANCE) {
            Relic = AbstractDungeon.returnRandomRelic(AbstractRelic.RelicTier.COMMON);
        } else if (roll < this.UNCOMMON_CHANCE + this.COMMON_CHANCE) {
            Relic= AbstractDungeon.returnRandomRelic(AbstractRelic.RelicTier.UNCOMMON);
        } else {
            Relic = AbstractDungeon.returnRandomRelic(AbstractRelic.RelicTier.RARE);
        }
        DragonsHoardLinkedReward reward1 = new DragonsHoardLinkedReward(new RewardItem(Relic));
        AbstractDungeon.getCurrRoom().rewards.add(reward1);
        addRelicLink(reward1);

        DragonsHoardLinkedReward reward2 = new DragonsHoardLinkedReward(new RareCardsReward());
        AbstractDungeon.getCurrRoom().rewards.add(reward2);
        addRelicLink(reward2);

        //DragonsHoardLinkedReward reward3 = new DragonsHoardLinkedReward(new RewardItem(Math.round(AbstractDungeon.treasureRng.random((float)75 * 0.9F, (float)75 * 1.1F))));
        //AbstractDungeon.getCurrRoom().rewards.add(reward3);
       // addRelicLink(reward3);
    }

    public DragonsHoardLinkedReward(RewardItem original){
        type = original.type;
        outlineImg = original.outlineImg;
        img = original.img;
        goldAmt = original.goldAmt;
        bonusGold = original.bonusGold;
        text = original.text;
        //relicLink = original.relicLink; // TODO?
        if (original.relicLink != null) {
            //relicLinks.add(original.relicLink);
        }
        relic = original.relic;
        potion = original.potion;
        cards = original.cards;
        //effects
        //isBoss
        hb = original.hb;
        y = original.y;
        flashTimer = original.flashTimer;
        isDone = original.isDone;
        ignoreReward = original.ignoreReward;
        redText = original.redText;
    }

    public void addRelicLink(DragonsHoardLinkedReward setRelicLink)
    {
        if (!relicLinks.contains(setRelicLink)) {
            relicLinks.add(setRelicLink);
        }
        if (!setRelicLink.relicLinks.contains(this)) {
            setRelicLink.relicLinks.add(this);
        }
    }

    private boolean isFirst()
    {
        //if (AbstractDungeon.getCurrRoom().rewards.indexOf(this) > AbstractDungeon.getCurrRoom().rewards.indexOf(relicLink)) {
        int thisIndexOf = AbstractDungeon.getCurrRoom().rewards.indexOf(this);
        for (RewardItem link : relicLinks) {
            if (AbstractDungeon.getCurrRoom().rewards.indexOf(link) < thisIndexOf) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean claimReward()
    {
        boolean ret;
        if (type == RewardType.SAPPHIRE_KEY) {
            if (!ignoreReward) {
                AbstractDungeon.topLevelEffects.add(new ObtainKeyEffect(ObtainKeyEffect.KeyColor.BLUE));
            }
            ret = true;
        } else {
            ret = super.claimReward();
        }
        if (ret) {
            for (RewardItem link : relicLinks) {
                link.isDone = true;
                link.ignoreReward = true;
            }
        }
        return ret;
    }

    @Override
    public void update()
    {
        super.update();

        if (isFirst()) {
            redText = false;
            for (RewardItem link : relicLinks) {
                link.redText = false;
            }
        }

        if (hb.hovered) {
            for (RewardItem link : relicLinks) {
                link.redText = hb.hovered;
            }
        }
    }

    @Override
    public void render(SpriteBatch sb)
    {
        super.render(sb);

        if (!relicLinks.isEmpty() && type != RewardType.SAPPHIRE_KEY) {
            if (hb.hovered) {
                // Make TipHelper think we haven't tried to render a tip this frame
                try {
                    Field f = TipHelper.class.getDeclaredField("renderedTipThisFrame");
                    f.setAccessible(true);
                    f.setBoolean(null, false);
                } catch (IllegalAccessException | NoSuchFieldException e) {
                    e.printStackTrace();
                }

                ArrayList<PowerTip> tips = new ArrayList<>();
                tips.add(new PowerTip(relic.name, relic.description));
                for (RewardItem link : relicLinks) {
                    if (link.type == RewardType.SAPPHIRE_KEY) {
                        tips.add(new PowerTip(TEXT[7], TEXT[8] + FontHelper.colorString(TEXT[6] + TEXT[9], "y")));
                    } else if (link.relic != null) {
                        tips.add(new PowerTip(TEXT[7], TEXT[8] + FontHelper.colorString(link.relic.name, "y") + TEXT[9]));
                    }
                }
                TipHelper.queuePowerTips(360.0f * Settings.scale, InputHelper.mY + 50.0f * Settings.scale, tips);
            }

            if (!isFirst()) {
                renderRelicLink(sb);
            }
        } else if (!relicLinks.isEmpty() && type == RewardType.SAPPHIRE_KEY) {
            if (hb.hovered) {
                ArrayList<PowerTip> tips = new ArrayList<>();
                for (RewardItem link : relicLinks) {
                    if (link.type == RewardType.RELIC) {
                        tips.add(new PowerTip(TEXT[7], TEXT[8] + FontHelper.colorString(link.relic.name + TEXT[9], "y")));
                    } else if (link.type == RewardType.CARD){
                        tips.add(new PowerTip(TEXT[7], TEXT[8] + FontHelper.colorString(uiStrings.TEXT[1], "y")));
                    } else {
                        tips.add(new PowerTip(TEXT[7], TEXT[8] + FontHelper.colorString(uiStrings.TEXT[0], "y")));
                    }
                }
                TipHelper.queuePowerTips(360.0f * Settings.scale, InputHelper.mY + 50.0f * Settings.scale, tips);
            }
        }

        hb.render(sb);
    }

    @SpireOverride
    protected void renderRelicLink(SpriteBatch sb)
    {
        SpireSuper.call(sb);
    }
}
