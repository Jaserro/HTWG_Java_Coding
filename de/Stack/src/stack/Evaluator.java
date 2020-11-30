package stack;
/*
 * class Evaluator
 * repl-Schleife: lese von der Konsole eine Zeile und
 * werte sie als arithmetischen Ausdruck aus.
 * Da tokenizer String-Konstante zurückliefert, reicht
 * Gleichheitsprüfung mit == aus (siehe z.B. shift-Methode).
 */

import expressionevaluation.Tokenizer;

import java.util.Scanner;

import static expressionevaluation.Tokenizer.*;

/**
 * Klasse zum Auswerten von arithmetischen Ausdrücken.
 */
public class Evaluator {

    private static final String ANSI_BLUE = "\u001B[34m";
    private static Object[] stack = new Object[200];        // Stack
    private static int top = -1;                    // Index des obersten Kellerelements
    private static Object token;                    // Aktuelles Token
    private static Tokenizer tokenizer;                // Zerlegt String-Eingabe in Tokens

    /**
     * Wertet expr als arithmetischen Ausdruck aus.
     *
     * @param expr Arthmetischer Ausdruck als String
     * @return Wert des Ausdrucks oder null, falls der Ausdruck fehlerhaft ist.
     */
    public static Double eval(String expr) {
        // Dollar in leeren Stack ablegen:
        top = -1;
        stack[++top] = DOLLAR;

        // expr in Tokens zerlegen und erstes Token abholen:
        tokenizer = new Tokenizer(expr);
        token = tokenizer.nextToken();

        while (token != null) {
            if (!shift()) {
                return null;
            }
        }

        try {
            return (Double) stack[top];
        }catch (Exception e){
            System.out.println(ANSI_BLUE + "!!! error");
        }
        return null;
    }

    private static boolean shift() {
        if(stack[top] == DOLLAR && (token == KL_ZU || isOp(token) || token == DOLLAR)){ // nur zum testen
            return false;
        }
        else if (stack[top] == DOLLAR && (token == KL_AUF || isVal(token))) {           // Regel 1 der Parser-Tabelle
            doShift();
            return true;
        } else if (isOp(stack[top]) && (token == KL_AUF || isVal(token))) {           //Regel 2
            doShift();
            return true;
        } else if (stack[top] == KL_AUF && (token == KL_AUF || isVal(token))) {      //Regel 3
            doShift();
            return true;
        }  else if (stack[top - 1] == DOLLAR && isVal(stack[top]) && token == DOLLAR) { //Regel 5
            return accept();
        } else if (stack[top - 1] == DOLLAR && isVal(stack[top]) && isOp(token)) {   //Regel 6
            doShift();
            return true;
        } else if (stack[top - 1] == KL_AUF && isVal(stack[top]) && (isOp(token) || token == KL_ZU )) {   //Regel 7
            doShift();
            return true;
        } else if ( isVal(stack[top - 1]) && stack[top] == KL_ZU
                && (token == KL_ZU || isOp(token) || token == DOLLAR) && stack[top - 2] == KL_AUF ) {          //Regel 4 //KL VAR KL
            return reduce();
        }else if (isOp(stack[top - 1]) && isVal(stack[top])
                && (token == KL_ZU || token == DOLLAR) && isVal(stack[top - 2])) {                             //Regel 8 //VAR OP VAR
            return reduce();
        } else if (isOp(stack[top - 1]) && isVal(stack[top]) && isOp(token) && isVal(stack[top - 2])) {   //Regel 9
            if (hashOP(stack[top - 1]) - hashOP(token) < 0) {  //rechts mehr Wertig
                doShift();
                return true;
            } else {
                return reduce();
            }
        } else {
            return false;
        }
    }

    /**
     * ordnet jedem operanten einen numerischen code zu
     *
     * @param token
     * @return + = 1, * = 2, ^ = 3
     */
    private static int hashOP(Object token) {
        if (token.equals(PLUS)) {
            return 1;
        } else if (token.equals(MULT)) {
            return 2;
        } else if (token.equals(POWER)) {
            return 3;
        }
        return 0;
    }

