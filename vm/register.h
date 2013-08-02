#if !defined(SD_REGISTER_H)
#define SD_REGISTER_H

#include <inttypes.h>

#if defined(SD_REGISTER_DEFINITION)
#define SD_REGISTER(name) uint16_t name
#else
#define SD_REGISTER(name) extern uint16_t name
#endif

/* Instruction Pointer register. */
SD_REGISTER(IPTR);

/* Data Pointer register */
SD_REGISTER(DPTR);

/* Argument register */
SD_REGISTER(ARG);

/* User register */
SD_REGISTER(USR);

#endif /* SD_REGISTER_H */
