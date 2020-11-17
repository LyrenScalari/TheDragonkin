package theDragonkin.cards.Gremory;

import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.BranchingUpgradesCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theDragonkin.CardMods.MiasmaCardMod;
import theDragonkin.CardMods.NosferatuCardMod;
import theDragonkin.CardMods.NosferatuYCardMod;
import theDragonkin.CustomTags;
import theDragonkin.DefaultMod;
import theDragonkin.characters.TheGremory;

import static theDragonkin.DefaultMod.makeCardPath;

public class Nosferatu extends AbstractMagicGremoryCard implements BranchingUpgradesCard {
    public static final String ID = DefaultMod.makeID(Nosferatu.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");


    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheGremory.Enums.Gremory_Purple_Color;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 2;

    public Nosferatu() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(CustomTags.Light);
        MagDamage = baseMagDamage = 12;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.hasTag(CustomTags.Light)){
            addToBot(new DamageAction(m, new DamageInfo(p,MagDamage, DamageInfo.DamageType.NORMAL)));
            if (upgraded) {
                for (AbstractCard c : AbstractDungeon.player.hand.group) {
                    addToBot(new AbstractGameAction() {
                        public void update() {
                            CardModifierManager.addModifier(c, new NosferatuCardMod(1, m.lastDamageTaken / 2));
                            isDone = true;
                        }
                    });
                }
                for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
                    addToBot(new AbstractGameAction() {
                        public void update() {
                            CardModifierManager.addModifier(c, new NosferatuCardMod(1, m.lastDamageTaken / 2));
                            isDone = true;
                        }
                    });
                }
                for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
                    addToBot(new AbstractGameAction() {
                        public void update() {
                            CardModifierManager.addModifier(c, new NosferatuCardMod(1, m.lastDamageTaken / 2));
                            isDone = true;
                        }
                    });
                }
            } else {
                for (AbstractCard c : AbstractDungeon.player.hand.group) {
                    addToBot(new AbstractGameAction() {
                        public void update() {
                            CardModifierManager.addModifier(c, new NosferatuYCardMod(1, m.lastDamageTaken / 2));
                            isDone = true;
                        }
                    });
                }
                for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
                    addToBot(new AbstractGameAction() {
                        public void update() {
                            CardModifierManager.addModifier(c, new NosferatuYCardMod(1, m.lastDamageTaken / 2));
                            isDone = true;
                        }
                    });
                }
                for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
                    addToBot(new AbstractGameAction() {
                        public void update() {
                            CardModifierManager.addModifier(c, new NosferatuYCardMod(1, m.lastDamageTaken / 2));
                            isDone = true;
                        }
                    });
                }
            }
        }
        else {
            addToBot(new DamageAction(m, new DamageInfo(p,MagDamage, DamageInfo.DamageType.NORMAL)));
            for (AbstractCard c : AbstractDungeon.player.hand.group){
                addToBot(new AbstractGameAction() {
                    public void update() {
                        CardModifierManager.addModifier(c, new MiasmaCardMod(1,4,1));
                        isDone = true;
                    }
                });
            }
            for (AbstractCard c : AbstractDungeon.player.drawPile.group){
                addToBot(new AbstractGameAction() {
                    public void update() {
                        CardModifierManager.addModifier(c, new MiasmaCardMod(1,4,1));
                        isDone = true;
                    }
                });
            }
            for (AbstractCard c : AbstractDungeon.player.discardPile.group){
                addToBot(new AbstractGameAction() {
                    public void update() {
                        CardModifierManager.addModifier(c, new MiasmaCardMod(1,4,1));
                        isDone = true;
                    }
                });
            }
        }
        super.use(p, m);
    }
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            if (isBranchUpgrade()) {
                branchUpgrade();
            } else {
                baseUpgrade();
            }
        }
    }

    public void baseUpgrade() {
        name = cardStrings.EXTENDED_DESCRIPTION[0];
        this.rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }

    public void branchUpgrade() {
        name = cardStrings.EXTENDED_DESCRIPTION[2];
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[3];
        tags.remove(CustomTags.Light);
        tags.add(CustomTags.Dark);
        baseMagDamage -= 5;
        this.upgradeBaseCost(1);
        MagDamageUpgraded = true;
        initializeDescription();

    }
}
