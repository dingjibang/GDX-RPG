DEFAULTS = Makefile includes.h blowfish.h functions.h config.h
CC = gcc
CFLAGS = -O2 -Wall 
COMPILE = ${CC} ${CFLAGS}
OBJS = main.o blowfish.o rwfile.o keys.o wrapbf.o endian.o wrapzl.o
LDFLAGS = -L/usr/local/lib -lz 
PREFIX = /usr/local

bcrypt:	${OBJS} Makefile
	${COMPILE} -o bcrypt ${OBJS} ${LDFLAGS}

install:	bcrypt Makefile
	mkdir -p ${PREFIX}/bin;\
	mkdir -p ${PREFIX}/man/man1;\
	cp bcrypt ${PREFIX}/bin;\
	cp bcrypt.1 ${PREFIX}/man/man1;\
	chmod 755 ${PREFIX}/bin/bcrypt;\
	chmod 644 ${PREFIX}/man/man1/bcrypt.1

main.o:	main.c ${DEFAULTS}
	${COMPILE} -c main.c

blowfish.o:	blowfish.c ${DEFAULTS}
	${COMPILE} -c blowfish.c

rwfile.o:	rwfile.c ${DEFAULTS}
	${COMPILE} -c rwfile.c

keys.o:	keys.c ${DEFAULTS}
	${COMPILE} -c keys.c

wrapbf.o:	wrapbf.c ${DEFAULTS}
	${COMPILE} -c wrapbf.c

wrapzl.o:	wrapzl.c ${DEFAULTS}
	${COMPILE} -c wrapzl.c

endian.o:	endian.c ${DEFAULTS}
	${COMPILE} -c endian.c

clean:
	rm -rf *.o bcrypt bcrypt.core core bcrypt.tgz 

