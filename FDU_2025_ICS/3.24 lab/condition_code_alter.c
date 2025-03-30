#include <stdio.h>
#include <limits.h>

// 判断进位（无符号溢出）
int check_carry(unsigned int a, unsigned int b) {
    return (a + b) < a;
}

// 判断溢出（有符号溢出）
int check_overflow(int a, int b) {
    int sum = a + b;
    return ((a > 0 && b > 0 && sum < 0) || (a < 0 && b < 0 && sum > 0));
}

// 判断结果是否为0
int check_zero(int result) {
    return result == 0;
}

int main() {
    int a = 0x7FFFFFFF, b = 1;
    int sum = a + b;

    printf("进位标志 CF: %d\n", check_carry(a, b));
    printf("溢出标志 OF: %d\n", check_overflow(a, b));
    printf("零标志 ZF: %d\n", check_zero(sum));

    return 0;
}
