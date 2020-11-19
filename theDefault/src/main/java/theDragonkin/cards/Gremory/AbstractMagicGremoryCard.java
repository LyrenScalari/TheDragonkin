package theDragonkin.cards.Gremory;


        import basemod.BaseMod;
        import basemod.abstracts.AbstractCardModifier;
        import basemod.helpers.CardModifierManager;
        import com.megacrit.cardcrawl.actions.AbstractGameAction;
        import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
        import com.megacrit.cardcrawl.cards.AbstractCard;
        import com.megacrit.cardcrawl.cards.CardGroup;
        import com.megacrit.cardcrawl.characters.AbstractPlayer;
        import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
        import com.megacrit.cardcrawl.monsters.AbstractMonster;
        import com.megacrit.cardcrawl.powers.AbstractPower;
        import theDragonkin.CardMods.DarkenCardMod;
        import theDragonkin.CardMods.TailwindCardmod;
        import theDragonkin.CustomTags;
        import theDragonkin.powers.ChillPower;
        import theDragonkin.powers.JoltedPower;
        import theDragonkin.powers.modifyMagicPower;
        import java.util.ArrayList;

        import static theDragonkin.characters.TheDefault.Enums.Dragonkin_Red_COLOR;


public abstract class AbstractMagicGremoryCard extends AbstractGremoryCard {

    public boolean HotStreak = false;
    public boolean Tailwind = false;
    public boolean Galeforce = true;
    public boolean FiredUp = false;
    public boolean FiredUpUp = false;
    public int GaleforceBonus = 4;
    public static ArrayList<AbstractMagicGremoryCard> Windcards = new ArrayList<>();
    public int MagDamage;
    public int baseMagDamage;
    public boolean MagDamageModified;
    public boolean MagDamageUpgraded;
    public static CardGroup AllCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

    public AbstractMagicGremoryCard(String id, String img, int cost, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, img, cost, type, color, rarity, target);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        if (this.hasTag(CustomTags.Ice)) {
            HotStreak = false;
            addToBot(new AbstractGameAction() {
                public void update() {
                    if (!abstractMonster.isDying && abstractMonster.currentHealth > 0 && !abstractMonster.isEscaping) {
                        if (abstractMonster.lastDamageTaken > 0) {
                            addToBot(new ApplyPowerAction(abstractMonster, AbstractDungeon.player, new ChillPower(abstractMonster, AbstractDungeon.player, 1), 1));
                            isDone = true;
                        } else isDone = true;
                    } else isDone = true;
                }
            });
        }
        if (this.hasTag(CustomTags.Fire)) {
            HotStreak = true;
        }
        if (this.hasTag(CustomTags.Wind)) {
            HotStreak = false;
            if (Windcards.size() > 0) {
                Windcards.clear();
            }
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (c.hasTag(CustomTags.Wind)) {
                    Windcards.add((AbstractMagicGremoryCard) c);
                }
            }if (!Tailwind) {
                Tailwind = true;
                for (AbstractMagicGremoryCard c : Windcards) {
                    addToBot(new AbstractGameAction() {
                        public void update() {
                            CardModifierManager.addModifier(c, new TailwindCardmod(1, 1, GaleforceBonus));
                            isDone = true;
                        }
                    });
                }
            }else{Tailwind = false;}
        }

        if (this.hasTag(CustomTags.Thunder)) {
            HotStreak = false;
            addToBot(new AbstractGameAction() {
                public void update() {
                    if (!abstractMonster.isDying && abstractMonster.currentHealth > 0 && !abstractMonster.isEscaping) {
                        if (abstractMonster.lastDamageTaken > 0) {
                            addToBot(new ApplyPowerAction(abstractMonster, AbstractDungeon.player, new JoltedPower(abstractMonster, AbstractDungeon.player, 1), 1));
                            isDone = true;
                        } else isDone = true;
                    } else isDone = true;
                }
            });
        }
        if (this.hasTag(CustomTags.Dark)) {
            for (AbstractCard c : AllCards.group)  {
                for (AbstractCardModifier m : CardModifierManager.modifiers(c)) {
                    AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
                        @Override
                        public void update() {
                            if (m instanceof DarkenCardMod) {
                                CardModifierManager.removeSpecificModifier(c, m, true);
                                isDone = true;
                            }
                            isDone = true;
                        }
                    });
                }
            }
            HotStreak = false;
        }
        if (this.hasTag(CustomTags.Light)) {
            HotStreak = false;
            for (AbstractCard c : AllCards.group) {
                for (AbstractCardModifier m : CardModifierManager.modifiers(c)) {
                    AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
                        @Override
                        public void update() {
                            if (m instanceof DarkenCardMod) {
                                CardModifierManager.removeSpecificModifier(c, m, true);
                                isDone = true;
                            }
                            isDone = true;
                        }
                    });
                }
            }
        }
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        float temp = baseMagDamage;
        super.calculateCardDamage(mo);
        if (!this.isMultiDamage) {
            for (AbstractPower p : AbstractDungeon.player.powers) {
                if (p instanceof modifyMagicPower) {
                    temp = ((modifyMagicPower) p).modifyMagicCard(this, temp);
                }
            }
            for (AbstractPower p : mo.powers) {
                if (p instanceof modifyMagicPower) {
                    temp = ((modifyMagicPower) p).modifyMagicCard(this, temp);
                }
            }
            this.MagDamage = (int) temp;
        }
    }



    @Override
    public void atTurnStart() {
        HotStreak = false;
        Tailwind = false;
        Galeforce = true;
        FiredUp = false;
        FiredUpUp = false;
        GaleforceBonus = 4;
        AllCards.clear();
        for (AbstractCard c : AbstractDungeon.player.hand.group){
            AllCards.addToBottom(c);
        }
        for (AbstractCard c : AbstractDungeon.player.drawPile.group){
            AllCards.addToBottom(c);
        }
        for (AbstractCard c : AbstractDungeon.player.discardPile.group){
            AllCards.addToBottom(c);
        }
    }
}
