import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
        System.out.println("6 задание: " + ulam(4));
        System.out.println("7 задание: " + longestNonrepeatingSubstring("abcabcbb"));
        System.out.println("8 задание: " + convertToRoman(16));
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
        //первый элемент последнего ряда
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
        for (int i = 0; i < words.length; i++){
            words[i] = translateWord(words[i]);
        }
        return String.join(" ", words);
    }

    public static boolean validColor(String color) {
        // rgb in color
        if (!color.contains("rgb"))
            return false;
        boolean isRgba = false;
        if (color.contains("rgba")){
            isRgba = true;
            color = color.substring(4);
        } else {
            color = color.substring(3);
        }
        //Проверка строки на наличие скобок и затем обрезка их.
        if (color.charAt(0) != '(' || color.charAt(color.length() - 1) != ')')
            return false;
        color = color.substring(1, color.length() - 1);

        //Разбка оставшихся чисел на массив строк.
        String[] numbers = color.split(",");

        //Проверка на то что цвет не в формате RGBA, а чисел не три.
        if (!isRgba && numbers.length != 3)
            return false;

        //Попытка спарсить три первых числа и сразу проверка на то, что они в интервале [0, 255].
        for (int i = 0; i < 3; i++) {
            try {
                int num = Integer.parseInt(numbers[i]);
                if (num < 0 || num > 255)
                    return false;
            } catch (NumberFormatException num_error){
                return false;
            }
        }
        //Когда цвет в формате RGBA пытаемся спарсить и проверить четвёртое дробное число на интервал [0, 1.0].
        if (numbers.length == 4) {
            try {
                double num = Double.parseDouble(numbers[3]);
                if (num < 0 || num > 1)
                    return false;
            } catch (NumberFormatException num_error){
                return false;
            }
        }
        return true;
    }

    /** Фукнция для исключения повторяющихся параметров из строки URL, а также для удаления исключённых параметров*/
    public static String stripUrlParams(String url, String[] ...subParamsToStrip){
        //Узнаём где у нас в строке '?' слева получается чистый url, справа параметры.
        int askIdx = url.indexOf('?');
        //Если параметров нет, то ответом будет url.
        if(askIdx == -1)return url;
        //Получаем чистый url.
        String cleanUrl = url.substring(0, askIdx);
        //Получаем параметры разбивая правую часть строки по '&'.
        String[] params = url.substring(askIdx + 1).split("&");
        int excludedLength = params.length;
        //Указатель на пустую ячейку исключений.
        int excludedPtr = 0;
        //Объявляем массив исключений, его длина - максимальное число исключений, т.е кол-во параметров + кол-во исключений.
        String[] excluded;
        //Если есть исключения.
        if(subParamsToStrip.length != 0){
            //Длина массива исключений увеличивается.
            excludedLength += subParamsToStrip[0].length;
            //Если длина исключений больше единицы, то параметры заданы неправильно.
            if(subParamsToStrip.length > 1) return "Error";
            //Создаём массив исключений.
            excluded = new String[excludedLength];
            //Добавляем исключения в массив исключений.
            for(int i = 0; i < subParamsToStrip[0].length; i++){
                excluded[excludedPtr] = subParamsToStrip[0][i];
                excludedPtr++;
            }
        } else {
            //Если нет исключения, то массив исключений будет пустым.
            excluded = new String[excludedLength];
        }
        StringBuilder answer = new StringBuilder();
        //Инициализируем массив для разбиения параметра, а также переменную для ключа и значения.
        String paramWord;
        String paramVal;
        //Итерируем по параметрам.
        for(int i = 0; i < params.length; i++){
            //Получаем ключ из параметра.
            paramWord = params[i].split("=")[0];
            //Если параметр уже в исключениях, то пропускаем его.
            if(Arrays.asList(excluded).contains(paramWord))continue;
            //Ищем СловоПараметра + "=" в массиве параметров, с конца, чтобы получить последнее значение.
            String toFind = paramWord + "=";
            //ptr - указатель на значение параметра с конца.
            int ptr = params.length - 1;
            int paramIdx = params[ptr].indexOf(toFind);
            while(ptr > -1 && paramIdx != 0){
                paramIdx = params[ptr].indexOf(toFind);
                ptr--;
            }
            //Сохраняем последнее значение параметра в строку.
            paramVal = params[ptr].substring(paramIdx + toFind.length());
            //Если параметр первый, то добавляем ? иначе &.
            if(i == 0)answer.append("?");
            else answer.append("&");
            //Добавляем параметр в ответ.
            answer.append(paramWord).append("=").append(paramVal);
            //Добавляем параметр в исключения.
            excluded[excludedPtr] = paramWord;
            excludedPtr++;
        }
        answer.insert(0, cleanUrl);
        return answer.toString();
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

        for (int i = 0; i < 3; i++) { // поочередно ищем самое длинное слово
            s1 = getLongestWord(strArr);
            longestWords.add("#" + s1.toLowerCase());
            strArr.remove(s1); // удаляем самое длинное слово, чтобы не встретить его вновь
        }
        return longestWords.toArray();
    }

    /** Функция находящая n-ое число последовательности улама */
    public static int ulam(int n){
        //Если число меньше трёх, то возвращаем его значение.
        if(n < 1)return 0;
        if(n == 1)return 1;
        if(n == 2)return 2;
        //Создаём массив для хранения чисел улама.
        int[] ulamSeq = new int[n];
        //Вносим первые элементы.
        ulamSeq[0] = 1;
        ulamSeq[1] = 2;
        int ulamSeqIdx = 2;
        int ulamNum = 3;
        //Так как сумма всегда возрастает от числа к числу(Далее СЧУ), то мы перебираем последовательно все числа от 3 до n.
        //Итерируем в цикле пока номер числа улана не будет n.
        while(ulamSeqIdx < n){
            //Считаем сколькими способами можно представить СЧУ.
            int counter = 0;
            for(int i = 0; i < ulamSeqIdx; i++){
                for(int j = i + 1; j < ulamSeqIdx; j++){
                    if(ulamSeq[i] + ulamSeq[j] == ulamNum)counter++;
                }
            }
            //Если способ один, то это число улама, записываем число в массив и увеличиваем индекс.
            if(counter == 1){
                ulamSeq[ulamSeqIdx] = ulamNum;
                ulamSeqIdx++;
            }
            //Увеличиваем СЧУ.
            ulamNum++;
        }
        return ulamSeq[n - 1];
    }

    /** Функция, находящая самую длинную, неповторяющуюся символьную последовательность в строке */
    public static String longestNonrepeatingSubstring(String seq){
        int seqLength = seq.length();
        if(seqLength == 0)return "";
        if(seqLength == 1)return seq;
        //Инициализация переменных максимальной длины последовательности и индекса её начала.
        int maxLen = 1;
        int maxIdx = 0;
        //Инициализация переменных текущей длины последовательности и индекса её начала.
        int currLen = 1;
        int currIdx = 0;
        StringBuilder usedChars = new StringBuilder();
        usedChars.append(seq.charAt(0));
        for(int i = 1; i < seqLength; i++){
            //Получаем индекс текущего итерируемого символа.
            int charIdx = usedChars.indexOf(String.valueOf(seq.charAt(i)));
            //Если его нет в сохранённой последовательности, то добавляем его туда и идём дальше.
            if(charIdx == -1){
                currLen++;
                usedChars.append(seq.charAt(i));
            } else {
                //Если же он там есть, то уникальная последовательность закончилась, и мы сохраняем максимальную из них.
                if(currLen > maxLen){
                    maxLen = currLen;
                    maxIdx = currIdx;
                }
                currLen = 1;
                currIdx = i;
                usedChars = new StringBuilder();
                usedChars.append(seq.charAt(i));
            }
        }
        //Последняя проверка, т.к. последовательность может закончиться в конце строки.
        if(currLen > maxLen){
            maxLen = currLen;
            maxIdx = currIdx;
        }
        return seq.substring(maxIdx, maxIdx + maxLen);
    }
    public static String convertToRoman(int n) {
        if (n > 3999) return "Перебор";
        StringBuilder s = new StringBuilder();

        int m1 = n / 1000;

        for (int i = 0; i < m1; i++) s.append("M");

        int m2 = n % 1000;
        int c1 = m2 / 100;

        if (c1 >= 5) {
            if (c1 == 9) s.append("CM");
            else if (c1 >= 5) s.append("D");
        } else {
            if (c1 == 4) s.append("CD");
            else if (c1 == 0) s.append("");
            else for (int i = 0; i < c1; i++) s.append("C");
        }

        int c2 = m2 % 100;
        int x1 = c2 / 10;

        if (x1 >= 5) {
            if (x1 == 9) s.append("XC");
            else if (x1 >= 5) s.append("L");
        } else {
            if (x1 == 4) s.append("XL");
            else if (x1 == 0) s.append("");
            else for (int i = 0; i < x1; i++) s.append("X");
        }

        int x2 = c2 % 10;

        String[] a = {"", "I", "II", "III","IV", "V", "VI", "VII", "VIII", "IX"};
        s.append(a[x2]);
        return s.toString();
    }

    /** Функция решает простейшие математические выражения путём обращения в рекурсию при каждой новой операции */
    public static int solvePart(String part){
        //Строка со всеми допущенными операциями.
        String operations = "+-*/";
        //Находим индекс символа операции в строке.
        int idx = 0;
        while(idx < part.length() && operations.indexOf(part.charAt(idx)) == -1)idx++;
        //Если операция не найдена в строке, то мы считам что там уже нет операций и возвращаем число.
        if(idx == part.length())return Integer.parseInt(part.strip());
        //Получаем операцию и в зависимости от неё возвращаем рекурсивно обработку левой и правой части по необходимой операции.
        char operation = part.charAt(idx);
        return switch (operation) {
            case '+' -> solvePart(part.substring(0, idx)) + solvePart(part.substring(idx + 1));
            case '-' -> solvePart(part.substring(0, idx)) - solvePart(part.substring(idx + 1));
            case '*' -> solvePart(part.substring(0, idx)) * solvePart(part.substring(idx + 1));
            case '/' -> solvePart(part.substring(0, idx)) / solvePart(part.substring(idx + 1));
            default -> 0;
        };
    }

    /** Функция проверяет строку с формулой на математическую достоверность*/
    public static boolean formula(String form){
        //Проверяем есть ли '=' в строке.
        int equalsIdx = form.indexOf("=");
        if(equalsIdx == -1)return false;
        //Проверяем больше ли одного "=" в строке.
        if(form.indexOf("=", equalsIdx + 1) != -1)return false;
        //Разбиваем строку на две части по "=".
        String[] parts = form.split("=");
        //Если "=" в начале или конце строки, то формула неверна.
        if(parts.length != 2)return false;
        //Получаем вычисленные части формулы.
        int leftPart = solvePart(parts[0]);
        int rightPart = solvePart(parts[1]);
        //Сравниваем их.
        return leftPart == rightPart;
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