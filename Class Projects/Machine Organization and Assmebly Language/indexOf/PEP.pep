;PMJ's MPP ver2012.0 ...
; AS05.pep2 - This program reads a zero-terminated sequence of integers
; and stores them in an array, prints them out and then reads a second
; zero-terminated sequence of integers.  Each of the integers in the second
; sequence is searched for in the array and the corresponding index is printed.
;
; CMPS 250 - Spring 2022
;
; P.M.J.
;
           BR       main
;
;{ PEP2.pep1 <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
;-------------------------------------------------------------------
; Global "low memory" locations used for temporary static storage
;.GLOBAL    TEMP
TEMP:      .BLOCK   4
;.GLOBAL    JUNK
JUNK:      .BLOCK   2
;.GLOBAL    SAVED
SAVED:     .BLOCK   2
;.GLOBAL    SAVEA
SAVEA:     .BLOCK   2                                              ;where A is saved statically
;.GLOBAL    SAVEX
SAVEX:     .BLOCK   2                                              ;where X is saved statically
;.GLOBAL    SAVEPP
SAVEPP:    .BLOCK   2
;============================================================
;============================================================
;============================================================
;============================================================
;} PEP2.pep1 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
;-----------------------------------------------------------------------
; String declarations
;-----------------------------------------------------------------------
P1:        .ASCII   ":>\x00"
M1:        .ASCII   " found at \x00"
NL:        .ASCII   "\n\x00"
MDONE:     .ASCII   "Done!!!\n\x00"
;-----------------------------------------------------------------------
; Defines
;-----------------------------------------------------------------------
LIMIT:     .EQUATE  32                                             ; The capacity of the array of integers
SENTINEL:  .EQUATE  0                                              ; The sentinel value
;-----------------------------------------------------------------------
; Variables
;-----------------------------------------------------------------------
a:         .BLOCK   64                                             ; (32 * 2) but this is invalid syntax here
n:         .BLOCK   2                                              ; The number of relevant elements in the array
value:     .BLOCK   2
index:     .BLOCK   2                                              ;
;-----------------------------------------------------------------------
main:      NOP0
;----------
;;;;;;;;;; PUSH     SENTINEL,i                                     ; + n = readInts(a,LIMIT,SENTINEL) ;
           STWA     SAVEPP,d                                       ;< PUSH >
           LDWA     SENTINEL,i                                     ;< PUSH >
;;;;;;;;;; PUSHA                                                   ;< PUSH >
           STWA     -2,s                                           ;< PUSHA,PUSH >
           SUBSP    2,i                                            ;< PUSHA,PUSH >
           LDWA     SAVEPP,d                                       ;< PUSH >
;;;;;;;;;; PUSH     LIMIT,i                                        ; | ;
           STWA     SAVEPP,d                                       ;< PUSH >
           LDWA     LIMIT,i                                        ;< PUSH >
;;;;;;;;;; PUSHA                                                   ;< PUSH >
           STWA     -2,s                                           ;< PUSHA,PUSH >
           SUBSP    2,i                                            ;< PUSHA,PUSH >
           LDWA     SAVEPP,d                                       ;< PUSH >
;;;;;;;;;; PUSH     a,i                                            ; | ;
           STWA     SAVEPP,d                                       ;< PUSH >
           LDWA     a,i                                            ;< PUSH >
;;;;;;;;;; PUSHA                                                   ;< PUSH >
           STWA     -2,s                                           ;< PUSHA,PUSH >
           SUBSP    2,i                                            ;< PUSHA,PUSH >
           LDWA     SAVEPP,d                                       ;< PUSH >
           CALL     readInts                                       ; |
           ADDSP    6,i                                            ; |
           STWA     n,d                                            ; +
;----------
;;;;;;;;;; PUSH     n,d                                            ; + printInts(a,n) ;
           STWA     SAVEPP,d                                       ;< PUSH >
           LDWA     n,d                                            ;< PUSH >
;;;;;;;;;; PUSHA                                                   ;< PUSH >
           STWA     -2,s                                           ;< PUSHA,PUSH >
           SUBSP    2,i                                            ;< PUSHA,PUSH >
           LDWA     SAVEPP,d                                       ;< PUSH >
