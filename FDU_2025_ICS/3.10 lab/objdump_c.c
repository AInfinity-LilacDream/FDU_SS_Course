#include <stdio.h>

int main() {
    int a = 10, b = 3;
    printf("1. int and int:\n");
    printf("%d + %d = %d\n", a, b, a + b);
    printf("%d - %d = %d\n", a, b, a - b);
    printf("%d * %d = %d\n", a, b, a * b);
    printf("%d / %d = %d\n\n", a, b, a / b);

    int c = 5;
    float d = 2.5f;
    printf("2. int and float:\n");
    printf("%d + %.1f = %.2f\n", c, d, c + d);
    printf("%d - %.1f = %.2f\n", c, d, c - d);
    printf("%d * %.1f = %.2f\n", c, d, c * d);
    printf("%d / %.1f = %.2f\n\n", c, d, c / d);

    float e = 3.14f;
    double f = 2.71828;
    printf("3. float and double:\n");
    printf("%.2f + %.5f = %.6f\n", e, f, e + f);
    printf("%.2f * %.5f = %.6f\n\n", e, f, e * f);

    int i = 7;
    int j = 2;
    printf("5. force transition: \n");
    printf("%d / %d = %d\n", i, j, i/j);
    printf("(float)%d / %d = %.2f\n", i, j, (float)i/j);

    return 0;
}