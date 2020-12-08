package theDragonkin.cards.Gremory;

        import basemod.abstracts.AbstractCardModifier;
        import basemod.helpers.CardModifierManager;
        import com.megacrit.cardcrawl.actions.AbstractGameAction;
        import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
        import com.megacrit.cardcrawl.cards.AbstractCard;
        import com.megacrit.cardcrawl.cards.CardGroup;
        import com.megacrit.cardcrawl.cards.DamageInfo;
        import com.megacrit.cardcrawl.characters.AbstractPlayer;
        import com.megacrit.cardcrawl.core.CardCrawlGame;
        import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
        import com.megacrit.cardcrawl.localization.UIStrings;
        import com.megacrit.cardcrawl.monsters.AbstractMonster;
        import com.megacrit.cardcrawl.powers.AbstractPower;
        import theDragonkin.CardMods.AfterglowCardMod;
        import theDragonkin.CardMods.DarkenCardMod;
        import theDragonkin.CardMods.TailwindCardmod;
        import theDragonkin.DefaultMod;
        import theDragonkin.powers.WindsSong;
        import theDragonkin.CustomTags;
        import theDragonkin.actions.WitherAction;
        import theDragonkin.powers.ChillPower;
        import theDragonkin.powers.JoltedPower;
        import theDragonkin.powers.modifyMagicPower;
        import java.util.ArrayList;


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
    public String witherEffect = "";
    public boolean upgradedMisc = false;
    public static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("WitherCard");
    // misc (found in AbstractCard):    // the current power of the card. Wither effects change this. Use !I! in card descriptions to show this value.
    public int baseMisc = 1;                // the starting power of the card. Does not change as the card withers.
    public boolean isDepleted = false;  // if the card's power has withered to 0.
    public int witherAmount = 1;        // how much the card withers, ie. the 2 in "Wither 2." Also used by replenish effects.

    public static CardGroup AllCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

    public AbstractMagicGremoryCard(String id, String img, int cost, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, img, cost, type, color, rarity, target);
        if (this.rarity == CardRarity.COMMON){
            this.baseMisc = 9;
            this.misc = this.baseMisc;
        }
        if (this.rarity == CardRarity.UNCOMMON){
            this.baseMisc = 6;
            this.misc = this.baseMisc;
        }
        if (this.rarity == CardRarity.RARE){
            this.baseMisc = 3;
            this.misc = this.baseMisc;
        }
        if (this.rarity == CardRarity.SPECIAL){
            this.baseMisc = 2;
            this.misc = this.baseMisc;
        }
    }
    @Override
    public boolean hasEnoughEnergy (){
        if (this.isDepleted) {
            this.cantUseMessage = uiStrings.TEXT[1];
            return false;
        }
        return super.hasEnoughEnergy();
    }

    @Override
    public AbstractCard makeStatEquivalentCopy(){
        AbstractCard c = super.makeStatEquivalentCopy();
        if (c.misc == 0) ((AbstractMagicGremoryCard)c).onDepleted();
        return c;
    }

    public String getMiscValueText(){
        return uiStrings.TEXT[3] + this.misc + uiStrings.TEXT[4];
    }

    @Override
    public void applyPowers() {

        if (!this.isDepleted && ((this.baseMisc > 0 && this.misc <= 0) || (this.baseMisc < 0 && this.misc >= 0))){
            onDepleted();
        } else if (this.isDepleted && ((this.baseMisc < 0 && this.misc < 0) || (this.baseMisc > 0 && this.misc > 0))){
            onRestored();
        }

        // game crashes if calculating multidamage in a null room (ie. when loading a file), so avoid that.
        boolean tmp = this.isMultiDamage;
        if (AbstractDungeon.currMapNode == null){
            this.isMultiDamage = false;
        }
        // Only do base applyPowers for cards in hand. This causes their displayed dmg/block to change based on in-battle effects.
        if (AbstractDungeon.player != null && AbstractDungeon.player.hand.contains(this)) super.applyPowers();
        this.isMultiDamage = tmp;

    }

    public void onDepleted(){
        if (this.isDepleted) return;

        this.isDepleted = true;
        this.misc = 0;
        this.applyPowers(); // updates damage/block values to 0 instead of negative if misc dips too low.
        this.initializeDescription();
    }
    public void onRestored(){
        if (!this.isDepleted) return;

        this.isDepleted = false;
        this.applyPowers();
        this.initializeDescription();
    }

    public void replenish(int numUses){
        int tmp = this.misc;

        this.misc -= this.witherAmount * numUses;
        if (tmp <= this.baseMisc && this.misc > this.baseMisc ||
                tmp >= this.baseMisc && this.misc < this.baseMisc){
            // can't replenish beyond original value
            this.misc = this.baseMisc;
        }

        this.flash();
        applyPowers();
    }

    public void triggerOnGlowCheck() {
        //if (AbstractDungeon.player.hasPower(NegationPower.POWER_ID)) {

            // glow green if the wither would be negated
            //this.glowColor = AbstractCard.GREEN_BORDER_GLOW_COLOR.cpy();

        //} else
            {

            // glow purple if this would wither a card in the master deck when played
                for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
                    if (c.uuid.equals(uuid)) {
                        this.glowColor = BLUE_BORDER_GLOW_COLOR.cpy();
                        return;
                    }
                }

            // glow blue if the card will wither in combat, but won't affect master deck
            // this might be too confusing to players idk
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR;
        }
    }

    @Override
    public void initializeDescription(){
        // This is fine to not interact with pluralization, it just adds and removes "Depleted" from description.
        if (this.isDepleted && !this.rawDescription.startsWith(uiStrings.TEXT[0])){
            this.rawDescription = uiStrings.TEXT[0] + this.rawDescription;
        } else if (!this.isDepleted && this.rawDescription.startsWith(uiStrings.TEXT[0])){
            this.rawDescription = this.rawDescription.replaceFirst(uiStrings.TEXT[0],"");
        }
        super.initializeDescription();
    }

    protected void upgradeMisc(int amount){
        this.baseMisc += amount;
        this.misc += amount;
        this.upgradedMisc = true;
        applyPowers();
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
            if (!this.isMultiDamage) {
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
            else {
                addToBot(new AbstractGameAction() {
                    public void update() {
                        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters){
                            if (!mo.isDying && mo.currentHealth > 0 && !mo.isEscaping) {
                                if (mo.lastDamageTaken > 0) {
                                    addToBot(new ApplyPowerAction(mo, AbstractDungeon.player, new JoltedPower(mo, AbstractDungeon.player, 1), 1));
                                    isDone = true;
                                } else isDone = true;
                            } else isDone = true;
                        }
                        isDone = true;
                    }
                });
            }
        }
        if (this.hasTag(CustomTags.Dark)) {
            for (AbstractCard c : AllCards.group)  {
                for (AbstractCardModifier m : CardModifierManager.modifiers(c)) {
                    AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
                        @Override
                        public void update() {
                            if (m instanceof AfterglowCardMod) {
                                CardModifierManager.removeSpecificModifier(c, m, true);
                                isDone = true;
                            }
                            isDone = true;
                        }
                    });
                }
            }
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
        addToTop(new WitherAction(this));
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        float temp = baseMagDamage;
        super.calculateCardDamage(mo);
        if (!this.isMultiDamage && this.target == CardTarget.ENEMY) {
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
        else if (this.target != CardTarget.SELF){
            for (AbstractPower p : AbstractDungeon.player.powers) {
                if (p instanceof modifyMagicPower) {
                    temp = ((modifyMagicPower) p).modifyMagicCard(this, temp);
                }
            }
        }
        else if (this.isMultiDamage){
            for (AbstractPower p : AbstractDungeon.player.powers) {
                if (p instanceof modifyMagicPower) {
                    temp = ((modifyMagicPower) p).modifyMagicCard(this, temp);
                }
            }
        }
    }



    @Override
    public void atTurnStart() {
        HotStreak = false;
        FiredUp = false;
        FiredUpUp = false;
        GaleforceBonus = 4;
        AllCards.clear();
        if (!(AbstractDungeon.player.hasPower(WindsSong.POWER_ID))) {
            Tailwind = false;
            Galeforce = true;
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                AllCards.addToBottom(c);
            }
            for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
                AllCards.addToBottom(c);
            }
            for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
                AllCards.addToBottom(c);
            }
        }
    }
}
