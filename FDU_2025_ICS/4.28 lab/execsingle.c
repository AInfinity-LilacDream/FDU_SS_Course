#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
#include <errno.h>
#include <string.h>
#include <stdio.h>

int main() {
	char *argv[] = {"ls", "-l", NULL};
	char *envp[] = {NULL};
	if (execve("/bin/ls", argv, envp) == -1) {
		printf("execve failed");
		exit(1);
	}
	return 0;
}
