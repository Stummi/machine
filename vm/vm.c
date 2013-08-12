#include "vm.h"
#include "memory.h"
#include "register.h"
#include "instruction.h"
#include "platform.h"

#include <stdio.h>

static volatile int halt = 0;

/* ARG manipulation */

static void sd_agetb(void) { ARG = 0x00FF & sd_memory_read_8(DPTR); }
static void sd_agetw(void) { ARG = sd_memory_read_16(DPTR); }
static void sd_aputb(void) { sd_memory_write_8(DPTR, ARG & 0xFF); }
static void sd_aputw(void) { sd_memory_write_16(DPTR, ARG); }
static void sd_ainc(void) { ++ARG; }
static void sd_adec(void) { --ARG; }
static void sd_aset(void) { ARG = sd_memory_read_16(IPTR); IPTR += 2; }

/* USR manipulation */

static void sd_ugetb(void) { USR = 0x00FF & sd_memory_read_8(DPTR); }
static void sd_ugetw(void) { USR = sd_memory_read_16(DPTR); }
static void sd_uputb(void) { sd_memory_write_8(DPTR, USR & 0xFF); }
static void sd_uputw(void) { sd_memory_write_16(DPTR, USR); }
static void sd_uinc(void) { ++USR; }
static void sd_udec(void) { --USR; }
static void sd_uset(void) { USR = sd_memory_read_16(IPTR); IPTR += 2; }

/* DPTR manipulation */

static void sd_dajmp(void)
{
    DPTR = ARG;
}

static void sd_drjmp(void)
{
    DPTR = ((uint32_t)DPTR + (uint32_t)ARG) % 65536;
}

/* IPTR manipulation */

static void sd_iajmp(void)
{
    IPTR = ARG;
}

static void sd_irjmp(void)
{
    IPTR = ((uint32_t)IPTR + (uint32_t)ARG) % 65536;
}

static void sd_icjmp(void)
{
    if (ARG)
        IPTR += 4;
}

/* the main loop */

int sd_run(FILE * file)
{
    uint8_t opcode = NOP;

    if (!sd_memory_init(file))
    {
        fprintf(stderr, "Error: Could not allocate memory for VM!\n");
        return -1;
    }

    platform_init();

    while ((opcode = sd_memory_read_8(IPTR++)) != STOP && !halt)
    {
        switch (opcode)
        {
        case NOP: break;

        case AGETB: sd_agetb(); break;
        case AGETW: sd_agetw(); break;
        case APUTB: sd_aputb(); break;
        case APUTW: sd_aputw(); break;
        case ASET:  sd_aset();  break;
        case AINC:  sd_ainc();  break;
        case ADEC:  sd_adec();  break;

        case UGETB: sd_ugetb(); break;
        case UGETW: sd_ugetw(); break;
        case UPUTB: sd_uputb(); break;
        case UPUTW: sd_uputw(); break;
        case USET:  sd_uset();  break;
        case UINC:  sd_uinc();  break;
        case UDEC:  sd_udec();  break;

        case DAJMP: sd_dajmp(); break;
        case DRJMP: sd_drjmp(); break;

        case IAJMP: sd_iajmp(); break;
        case IRJMP: sd_irjmp(); break;
        case ICJMP: sd_icjmp(); break;
        
        default:
            /* unknown opcode */
            halt = 1;
        }
    }

    platform_quit();
    sd_memory_free();

    return 0;
}
