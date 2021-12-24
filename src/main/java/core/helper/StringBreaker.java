package core.helper;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class StringBreaker {

	public static List<String> beakString(final String str, final int maxPix, final Graphics g) {
		final List<List<String>> allLines = new ArrayList<>();
		allLines.add(new ArrayList<>());

		int countedPix = 0;
		for (final String word : str.split(" ")) {
			final FontMetrics fm = g.getFontMetrics();
			
			int stringPix = fm.stringWidth(word);
			
			countedPix += stringPix;
			
			if (countedPix > maxPix) {
				allLines.add(new ArrayList<>());
				countedPix = stringPix;
			}

			List<String> lastLine = allLines.get(allLines.size() - 1);
			lastLine.add(word);
			countedPix += fm.stringWidth(" ");
		}
		
		final List<String> out = new ArrayList<>();
		var line = new StringBuilder();
		for (final List<String> stringLine : allLines) {
			for (final String word : stringLine) {
				line.append(" ");
				line.append(word);
			}
			out.add(line.toString());
			line = new StringBuilder();
		}
		return out;
	}
}