;;;;;;;;;; PUSH     a,i                                            ; | ;
           STWA     SAVEPP,d                                       ;< PUSH >
           LDWA     a,i                                            ;< PUSH >
;;;;;;;;;; PUSHA                                                   ;< PUSH >
           STWA     -2,s                                           ;< PUSHA,PUSH >
           SUBSP    2,i                                            ;< PUSHA,PUSH >
           LDWA     SAVEPP,d                                       ;< PUSH >
           CALL     prntInts                                       ; |
           ADDSP    4,i                                            ; +
;----------
loop:      NOP0                                                    ; do {
           STRO     P1,d                                           ; |   printf(":>")
           DECI     value,d                                        ; | + value = nextInt
           LDWA     value,d                                        ; | +
           CPWA     SENTINEL,i                                     ; |   if(value != SENTINEL) {
           BREQ     done                                           ; |
;;;;;;;;;; PUSH     n,d                                            ; |   + index=indexOf(value,a,n) ;
           STWA     SAVEPP,d                                       ;< PUSH >
           LDWA     n,d                                            ;< PUSH >
;;;;;;;;;; PUSHA                                                   ;< PUSH >
           STWA     -2,s                                           ;< PUSHA,PUSH >
           SUBSP    2,i                                            ;< PUSHA,PUSH >
           LDWA     SAVEPP,d                                       ;< PUSH >
;;;;;;;;;; PUSH     a,i                                            ; |   | ;
           STWA     SAVEPP,d                                       ;< PUSH >
           LDWA     a,i                                            ;< PUSH >
;;;;;;;;;; PUSHA                                                   ;< PUSH >
           STWA     -2,s                                           ;< PUSHA,PUSH >
           SUBSP    2,i                                            ;< PUSHA,PUSH >
           LDWA     SAVEPP,d                                       ;< PUSH >
;;;;;;;;;; PUSH     value,d                                        ; |   | ;
           STWA     SAVEPP,d                                       ;< PUSH >
           LDWA     value,d                                        ;< PUSH >
;;;;;;;;;; PUSHA                                                   ;< PUSH >
           STWA     -2,s                                           ;< PUSHA,PUSH >
           SUBSP    2,i                                            ;< PUSHA,PUSH >
           LDWA     SAVEPP,d                                       ;< PUSH >
           CALL     indexOf                                        ; |   |
           ADDSP    6,i                                            ; |   |
           STWA     index,d                                        ; |   +
;----------                          ; |
           DECO     value,d                                        ; |   + printf("%d found at %f\n")
           STRO     M1,d                                           ; |   +
           DECO     index,d                                        ; |   +
           STRO     NL,d                                           ; |   +
           NOP0                                                    ; |   }
           BR       loop                                           ; + } while(value!=SENTINEL)
;-------------------------------------------------------------
done:      STRO     MDONE,d                                        ; printf("Done!")
           STOP                                                    ;
