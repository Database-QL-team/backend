package ggyuel.ggyuup.global;

public class Variable {
    private static int togetherid = 0;
    public static int getTogetherid() {
        return togetherid;
    }

    public static void setTogetherid() {
        togetherid += 1;
    }

}
