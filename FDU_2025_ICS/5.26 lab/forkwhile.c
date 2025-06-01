#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>

int main() {

    pid_t pid = fork();

    if (pid < 0) {
        perror("fork failed");
        return 1;
    } else if (pid == 0) {
        while (1) {
            sleep(1);
        }
    } else {
        printf("Parent PID: %d\n", getpid());
        printf("Child PID: %d\n", pid);
        while (1) {
            sleep(1);
        }
    }

    return 0;
}
