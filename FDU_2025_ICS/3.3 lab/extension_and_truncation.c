#include <stdio.h>
#include <stdint.h>

void printBinary(int num, int bits) {
    printf("%d\n", num);
    for (int i = bits - 1; i >= 0; i--) {
        // 检查第 i 位是否为 1
        if (num & (1 << i)) {
            printf("1");
        } else {
            printf("0");
        }
    }
    printf("\n");
}

// 主函数
int main() {
    int a = 10, b = -10;

    // extension
    printBinary(a, 5);
    printBinary(a, 10);

    printf("\n");

    printBinary(b, 5);
    printBinary(b, 10);

    uint64_t largeNumber = 0xFFFFFFFFFFFFFFFF;
    uint32_t smallNumber;

    smallNumber = largeNumber;

    // truncation
    printf("Original number: 0x%p\n", largeNumber);
    printf("Truncated number: 0x%x\n", smallNumber);

    return 0;
}