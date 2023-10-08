package Application.java;

public final class Sort {
    private static boolean less(Comparable a, Comparable b) {
        if(a.compareTo(b) < 0) {
            return true;
        }
        return false;
    }
    private static void swap(Comparable[] a, int i, int j) {
        Comparable mid = a[i];
        a[i] = a[j];
        a[j] = mid;
    }
    public static Comparable binarySearch(Comparable[] a, Comparable key) {
        int lo = 0;
        int hi = a.length - 1;
        while(lo <= hi) {
            int mid = lo + (hi - lo)/2;
            if(a[mid].compareTo(key) == 0) {
                return a[mid];
            } else if (less(a[mid], key)) {
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        return null;
    }
    public static void insertionSort(Comparable[] a, int lo, int hi) {
        for (int i = lo; i < hi; i++) {
            if (!less(a[i], a[i+1])) {
                swap(a, i, i+1);
                for (int j = i; j >= lo + 1; j--) {
                    if (less(a[j], a[j-1])) {
                        swap(a, j, j-1);
                    } else {
                        break;
                    }
                }
            }
        }
    }
    public static void merge(Comparable[] a, Comparable[] aux, int lo, int hi) {
        int mid = lo + (hi - lo)/2;
        int k = lo;
        int i = lo;
        int j = mid + 1;
        for (int z = lo; z <= hi; z++) {
            aux[z] = a[z];
        }
        while (k <= hi) {
            if (i <= mid && j <= hi) {
                if(less(aux[j], aux[i])) {
                    a[k++] = aux[j++];
                } else {
                    a[k++] = aux[i++];
                }
            } else if (i <= mid) {
                a[k++] = aux[i++];
            } else if (j <= hi) {
                a[k++] = aux[j++];
            } else {
                break;
            }
        }
    }
    public static void mergeSort(Comparable[] a, Comparable[] aux, int lo, int hi) {
        if (lo >= hi) {
            return;
        }
        int mid = lo + (hi - lo)/2;
        mergeSort(a, aux, lo, mid);
        mergeSort(a, aux, mid + 1, hi);
        if (!less(a[mid+1], a[mid])) {
            return;
        }
        merge(a, aux, lo, hi);
    }
}