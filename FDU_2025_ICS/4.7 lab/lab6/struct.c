#include <stdio.h>

// 定义结构体
struct s1 {
    char name;
    short code;
    int value;
};

struct s2 {
    short code;
    int value;
    char name;
};

struct s3 {
    int value;
    char name;
    short code;
};

int main() {
    struct s1 ss1 = {0, 10, 100};
    struct s2 ss2 = {10, 100, 0};
    struct s3 ss3 = {100, 0, 10};

    printf("%lu %lu %lu\n", sizeof(ss1), sizeof(ss2), sizeof(ss3));

    printf("Address of ss1.name: %p\n", (void*)&ss1.name);
    printf("Address of ss1.code: %p\n", (void*)&ss1.code);
    printf("Address of ss1.value: %p\n\n", (void*)&ss1.value);

    printf("Address of ss2.code: %p\n", (void*)&ss2.code);
    printf("Address of ss2.value: %p\n", (void*)&ss2.value);
    printf("Address of ss2.name: %p\n", (void*)&ss2.name);

    printf("Address of ss3.value: %p\n", (void*)&ss3.value);
    printf("Address of ss3.name: %p\n", (void*)&ss3.name);
    printf("Address of ss3.code: %p\n", (void*)&ss3.code);

    return 0;
}
