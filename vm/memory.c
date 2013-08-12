#include "memory.h"
#include "instruction.h"
#include "platform.h"

#include <stdlib.h>
#include <stdio.h>

struct
{
    uint8_t * memory_;
} sd_memory = { .memory_ = NULL };

int sd_memory_init(FILE * file)
{
    int ptr;

    if (!sd_memory.memory_)
    {
        sd_memory.memory_ = malloc(65536);
        if (!sd_memory.memory_)
            return 0;
    }

    for (ptr = 0; ptr < 65536; ++ptr)
    {
        int value = fgetc(file);
        if (value == EOF)
        {
            sd_memory.memory_[ptr] = STOP;
            break;
        }
        else
            sd_memory.memory_[ptr] = value & 0xFF;
    }

    return 1;
}

uint8_t sd_memory_read_8(uint16_t address)
{
    switch (address)
    {
    /* output device */
    case 0xFC00:
    case 0xFC01:
        return sd_memory.memory_[address];
        break;
    /* input device */
    case 0xFC02:
        return platform_getch() & 0xFF;
        break;
    case 0xFC03:
        return platform_kbhit() & 0xFF;
        break;
    default:
        return sd_memory.memory_[address];
    }
}

uint16_t sd_memory_read_16(uint16_t address)
{
    uint16_t result = sd_memory_read_8(address) << 8;

    if (address == 65535)
        address = 0;
    else
        ++address;

    result |= sd_memory_read_8(address);
    return result;
}

void sd_memory_write_8(uint16_t address, uint8_t value)
{
    switch (address)
    {
    /* output device */
    case 0xFC01:
        if (value) 
        {
            fputc(sd_memory.memory_[0xFC00], stdout);
        }
        break;
    /* input device */
    case 0xFC02:
    case 0xFC03: break;
    default:
        sd_memory.memory_[address] = value;
    }
}

void sd_memory_write_16(uint16_t address, uint16_t value)
{
    sd_memory_write_8(address, value >> 8);

    if (address == 65535)
        address = 0;
    else
        ++address;

    sd_memory_write_8(address, value & 0xFF);
}

void sd_memory_free(void)
{
    free(sd_memory.memory_);
}
