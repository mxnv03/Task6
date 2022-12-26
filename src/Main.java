import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws Exception{
        task_name(1, "bell");
        System.out.println(bell(1));
        System.out.println(bell(2));
        System.out.println(bell(3));
        System.out.println(bell(4));

        task_name(2, "translateWord");
        System.out.println(translateWord("flag"));
        System.out.println(translateWord("Apple"));
        System.out.println(translateWord("button"));
        System.out.println(translateWord(""));
        System.out.println(translateSentence("I like to eat honey waffles."));
        System.out.println(translateSentence("Do you think it is going to rain today?"));

        task_name(3, "validColor");
        System.out.println(validColor("rgb(0,0,0)"));
        System.out.println(validColor("rgb(0,,0)"));
        System.out.println(validColor("rgb(255,256,255)"));
        System.out.println(validColor("rgba(0,0,0,0.123456789)"));

        task_name(4,  "stripUrlParams");
        System.out.println(stripUrlParams("https://edabit.com?a=1&b=2&a=2"));
        System.out.println(stripUrlParams("https://edabit.com?a=1&b=2&a=2", new String[] {"b"}));
        System.out.println(stripUrlParams("https://edabit.com", new String[] {"b"}));

        task_name(5, "getHashTags");
        System.out.println(getHashTags("How the Avocado Became the Fruit of the Global Trade"));
        System.out.println(getHashTags("Why You Will Probably Pay More for Your Christmas Tree This Year"));
        System.out.println(getHashTags("Hey Parents, Surprise, Fruit Juice Is Not Fruit"));
        System.out.println(getHashTags("Science Visualizing"));

        task_name(6, "ulam");
        System.out.println(ulam(4));
        System.out.println(ulam(9));
        System.out.println(ulam(206));

        task_name(7, "longestNonrepeatingSubstring");
        System.out.println(longestNonrepeatingSubstring("abcabcbb"));
        System.out.println(longestNonrepeatingSubstring("aaaaaa"));
        System.out.println(longestNonrepeatingSubstring("abcde"));
        System.out.println(longestNonrepeatingSubstring("abcda"));

        task_name(8, "convertToRoman");
        System.out.println(convertToRoman(2));
        System.out.println(convertToRoman(12));
        System.out.println(convertToRoman(16));
        System.out.println(convertToRoman(66));
        System.out.println(convertToRoman(46));
        System.out.println(convertToRoman(996));
        System.out.println(convertToRoman(2499));

        task_name(9, "formula");
        System.out.println(formula("6 * 4 = 24"));
        System.out.println(formula("18 / 17 = 2"));
        System.out.println(formula("16 * 10 = 160 = 14 + 120"));

        task_name(10, "palindromedescendant");
        System.out.println(palindromeDescendant(11211230));
        System.out.println(palindromeDescendant(13001120));
        System.out.println(palindromeDescendant(23336014));
        System.out.println(palindromeDescendant(1211));
    }

    private static void task_name(int task, String name) {
        System.out.println("--- Task #" + task + " --- " + name + " ---");
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

    public static String getLongestWord(List<String> s) { // ищем самое длинное слово
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

        String s1;

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
    public static String convertToRoman(int num){
        if (num < 1 || num > 3999)
            return "Error";
        StringBuilder answer = new StringBuilder();
        int[] nums = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] romans = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int i = 0;
        //Итерируем по массиву чисел кратным римским
        while (num > 0){
            /*
             * Если число больше текущего ЧТР, то римское число по ЧТР добавляется в строку, а
             * из числа вычитается ЧТР, если же число меньше ЧТР, то переходим к следующему ЧТР.
             */
            if (num >= nums[i]) {
                answer.append(romans[i]);
                num -= nums[i];
            } else i++;
        }
        return answer.toString();
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