    private static void doShift() {
        stack[++top] = token;
        token = tokenizer.nextToken();
    }

    private static boolean isOp(Object o) {
        return (o == PLUS || o == MULT || o == POWER);
    }

    private static boolean isVal(Object o) {
        return o instanceof Double;
    }

    private static boolean reduce() {
        if (stack[top] == KL_ZU) {
            doReduceKlValKl();
            return true;
        } else if (isVal(stack[top])) {
            doReduceValOpVal();
            return true;
        }
        return false;
    }

    private static void doReduceKlValKl() {
        stack[top - 2] = stack[top - 1];
        stack[top--] = null;
        stack[top--] = null;
    }

    /**
     * val op val = val
     */
    private static void doReduceValOpVal() {
        switch (hashOP(stack[top - 1])) {
            case 1://+
                stack[top - 2] = (Double) stack[top - 2] + (Double) stack[top];
                break;
            case 2://*
                stack[top - 2] = (Double) stack[top - 2] * (Double) stack[top];
                break;
            case 3://^ ist böse => Math.pow is F powerful
                stack[top - 2] = Math.pow((Double) stack[top - 2], (Double) stack[top]);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + hashOP(stack[top - 1]));
        }
        stack[top--] = null;
        stack[top--] = null;

    }

    private static boolean accept() {
        token = tokenizer.nextToken();
        return true;
    }

    /**
     * Liest von der Konsole eine Folge von Zeilen, wertet jede Zeile als
     * Ausdruck aus und gibt seinen Wert aus. (repl = read-evaluate-print-loop).
     */
    public static void repl() {
        Scanner in = new Scanner(System.in);
        System.out.print(ANSI_BLUE + ">> ");

        while (in.hasNextLine()) {
            String line = in.nextLine();

            if (line.equals("end")) {
                System.out.println(ANSI_BLUE + "bye!");
                return;
            }
            Double ergebnis = eval(line);
            if (ergebnis != null) {
                if (Double.isInfinite(ergebnis)){
                    System.out.println(ANSI_BLUE + "\uD83E\uDD74 \uD83D\uDE1E");
                    System.out.println(ANSI_BLUE + "its over 9000");
                }else{
                    System.out.println(ANSI_BLUE + ergebnis);
                }

            } else {
                System.out.println(ANSI_BLUE + "!!! error");
            }
            System.out.print(ANSI_BLUE + ">> ");
        }
    }

    /**
     * Testprogramm.
     * @param args wird nicht benutzt.
     */
    public static void main(String[] args) {
        // Zum Testen, später auskommentieren:
        String s1 = "1+2";
        String s2 = "2^10+5";
        String s3 = "5+2^10";
        String s4 = "(2+3*4+4)^2";
        String s5 = "(2+3*4+4))*2";
        String s6 = "2+3**4";
        String s7 = "2^2^3";
        String s8 = "2^2*5";
        String s9 = "1+(2+(3+(4+(5+6))))";
        String s10 = "1+2+3+4+5+6";

        System.out.println(eval(s1) + " soll 3.0");    // 3.0
        System.out.println(eval(s2) + " soll 1029.0");    // 1029.0
        System.out.println(eval(s3) + " soll 1029.0");    // 1029.0
        System.out.println(eval(s4) + " soll 324.0");    // 324.0
        System.out.println(eval(s5) + " soll null");    // null; Syntaxfehler
        System.out.println(eval(s6) + " soll null");    // null; Syntaxfehler
        System.out.println(eval(s7) + " soll 64.0");    // 64.0
        System.out.println(eval(s8) + " soll 20.0");    // 20.0
        System.out.println(eval(s9) + " soll 21.0");    // 21.0
        System.out.println(eval(s10) + " soll 21.0");    // 21.0

       repl();
    }
}
