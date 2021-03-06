package it.shortener.utility;

import it.shortener.DAO.UrlAssociationDAO;

public class ShortUrlGenerator {
	public static final int URL_LENGHT = 7;
	private static UrlAssociationDAO uaDAO=UrlAssociationDAO.getInstance();
	public static void main(String[] args) {
		System.out.println(ShortUrlGenerator.generateShortUrl("la mammina"));

	}

	public static String generateShortUrl(String longUrl){
		boolean isUniqueShortUrl=false;
		String generatedShortUrl=ShortUrlGenerator.generateShortUrlString(longUrl);
		isUniqueShortUrl=uaDAO.isUnique(generatedShortUrl);
		while(!isUniqueShortUrl){	
	        	generatedShortUrl=ShortUrlGenerator.generateShortUrl(longUrl+Math.random()*1000);
	        	isUniqueShortUrl=uaDAO.isUnique(generatedShortUrl);
		}
		return generatedShortUrl;
	}
	private static String generateShortUrlString(String longUrl) {

		String[] elements = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
				"k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v",
				"w", "x", "y", "z", "1", "2", "3", "4", "5", "6", "7", "8",
				"9", "0", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
				"K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
				"W", "X", "Y", "Z" };
		String convertedString = "";
		long toBeConverted = Math.abs(longUrl.hashCode());
		int numOfDiffChars = elements.length;
		
		if (toBeConverted < numOfDiffChars + 1 && toBeConverted > 0) {

			convertedString = elements[(int) (toBeConverted - 1)];
		} else if (toBeConverted > numOfDiffChars) {

			long mod = 0;
			long multiplier = 0;
			boolean determinedTheLength = false;
			for (int j = URL_LENGHT; j >= 0; j--) {
				
				multiplier = (long) (toBeConverted / Math
						.pow(numOfDiffChars, j));
				if (multiplier > 0 && toBeConverted >= numOfDiffChars) {
					convertedString += elements[(int) multiplier];
					determinedTheLength = true;
				} else if (determinedTheLength && multiplier == 0) {
					convertedString += elements[0];
				} else if (toBeConverted < numOfDiffChars) {
					convertedString += elements[(int) mod];
				}
				mod = (long) (toBeConverted % Math.pow(numOfDiffChars, j));
				toBeConverted = mod;
			}
		}
		return convertedString;
	}
}
