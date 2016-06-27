ABOUT
--
Bcrypt is a cross platform file encryption utility. Encrypted files are
portable across all supported operating systems and processors. Passphrases
are between 8 and 56 characters and are hashed internally to a 448 bit
key. However, all characters supplied are significant. The stronger your
passphrase, the more secure your data.

Bcrypt uses the blowfish encryption algorithm published by Bruce Schneier
in 1993. More information on the algorithm can be found at:
	http://www.counterpane.com/blowfish.html

Specifically, bcrypt uses Paul Kocher's implementation of the algorithm.
The source distributed with bcrypt has been slightly altered from the 
original. Original source code can be obtained from:
	http://www.counterpane.com/bfsh-koc.zip

SUPPORTED PLATFORMS
--
Bcrypt has been successfully tested on the following platforms:

  x86
    FreeBSD, OpenBSD, Linux, Cygwin, Win32
  Sparc R220
    Solaris 2.7, 2.8
  Sparc Ultra60
    Linux 2.4
  Alpha
    Linux 2.4
  PPC G4
    MacOS X 10.1 SERVER
  PPC RS/6000
    Linux 2.4

No other operating systems have been tested, but most should work with 
minimal modifications. If you get bcrypt to compile without errors on any 
other platform / architecture, I'd like to know about it. If patches are 
necessary to get bcrypt work on your OS, I will try to incorporate them 
into the main distribution.

If you have a machine not listed above that is incapable of compiling
bcrypt and are willing to give me access to the machine, I will make an
attempt to port it to your OS.

SYSTEM REQUIREMENTS
--
zlib - http://www.gzip.org/zlib/

INSTALLATION
--
If you're so inclined, edit config.h and change the defaults to whatever
you think is appropriate for your needs. If you choose not to have
bcrypt remove input files after processing, or set SECUREDELETE to 0,
you are likely to have data on your hard drive that can be recovered
even after deletion.

All of these options can be set on the command line as well. When you're
satisfied with the default settings, simply type:
  make
then su and type:
  make install

It would be wise to test the installation on a few unimportant files
before encrypting anything you value and removing the only copy.

USAGE
--
       bcrypt [-orc][-sN] file ...

Encrypted files will be saved with an extension of .bfe. Any files ending
in .bfe will be assumed to be encrypted with bcrypt and will attempt to
decrypt them. Any other input files will be encrypted. If more than one
type of file is given, bcrypt will process all files which are the same as
the first filetype given.

By default, bcrypt will compress input files before encryption, remove
input files after they are processed (assuming they are processed
successfully) and overwrite input files with random data to prevent data
recovery.

Passphrases may be between 8 and 56 characters. Regardless of the
passphrase size, the key is hashed internally to 448 bits - the largest
keysize supported by the blowfish algorithm.  However, it is still wise to
use a strong passphrase.

OPTIONS
       -o     print output to standard out. Implies -r.

       -c     DO NOT compress files before encryption.

       -r     DO NOT remove input files after processing

       -sN    How many times to overwrite input files with random
              data  before  processing.   The  default  number of
              overwrites is 3. Use -s0 to disable  this  feature.
              No effect if -r is supplied.

The options o,c and r each have the opposite effects if the appropriate 
settings are altered from the default in config.h. To determine what 
effect each of these have, run bcrypt without any options.

Encrypted files should be compatible between most systems.  Binary 
compatibility has been tested for all systems listed above.

/* ====================================================================
 * Copyright (c) 2002 Johnny Shelley.  All rights reserved.
 *
 * Bcrypt is licensed under the BSD software license. See the file
 * called 'LICENSE' that you should have received with this software
 * for details
 * ====================================================================
 */
