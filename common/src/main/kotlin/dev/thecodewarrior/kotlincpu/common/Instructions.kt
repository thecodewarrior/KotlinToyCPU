package dev.thecodewarrior.kotlincpu.common

import dev.thecodewarrior.kotlincpu.common.Arguments.asm_const
import dev.thecodewarrior.kotlincpu.common.Arguments.reg
import dev.thecodewarrior.kotlincpu.common.Arguments.label
import dev.thecodewarrior.kotlincpu.common.Arguments.u8
import dev.thecodewarrior.kotlincpu.common.Arguments.i8
import dev.thecodewarrior.kotlincpu.common.Arguments.u16
import dev.thecodewarrior.kotlincpu.common.Arguments.i16
import dev.thecodewarrior.kotlincpu.common.Arguments.u32
import dev.thecodewarrior.kotlincpu.common.Arguments.i32
import dev.thecodewarrior.kotlincpu.common.Arguments.u64
import dev.thecodewarrior.kotlincpu.common.Arguments.i64
import dev.thecodewarrior.kotlincpu.common.Arguments.f32
import dev.thecodewarrior.kotlincpu.common.Arguments.f64

object Instructions {
    private val opcodes = OpcodeTracker()

    val instructions = mutableListOf<Insn>()

    val nop = +Insn("nop", opcodes.create()) %
        """
            %op%: nop
                  No-op
        """.trimIndent()


    val mov_imm = +Insn("mov_imm", opcodes.create(), u32("value"), reg("dest")) %
        """
            %op%: mov %args%
                  Move a constant into a register
        """.trimIndent()
    val pseudo_mov_label = Insn(mov_imm, label("value"), reg("dest")) %
        """
            %op%: mov %args%
                  Move a label's address into a register
        """.trimIndent()
    val mov_r = +Insn("mov_r", opcodes.create(), reg("src"), reg("dest")) %
        """
            %op%: mov %args%
                  Move one register's value into another register
        """.trimIndent()

    val ldr_imm = +Insn("ldr_imm", opcodes.create(), reg("dest"), asm_const("["), u32("address"), asm_const("]")) %
        """
            %op%: ldr %args%
                  Load a value from a fixed address
        """.trimIndent()
    val ldr_imm_off_r = +Insn("ldr_imm_off_r", opcodes.create(), reg("dest"), asm_const("["), u32("address"), reg("offset"), asm_const("]")) %
        """
            %op%: ldr %args%
                  Load a value from a fixed address with the offset stored in the offset register
        """.trimIndent()
    val pseudo_ldr_label = Insn(ldr_imm, reg("dest"), asm_const("["), label("address"), asm_const("]")) %
        """
            %op%: ldr %args%
                  Load a value from the label's location
        """.trimIndent()
    val pseudo_ldr_label_off_r = Insn(ldr_imm_off_r, reg("dest"), asm_const("["), label("address"), reg("offset"), asm_const("]")) %
        """
            %op%: ldr %args%
                  Load a value from the label's location with the offset stored in the offset register
        """.trimIndent()

    val ldr_r = +Insn("ldr_r", opcodes.create(), reg("dest"), asm_const("["), reg("address"), asm_const("]")) %
        """
            %op%: ldr %args%
                  Load a value from the address stored in a register
        """.trimIndent()
    val ldr_r_off_imm = +Insn("ldr_r_off_imm", opcodes.create(), reg("dest"), asm_const("["), reg("address"), i32("offset"), asm_const("]")) %
        """
            %op%: ldr %args%
                  Load a value from the address stored in a register with a fixed offset
        """.trimIndent()
    val ldr_r_off_r = +Insn("ldr_r_off_r", opcodes.create(), reg("dest"), asm_const("["), reg("address"), reg("offset"), asm_const("]")) %
        """
            %op%: ldr %args%
                  Load a value from the address stored in a register with the offset stored in the offset register
        """.trimIndent()

    val str_imm = +Insn("str_imm", opcodes.create(), reg("src"), asm_const("["), u32("address"), asm_const("]")) %
        """
            %op%: str %args%
                  Store a value at a fixed address
        """.trimIndent()
    val str_imm_off_r = +Insn("str_imm_off_r", opcodes.create(), reg("src"), asm_const("["), u32("address"), reg("offset"), asm_const("]")) %
        """
            %op%: str %args%
                  Store a value at a fixed address with the offset stored in the offset register
        """.trimIndent()
    val pseudo_str_label = Insn(str_imm, reg("src"), asm_const("["), label("address"), asm_const("]")) %
        """
            %op%: str %args%
                  Store a value at the label's location
        """.trimIndent()
    val pseudo_str_label_off_r = Insn(str_imm_off_r, reg("src"), asm_const("["), label("address"), reg("offset"), asm_const("]")) %
        """
            %op%: str %args%
                  Store a value at the label's location with the offset stored in the offset register
        """.trimIndent()

