package theDragonkin.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import javassist.CtBehavior;
import theDragonkin.cards.Gremory.AbstractInvokeCard;


public class SingleCardViewPopupPatch {
    @SpirePatch(
            clz = SingleCardViewPopup.class,
            method = "render"
    )
    public static class RenderArcanaPreviewPatch {
        @SpireInsertPatch(locator = RenderArcanaPreviewPatch.Locator.class, localvars = {"card"})
        public static void AddArcanaPreview(SingleCardViewPopup instance, SpriteBatch sb, AbstractCard card) {
            AbstractInvokeCard arcanaCard = null;
            if (card instanceof AbstractInvokeCard) {
                arcanaCard = (AbstractInvokeCard) card;
            }
            AbstractCard priestessPreviewCard = null;
            AbstractCard emperorPreviewCard = null;
            AbstractCard foolPreviewCard = null;
            AbstractCard judgementPreviewCard = null;
            AbstractCard deathPreviewCard = null;
            float previewCardHeight = 0;

            if (arcanaCard != null) {
                priestessPreviewCard = arcanaCard.CardPreview1.makeStatEquivalentCopy();
                emperorPreviewCard = arcanaCard.CardPreview2.makeStatEquivalentCopy();
                judgementPreviewCard = arcanaCard.CardPreview3.makeStatEquivalentCopy();
                deathPreviewCard = arcanaCard.CardPreview4.makeStatEquivalentCopy();
                previewCardHeight = priestessPreviewCard.hb.height;
                if (card.upgraded) {
                    priestessPreviewCard.upgrade();
                    emperorPreviewCard.upgrade();
                    foolPreviewCard.upgrade();
                    judgementPreviewCard.upgrade();
                    deathPreviewCard.upgrade();
                }
            }

            float drawScale = 0.75f;
            float xPosition1 = card.current_x + previewCardHeight * 0.6f;
            float xPosition2 = card.current_x + previewCardHeight * 1.6f;
            float xPosition3 = card.current_x + previewCardHeight * 1.2f;
            float yPosition1 = card.current_y + previewCardHeight * 0.90f;
            float yPosition2 = card.current_y + previewCardHeight * 2.70f;
            float yPosition3 = card.current_y + previewCardHeight * 2.95f;

            if (priestessPreviewCard != null) {
                priestessPreviewCard.drawScale = (float) (drawScale * 1.15);
                priestessPreviewCard.current_x = xPosition1;
                priestessPreviewCard.current_y = yPosition1;
                priestessPreviewCard.render(sb);
            }

            if (emperorPreviewCard != null) {
                emperorPreviewCard.drawScale = (float) (drawScale * 1.15);
                emperorPreviewCard.current_x = xPosition1;
                emperorPreviewCard.current_y = yPosition2;
                emperorPreviewCard.render(sb);
            }
            if (foolPreviewCard != null) {
                foolPreviewCard.drawScale = (float) (drawScale * 1.15);
                foolPreviewCard.current_x = xPosition3;
                foolPreviewCard.current_y = yPosition3;
                foolPreviewCard.render(sb);
            }

            if (judgementPreviewCard != null) {
                judgementPreviewCard.drawScale = (float) (drawScale * 1.15);
                judgementPreviewCard.current_x = xPosition2;
                judgementPreviewCard.current_y = yPosition2;
                judgementPreviewCard.render(sb);
            }
            if (deathPreviewCard != null) {
                deathPreviewCard.drawScale = (float) (drawScale * 1.15);
                deathPreviewCard.current_x = xPosition2;
                deathPreviewCard.current_y = yPosition1;
                deathPreviewCard.render(sb);
            }
        }
        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(SingleCardViewPopup.class, "renderTips");
                int[] lines = LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
                lines[0] ++;
                return lines;
            }
        }
    }
}