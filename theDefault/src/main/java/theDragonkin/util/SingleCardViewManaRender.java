package theDragonkin.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import theDragonkin.cards.Dragonkin.AbstractDragonkinCard;

import java.util.Map;

public class SingleCardViewManaRender {
    private static float yOffset = 125.0F * Settings.scale;
    private static float centerX = (float)Settings.WIDTH / 2.0F;
    private static float centerY = (float)Settings.HEIGHT / 2.0F;
    private static Texture Bloodcast;
    private static void renderElementHelper(SpriteBatch sb, Texture img, float drawX, float drawY) {
        sb.draw(img, drawX, drawY,
                0, 0, 125.0F, 125.0F,
                Settings.scale,  Settings.scale,
                0, 0, 0, 164, 164, false, false);

    }

    public static void renderElementCost(AbstractDragonkinCard card, SpriteBatch sb){
        int[] elementCost;

        float fScaleX = FontHelper.SCP_cardEnergyFont.getData().scaleX;

        FontHelper.SCP_cardEnergyFont.getData().setScale(0.75F);

        if(!card.isLocked && card.isSeen) {

            elementCost = card.getManaCost();
            boolean loop1 = false;
            boolean loop2 = false;
            int counter = 0;
            if(Bloodcast == null){
                Bloodcast = ImageMaster.loadImage("theDragonkinResources/images/1024/ExaltIcon.png");
            }
            //logger.info("attempting render");
            for(Map.Entry<TypeEnergyHelper.Mana,Integer> e : card.energyCosts.entrySet()){
                TypeEnergyHelper.Mana mana = e.getKey();
                Texture tex;
                switch (mana) {
                    case Exalt:
                        tex = Bloodcast;
                        break;
                    default:
                        throw new IllegalStateException("Unexpected mana type");
                }
                if (card.energyCosts.get(mana) != 0) {
                    //card.renderElementHelper(sb, (float)Settings.WIDTH / 2.0F - 270.0F * Settings.scale, (float)Settings.HEIGHT / 2.0F + 380.0F * Settings.scale);
                    renderElementHelper(sb, tex, centerX - 348.0F * Settings.scale,
                            centerY + 175.0F * Settings.scale - yOffset * counter);

                    Color c = Settings.CREAM_COLOR;
                        FontHelper.renderFont(sb, FontHelper.SCP_cardEnergyFont, card.energyCosts.get(mana)+"", 677.0F * Settings.scale,
                                (float)Settings.HEIGHT / 2.0F + 260.0F * Settings.scale - yOffset * counter, c);
                    counter++;
                }
            }
        }
        FontHelper.SCP_cardEnergyFont.getData().setScale(fScaleX);
    }
}
