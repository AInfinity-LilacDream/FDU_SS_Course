#include <stdio.h>

int getLayeredTotal(int m, int n, int k) {
    if (k == 0) return 0;
    return 2 * (m - 2 * (k - 1)) + 2 * (n - 2 * (k - 1)) - 4 + getLayeredTotal(m, n, k - 1);
}

void valToMap(int m, int n, int num, int *i, int *j) {
    int k = 0;
    while (1) {
        int tot = 2 * (m - 2 * k) + 2 * (n - 2 * k) - 4;
        if (tot <= 0 || num < getLayeredTotal(m, n, k) + tot) break;
        k++;
    }

    int offset = num - getLayeredTotal(m, n, k);
    int top = m - 2 * k, right = n - 2 * k;

    if (offset < right) {
        *i = k;
        *j = k + offset;
    } else if (offset < top + right - 1) {
        *i = k + (offset - right);
        *j = n - k - 1;
    } else if (offset < top + right - 1 + right - 1) {
        *i = m - k - 1;
        *j = n - k - 1 - (offset - (top + right - 1));
    } else {
        *i = m - k - 1 - (offset - (top + right - 1 + right - 1));
        *j = k;
    }
}

int mapToVal(int m, int n, int i, int j) {
    int k = 0;
    while (i >= k && i < m - k && j >= k && j < n - k) {
        k++;
    }
    k--;

    int offset = 0;
    if (i == k) {
        offset = j - k;
    } else if (j == n - k - 1) {
        offset = (n - 2 * k - 1) + (i - k);
    } else if (i == m - k - 1) {
        offset = (n - 2 * k - 1) + (m - 2 * k - 1) + (n - k - 1 - j);
    } else if (j == k) {
        offset = (n - 2 * k - 1) + (m - 2 * k - 1) + (n - 2 * k - 1) + (m - k - 1 - i);
    }

    return getLayeredTotal(m, n, k) + offset;
}

int main() {
    int m = 5, n = 6;
    int a[1009][1009];

    int i = 3, j = 5;
    printf("number (%d, %d) is %d\n", i, j, mapToVal(m, n, i, j));

    int num = 23;
    int ii, jj;
    valToMap(m, n, num, &ii, &jj);
    printf("position is (%d, %d)\n", ii, jj);

    return 0;
}
