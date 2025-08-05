public class mouseClicked {
    public static void main(String[] args) {
        int[][] integers = {{1, 2, 3, 4},
                            {5, 6, 7, 8},
                           {9, 10, 11, 12} };

int a=0;
        for (int i = 0; i <3; i++) {
            for (int j=0;j< 4;j++) {

            a+=integers[i][j]; }
            System.out.println("The sum of row "+(i+1)+" is : "+a);
            a=0;

            }


    }


}