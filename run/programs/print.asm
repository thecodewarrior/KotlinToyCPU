
print:
    mov r0, r1
    mov 0, r0
    pcall 0
    mov lr, pc

println:
    mov 0xA, r1
    mov 0, r0
    pcall 0
    mov lr, pc

print_hex:
    mov r0, r2
    mov 0, r0

    shr r2, 28, r1
    and r1, 0xf, r1
    ldr'b r1 [:print_hex_digits, r1]
    pcall 0

    shr r2, 24, r1
    and r1, 0xf, r1
    ldr'b r1 [:print_hex_digits, r1]
    pcall 0

    shr r2, 20, r1
    and r1, 0xf, r1
    ldr'b r1 [:print_hex_digits, r1]
    pcall 0

    shr r2, 16, r1
    and r1, 0xf, r1
    ldr'b r1 [:print_hex_digits, r1]
    pcall 0

    shr r2, 12, r1
    and r1, 0xf, r1
    ldr'b r1 [:print_hex_digits, r1]
    pcall 0

    shr r2, 8, r1
    and r1, 0xf, r1
    ldr'b r1 [:print_hex_digits, r1]
    pcall 0

    shr r2, 4, r1
    and r1, 0xf, r1
    ldr'b r1 [:print_hex_digits, r1]
    pcall 0

    shr r2, 0, r1
    and r1, 0xf, r1
    ldr'b r1 [:print_hex_digits, r1]
    pcall 0

    mov lr, pc

print_hex_digits: %data .ascii "0123456789abcdef"
