#include "vm.h"

#include <stdio.h>

int main(int argc, char ** argv)
{
    FILE * file;
    int result;

    if (argc < 2)
    {
        fprintf(stderr, "Error: No source file specified!\nUsage: %s [filename]\n", argv[0]);
        return -1;
    }

    file = fopen(argv[1], "r");
    if (!file)
    {
        fprintf(stderr, "Error: Could not open source file \"%s\"!\n", argv[1]);
        return -1;
    }

    result = sd_run(file);

    fclose(file);
    return result;
}
