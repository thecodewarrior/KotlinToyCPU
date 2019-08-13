package dev.thecodewarrior.kotlincpu.assembler.instructions

object InstructionRegistry {
    val factories: MutableList<InsnFactory> = mutableListOf()

    init {
        system()
        const()
        increment()
        decrement()
        add()
        subtract()
        multiply()
        divide()
        modulo()
        unconditionalJump()
    }

    private operator fun <T: InsnFactory> T.unaryPlus(): T {
        factories.add(this)
        return this
    }

    private fun system() {
        +SimpleInsnFactory("NOP", 0x0u)
        +SimpleInsnFactory("HALT", 0xFFFFu)
    }

    private fun const() {
        // const =======================================================================================================
        +SimpleInsnFactory("CONST.bool",  0x10u, Argument.Register("reg"), Argument.Boolean("value"))
        +SimpleInsnFactory("CONST.i8",  0x10u, Argument.Register("reg"), Argument.Byte("value"))
        +SimpleInsnFactory("CONST.u8", 0x11u, Argument.Register("reg"), Argument.UByte("value"))
        +SimpleInsnFactory("CONST.i16",  0x12u, Argument.Register("reg"), Argument.Short("value"))
        +SimpleInsnFactory("CONST.u16", 0x13u, Argument.Register("reg"), Argument.UShort("value"))
        +SimpleInsnFactory("CONST.i32",  0x14u, Argument.Register("reg"), Argument.Int("value"))
        +SimpleInsnFactory("CONST.u32", 0x15u, Argument.Register("reg"), Argument.UInt("value"))
        +SimpleInsnFactory("CONST.i64",  0x16u, Argument.Register("reg"), Argument.Long("value"))
        +SimpleInsnFactory("CONST.u64", 0x17u, Argument.Register("reg"), Argument.ULong("value"))
        +SimpleInsnFactory("CONST.f32",  0x18u, Argument.Register("reg"), Argument.Float("value"))
        +SimpleInsnFactory("CONST.f64",  0x19u, Argument.Register("reg"), Argument.Double("value"))
    }

    private fun increment() {
        // increment
        +SimpleInsnFactory("INC.i8",  0x20u, 0x000u, Argument.Register("reg"))
        +SimpleInsnFactory("INC.u8", 0x20u, 0x001u, Argument.Register("reg"))
        +SimpleInsnFactory("INC.i16",  0x20u, 0x002u, Argument.Register("reg"))
        +SimpleInsnFactory("INC.u16", 0x20u, 0x003u, Argument.Register("reg"))
        +SimpleInsnFactory("INC.i32",  0x20u, 0x004u, Argument.Register("reg"))
        +SimpleInsnFactory("INC.u32", 0x20u, 0x005u, Argument.Register("reg"))
        +SimpleInsnFactory("INC.i64",  0x20u, 0x006u, Argument.Register("reg"))
        +SimpleInsnFactory("INC.u64", 0x20u, 0x007u, Argument.Register("reg"))
    }

    private fun decrement() {
        // decrement
        +SimpleInsnFactory("DEC.i8",  0x20u, 0x010u, Argument.Register("reg"))
        +SimpleInsnFactory("DEC.u8", 0x20u, 0x011u, Argument.Register("reg"))
        +SimpleInsnFactory("DEC.i16",  0x20u, 0x012u, Argument.Register("reg"))
        +SimpleInsnFactory("DEC.u16", 0x20u, 0x013u, Argument.Register("reg"))
        +SimpleInsnFactory("DEC.i32",  0x20u, 0x014u, Argument.Register("reg"))
        +SimpleInsnFactory("DEC.u32", 0x20u, 0x015u, Argument.Register("reg"))
        +SimpleInsnFactory("DEC.i64",  0x20u, 0x016u, Argument.Register("reg"))
        +SimpleInsnFactory("DEC.u64", 0x20u, 0x017u, Argument.Register("reg"))
    }

