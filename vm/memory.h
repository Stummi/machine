#if !defined(SD_MEMORY_H)
#define SD_MEMORY_H

#include <inttypes.h>
#include <stdio.h>

/*
    initializes the virtual stummidevice memory (64 KB)

    Return Values:
        0 => memory could not me allocated
        1 => everything is fine
*/
int sd_memory_init(FILE * file);

uint8_t  sd_memory_read_8(uint16_t address);
uint16_t sd_memory_read_16(uint16_t address);

void sd_memory_write_8(uint16_t address, uint8_t value);
void sd_memory_write_16(uint16_t address, uint16_t value);

void sd_memory_free(void);

#endif /* SD_MEMORY_H */
