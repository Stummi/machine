all: stummi_vm

stummi_vm: main.o register.o memory.o vm.o
	gcc -std=c99 -Os -o stummi_vm main.o register.o memory.o vm.o

main.o: main.c
	gcc -std=c99 -O3 -Wall -c -o main.o main.c

register.o: register.c
	gcc -std=c99 -O3 -Wall -c -o register.o register.c

memory.o: memory.c
	gcc -std=c99 -O3 -Wall -c -o memory.o memory.c

vm.o: vm.c

register.c: register.h

memory.c: memory.h

vm.c: vm.h instruction.h

.PHONY: clean
clean:
	rm -f main.o
	rm -f register.o
	rm -f memory.o
	rm -f vm.o
	rm -f stummi_vm

