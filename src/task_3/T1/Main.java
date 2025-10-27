package task_3.T1;

public class Main {

    /* Выводящую на экран 3 случайно сгенерированных
    трёхзначных числа и сумму их первых цифр */

    static int a, b, c, Fa, Fb, Fc;

    public static void main(String[] args) {
        randomGeneration();
        Fa = getFirst(a);
        Fb = getFirst(b);
        Fc = getFirst(c);

        System.out.printf("a = %d, b = %d, c = %d\n", a, b, c);
        System.out.printf("Сумма первых элементов = %d", Fa+Fb+Fc);
    }

    public static void randomGeneration() {
        a = (new java.util.Random()).nextInt(100,999);
        b = (new java.util.Random()).nextInt(100,999);
        c = (new java.util.Random()).nextInt(100,999);
    }

    public static int getFirst(int value) {
        return value/100;
    }
}
