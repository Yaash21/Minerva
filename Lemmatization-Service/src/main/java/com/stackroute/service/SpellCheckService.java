package com.stackroute.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
public class SpellCheckService {

	private String spelling;

	public String getSpelling() {
		return spelling;
	}

	public String calculateDistance(String query) {
		List<String> listAfterCheck = new ArrayList<String>();
		try {
			ClassPathResource classPathResource = new ClassPathResource("dictionaryTechWords.txt");
			InputStream inputStream = classPathResource.getInputStream();
			File file = File.createTempFile("test", ".txt");
			try {
				FileUtils.copyInputStreamToFile(inputStream, file);
			} finally {
				IOUtils.closeQuietly(inputStream);
			}
			BufferedReader br = new BufferedReader(new FileReader(file));
			String[] queryList = query.split("\\s+");

			// System.out.println(queryList.toString());
			// List<String> queryList1 = new ArrayList<String>();
			// for(String word:queryList){
			// if(word.contains("NN")){
			// String[] words = word.split("\\s+");
			// //System.out.println(Arrays.toString(words));
			// for(String str:words){
			// //System.out.println(str);
			// int index = str.indexOf("/");
			// queryList1.add(str.substring(0, index));
			// }
			// }
			// else{
			// int index = word.indexOf("/");
			// queryList1.add(word.substring(0, index));
			// }
			// }

			// System.out.println(queryList1.toString());

			String currentLine;
			int prevCheck = 1;
			int currCheck = 1;
			boolean flag = false;
			spelling = "correct";
			for (String word1 : queryList) {
				// System.out.println(word1);
				int prevDist = 20;
				String replaceWord = null;
				int distance = 0;
				try {
					while ((currentLine = br.readLine()) != null) {

						distance = calculate(currentLine, word1.toLowerCase());
						System.out.println(distance + " " + word1 + " " + currentLine);
						if (distance == 0) {
							replaceWord = word1;
							flag = true;
							currCheck = 1;
							break;
						}

						else if (distance == 1) {
							flag = true;
							replaceWord = currentLine;
							prevDist = distance;
							currCheck = 0;
						}

						else if (distance == 2) {
							if (prevDist > distance) {
								// System.out.println("in 2");
								flag = true;
								replaceWord = currentLine;
								prevDist = distance;
								currCheck = 0;
							}
						}

					}

					if (prevCheck == 1 && currCheck == 1)
						prevCheck = 1;
					else
						prevCheck = 0;

					if (flag == false) {
						// System.out.println(word1);
						replaceWord = word1;
					}

					br = new BufferedReader(new FileReader(file));

				} catch (IOException e) {
					e.printStackTrace();
				}
				flag = false;
				listAfterCheck.add(replaceWord);
			}

			if (prevCheck == 1)
				spelling = "correct";
			else
				spelling = "wrong";

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		String lemmatize = "";
		for (String s : listAfterCheck) {
			lemmatize += s + " ";
		}

		return lemmatize;

	}

	public static int calculate(String x, String y) {
		int[][] dp = new int[x.length() + 1][y.length() + 1];

		for (int i = 0; i <= x.length(); i++) {
			for (int j = 0; j <= y.length(); j++) {
				if (i == 0) {
					dp[i][j] = j;
				} else if (j == 0) {
					dp[i][j] = i;
				} else {
					dp[i][j] = min(dp[i - 1][j - 1] + costOfSubstitution(x.charAt(i - 1), y.charAt(j - 1)),
							dp[i - 1][j] + 1, dp[i][j - 1] + 1);
				}
			}
		}

		return dp[x.length()][y.length()];
	}

	public static int costOfSubstitution(char a, char b) {
		return a == b ? 0 : 1;
	}

	public static int min(int... numbers) {
		return Arrays.stream(numbers).min().orElse(Integer.MAX_VALUE);
	}

	// public static void main(String args[])
	// {
	// MainService mainService = new MainService();
	//
	// LemmatizerService lemmatizerService= new LemmatizerService();
	//
	// String lemmatize = "";
	// String str2 = "";
	// String str1="Defin how sprin boot working";
	//
	// String listAfterLemmatizing=lemmatizerService.lemmatize(str1);
	//
	// //System.out.println(levelstienDistance.calculateDistance(str1).toString());
	// String listFromSpellChecks=calculateDistance(listAfterLemmatizing);

	// PosTaggingService posTaggingService = new PosTaggingService();
	// System.out.println(posTaggingService.posTagging(lemmatize));
	// mainService.getConceptAndIntent(posTaggingService.posTagging(lemmatize));

	// }

}
