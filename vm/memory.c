#include "memory.h"

#include <stdlib.h>

struct
{
    uint8_t * memory_;
} sd_memory = { .memory_ = NULL };

int sd_memory_init(void)
{
    if (!sd_memory.memory_)
        sd_memory.memory_ = malloc(65536);

    return (sd_memory.memory_ ? 1 : 0);
}

uint8_t  sd_memory_read_8(uint16_t address)
{
    return sd_memory.memory_[address];
}

uint16_t sd_memory_read_16(uint16_t address)
{
    uint16_t result = sd_memory.memory_[address] << 8;

    if (address == 65535)
        address = 0;
    else
        ++address;

    result |= sd_memory.memory_[address];
}

void sd_memory_write_8(uint16_t address, uint8_t value)
{
    sd_memory.memory_[address] = value;
}

void sd_memory_write_16(uint16_t address, uint16_t value)
{
    sd_memory.memory_[address] = value >> 8;

    if (address == 65535)
        address = 0;
    else
        ++address;

    sd_memory.memory_[address] = value & 0xFF;
}

void sd_memory_free(void)
{
    free(sd_memory.memory_);
}
