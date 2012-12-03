package cards;

import roma.Game;

public class GrimReaper extends Card {
	private final static int PRICE = 6;
	private final static int DEFENCE = 3;
	private final static String TITLE = "GRIMREAPER";
	private final static CardType TYPE = CardType.CHARACTER;
	
	public GrimReaper() {
		super(PRICE,DEFENCE,TITLE,TYPE);
	}
	
	@Override
	public void activate(Game g) {
		System.out.println("Muahahahaha!");
	}

}
