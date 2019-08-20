; index: 0, 1, 2, 3, 4, 5, 6,  7,  8,  9, 10, 11,  12, ...
; value: 0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, ...

mov 12, r0
call :fib
halt

fib:
    push r5, r6, lr
    cmp r0, 1
    le? jmp :return

    mov r0, r5

    sub r0, 2, r0
    call :fib

    mov r0, r6

    sub r5, 1, r0
    call :fib

    add r6, r0, r0

    return:
        pop r5, r6, pc
