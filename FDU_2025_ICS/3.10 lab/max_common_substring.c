#include <stdio.h>
#include <stdint.h>
#include <inttypes.h>
#include <string.h>
#include <stdlib.h>

union {
    float f;
    unsigned int i;
} fn;

union {
    double d;
    uint64_t i;
} dn;

void printFloat(float x) {
    unsigned int* intVal = (unsigned int*)&x;
    for (int i = 31; i >= 0; --i) {
        printf("%d", ((*intVal >> i) & 1));
        if (i == 31 || i == 23) printf(" ");
    }
    printf("\n");
}

void printDouble(double x) {
    unsigned long long* intVal = (unsigned long long*)&x;
    for (int i = 63; i >= 0; --i) {
        printf("%d", ((*intVal >> i) & 1));
        if (i == 63 || i == 52) printf(" ");
    }
    printf("\n");
}

// Function to find the longest common substring
char *longestCommonSubstring(const char *s1, const char *s2) {
    int len1 = strlen(s1);
    int len2 = strlen(s2);
    int dp[len1 + 1][len2 + 1];
    int maxLength = 0;
    int endIndex = -1;

    for (int i = 0; i <= len1; ++i) {
        for (int j = 0; j <= len2; ++j) {
            if (i == 0 || j == 0) {
                dp[i][j] = 0;
            } else if (s1[i - 1] == s2[j - 1]) {
                dp[i][j] = dp[i - 1][j - 1] + 1;
                if (dp[i][j] > maxLength) {
                    maxLength = dp[i][j];
                    endIndex = i - 1;
                }
            } else {
                dp[i][j] = 0;
            }
        }
    }

    if (maxLength == 0) return strdup("");

    char *result = (char *)malloc((maxLength + 1) * sizeof(char));
    strncpy(result, s1 + endIndex - maxLength + 1, maxLength);
    result[maxLength] = '\0';
    return result;
}

int main() {
    long x;
    scanf("%ld", &x);

    // Convert to float and double
    float floatX = (float)x;
    double doubleX = (double)x;

    // Print values
    printf("x: %ld\n", x);
    printf("-x: %ld\n", -x);
    printf("(float)x: %f\n", floatX);
    printf("(double)x: %lf\n", doubleX);

    printf("x: ");
    for (int i = 63; i >= 0; --i) {
        printf("%d", ((x >> i) & 1));
    }
    printf("\n");

    printf("-x: ");
    for (int i = 63; i >= 0; --i) {
        printf("%d", (((-x) >> i) & 1));
    }
    printf("\n");

    printf("float x: ");
    printFloat(floatX);
    printf("double x: ");
    printDouble(doubleX);

    char strX[65];
    sprintf(strX, "%ld", x);
    char strFloatX[65];
    sprintf(strFloatX, "%f", floatX);
    char strDoubleX[65];
    sprintf(strDoubleX, "%lf", doubleX);

    // Find longest common substring
    char *lcsXFloat = longestCommonSubstring(strX, strFloatX);
    char *lcsXDouble = longestCommonSubstring(strX, strDoubleX);

    printf("Longest common substring between |x| and (float)x: %s\n", lcsXFloat);
    printf("Longest common substring between |x| and (double)x: %s\n", lcsXDouble);

    free(lcsXFloat);
    free(lcsXDouble);

    return 0;
}