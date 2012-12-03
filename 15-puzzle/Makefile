CC=gcc -Wall -Werror -O
assignment=astar puzzleADT linkedADT priqADT boardADT final
ofiles=astar.o puzzleADT.o linkedADT.o priqADT.o boardADT.o

#$@ is target name, $< is implicit source
astar: astar.c
	$(CC) -c -o $@.o $<

puzzleADT: puzzleADT.c
	$(CC) -c -o $@.o $<

linkedADT: linkedADT.c
	$(CC) -c -o $@.o $<

priqADT: priqADT.c
	$(CC) -c -o $@.o $<

boardADT: boardADT.c
	$(CC) -c -o $@.o $<

final: $(ofiles)
	$(CC) -o $@ $(ofiles)

all: $(assignment)
	rm *.o

clean:
	rm $(assignment) $(ofiles) core
