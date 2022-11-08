package theDragonkin.CardMods;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class PlusDamageBlockCardMod extends AbstractCardModifier {
        int modifer = 0;
        public PlusDamageBlockCardMod(int modifer){
                this.modifer = modifer;
        }

@Override
public AbstractCardModifier makeCopy() {
        return new PlusDamageBlockCardMod(modifer);
        }
public float modifyDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        return damage+modifer;
        }
public float modifyBlock(float block, AbstractCard card) {
        return block+modifer;
        }
        }
