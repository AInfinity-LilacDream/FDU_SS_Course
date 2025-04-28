#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <sys/wait.h>

int main() {
	pid_t fpid;
	fpid = fork();
	if (fpid == 0) {
		char *argv[] = {"ls", "-l", "/home/ainfinity/ics", NULL};
		char *envp[] = {NULL};
		int status;
		if (execve("/bin/ls", argv, envp) == -1) {
			printf("execve failed");
			exit(1);
		}
	}
	else if (fpid < 0) {
		printf("fork failed");
		exit(1);
	}
	else {
		int status;
		wait(&status);
		if (WIFEXITED(status)) {
			printf("exit normally\n");
		}
		else {
			printf("exit abnormally\n");
		}
	}	
	return 0;
}

