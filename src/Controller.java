import org.apache.commons.lang3.StringUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class Controller {
    private static final String[] theWordFoundHasTooManyRepeats = {};
    private static final ArrayList<CharAndCount> charAndCounts = new ArrayList<>();
    private static final ArrayList<String> finalRegs = new ArrayList<>();
    private static ArrayList<String> foundWordsWithRepeatChar = new ArrayList<>();
    private static ArrayList<String> listOfBooleansAndNulls = new ArrayList<>();

    public static void unscrabbleTheLetters(String input) {
        String inputUpper = input.toUpperCase();
        createCharAndCountObj(inputUpper);//to keep count of the number of chars per letter
        removeDuplicateChars(charAndCounts);//removes the duplicate objects that have the same char
        StringBuilder regex = createRegexFromInput(inputUpper);
        createAllRegexsForPossibleWordMatches(regex, inputUpper);
        findMatchesRegardlessOfCharCountPerChar(inputUpper);
        if (!foundWordsWithRepeatChar.isEmpty()) {

            foundWordsWithRepeatChar.forEach((w -> {
                String[] booleansAndNulls = findTheWordsThatHaveTheCorrectCountOfChars(w);
                ArrayList<String> listOfBooleansAndNulls = transferArrayToArraylist(w, booleansAndNulls);
                if (theListOfBooleansAndNullsIndicatesWordsFound(listOfBooleansAndNulls, inputUpper)) {
                    System.out.println(w);
                    clearListOfBooleansAndNulls();
                }
            }));
        } else
            System.out.println("No Matches Were Found!");
    }

    public static void clearAllLists() {
        clearCharAndCountList();
        clearfoundWordsWithRepeatChar();
        clearListOfBooleansAndNulls();
        clearFinalRegs();
    }

    private static boolean theListOfBooleansAndNullsIndicatesWordsFound(ArrayList<String> listOfBooleansAndNulls, String input) {
        if (listOfBooleansAndNulls.size() == input.length() && !listOfBooleansAndNulls.contains(null)
                || !listOfBooleansAndNulls.isEmpty()) {
            return true;
        }
        return false;
    }

    private static void createCharAndCountObj(String inputUpper) {
        for (int k = 0; k < inputUpper.length(); k++)
            charAndCounts.add(new CharAndCount(inputUpper.charAt(k), StringUtils.countMatches(inputUpper, inputUpper.charAt(k))));
    }

    private static ArrayList<String> transferArrayToArraylist(String w, String[] booleans) {
        if (booleans.length != 0) {
            for (int i = 0; i < w.length(); i++) {
                getListOfBooleansAndNulls().add(booleans[i]);
            }
        }
        return getListOfBooleansAndNulls();
    }

    private static String[] findTheWordsThatHaveTheCorrectCountOfChars(String w) {
        String[] booleansAndNulls = new String[charAndCounts.size()];
        for (int k = 0; k < charAndCounts.size(); k++) {
            int a = charAndCounts.get(k).getNumberOfChar();
            int b = StringUtils.countMatches(w, charAndCounts.get(k).getaChar());
            if (a < b) {
                return theWordFoundHasTooManyRepeats;
            }
            if ( b != 0) {
                booleansAndNulls[k] = "true";
            }
        }
        return booleansAndNulls;
    }

    private static void findMatchesRegardlessOfCharCountPerChar(String input) {
        int offset = input.length() + 3;
        for (String word : Model.getWords()) {
            for (String finalReg : finalRegs) {
                try {
                    if (Character.getNumericValue(finalReg.charAt(offset)) == word.length() && Pattern.matches(finalReg, word)) {
                        getFoundWordsWithRepeatChar().add(word);
                    }
                } catch (StringIndexOutOfBoundsException e) {
                    System.out.println("Exception occurred . . . . . . . . try again. ");
                }
            }
        }
    }

    private static void createAllRegexsForPossibleWordMatches(StringBuilder regex, String input) {
        int offset = input.length() + 3;
        for (int i = input.length(); i > 1; i--) {
            regex.insert(offset, i);
            finalRegs.add(regex.toString().trim());
            regex.replace(offset, offset + 1, "");
        }
    }

    private static void removeDuplicateChars(ArrayList<CharAndCount> charAndCounts) {

        Set set = Collections.synchronizedSet(new HashSet<>());
        // the while set.iterator.hasNext prevents Concurrent modification exception
        while (set.iterator().hasNext()) {
            for (CharAndCount ch : charAndCounts) {
                if (!set.add(ch.getaChar())) {
                    charAndCounts.remove(ch);
                }
            }
        }
    }

    private static StringBuilder createRegexFromInput(String input) {
        StringBuilder regex = new StringBuilder();
        char charToBePlacedInRegex;
        regex.append("[");
        for (int i = 0; i < input.length(); i++) {
            charToBePlacedInRegex = input.toUpperCase().charAt(i);
            regex.append(charToBePlacedInRegex);
        }
        regex.append("]{}");
        return regex;
    }

    private static void clearListOfBooleansAndNulls() {
        listOfBooleansAndNulls.clear();
    }

    private static void clearCharAndCountList() {
        charAndCounts.clear();
    }

    private static void clearfoundWordsWithRepeatChar() {
        foundWordsWithRepeatChar.clear();
    }

    private static void clearFinalRegs() {
        finalRegs.clear();
    }

    public static ArrayList<String> getFoundWordsWithRepeatChar() {
        return foundWordsWithRepeatChar;
    }

    public static void setFoundWordsWithRepeatChar(ArrayList<String> foundWordsWithRepeatChar) {
        Controller.foundWordsWithRepeatChar = foundWordsWithRepeatChar;
    }

    public static ArrayList<String> getListOfBooleansAndNulls() {
        return listOfBooleansAndNulls;
    }

    public static void setListOfBooleansAndNulls(ArrayList<String> listOfBooleansAndNulls) {
        Controller.listOfBooleansAndNulls = listOfBooleansAndNulls;
    }
}

