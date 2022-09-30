import java.util.Arrays;

public class MainArraySort {
    public static void main(String[] args) {

        int[] arr = new int[]{5, 2, 4, 6, 1, 3, 2, 6, 7, 4, 9, 7, 8, 11, 21, 42, 3};
        int r = arr.length - 1;
        int p = 0;
        sort(arr, p, r);
        System.out.println(Arrays.toString(arr));
    }

    public static void sort(int[] A, int p, int r) {
        if (p >= r) return;

        int q = (p + r) / 2;
        sort(A, p, q);
        sort(A, q + 1, r);
        merge(A, p, q, r);
    }

    public static void merge(int[] A, int p, int q, int r) {

        int n1 = q - p + 1;
        int n2 = r - q;
        int[] L = new int[n1 + 1];
        int[] R = new int[n2 + 1];
        L[n1] = 10000;
        R[n2] = 10000;
        System.arraycopy(A, p, L, 0, n1);
        for (int j = 0; j < n2; j++) {
            R[j] = A[q + j + 1];
        }
        int x = 0, y = 0;
        for (int k = p; k <= r; k++) {
            if (L[x] <= R[y]) {
                A[k] = L[x];
                x++;
            } else {
                A[k] = R[y];
                y++;
            }
        }
    }
}
