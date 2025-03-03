#include <stdio.h>
#include <limits.h>

int main() {
    signed int a = -1; // negative signed
    unsigned int b = 2; // positive unsigned

    printf("a = %d (signed)\n", a);
    printf("b = %u (unsigned)\n", b);

    if (a > b) {
        printf("Unexpected: a (%d) is greater than b (%u)\n", a, b);
    } else {
        printf("Expected: a (%d) is not greater than b (%u)\n", a, b);
    }

    printf("a + b = %u\n", a + b); // result unsigned

    // 计算 a * b
    printf("a * b = %u\n", a * b); // result unsigned

    return 0;
}

// signed和unsigned混合运算时，编译器会自动将结果从signed转换成unsigned类型