    val str_r = +Insn("str_r", opcodes.create(), reg("src"), asm_const("["), reg("address"), asm_const("]")) %
        """
            %op%: str %args%
                  Store a value at the address stored in a register
        """.trimIndent()
    val str_r_off_imm = +Insn("str_r_off_imm", opcodes.create(), reg("src"), asm_const("["), reg("address"), i32("offset"), asm_const("]")) %
        """
            %op%: str %args%
                  Store a value at the address stored in a register with a fixed offset
        """.trimIndent()
    val str_r_off_r = +Insn("str_r_off_r", opcodes.create(), reg("src"), asm_const("["), reg("address"), reg("offset"), asm_const("]")) %
        """
            %op%: str %args%
                  Store a value at the address stored in a register with the offset stored in the offset register
        """.trimIndent()

    val cmp_imm = +Insn("cmp_imm", opcodes.create(), reg("left"), u32("right")) %
        """
            %op%: cmp %args%
                  Compare a register's value with a constant value
        """.trimIndent()
    val cmp_r = +Insn("cmp_r", opcodes.create(), reg("left"), reg("right")) %
        """
            %op%: cmp %args%
                  Compare a register's value with another register's value
        """.trimIndent()

    val jmp_imm = +Insn("jmp", opcodes.create(), u32("dest")) %
        """
            %op%: jmp %args%
                  Jump to a constant address
        """.trimIndent()
    val pseudo_jmp_label = Insn(jmp_imm, label("dest")) %
        """
            %op%: jmp %args%
                  Jump to a label
        """.trimIndent()
    val jmp_r = +Insn("jmp_r", opcodes.create(), reg("dest")) %
        """
            %op%: jmp %args%
                  Jump to the address in a register
        """.trimIndent()

    val call_imm = +Insn("call_imm", opcodes.create(), u32("subroutine")) %
        """
            %op%: call %args%
                  Jump to a constant address, placing the return address in the link register 
        """.trimIndent()
    val pseudo_call_label = Insn(call_imm, label("subroutine")) %
        """
            %op%: call %args%
                  Jump to a label, placing the return address in the link register 
        """.trimIndent()
    val call_r = +Insn("call_r", opcodes.create(), reg("subroutine")) %
        """
            %op%: call %args%
                  Jump to the address in a register, placing the return address in the link register 
        """.trimIndent()

    val inc = +Insn("inc", opcodes.create(), reg("reg")) %
        """
            %op%: inc %args%
                  Increment the value in a register
        """.trimIndent()
    val dec = +Insn("dec", opcodes.create(), reg("reg")) %
        """
            %op%: dec %args%
                  Decrement the value in a register
        """.trimIndent()

    val add_imm = +Insn("add_imm", opcodes.create(), reg("left"), u32("right"), reg("dest")) %
        """
            %op%: add %args%
                  Add a constant value to the value in a register
        """.trimIndent()
    val add_r = +Insn("add_r", opcodes.create(), reg("left"), reg("right"), reg("dest")) %
        """
            %op%: add %args%
                  Add the values of two registers
        """.trimIndent()

    val sub_imm = +Insn("sub_imm", opcodes.create(), reg("left"), u32("right"), reg("dest")) %
        """
            %op%: sub %args%
                  Subtract a constant value to from value in a register
        """.trimIndent()
    val sub_r = +Insn("sub_r", opcodes.create(), reg("left"), reg("right"), reg("dest")) %
        """
            %op%: sub %args%
                  Subtract the values of two registers
        """.trimIndent()

    val mul_imm = +Insn("mul_imm", opcodes.create(), reg("left"), u32("right"), reg("dest")) %
        """
            %op%: mul %args%
                  Multiply the value in a register by a constant
        """.trimIndent()
    val mul_r = +Insn("mul_r", opcodes.create(), reg("left"), reg("right"), reg("dest")) %
        """
            %op%: mul %args%
                  Multiply the values of two registers
        """.trimIndent()

