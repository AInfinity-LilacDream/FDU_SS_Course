#include <stdio.h>

// 读取进位标志（CF）
int get_carry_flag(int a, int b) {
    int result; 
    unsigned char carry;
    asm("addl %2, %1\n"      // 执行加法操作 a + b
        "setc %0"           // 将CF的值存入carry变量
        : "=r"(carry)
        : "r"(a),"r"(b)
        : "cc"
    );
    return carry;
}

// 读取溢出标志（OF）
int get_overflow_flag(int a, int b) {
    int result;
    unsigned char overflow;
    asm("addl %2, %1\n"      // 执行加法操作 a + b
        "seto %0"           // 将OF的值存入overflow变量
        : "=r"(overflow)
        : "r"(a),"r"(b)
        : "cc"
    );
    return overflow;
}

// 读取零标志（ZF）
int get_zero_flag(int result) {
    unsigned char zero;
    asm("testl %1, %1\n"    // 测试result是否为0
        "setz %0"           // 将ZF的值存入zero变量
        : "=r"(zero)
        : "r"(result)
        : "cc"
    );
    return zero;
}

int main() {
    int a = 0x7FFFFFFF, b = 1;
    int sum = a + b;

    printf("进位标志 CF: %d\n", get_carry_flag(a, b));
    printf("溢出标志 OF: %d\n", get_overflow_flag(a, b));
    printf("零标志 ZF: %d\n", get_zero_flag(sum));

    return 0;
}
