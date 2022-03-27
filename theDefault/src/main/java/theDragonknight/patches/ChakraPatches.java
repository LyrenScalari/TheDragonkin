package theDragonknight.patches;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theDragonknight.CustomTags;
import theDragonknight.powers.WindWalker.AbstractChakraPower;

import java.util.ArrayList;

public class ChakraPatches {
    @SpirePatch(clz = AbstractCard.class, method = "calculateCardDamage")
    public static class AddTempModifiers {

        private static final ArrayList<AbstractDamageModifier> pushedMods = new ArrayList<>();

        @SpirePrefixPatch()
        public static void addMods(AbstractCard __instance, AbstractMonster mo) {
            if (__instance != null && (__instance.hasTag(AbstractCard.CardTags.STRIKE) || (__instance.hasTag(AbstractCard.CardTags.STARTER_DEFEND) || __instance.hasTag(CustomTags.Defend)) && DamageModifierManager.modifiers(__instance).isEmpty())) {
                for (AbstractPower p : AbstractDungeon.player.powers) {
                    if (p instanceof AbstractChakraPower) {
                        //DamageModifierManager.addModifiers(__instance, DamageModifierManager.modifiers(AbstractDungeon.player.getPower(p.ID)));
                        //pushedMods.addAll(DamageModifierManager.modifiers(AbstractDungeon.player.getPower(p.ID)));
                    }
                }
            }
        }

        @SpirePostfixPatch()
        public static void removeMods(AbstractCard __instance, AbstractMonster mo) {
            DamageModifierManager.removeModifiers(__instance, pushedMods);
            pushedMods.clear();
        }
    }

    @SpirePatch(clz = AbstractCard.class, method = "renderEnergy")
    public static class RenderOnCardPatch {
        @SpirePostfixPatch
        public static void RenderOnCard(AbstractCard __instance, SpriteBatch sb) {
            if (AbstractDungeon.player != null && validLocation(__instance)) {
                if (__instance != null && DamageModifierManager.modifiers(__instance).isEmpty() && (__instance.hasTag(AbstractCard.CardTags.STRIKE) || (__instance.hasTag(AbstractCard.CardTags.STARTER_DEFEND) || __instance.hasTag(CustomTags.Defend)))) {
                    renderHelper(sb, __instance.current_x, __instance.current_y, __instance);
                }
            }
        }

        private static boolean validLocation(AbstractCard c) {
            return AbstractDungeon.player.hand.contains(c) ||
                    AbstractDungeon.player.drawPile.contains(c) ||
                    AbstractDungeon.player.discardPile.contains(c) ||
                    AbstractDungeon.player.exhaustPile.contains(c);
        }
    }
    private static void renderHelper(SpriteBatch sb, float drawX, float drawY, AbstractCard C) {
        sb.setColor(Color.WHITE.cpy());
        ArrayList<AbstractDamageModifier> mods = new ArrayList<>();
        for (AbstractPower p : AbstractDungeon.player.powers) {
            if (p instanceof AbstractChakraPower) {
                //mods.add(((AbstractChakraPower) p).GetChakraType());
            }
        }

        float dx = -(mods.size()-1)*16f;
        float dy = 210f;
        TextureAtlas.AtlasRegion img = null;
        for (AbstractDamageModifier mod : mods) {
/*            if (mod instanceof SpiritDamage){
                img = getAtlasRegion(DrainIcon.get());
            }
            if (mod instanceof LightningDamage){
                img = getAtlasRegion(ElectricIcon.get());
            }*/
            if (img != null) {
                sb.draw(img, drawX + dx + img.offsetX - (float) img.originalWidth / 2.0F, drawY + dy + img.offsetY - (float) img.originalHeight / 2.0F,
                        (float) img.originalWidth / 2.0F - img.offsetX - dx, (float) img.originalHeight / 2.0F - img.offsetY - dy,
                        (float) img.packedWidth, (float) img.packedHeight,
                        C.drawScale * Settings.scale, C.drawScale * Settings.scale, C.angle);
                dx += 32f;
            }
        }
    }
    /*private static TextureAtlas.AtlasRegion getAtlasRegion(AbstractCustomIcon icon){
        return icon.getAtlasTexture();
    }*/

}
