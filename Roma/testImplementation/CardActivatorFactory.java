package testImplementation;


import cards.GrimReaper;
import cards.Kat;
import roma.Game;
import framework.interfaces.activators.CardActivator;
import framework.interfaces.activators.implementations.*;

public class CardActivatorFactory {
	
	public static CardActivator getActivator(String cardName, int position, Game g) {
		
		CardActivator activator = null;
		if (cardName.equals(("Sicarius").toUpperCase())) {
			activator = new MySicariusActivator(position, g);			//done!
		} else if (cardName.equals(("Architectus").toUpperCase())) {
			activator = new MyArchitectusActivator(position);
		} else if (cardName.equals(("Consiliarius").toUpperCase())) {
			activator = new MyConsiliariusActivator(position);
		} else if (cardName.equals(("Legat").toUpperCase())) {
			activator = new MyLegatActivator(position, g);				//done!
		} else if (cardName.equals(("Gladiator").toUpperCase())) {
			activator = new MyGladiatorActivator(position, g);			//done!
		} else if (cardName.equals(("Mercator").toUpperCase())) {
			activator = new MyMercatorActivator(position, g);			//done!
		} else if (cardName.equals(("Consul").toUpperCase())) {
			activator = new MyConsulActivator(position, g);				//done!
		} else if (cardName.equals(("Legionarius").toUpperCase())) {
			activator = new MyLegionariusActivator(position, g);		//done!
		} else if (cardName.equals(("Nero").toUpperCase())) {
			activator = new MyNeroActivator(position, g);				//done!
		} else if (cardName.equals(("Praetorianus").toUpperCase())) {
			activator = new MyPraetorianusActivator(position, g);		//done!
		} else if (cardName.equals(("Scaenicus").toUpperCase())) {
			activator = new MyScaenicusActivator(position, g);
		} else if (cardName.equals(("Haruspex").toUpperCase())) {
			activator = new MyHaruspexActivator(position, g);			//done!
		} else if (cardName.equals(("Senator").toUpperCase())) {
			activator = new MySenatorActivator(position);
		} else if (cardName.equals(("Velites").toUpperCase())) {
			activator = new MyVelitesActivator(position, g);			//done!
		} else if (cardName.equals(("Essedum").toUpperCase())) {
			activator = new MyEssedumActivator(position, g);			//done!
		} else if (cardName.equals(("TribunusPlebis").toUpperCase())) {
			activator = new MyTribunusPlebisActivator(position, g);		//done!
		} else if (cardName.equals(("Centurio").toUpperCase())) {
			activator = new MyCenturioActivator(position, g);			//done!

			// END CHARACTERS
		} else if (cardName.equals(("Aesculapinum").toUpperCase())) {
			activator = new MyAesculapinumActivator(position, g);		//done!		
		} else if (cardName.equals(("Basilica").toUpperCase())) {
			//activator = new MydefaultCard(0, 0, "BASILICA", CardType.BUILDING);
		} else if (cardName.equals(("Machina").toUpperCase())) {
			activator = new MyMachinaActivator(position);
		} else if (cardName.equals(("Forum").toUpperCase())) {
			activator = new MyForumActivator(position, g);				//done!
		} else if (cardName.equals(("Mercatus").toUpperCase())) {
			activator = new MyMercatusActivator(position, g);			//done!
		} else if (cardName.equals(("Onager").toUpperCase())) {
			activator = new MyOnagerActivator(position, g);				//done!
		} else if (cardName.equals(("Templum").toUpperCase())) {
			//activator = new MydefaultCard(0, 0, "TEMPLUM", CardType.BUILDING);
		} else {
			System.out.println(("testImplementation.CardActivatorFactory:\nInvalidCardName"));
			System.out.println(cardName);
		}
		return activator;
	}
}
