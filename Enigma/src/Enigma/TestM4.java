package Enigma;

public class TestM4 {
	
	public static void main (String[] args) {
		// simple M4 test
		System.out.println("starting simple test");
		EnigmaMachine m4 = EnigmaBuilder.constructM4("6664C");
		m4.setIndicators("AAAAA");
		String plainText = "SDKLFJSLDFJKSLDFKJSDFLKJELKSJDFLKSJDLKSDJFLKJSDF";
		String encoded =   "AEOVXIPCPVZUDSMZCIRRPJCDOYSGATOYBBVSCIBGRKMOSOPU";
		String result = m4.encipher(plainText);
		assert(result.equals(encoded));
		
		// rotor indicator test
		
		// organised in list 4321B, as that's how each tells whether the
		// next steps. 
		// how to set each one it's corresponding indicator?
		// easy with immobile reflector, 
		
		// ah! setIndicators is a public function, so you can change that to
		// your heart's content without messing up M3. Done?
		m4 = EnigmaBuilder.constructM4("1111A");
		// slowest, slow, medium, fast, reflector
		m4.setIndicators("AAAZA");
		plainText = "A";
		encoded   = "H";
		result = m4.encipher(plainText);
		assert(result.equals(encoded));
		
		// another simple test
		
		m4 = EnigmaBuilder.constructM4("4142A");
		m4.setIndicators("AFAFA");
		plainText = "JJJJJFEIFKSIIQZZFKEIJNVVCOOW";
		encoded   = "OVGGKNRQKXGTZSJKUILBXUCXRZLU";
		result = m4.encipher(plainText);
		assert(result.equals(encoded));
		
		// double stepping
		
		m4 = EnigmaBuilder.constructM4("2178B");
		m4.setIndicators("COMPP");
		plainText = "ENIGMAENGMAENIGMAENIGMA";
		encoded   = "SMCRAHCZHJPNAMVAZWWFKBW";
		result = m4.encipher(plainText);
		//assert(result.equals(encoded));
		
		m4 = EnigmaBuilder.constructM4("1182C");
		m4.setIndicators("NAZIS");
		plainText = "OURFORMOFGOVERNMENTDOESNOTENTERINTORIVALRYWITHTHEINSTITUTIONS" +
	            "OFOTHERSOURGOVERNMENTDOESNOTCOPYOURNEIGHBORSBUTISANEXAMPLE" +
	            "TOTHEMITISTRUETHATWEARECALLEDADEMOCRACYFORTHEADMINISTRATION" +
	            "ISINTHEHANDSOFTHEMANYANDNOTOFTHEFEWBUTWHILETHEREEXISTSEQUAL" +
	            "JUSTICETOALLANDALIKEINTHEIRPRIVATEDISPUTESTHECLAIMOFEXCELLENCE" +
	            "ISALSORECOGNIZEDANDWHENACITIZENISINANYWAYDISTINGUISHEDHEISPREFERRED" +
	            "TOTHEPUBLICSERVICENOTASAMATTEROFPRIVILEGEBUTASTHEREWARDOFMERIT" +
	            "NEITHERISPOVERTYANOBSTACLEBUTAMANMAYBENEFITHISCOUNTRYWHATEVER" +
	            "THEOBSCURITYOFHISCONDITIONTHEREISNOEXCLUSIVENESSINOURPUBLICLIFE" +
	            "ANDINOURPRIVATEBUSINESSWEARENOTSUSPICIOUSOFONEANOTHERNORANGRYWITH" +
	            "OURNEIGHBORIFHEDOESWHATHELIKESWEDONOTPUTONSOURLOOKSATHIMWHICHTHOUGH" +
	            "HARMLESSARENOTPLEASANTWHILEWEARETHUSUNCONSTRAINEDINOURPRIVATEBUSINESS" +
	            "ASPIRITOFREVERENCEPERVADESOURPUBLICACTSWEAREPREVENTEDFROMDOINGWRONG" +
	            "BYRESPECTFORTHEAUTHORITIESANDFORTHELAWSHAVINGAPARTICULARREGARDTOTHOSE" +
	            "WHICHAREORDAINEDFORTHEPROTECTIONOFTHEINJUREDASWELLASTHOSEUNWRITTENLAWS" +
	            "WHICHBRINGUPONTHETRANSGRESSOROFTHEMTHEREPROBATIONOFTHEGENERALSENTIMENT";
		
		encoded   = "BAZBMJXZYJQDVFIZUVNJMLEOAEQGWJHVBWIABNIYDNNSOQCJMVDZHQLQKZQSPKVGFLXTOEBHDEFJXBBIWUZCMTBHWPVGZGCXEXNQFGMPACXGURVXYVTXGYKEQMXIIVPQWP" +
			      "TQLUSJHEDYSQXVMZLBFHFQRUJRJSMIVDEUCLBPWOOXOFYZDQYVVYGMTCNPEVEIQUPQOAIJVSYWBYPQDPJUGKGVTRXNQMZREMXKCZQUGSBECCBIMTXTKQTPERCIWUKRZXJU" +
			      "OBUCRIYNMSXADNDPJYESLJVHEWQFQNRLSWQMCEGBACQMDKWVPIEXNKEPFSPOGDNIVSUHGSAWAXVPKLHTSZDXQVCGNVNIHEZOUTXZWMACTIKQARRDNIUHMMIBBEPIAKUTOI" +
			      "XSFJLLFGXOAHHPQAJSDWCESPGALKPWMPUPVNWLZDARPTNCWDPZHPLHKHNMFSXSKGHTKRCWEBVTSJAKKAGXJQNALHPVWECFKKYAKATUJPMQHYDYVKHNDFIQKLBNPQJPKAZI" +
			      "RHLVOPZYAGMLCPGKCGQBMHZTOIFRQPBPDITQAMIWEBFHQRKZOQHPOPCCYJYFKIMBSFZGLYMECXTIKWIHDWMPCXWDTBBCTCSWNMXHVWGWWKPHPJLSNASLPZYJXRSUVJZMIR" +
			      "CFUYNGPRJCWCEJSFDMYHOQYVYFECWUFJXQQRSOQQYQBHCPXDEHAJDKUAPPYTOHQTFTIJVXYSWYQXCTVLUVQZKBWLQNGGFRWEYNHDPWNKHYRLBLBDUWDRVHMSWEIHMBHQQE" +
			      "GKMYRSTZSZINLLMOYAVMPSCAQKBZJIHRHGMTOSAIPJWJQVMKDNSNCQUSLHVRSELUYIJSKTEJIYWFCIUOJACSZQNJVSWYKPLYFRKZFESBFXUCQXAVSLTJNPIVINPRMEUOHV" +
			      "UEUEWJOSQIIRDQBIRWOSPXOXTRNYCTPZOECZODXYMGBOVEGUQBWTNVGTSEATTABPRZWCQQMRUDUOHWDGURTVGVHISMSQSKZLRESGBTYLYXTGSWYGPOXJQEV";
		result = m4.encipher(plainText);
		try {
			assert(result.equals(encoded));
		} catch (AssertionError e) {
			System.out.println(encoded);
			System.out.println(result);
			System.exit(0);
		}
		
		System.out.println("All tests passed, you are awesome!");
	}

}