    val div_imm = +Insn("div_imm", opcodes.create(), reg("left"), u32("right"), reg("dest")) %
        """
            %op%: div %args%
                  Divide the value in a register by a constant (unsigned)
        """.trimIndent()
    val div_r = +Insn("div_r", opcodes.create(), reg("left"), reg("right"), reg("dest")) %
        """
            %op%: div %args%
                  Divide the values of two registers (unsigned)
        """.trimIndent()
    val sdiv_imm = +Insn("sdiv_imm", opcodes.create(), reg("left"), u32("right"), reg("dest")) %
        """
            %op%: sdiv %args%
                  Divide the value in a register by a constant (signed)
        """.trimIndent()
    val sdiv_r = +Insn("sdiv_r", opcodes.create(), reg("left"), reg("right"), reg("dest")) %
        """
            %op%: sdiv %args%
                  Divide the values of two registers (signed)
        """.trimIndent()

    val mod_imm = +Insn("mod_imm", opcodes.create(), reg("left"), u32("right"), reg("dest")) %
        """
            %op%: mod %args%
                  Get the modulo of the value in a register and a constant (unsigned)
        """.trimIndent()
    val mod_r = +Insn("mod_r", opcodes.create(), reg("left"), reg("right"), reg("dest")) %
        """
            %op%: mod %args%
                  Get the modulo of the values of two registers (unsigned)
        """.trimIndent()
    val smod_imm = +Insn("smod_imm", opcodes.create(), reg("left"), u32("right"), reg("dest")) %
        """
            %op%: smod %args%
                  Get the modulo of the value in a register and a constant (signed)
        """.trimIndent()
    val smod_r = +Insn("smod_r", opcodes.create(), reg("left"), reg("right"), reg("dest")) %
        """
            %op%: smod %args%
                  Get the modulo of the values of two registers (signed)
        """.trimIndent()

    val shl_imm = +Insn("shl_imm", opcodes.create(), reg("value"), u32("shift"), reg("dest")) %
        """
            %op%: shl %args%
                  Shift the bits of a register left by a constant value
        """.trimIndent()
    val shl_r = +Insn("shl_r", opcodes.create(), reg("value"), reg("shift"), reg("dest")) %
        """
            %op%: shl %args%
                  Shift the bits of a register left by the value in a register
        """.trimIndent()

    val shr_imm = +Insn("shr_imm", opcodes.create(), reg("value"), u32("shift"), reg("dest")) %
        """
            %op%: shr %args%
                  Shift the bits of a register right by a constant value (unsigned)
        """.trimIndent()
    val shr_r = +Insn("shr_r", opcodes.create(), reg("value"), reg("shift"), reg("dest")) %
        """
            %op%: shr %args%
                  Shift the bits of a register right by the value in a register (unsigned)
        """.trimIndent()
    val sshr_imm = +Insn("sshr_imm", opcodes.create(), reg("value"), u32("shift"), reg("dest")) %
        """
            %op%: sshr %args%
                  Shift the bits of a register right by a constant value (signed)
        """.trimIndent()
    val sshr_r = +Insn("sshr_r", opcodes.create(), reg("value"), reg("shift"), reg("dest")) %
        """
            %op%: sshr %args%
                  Shift the bits of a register right by the value in a register (signed)
        """.trimIndent()

    val and_imm = +Insn("and_imm", opcodes.create(), reg("left"), u32("right"), reg("dest")) %
        """
            %op%: and %args%
                  Perform a bitwise AND between the value in a register and a constant
        """.trimIndent()
    val and_r = +Insn("and_r", opcodes.create(), reg("left"), reg("right"), reg("dest")) %
        """
            %op%: and %args%
                  Perform a bitwise AND between the values in two registers
        """.trimIndent()
    val or_imm = +Insn("or_imm", opcodes.create(), reg("left"), u32("right"), reg("dest")) %
        """
            %op%: or %args%
                  Perform a bitwise OR between the value in a register and a constant
        """.trimIndent()
    val or_r = +Insn("or_r", opcodes.create(), reg("left"), reg("right"), reg("dest")) %
        """
            %op%: or %args%
                  Perform a bitwise OR between the values in two registers
        """.trimIndent()
    val xor_imm = +Insn("xor_imm", opcodes.create(), reg("left"), u32("right"), reg("dest")) %
        """
            %op%: xor %args%
                  Perform a bitwise XOR between the value in a register and a constant
        """.trimIndent()
    val xor_r = +Insn("xor_r", opcodes.create(), reg("left"), reg("right"), reg("dest")) %
        """
            %op%: xor %args%
                  Perform a bitwise XOR between the values in two registers
        """.trimIndent()

    val not = +Insn("not", opcodes.create(), reg("value"), reg("dest")) %
        """
            %op%: not %args%
                  Invert the bits of a register
        """.trimIndent()

