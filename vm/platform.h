#if !defined(SD_PLATFORM_H)
#define SD_PLATFORM_H

void platform_init(void);
void platform_quit(void);

int platform_kbhit(void);
int platform_getch(void);

#endif /* SD_PLATFORM_H */
