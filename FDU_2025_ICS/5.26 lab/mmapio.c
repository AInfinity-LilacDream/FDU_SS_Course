#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>
#include <unistd.h>
#include <sys/mman.h>
#include <string.h>

int main() {
    const char *filename = "test.txt";

    // system read
    int file1 = open(filename, O_RDONLY);

    char buffer[1024];
    ssize_t bytes_read = read(file1, buffer, sizeof(buffer) - 1);
    if (bytes_read == -1) {
        perror("read failed");
        close(file1);
        return 1;
    }
    buffer[bytes_read] = '\0';
    printf("%s\n", buffer);
    close(file1);

    // mmap
    int file2 = open(filename, O_RDONLY);
    if (file2 == -1) {
        perror("open failed");
        return 1;
    }

    off_t file_size = lseek(file2, 0, SEEK_END);
    void *mapped = mmap(NULL, file_size, PROT_READ, MAP_PRIVATE, file2, 0);
    if (mapped == MAP_FAILED) {
        perror("mmap failed");
        close(file2);
        return 1;
    }

    printf("%.*s\n", (int)file_size, (char *)mapped);

    // release
    munmap(mapped, file_size);
    close(file3);

    return 0;
}
