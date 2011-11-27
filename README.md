svdrp4j
=================

svdrp4j is an implementation of the Simple Video Disk Recorder Protocol (SVDRP) in Java. SVDRP is used by the PVR software VDR to give external applications 
access to some functions of VDR. svdrp4j is a stable library with about 250 unit tests and a code coverage of over 80%.

To get started with svdrp4j have a look at the VDR class. This is a facade, which contains some convenience methods for most of the usual tasks. If you need
more control have a look at the org.hampelratte.svdrp.commands package, which contains all available SVDRP commands, the org.hampelratte.svdrp.responses, 
which contains all possible responses, the Connection class, which is used to send the commands and the parsers in org.hampelratte.svdrp.parsers, which can
be used to parse the responses.