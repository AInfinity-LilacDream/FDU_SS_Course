#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>

void printID(const char *message) {
	printf("%s: PID = %d, PGID = %d\n", message, getpid(), getpgid(getpid()));
}

int main() {
	// print main pid and pgid
	printID("main process");

	pid_t pid;

	pid = fork();
	if (pid == -1) {
		perror("fork failed");
		return -1;
	}
	else if (pid == 0) {
		printID("child process");
		return 0;
	}

	pid = fork();
	if (pid == -1) {
		perror("fork failed");
		return -1;
	}
	else if (pid == 0) {
		setpgid(0, 0); // set self pgid to self pid
		printID("self pgrouped child process");
		return 0;
	}

	return 0;
}

