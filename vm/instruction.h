#if !defined(SD_INSTRUCTION_H)
#define SD_INSTRUCTION_H

#include <inttypes.h>

/* the type for instructions */
typedef uint8_t sd_instruction_t;

/* possible instruction values */
enum
{
    /* Special */
    NOP     = 0x00,
    STOP    = 0xFF,

    /* ARG manipulation */
    AGETB   = 0x01,
    AGETW   = 0x02,
    APUTB   = 0x03,
    APUTW   = 0x04,
    ASET    = 0x05,
    AINC    = 0x06,
    ADEC    = 0x07,

    /* USR manipulation */
    UGETB   = 0x08,
    UGETW   = 0x09,
    UPUTB   = 0x0A,
    UPUTW   = 0x0B,
    USET    = 0x0C,
    UINC    = 0x0D,
    UDEC    = 0x0E,

    /* DPTR manipulation */
    DAJMP   = 0x0F,
    DRJMP   = 0x10,

    /* IPTR manipulation */
    IAJMP   = 0x11,
    IRJMP   = 0x12,
    ICJMP   = 0x13,
};

#endif /* SD_INSTRUCTION_H */