    val push1 = +Insn("push1", opcodes.create(), reg("value1")) %
        """
            %op%: push %args%
                  Push the value in a register onto the stack
        """.trimIndent()
    val push2 = +Insn("push2", opcodes.create(), reg("value1"), reg("value2")) %
        """
            %op%: push %args%
                  Push the values in two register onto the stack
        """.trimIndent()
    val push3 = +Insn("push3", opcodes.create(), reg("value1"), reg("value2"), reg("value3")) %
        """
            %op%: push %args%
                  Push the values in three register onto the stack
        """.trimIndent()
    val push4 = +Insn("push4", opcodes.create(), reg("value1"), reg("value2"), reg("value3"), reg("value4")) %
        """
            %op%: push %args%
                  Push the values in four register onto the stack
        """.trimIndent()
    val push5 = +Insn("push5", opcodes.create(), reg("value1"), reg("value2"), reg("value3"), reg("value4"), reg("value5")) %
        """
            %op%: push %args%
                  Push the values in five register onto the stack
        """.trimIndent()

    val pop1 = +Insn("pop1", opcodes.create(), reg("dest1")) %
        """
            %op%: pop %args%
                  Pop one value off the stack
        """.trimIndent()
    val pop2 = +Insn("pop2", opcodes.create(), reg("dest1"), reg("dest2")) %
        """
            %op%: pop %args%
                  Pop two values off the stack
        """.trimIndent()
    val pop3 = +Insn("pop3", opcodes.create(), reg("dest1"), reg("dest2"), reg("dest3")) %
        """
            %op%: pop %args%
                  Pop three values off the stack
        """.trimIndent()
    val pop4 = +Insn("pop4", opcodes.create(), reg("dest1"), reg("dest2"), reg("dest3"), reg("dest4")) %
        """
            %op%: pop %args%
                  Pop four values off the stack
        """.trimIndent()
    val pop5 = +Insn("pop5", opcodes.create(), reg("dest1"), reg("dest2"), reg("dest3"), reg("dest4"), reg("dest5")) %
        """
            %op%: pop %args%
                  Pop five values off the stack
        """.trimIndent()

    val peek = +Insn("peek", opcodes.create(), reg("dest")) %
        """
            %op%: peek %args%
                  Peek the value at the top of the stack
        """.trimIndent()

    val mkframe = +Insn("mkframe", opcodes.create()) %
        """
            %op%: mkframe %args%
                  Create a stack frame, storing the current frame pointer immediately before the new frame pointer
        """.trimIndent()
    val mkframe_keep_imm = +Insn("mkframe_keep_imm", opcodes.create(), u32("count")) %
        """
            %op%: mkframe %args%
                  Create a stack frame, including the top N stack elements in the newly created frame, and
                  storing the current frame pointer immediately before the new frame pointer
        """.trimIndent()
    val mkframe_keep_r = +Insn("mkframe_keep_r", opcodes.create(), reg("count")) %
        """
            %op%: mkframe %args%
                  Create a stack frame, including the top N stack elements in the newly created frame, and
                  storing the current frame pointer immediately before the new frame pointer
        """.trimIndent()
    val rmframe = +Insn("rmframe", opcodes.create()) %
        """
            %op%: rmframe %args%
                  Remove a stack frame, restoring the old frame pointer 
        """.trimIndent()
    val rmframe_keep_imm = +Insn("rmframe_keep_imm", opcodes.create(), u32("count")) %
        """
            %op%: rmframe %args%
                  Remove a stack frame, leaving the bottom N frame elements at the top of the previous stack frame,
                  and restoring the old frame pointer 
        """.trimIndent()
    val rmframe_keep_r = +Insn("rmframe_keep_r", opcodes.create(), reg("count")) %
        """
            %op%: rmframe %args%
                  Remove a stack frame, leaving the bottom N frame elements at the top of the previous stack frame,
                  and restoring the old frame pointer 
        """.trimIndent()

    private val instructionsMap = instructions.associateBy { it.opcode }
    fun decode(opcode: UShort): Insn? {
        return instructionsMap[opcode and Insn.OPCODE_MASK]
    }

    private operator fun Insn.unaryPlus(): Insn {
        instructions.add(this)
        return this
    }

    private operator fun Insn.rem(help: String): Insn {
        this.help = help
            .replace("%op%", this.opcode.toString(16).padStart(4, '0'))
            .replace("%args%", this.argsHelp)
        return this
    }

    private class OpcodeTracker {
        var opcode: UShort = 0u
            private set

        fun create(): UShort {
            return opcode++
        }
    }
}

