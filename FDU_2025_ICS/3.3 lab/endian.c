#include <stdio.h>

int main() {
    int a = 0x12345678;
    char *ptr = (char *) &a;

    if (*ptr == 0x78) {
        printf("The system is Little Endian.\n");
    } else if (*ptr == 0x12) {
        printf("The system is Big Endian.\n");
    }

    return 0;
}

// 定义了一个多字节整数，并通过将一个字节指针指向整数地址来实现判断第一个字节的数据是首端还是末端
// 是首端则大端序，否则小端序。