import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args){
        System.out.println("1 задание: " + bell(2));
        System.out.println("2 задание: " + translateWord("flag"));
        System.out.println("2 задание: " + translateSentence("I like to eat honey waffles."));
        System.out.println("3 задание: " + validColor("rgb(0,,0)"));
        System.out.println("3 задание: " + validColor("rgba(0,0,0,0.123456789)"));
        System.out.println("4 задание: " + stripUrlParams("https://edabit.com?a=1&b=2&a=2"));
        System.out.println("5 задание: " + Arrays.toString(getHashTags("Why You Will Probably Pay More for Your Christmas Tree This Year")));
        System.out.println("5 задание: " + Arrays.toString(getHashTags("Visualizing Science")));
        System.out.println("6 задание: " + ulam(206 ));
        System.out.println("7 задание: " + longestNonrepeatingSubstring("abcabcbb"));
        System.out.println("8 задание: " + convertToRoman(812));
        System.out.println("9 задание: " + formula("6 * 4 = 24"));
        System.out.println("10 задание: " + palindromeDescendant(11211230));
    }
    public static int bell(int n) {
        int[][] bell = new int[n+1][n+1];
        bell[0][0] = 1;
        /*
         Решается через треугольник Белла
         * 1
         * 1 2
         * 2 3 5
         * 5 7 10 15
         * 15 20 27 37 52
         */
        for (int i = 1; i <= n; i++) {
            bell[i][0] = bell[i-1][i-1];
            for (int j = 1; j <= i; j++) {
                bell[i][j] = bell[i-1][j-1] + bell[i][j-1];
            }
        }
        // первый элемент последнего ряда
        return bell[n][0];
    }

    public static String translateWord(String word) {
        Pattern pattern = Pattern.compile("^[^aeiouyAEIOUY]+");
        Matcher matcher = pattern.matcher(word);
        if (matcher.find())
            return matcher.replaceFirst("") + matcher.group() + "ay";
        return word + "yay";
    }

    public static String translateSentence(String sentence) {
        String[] words = sentence.split(" ");
        for (int i = 0; i < words.length; i++) {
            words[i] = translateWord(words[i]);
        }
        return String.join(" ", words);
    }

    public static boolean validColor(String sentence) {
        Pattern pattern_usual = Pattern.compile("^rgb\\((\\d+),(\\d+),(\\d+)\\)$"); // rgb
        Pattern pattern_a = Pattern.compile("^rgba\\((\\d+),(\\d+),(\\d+),(\\d+(?:\\.\\d)\\d*)\\)$"); // rgba
        Matcher matcher_a = pattern_a.matcher(sentence);
        boolean isUsual = false;
        Matcher matcher;

        if (matcher_a.find())
            matcher = pattern_a.matcher(sentence);
        else {
            matcher= pattern_usual.matcher(sentence);
            isUsual = true;
        }

        if (matcher.find()){
            for (int i = 1; i <= 3; i++)
            {
                int value = Integer.parseInt(matcher.group(i));
                if (value < 0  || value > 255)
                    return false;
            }
            if (!isUsual && (Double.parseDouble(matcher.group(4)) < 0 || Double.parseDouble(matcher.group(4)) > 1))
                return false;
        }
        else
            return false;
        return true;
    }

    public static String stripUrlParams(String url, String[] arr) {
        if (url.contains("?")) {
            String newUrl = url.split("\\?")[0] + "?";
            String[] pArr = url.split("\\?")[1].split("\\&");

            Map<String, Integer> pMap = new HashMap<String, Integer>();
            for (var p : pArr) {
                String[] t = p.split("\\=");

                boolean skip = false;
                for (var sep: arr) {
                    if (t[0].equals(sep)) {
                        skip = true;
                        break;
                    }
                }

                if (skip) continue;
                pMap.put(t[0], Integer.parseInt(t[1]));
            }

            for (Map.Entry<String, Integer> item : pMap.entrySet()) {
                newUrl += item.getKey() + "=" + item.getValue() + "&";
            }
            newUrl = (newUrl.charAt(newUrl.length()-1) == '&') ? newUrl.substring(0, newUrl.length()-1) : newUrl;
            return newUrl;
        } else
            return url;
    }
    public static String stripUrlParams(String url) {
        return stripUrlParams(url, new String[] {""});
    }

    public static String getLongestWord(List<String> s) { // ищём самое длинное слово
        String longest_word = "";

        for (String w: s) {
            if (w.length() > longest_word.length())
                longest_word = w;
        }
        return longest_word;
    }
    public static Object[] getHashTags(String s) {
        List<String> strArr = new ArrayList<String>(Arrays.asList(s.split(" ")));
        List<String> longestWords = new ArrayList<String>();

        String s1 = getLongestWord(strArr);

        for (int i = 0; i < 3; i++) {
            s1 = getLongestWord(strArr);
            longestWords.add("#" + s1.toLowerCase());
            strArr.remove(s1);
        }
        return longestWords.toArray();
    }

    public static int ulam(int n){
        if (n < 1)
            return 0;
        if (n == 1)
            return 1;
        if (n == 2)
            return 2;
        int[] ulamSeq = new int[n];
        ulamSeq[0] = 1; ulamSeq[1] = 2;
        int ulamSeqIdx = 2; // индекс номер числа
        int ulamNum = 3; // номер числа
        while (ulamSeqIdx < n){
            int counter = 0;
            for (int i = 0; i < ulamSeqIdx; i++){
                for (int j = i + 1; j < ulamSeqIdx; j++){
                    if (ulamSeq[i] + ulamSeq[j] == ulamNum)
                        counter++;
                    if (counter > 1) break;
                }
            }
            if (counter == 1) {
                ulamSeq[ulamSeqIdx] = ulamNum;
                ulamSeqIdx++;
            }
            ulamNum++;
        }
        return ulamSeq[n - 1];
    }

    public static String longestNonrepeatingSubstring(String seq){
        if (seq.length() == 0)
             return "";
        if (seq.length() == 1)
            return seq;
        // макс длина последовательности и индекса её начала.
        int maxLen = 1;
        int maxIdx = 0;
        // текущая длины последовательности и индекса её начала.
        int currLen = 1;
        int currIdx = 0;
        StringBuilder usedChars = new StringBuilder();
        usedChars.append(seq.charAt(0));
        for (int i = 1; i < seq.length(); i++){
            if (usedChars.indexOf(String.valueOf(seq.charAt(i))) == -1){
                currLen++;
                usedChars.append(seq.charAt(i));
            } else {
                // Если есть, то уникальная последовательность закончилась, и мы сохраняем максимальную из них.
                if (currLen > maxLen) {
                    maxLen = currLen; maxIdx = currIdx;
                }
                currLen = 1; currIdx = i;
                usedChars = new StringBuilder();
                usedChars.append(seq.charAt(i));
            }
        }
        // если конец последовательности в конце строки
        if (currLen > maxLen) {
            maxLen = currLen; maxIdx = currIdx;
        }
        return seq.substring(maxIdx, maxIdx + maxLen);
    }
    public static String convertToRoman(int n) {
        if (n > 3999) return "Перебор";
        StringBuilder s = new StringBuilder();

        int m1 = n / 1000;

        s.append("M".repeat(m1));

        int m2 = n % 1000;
        int c1 = m2 / 100;

        if (c1 >= 5) {
            if (c1 == 9) s.append("CM");
            else if (c1 >= 5)
                s.append("D");
                s.append("C".repeat(c1-5));
        }
        else {
            if (c1 == 4) s.append("CD");
            else
                for (int i = 0; i < c1; i++)
                    s.append("C");
        }

        int c2 = m2 % 100;
        int x1 = c2 / 10;

        if (x1 >= 5) {
            if (x1 == 9)
                s.append("XC");
            else if (x1 >= 5)
                s.append("L");
        } else {
            if (x1 == 4)
                s.append("XL");
            else s.append("X".repeat(x1));
        }

        int x2 = c2 % 10;

        String[] a = {"", "I", "II", "III","IV", "V", "VI", "VII", "VIII", "IX"};
        s.append(a[x2]);
        return s.toString();
    }

    public static boolean formula(String s) {
        Set Set_res = new LinkedHashSet();
        String[] gg = s.split("=");
        for (var n : gg) {
            n = n.trim();
            String[] n1 = n.split(" ");
            if (n1.length == 3) {
                double a = Double.parseDouble(n1[0]);
                double b = Double.parseDouble(n1[2]);
                double res = 0;
                switch (n1[1])
                {
                    case "+":
                        res = a + b;
                        break;
                    case "-":
                        res = a - b;
                        break;
                    case "/":
                        if (b == 0)
                            return false;
                        res = a / b;
                        break;
                    case "*":
                        res = a*b;
                        break;
                }
                Set_res.add(res);
            }
            else Set_res.add(Double.parseDouble(n1[0]));
        }
        return Set_res.size() == 1;
    }

    public static String getDescendant(String n) { // получение потомка
        StringBuilder newStr = new StringBuilder();

        for (int i = 0; i < n.length()/2; i++) {
            int newI = Character.getNumericValue(n.charAt(i*2)) + Character.getNumericValue(n.charAt(i*2+1));
            newStr.append(Integer.toString(newI));
        }

        return newStr.toString();
    }
    public static boolean isPalindrome(String s) { // проверка на палиндром
        int length = s.length();
        for (int i = 0; i < length/2; i++) {
            if (s.charAt(i) != s.charAt(length-i-1)) {
                return false;
            }
        }
        return true;
    }

    public static boolean palindromeDescendant(int n) {
        String s = Integer.toString(n);

        while (s.length() >= 2) {
            if (isPalindrome(s))
                return true;
            s = getDescendant(s);
        }
        return false;
    }
}