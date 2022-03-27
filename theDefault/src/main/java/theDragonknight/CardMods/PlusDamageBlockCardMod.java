package theDragonknight.CardMods;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class PlusDamageBlockCardMod extends AbstractCardModifier {
@Override
public AbstractCardModifier makeCopy() {
        return new PlusDamageBlockCardMod();
        }
public float modifyDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        return damage+2;
        }
public float modifyBlock(float block, AbstractCard card) {
        return block+2;
        }
        }
