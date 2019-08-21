#include log.asm

; pcall PTEXT, PRINT_CHAR
; pcall PTEXT, PRINT_ASCIIZ
; pcall PTEXT, PRINT_FMT

println:
    mov 0xA, r0
    pcall PTEXT, PRINT_CHAR
    mov lr, pc

print_hex:
    mov r0, r1

    shr r1, 28, r0
    and r0, 0xf, r0
    ldr'b r0 [:print_hex_digits, r0]
    pcall PTEXT, PRINT_CHAR

    shr r1, 24, r0
    and r0, 0xf, r0
    ldr'b r0 [:print_hex_digits, r0]
    pcall PTEXT, PRINT_CHAR

    shr r1, 20, r0
    and r0, 0xf, r0
    ldr'b r0 [:print_hex_digits, r0]
    pcall PTEXT, PRINT_CHAR

    shr r1, 16, r0
    and r0, 0xf, r0
    ldr'b r0 [:print_hex_digits, r0]
    pcall PTEXT, PRINT_CHAR

    shr r1, 12, r0
    and r0, 0xf, r0
    ldr'b r0 [:print_hex_digits, r0]
    pcall PTEXT, PRINT_CHAR

    shr r1, 8, r0
    and r0, 0xf, r0
    ldr'b r0 [:print_hex_digits, r0]
    pcall PTEXT, PRINT_CHAR

    shr r1, 4, r0
    and r0, 0xf, r0
    ldr'b r0 [:print_hex_digits, r0]
    pcall PTEXT, PRINT_CHAR

    shr r1, 0, r0
    and r0, 0xf, r0
    ldr'b r0 [:print_hex_digits, r0]
    pcall PTEXT, PRINT_CHAR

    mov lr, pc

print_hex_digits: %data .ascii "0123456789abcdef"
