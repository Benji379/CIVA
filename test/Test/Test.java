package Test;

public class Test {

    public static void main(String[] args) {

//        for (int i = 0; i <= 8; i++) {
//            System.out.println("digitalWrite(" + (i + 2) + ", HIGH);");
//            System.out.println("digitalWrite(" + (10 - i) + ", HIGH);");
//            System.out.println("digitalWrite(" + (i + 2) + ", LOW);");
//            System.out.println("digitalWrite(" + (10 - i) + ", LOW);");
//        }
        int c = 0;
        for (int i = 1; i <= 4; i++) {
            c++;
            System.out.println(c);
            System.out.println("digitalWrite(" + (6 - i) + ", HIGH);");
            System.out.println("digitalWrite(" + (6 + i) + ", HIGH);");
            System.out.println("digitalWrite(" + (6 - i) + ", LOW);");
            System.out.println("digitalWrite(" + (6 + i) + ", LOW);");
        }
    }
}
