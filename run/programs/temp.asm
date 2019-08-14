restart:
CONST.i32 R0 0x10
CONST.i32 R1 0x02
start:
    ADD.i32 R0 R1 R0
    JMP_LT_RC.i32 R0 0xF0 @start
    ADD_RC.i32 R2 0 R2
    JMP @restart
HALT
