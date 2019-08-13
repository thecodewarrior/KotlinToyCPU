CONST.i32 R0 0x10
CONST.i32 R1 0x03
start:
    ADD.i32 R0 R1 R0
    JMP @start
HALT