    private fun add() {
        // add registers
        +SimpleInsnFactory("ADD.i8",  0x20u, 0x020u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("ADD.u8", 0x20u, 0x021u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("ADD.i16",  0x20u, 0x022u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("ADD.u16", 0x20u, 0x023u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("ADD.i32",  0x20u, 0x024u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("ADD.u32", 0x20u, 0x025u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("ADD.i64",  0x20u, 0x026u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("ADD.u64", 0x20u, 0x027u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("ADD.f32",  0x20u, 0x028u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("ADD.f64",  0x20u, 0x029u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))

        // add register and constant
        +SimpleInsnFactory("ADDC.i8",  0x20u, 0x030u, Argument.Register("left"), Argument.Byte("right"), Argument.Register("out"))
        +SimpleInsnFactory("ADDC.u8", 0x20u, 0x031u, Argument.Register("left"), Argument.UByte("right"), Argument.Register("out"))
        +SimpleInsnFactory("ADDC.i16",  0x20u, 0x032u, Argument.Register("left"), Argument.Short("right"), Argument.Register("out"))
        +SimpleInsnFactory("ADDC.u16", 0x20u, 0x033u, Argument.Register("left"), Argument.UShort("right"), Argument.Register("out"))
        +SimpleInsnFactory("ADDC.i32",  0x20u, 0x034u, Argument.Register("left"), Argument.Int("right"), Argument.Register("out"))
        +SimpleInsnFactory("ADDC.u32", 0x20u, 0x035u, Argument.Register("left"), Argument.UInt("right"), Argument.Register("out"))
        +SimpleInsnFactory("ADDC.i64",  0x20u, 0x036u, Argument.Register("left"), Argument.Long("right"), Argument.Register("out"))
        +SimpleInsnFactory("ADDC.u64", 0x20u, 0x037u, Argument.Register("left"), Argument.ULong("right"), Argument.Register("out"))
        +SimpleInsnFactory("ADDC.f32",  0x20u, 0x038u, Argument.Register("left"), Argument.Float("right"), Argument.Register("out"))
        +SimpleInsnFactory("ADDC.f64",  0x20u, 0x039u, Argument.Register("left"), Argument.Double("right"), Argument.Register("out"))
    }

    private fun subtract() {
        // subtract registers
        +SimpleInsnFactory("SUB.i8",  0x20u, 0x040u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("SUB.u8", 0x20u, 0x041u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("SUB.i16",  0x20u, 0x042u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("SUB.u16", 0x20u, 0x043u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("SUB.i32",  0x20u, 0x044u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("SUB.u32", 0x20u, 0x045u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("SUB.i64",  0x20u, 0x046u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("SUB.u64", 0x20u, 0x047u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("SUB.f32",  0x20u, 0x048u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("SUB.f64",  0x20u, 0x049u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))

        // subtract register and constant
        +SimpleInsnFactory("SUBC.i8",  0x20u, 0x050u, Argument.Register("left"), Argument.Byte("right"), Argument.Register("out"))
        +SimpleInsnFactory("SUBC.u8", 0x20u, 0x051u, Argument.Register("left"), Argument.UByte("right"), Argument.Register("out"))
        +SimpleInsnFactory("SUBC.i16",  0x20u, 0x052u, Argument.Register("left"), Argument.Short("right"), Argument.Register("out"))
        +SimpleInsnFactory("SUBC.u16", 0x20u, 0x053u, Argument.Register("left"), Argument.UShort("right"), Argument.Register("out"))
        +SimpleInsnFactory("SUBC.i32",  0x20u, 0x054u, Argument.Register("left"), Argument.Int("right"), Argument.Register("out"))
        +SimpleInsnFactory("SUBC.u32", 0x20u, 0x055u, Argument.Register("left"), Argument.UInt("right"), Argument.Register("out"))
        +SimpleInsnFactory("SUBC.i64",  0x20u, 0x056u, Argument.Register("left"), Argument.Long("right"), Argument.Register("out"))
        +SimpleInsnFactory("SUBC.u64", 0x20u, 0x057u, Argument.Register("left"), Argument.ULong("right"), Argument.Register("out"))
        +SimpleInsnFactory("SUBC.f32",  0x20u, 0x058u, Argument.Register("left"), Argument.Float("right"), Argument.Register("out"))
        +SimpleInsnFactory("SUBC.f64",  0x20u, 0x059u, Argument.Register("left"), Argument.Double("right"), Argument.Register("out"))

        // subtract constant and register
        +SimpleInsnFactory("SUB_CR.i8",  0x20u, 0x060u, Argument.Byte("right"),   Argument.Register("left"), Argument.Register("out"))
        +SimpleInsnFactory("SUB_CR.u8", 0x20u, 0x061u, Argument.UByte("right"),  Argument.Register("left"), Argument.Register("out"))
        +SimpleInsnFactory("SUB_CR.i16",  0x20u, 0x062u, Argument.Short("right"),  Argument.Register("left"), Argument.Register("out"))
        +SimpleInsnFactory("SUB_CR.u16", 0x20u, 0x063u, Argument.UShort("right"), Argument.Register("left"), Argument.Register("out"))
        +SimpleInsnFactory("SUB_CR.i32",  0x20u, 0x064u, Argument.Int("right"),    Argument.Register("left"), Argument.Register("out"))
        +SimpleInsnFactory("SUB_CR.u32", 0x20u, 0x065u, Argument.UInt("right"),   Argument.Register("left"), Argument.Register("out"))
        +SimpleInsnFactory("SUB_CR.i64",  0x20u, 0x066u, Argument.Long("right"),   Argument.Register("left"), Argument.Register("out"))
        +SimpleInsnFactory("SUB_CR.u64", 0x20u, 0x067u, Argument.ULong("right"),  Argument.Register("left"), Argument.Register("out"))
        +SimpleInsnFactory("SUB_CR.f32",  0x20u, 0x068u, Argument.Float("right"),  Argument.Register("left"), Argument.Register("out"))
        +SimpleInsnFactory("SUB_CR.f64",  0x20u, 0x069u, Argument.Double("right"), Argument.Register("left"), Argument.Register("out"))
    }

    private fun multiply() {
        // multiply registers
        +SimpleInsnFactory("MUL.i8",  0x20u, 0x070u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("MUL.u8", 0x20u, 0x071u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("MUL.i16",  0x20u, 0x072u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("MUL.u16", 0x20u, 0x073u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("MUL.i32",  0x20u, 0x074u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("MUL.u32", 0x20u, 0x075u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("MUL.i64",  0x20u, 0x076u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("MUL.u64", 0x20u, 0x077u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("MUL.f32",  0x20u, 0x078u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("MUL.f64",  0x20u, 0x079u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))

        // multiply register and constant
        +SimpleInsnFactory("MULC.i8",  0x20u, 0x080u, Argument.Register("left"), Argument.Byte("right"), Argument.Register("out"))
        +SimpleInsnFactory("MULC.u8", 0x20u, 0x081u, Argument.Register("left"), Argument.UByte("right"), Argument.Register("out"))
        +SimpleInsnFactory("MULC.i16",  0x20u, 0x082u, Argument.Register("left"), Argument.Short("right"), Argument.Register("out"))
        +SimpleInsnFactory("MULC.u16", 0x20u, 0x083u, Argument.Register("left"), Argument.UShort("right"), Argument.Register("out"))
        +SimpleInsnFactory("MULC.i32",  0x20u, 0x084u, Argument.Register("left"), Argument.Int("right"), Argument.Register("out"))
        +SimpleInsnFactory("MULC.u32", 0x20u, 0x085u, Argument.Register("left"), Argument.UInt("right"), Argument.Register("out"))
        +SimpleInsnFactory("MULC.i64",  0x20u, 0x086u, Argument.Register("left"), Argument.Long("right"), Argument.Register("out"))
        +SimpleInsnFactory("MULC.u64", 0x20u, 0x087u, Argument.Register("left"), Argument.ULong("right"), Argument.Register("out"))
        +SimpleInsnFactory("MULC.f32",  0x20u, 0x088u, Argument.Register("left"), Argument.Float("right"), Argument.Register("out"))
        +SimpleInsnFactory("MULC.f64",  0x20u, 0x089u, Argument.Register("left"), Argument.Double("right"), Argument.Register("out"))
    }

    private fun divide() {
        // divide registers
        +SimpleInsnFactory("DIV.i8",  0x20u, 0x090u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("DIV.u8", 0x20u, 0x091u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("DIV.i16",  0x20u, 0x092u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("DIV.u16", 0x20u, 0x093u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("DIV.i32",  0x20u, 0x094u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("DIV.u32", 0x20u, 0x095u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("DIV.i64",  0x20u, 0x096u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("DIV.u64", 0x20u, 0x097u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("DIV.f32",  0x20u, 0x098u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("DIV.f64",  0x20u, 0x099u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))

        // divide register and constant
        +SimpleInsnFactory("DIVC.i8",  0x20u, 0x0a0u, Argument.Register("left"), Argument.Byte("right"), Argument.Register("out"))
        +SimpleInsnFactory("DIVC.u8", 0x20u, 0x0a1u, Argument.Register("left"), Argument.UByte("right"), Argument.Register("out"))
        +SimpleInsnFactory("DIVC.i16",  0x20u, 0x0a2u, Argument.Register("left"), Argument.Short("right"), Argument.Register("out"))
        +SimpleInsnFactory("DIVC.u16", 0x20u, 0x0a3u, Argument.Register("left"), Argument.UShort("right"), Argument.Register("out"))
        +SimpleInsnFactory("DIVC.i32",  0x20u, 0x0a4u, Argument.Register("left"), Argument.Int("right"), Argument.Register("out"))
        +SimpleInsnFactory("DIVC.u32", 0x20u, 0x0a5u, Argument.Register("left"), Argument.UInt("right"), Argument.Register("out"))
        +SimpleInsnFactory("DIVC.i64",  0x20u, 0x0a6u, Argument.Register("left"), Argument.Long("right"), Argument.Register("out"))
        +SimpleInsnFactory("DIVC.u64", 0x20u, 0x0a7u, Argument.Register("left"), Argument.ULong("right"), Argument.Register("out"))
        +SimpleInsnFactory("DIVC.f32",  0x20u, 0x0a8u, Argument.Register("left"), Argument.Float("right"), Argument.Register("out"))
        +SimpleInsnFactory("DIVC.f64",  0x20u, 0x0a9u, Argument.Register("left"), Argument.Double("right"), Argument.Register("out"))

        // divide constant and register
        +SimpleInsnFactory("DIV_CR.i8",  0x20u, 0x0b0u, Argument.Byte("right"),   Argument.Register("left"), Argument.Register("out"))
        +SimpleInsnFactory("DIV_CR.u8", 0x20u, 0x0b1u, Argument.UByte("right"),  Argument.Register("left"), Argument.Register("out"))
        +SimpleInsnFactory("DIV_CR.i16",  0x20u, 0x0b2u, Argument.Short("right"),  Argument.Register("left"), Argument.Register("out"))
        +SimpleInsnFactory("DIV_CR.u16", 0x20u, 0x0b3u, Argument.UShort("right"), Argument.Register("left"), Argument.Register("out"))
        +SimpleInsnFactory("DIV_CR.i32",  0x20u, 0x0b4u, Argument.Int("right"),    Argument.Register("left"), Argument.Register("out"))
        +SimpleInsnFactory("DIV_CR.u32", 0x20u, 0x0b5u, Argument.UInt("right"),   Argument.Register("left"), Argument.Register("out"))
        +SimpleInsnFactory("DIV_CR.i64",  0x20u, 0x0b6u, Argument.Long("right"),   Argument.Register("left"), Argument.Register("out"))
        +SimpleInsnFactory("DIV_CR.u64", 0x20u, 0x0b7u, Argument.ULong("right"),  Argument.Register("left"), Argument.Register("out"))
        +SimpleInsnFactory("DIV_CR.f32",  0x20u, 0x0b8u, Argument.Float("right"),  Argument.Register("left"), Argument.Register("out"))
        +SimpleInsnFactory("DIV_CR.f64",  0x20u, 0x0b9u, Argument.Double("right"), Argument.Register("left"), Argument.Register("out"))
    }

    private fun modulo() {
        // divide registers
        +SimpleInsnFactory("MOD.i8",  0x20u, 0x0c0u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("MOD.u8", 0x20u, 0x0c1u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("MOD.i16",  0x20u, 0x0c2u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("MOD.u16", 0x20u, 0x0c3u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("MOD.i32",  0x20u, 0x0c4u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("MOD.u32", 0x20u, 0x0c5u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("MOD.i64",  0x20u, 0x0c6u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("MOD.u64", 0x20u, 0x0c7u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("MOD.f32",  0x20u, 0x0c8u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("MOD.f64",  0x20u, 0x0c9u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))

        // divide register and constant
        +SimpleInsnFactory("MODC.i8",  0x20u, 0x0d0u, Argument.Register("left"), Argument.Byte("right"), Argument.Register("out"))
        +SimpleInsnFactory("MODC.u8", 0x20u, 0x0d1u, Argument.Register("left"), Argument.UByte("right"), Argument.Register("out"))
        +SimpleInsnFactory("MODC.i16",  0x20u, 0x0d2u, Argument.Register("left"), Argument.Short("right"), Argument.Register("out"))
        +SimpleInsnFactory("MODC.u16", 0x20u, 0x0d3u, Argument.Register("left"), Argument.UShort("right"), Argument.Register("out"))
        +SimpleInsnFactory("MODC.i32",  0x20u, 0x0d4u, Argument.Register("left"), Argument.Int("right"), Argument.Register("out"))
        +SimpleInsnFactory("MODC.u32", 0x20u, 0x0d5u, Argument.Register("left"), Argument.UInt("right"), Argument.Register("out"))
        +SimpleInsnFactory("MODC.i64",  0x20u, 0x0d6u, Argument.Register("left"), Argument.Long("right"), Argument.Register("out"))
        +SimpleInsnFactory("MODC.u64", 0x20u, 0x0d7u, Argument.Register("left"), Argument.ULong("right"), Argument.Register("out"))
        +SimpleInsnFactory("MODC.f32",  0x20u, 0x0d8u, Argument.Register("left"), Argument.Float("right"), Argument.Register("out"))
        +SimpleInsnFactory("MODC.f64",  0x20u, 0x0d9u, Argument.Register("left"), Argument.Double("right"), Argument.Register("out"))

        // divide constant and register
        +SimpleInsnFactory("MOD_CR.i8",  0x20u, 0x0e0u, Argument.Byte("right"),   Argument.Register("left"), Argument.Register("out"))
        +SimpleInsnFactory("MOD_CR.u8", 0x20u, 0x0e1u, Argument.UByte("right"),  Argument.Register("left"), Argument.Register("out"))
        +SimpleInsnFactory("MOD_CR.i16",  0x20u, 0x0e2u, Argument.Short("right"),  Argument.Register("left"), Argument.Register("out"))
        +SimpleInsnFactory("MOD_CR.u16", 0x20u, 0x0e3u, Argument.UShort("right"), Argument.Register("left"), Argument.Register("out"))
        +SimpleInsnFactory("MOD_CR.i32",  0x20u, 0x0e4u, Argument.Int("right"),    Argument.Register("left"), Argument.Register("out"))
        +SimpleInsnFactory("MOD_CR.u32", 0x20u, 0x0e5u, Argument.UInt("right"),   Argument.Register("left"), Argument.Register("out"))
        +SimpleInsnFactory("MOD_CR.i64",  0x20u, 0x0e6u, Argument.Long("right"),   Argument.Register("left"), Argument.Register("out"))
        +SimpleInsnFactory("MOD_CR.u64", 0x20u, 0x0e7u, Argument.ULong("right"),  Argument.Register("left"), Argument.Register("out"))
        +SimpleInsnFactory("MOD_CR.f32",  0x20u, 0x0e8u, Argument.Float("right"),  Argument.Register("left"), Argument.Register("out"))
        +SimpleInsnFactory("MOD_CR.f64",  0x20u, 0x0e9u, Argument.Double("right"), Argument.Register("left"), Argument.Register("out"))
    }

    private fun unconditionalJump() {
        +SimpleInsnFactory("JMP",  0x30u, Argument.Label("target"))
    }

    val factoryMap: Map<String, InsnFactory> = factories.associateBy { it.name }
}