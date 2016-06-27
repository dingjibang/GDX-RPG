To run the program, make sure the bcrypt.exe and zlib.dll files are 
in the same directory.  It will also work if zlib.dll is located 
elsewhere, but still in the path.  The win32 native version behaves
slightly differently, in that it doesn't hide the passphrase as you 
type it in.  Otherwise, it's functionally identical to Un*x native 
code.

ZLib for Win32 obtained from the GnuWin32 project at:
http://sourceforge.net/project/showfiles.php?group_id=23617
Download zlib-1.1.4-bin.zip to get the .dll.

If you plan on compiling from source, download zlib-1.1.4-bin.zip to 
get the .dll, and zlib-1.1.4-lib.zip to get the include files.  Place 
the .dll file in the win32console directory, and everything should 
work perfectly from within the Visual Studio environment.