;-----------------------------------------------------------------------
; S u b p r o g r a m s
;-----------------------------------------------------------------------
;
;{ readInts.pep1 <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
;.GLOBAL  readInts
;-----------------------------------------------------------------------------
; int readInts(int array[], int limit, int sentinel)
;-----------------------------------------------------------------------------
array:     .EQUATE  2
limit:     .EQUATE  4
sentinel:  .EQUATE  6
;-----------------------------------------------------------------------------
;-----------------------------------------------------------------------------
readInts:  NOP0
           LDWX     -1,i                                           ; X = -1
LL0:       NOP0                                                    ; + do {
           ADDX     1,i                                            ; |   X = X + 1
           ASLX                                                    ; | +
           DECI     array,sfx                                      ; | | array[X] = nextInt
           ASRX                                                    ; | +
           CPWX     limit,s                                        ; | + (X < limit) &&
           BRGE     LL1                                            ; | +
           ASLX                                                    ; | +
           LDWA     array,sfx                                      ; | | (array[X] != sentinel) {
           ASRX                                                    ; | +
           CPWA     sentinel,s                                     ; | |
           BRNE     LL0                                            ; | +
           NOP0                                                    ; + } while((X<limit)&&(array[X]!=sentinel))
LL1:       STWX     -2,s                                           ; + A = X
           LDWA     -2,s                                           ; +
           RET
;} readInts.pep1 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
;
;{ prntInts.pep1 <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
;.GLOBAL  prntInts
;-----------------------------------------------------------------------------
; int prntInts(int array[], int n)
;-----------------------------------------------------------------------------
LL2:       .EQUATE  2
LL3:       .EQUATE  4
;-----------------------------------------------------------------------------
M:         .ASCII   " : \x00"
LL4:       .ASCII   "\n\x00"
;-----------------------------------------------------------------------------
prntInts:  NOP0
           LDWX     0,i                                            ;   X = 0
LL5:       CPWX     LL3,s                                          ; + while(X < n) {
           BRGE     LL6                                            ; |
           STWX     -2,s                                           ; | +  printf("%d : %d\n",array[X])
           DECO     -2,s                                           ; | |
           STRO     M,d                                            ; | |
           ASLX                                                    ; | |
           DECO     LL2,sfx                                        ; | |
           ASRX                                                    ; | |
           STRO     LL4,d                                          ; | +
           ADDX     1,i                                            ; |    X = X + 1
           BR       LL5                                            ; + }
LL6:       RET
;} prntInts.pep1 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
;
; Put code here
;
;.GLOBAL  indexOf 
;  int indexOf(int item, int array[],int n);
;---------------------------------------------------------------------------------------------------
;.GLOBAL  indexOf 
;  int indexOf(int item, int array[],int n);
;---------------------------------------------------------------------------------------------------
;.GLOBAL  indexOf 
;  int indexOf(int item, int array[],int n);
;---------------------------------------------------------------------------------------------------
item:        .EQUATE         2               ; item
LL7:         .EQUATE         4               ; array 
LL8:         .EQUATE         6               ; n
;---------------------------------------------------------------------------------------------------
result:      .BLOCK          2
indexOf:         NOP0                             ;
                 LDWX             0,i             ; result = 0


loop1:           NOP0
                 CPWX             LL8,s
                 BRGE             exit
                 ASLX     
                 LDWA             LL7,sfx
                 ASRX
                 CPWA             item,s
                 BREQ             exit
                 ADDX             1,i
                 BR               loop1 

exit:            NOP0
                 CPWX             LL8,s
                 BRNE             done1
                 LDWX             -1,i
                 STWX             result,s 
                 
done1:           STWX             -2,s 
                 LDWA             -2,s
                 RET
;<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
;
;{ STRInput.pep1 <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
;.GLOBAL  STRInput
STRInput:  NOP0
Sobject:   .EQUATE  6
capacity:  .EQUATE  0
ignored:   .EQUATE  2
           SUBSP    4,i
           LDWA     0,i                                            ;ignored = 0;
           STWA     ignored,s                                      ;
           LDWX     -1,i                                           ;X = -1;
           LDWA     0,i                                            ;A = 0;
           LDBA     Sobject,sfx                                    ;capacity = Sobject[0];//the "before byte" holds the length
           STWA     capacity,s                                     ;
           LDWX     0,i                                            ;X = 0;
           CPWX     capacity,s                                     ;if(X < capacity) {
           BRGE     error                                          ;
           LDWA     0,i                                            ;   while(X != capacity) {
LL9:       CPWX     capacity,s                                     ;
           BREQ     full                                           ;
           LDBA     charIn,d                                       ;       //get next char & put in in A
           STBA     Sobject,sfx                                    ;       Sobject = charIn(A);
           LDBA     Sobject,sfx                                    ;       Sobject = A;
           CPWA     '\n',i                                         ;       if(Sobject != '\n') {
           BREQ     LL10                                           ;
           ADDX     1,i                                            ;       X++;
           BR       LL9                                            ;   }
full:      LDWA     ignored,s                                      ;   ignored++;
           ADDA     1,i                                            ;
           STWA     ignored,s                                      ;
           SUBX     1,i                                            ;   X--;
ignore:    LDBA     charIn,d                                       ;   Sobject[X] = nextChar();
           STBA     Sobject,sfx
           LDWA     0,i                                            ;   A = 0;
           LDBA     Sobject,sfx                                    ;   while(Sobject[x] != '\n') {
           CPWA     '\n',i
           BREQ     LL10
           LDWA     ignored,s                                      ;       ignored++;
           ADDA     1,i
           STWA     ignored,s                                      ;   }
           BR       ignore
LL10:      LDBA     0,i                                            ;Sobject[X] = 0;
           STBA     Sobject,sfx
           LDWA     ignored,s                                      ;A = ignored;
           BR       LL11
error:     LDWA     -1,i
LL11:      ADDSP    4,i
           RET
;} STRInput.pep1 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
;
;{ BINOutpt.pep1 <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
;.GLOBAL  BINOutpt
m:         .EQUATE  4
BINOutpt:  NOP0
           SUBSP    0,i
           STWA     SAVED,d
           LDWA     0,i
           STWA     -2,s
           SUBSP    2,i
           LDWA     SAVED,d
           STWA     SAVEA,d
           STWX     SAVEX,d
           LDWA     m,s
           LDWX     16,i
BINOloop:  NOP0
           CPWX     0,i
           BREQ     BINOdone
           ROLA
           BRC      BINO1
BINO0:     STWA     -2,s
           LDBA     '0',i
           STBA     charOut,d
           BR       BINOnext
BINO1:     STWA     -2,s
           LDBA     '1',i
           STBA     charOut,d
BINOnext:  LDWA     -2,s
           SUBX     1,i
           BR       BINOloop
BINOdone:  NOP0
           LDWX     SAVEX,d
           LDWA     SAVEA,d
           ADDSP    0,s
           ADDSP    2,i
           RET
;} BINOutpt.pep1 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
;
;{ DUMPS.pep1 <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
;.GLOBAL  DumpS
Acopy:     .BLOCK   2
Xcopy:     .BLOCK   2
display:   .BLOCK   2
actual:    .BLOCK   2
dmsg:      .ASCII   "------------------------------- DUMPS\n\x00"
cmsg:      .ASCII   ",\x00"
amsg:      .ASCII   "(A=\x00"
xmsg:      .ASCII   "(X=\x00"
dlmsg:     .ASCII   ": \x00"
DumpSN:    .EQUATE  2
DumpS:     NOP0
           STWA     Acopy,d
           STWX     Xcopy,d
           STRO     dmsg,d
           STWA     actual,d
           STRO     amsg,d
           HEXO     actual,d
           STRO     cmsg,d
           DECO     actual,d
           LDBA     ')',i
           STBA     charOut,d
           STRO     cmsg,d
           STWX     actual,d
           STRO     xmsg,d
           HEXO     actual,d
           STRO     cmsg,d
           DECO     actual,d
           LDBA     ')',i
           STBA     charOut,d
           LDBA     '\n',i
           STBA     charOut,d
           LDWX     DumpSN,s
           MOVSPA
           ADDA     4,i
           STWA     actual,d
DumpLoop:  CPWX     0,i
           BRLE     DumpDone
           HEXO     actual,d
           STRO     dlmsg,d
           LDWA     actual,n
           STWA     display,d
           HEXO     display,d
           STRO     cmsg,d
           DECO     display,d
           LDBA     '\n',i
           STBA     charOut,d
           LDWA     actual,d
           ADDA     2,i
           STWA     actual,d
           SUBX     2,i
           BR       DumpLoop
DumpDone:  LDWX     Xcopy,d
           LDWA     Acopy,d
           RET
;} DUMPS.pep1 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
;>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
STOPEND:   STOP
           .END
;Resolver Report:
; loop --> LL0
; done --> LL1
; array --> LL2
; n --> LL3
; NL --> LL4
; loop --> LL5
; done --> LL6
; array --> LL7
; n --> LL8
; loop --> LL9
; done --> LL10
; return --> LL11
