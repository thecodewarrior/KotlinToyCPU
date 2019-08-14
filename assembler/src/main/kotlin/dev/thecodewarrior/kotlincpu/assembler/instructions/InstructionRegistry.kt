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
        compareEq()
        compareNe()
        compareLt()
        compareLe()
        compareGt()
        compareGe()
        jump()
        jumpEq()
        jumpNe()
        jumpLt()
        jumpLe()
        jumpGt()
        jumpGe()
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
        +SimpleInsnFactory("CONST.bool", 0x10u, Argument.Register("reg"), Argument.Boolean("value"))
        +SimpleInsnFactory("CONST.i8",  0x10u, Argument.Register("reg"), Argument.Byte("value"))
        +SimpleInsnFactory("CONST.u8",  0x11u, Argument.Register("reg"), Argument.UByte("value"))
        +SimpleInsnFactory("CONST.i16", 0x12u, Argument.Register("reg"), Argument.Short("value"))
        +SimpleInsnFactory("CONST.u16", 0x13u, Argument.Register("reg"), Argument.UShort("value"))
        +SimpleInsnFactory("CONST.i32", 0x14u, Argument.Register("reg"), Argument.Int("value"))
        +SimpleInsnFactory("CONST.u32", 0x15u, Argument.Register("reg"), Argument.UInt("value"))
        +SimpleInsnFactory("CONST.i64", 0x16u, Argument.Register("reg"), Argument.Long("value"))
        +SimpleInsnFactory("CONST.u64", 0x17u, Argument.Register("reg"), Argument.ULong("value"))
        +SimpleInsnFactory("CONST.f32", 0x18u, Argument.Register("reg"), Argument.Float("value"))
        +SimpleInsnFactory("CONST.f64", 0x19u, Argument.Register("reg"), Argument.Double("value"))
    }

    private fun increment() {
        // increment
        +SimpleInsnFactory("INC.i8",  0x20u, 0x000u, Argument.Register("reg"))
        +SimpleInsnFactory("INC.u8",  0x20u, 0x001u, Argument.Register("reg"))
        +SimpleInsnFactory("INC.i16", 0x20u, 0x002u, Argument.Register("reg"))
        +SimpleInsnFactory("INC.u16", 0x20u, 0x003u, Argument.Register("reg"))
        +SimpleInsnFactory("INC.i32", 0x20u, 0x004u, Argument.Register("reg"))
        +SimpleInsnFactory("INC.u32", 0x20u, 0x005u, Argument.Register("reg"))
        +SimpleInsnFactory("INC.i64", 0x20u, 0x006u, Argument.Register("reg"))
        +SimpleInsnFactory("INC.u64", 0x20u, 0x007u, Argument.Register("reg"))
    }

    private fun decrement() {
        // decrement
        +SimpleInsnFactory("DEC.i8",  0x20u, 0x010u, Argument.Register("reg"))
        +SimpleInsnFactory("DEC.u8",  0x20u, 0x011u, Argument.Register("reg"))
        +SimpleInsnFactory("DEC.i16", 0x20u, 0x012u, Argument.Register("reg"))
        +SimpleInsnFactory("DEC.u16", 0x20u, 0x013u, Argument.Register("reg"))
        +SimpleInsnFactory("DEC.i32", 0x20u, 0x014u, Argument.Register("reg"))
        +SimpleInsnFactory("DEC.u32", 0x20u, 0x015u, Argument.Register("reg"))
        +SimpleInsnFactory("DEC.i64", 0x20u, 0x016u, Argument.Register("reg"))
        +SimpleInsnFactory("DEC.u64", 0x20u, 0x017u, Argument.Register("reg"))
    }

    private fun add() {
        // add registers
        +SimpleInsnFactory("ADD.i8",  0x20u, 0x020u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("ADD.u8",  0x20u, 0x021u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("ADD.i16", 0x20u, 0x022u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("ADD.u16", 0x20u, 0x023u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("ADD.i32", 0x20u, 0x024u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("ADD.u32", 0x20u, 0x025u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("ADD.i64", 0x20u, 0x026u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("ADD.u64", 0x20u, 0x027u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("ADD.f32", 0x20u, 0x028u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("ADD.f64", 0x20u, 0x029u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))

        // add register and constant
        +SimpleInsnFactory("ADD_RC.i8",  0x20u, 0x030u, Argument.Register("left"), Argument.Byte("right"), Argument.Register("out"))
        +SimpleInsnFactory("ADD_RC.u8",  0x20u, 0x031u, Argument.Register("left"), Argument.UByte("right"), Argument.Register("out"))
        +SimpleInsnFactory("ADD_RC.i16", 0x20u, 0x032u, Argument.Register("left"), Argument.Short("right"), Argument.Register("out"))
        +SimpleInsnFactory("ADD_RC.u16", 0x20u, 0x033u, Argument.Register("left"), Argument.UShort("right"), Argument.Register("out"))
        +SimpleInsnFactory("ADD_RC.i32", 0x20u, 0x034u, Argument.Register("left"), Argument.Int("right"), Argument.Register("out"))
        +SimpleInsnFactory("ADD_RC.u32", 0x20u, 0x035u, Argument.Register("left"), Argument.UInt("right"), Argument.Register("out"))
        +SimpleInsnFactory("ADD_RC.i64", 0x20u, 0x036u, Argument.Register("left"), Argument.Long("right"), Argument.Register("out"))
        +SimpleInsnFactory("ADD_RC.u64", 0x20u, 0x037u, Argument.Register("left"), Argument.ULong("right"), Argument.Register("out"))
        +SimpleInsnFactory("ADD_RC.f32", 0x20u, 0x038u, Argument.Register("left"), Argument.Float("right"), Argument.Register("out"))
        +SimpleInsnFactory("ADD_RC.f64", 0x20u, 0x039u, Argument.Register("left"), Argument.Double("right"), Argument.Register("out"))
    }

    private fun subtract() {
        // subtract registers
        +SimpleInsnFactory("SUB.i8",  0x20u, 0x040u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("SUB.u8",  0x20u, 0x041u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("SUB.i16", 0x20u, 0x042u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("SUB.u16", 0x20u, 0x043u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("SUB.i32", 0x20u, 0x044u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("SUB.u32", 0x20u, 0x045u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("SUB.i64", 0x20u, 0x046u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("SUB.u64", 0x20u, 0x047u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("SUB.f32", 0x20u, 0x048u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("SUB.f64", 0x20u, 0x049u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))

        // subtract register and constant
        +SimpleInsnFactory("SUB_RC.i8",  0x20u, 0x050u, Argument.Register("left"), Argument.Byte("right"), Argument.Register("out"))
        +SimpleInsnFactory("SUB_RC.u8",  0x20u, 0x051u, Argument.Register("left"), Argument.UByte("right"), Argument.Register("out"))
        +SimpleInsnFactory("SUB_RC.i16", 0x20u, 0x052u, Argument.Register("left"), Argument.Short("right"), Argument.Register("out"))
        +SimpleInsnFactory("SUB_RC.u16", 0x20u, 0x053u, Argument.Register("left"), Argument.UShort("right"), Argument.Register("out"))
        +SimpleInsnFactory("SUB_RC.i32", 0x20u, 0x054u, Argument.Register("left"), Argument.Int("right"), Argument.Register("out"))
        +SimpleInsnFactory("SUB_RC.u32", 0x20u, 0x055u, Argument.Register("left"), Argument.UInt("right"), Argument.Register("out"))
        +SimpleInsnFactory("SUB_RC.i64", 0x20u, 0x056u, Argument.Register("left"), Argument.Long("right"), Argument.Register("out"))
        +SimpleInsnFactory("SUB_RC.u64", 0x20u, 0x057u, Argument.Register("left"), Argument.ULong("right"), Argument.Register("out"))
        +SimpleInsnFactory("SUB_RC.f32", 0x20u, 0x058u, Argument.Register("left"), Argument.Float("right"), Argument.Register("out"))
        +SimpleInsnFactory("SUB_RC.f64", 0x20u, 0x059u, Argument.Register("left"), Argument.Double("right"), Argument.Register("out"))

        // subtract constant and register
        +SimpleInsnFactory("SUB_CR.i8",  0x20u, 0x060u, Argument.Byte("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("SUB_CR.u8",  0x20u, 0x061u, Argument.UByte("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("SUB_CR.i16", 0x20u, 0x062u, Argument.Short("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("SUB_CR.u16", 0x20u, 0x063u, Argument.UShort("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("SUB_CR.i32", 0x20u, 0x064u, Argument.Int("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("SUB_CR.u32", 0x20u, 0x065u, Argument.UInt("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("SUB_CR.i64", 0x20u, 0x066u, Argument.Long("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("SUB_CR.u64", 0x20u, 0x067u, Argument.ULong("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("SUB_CR.f32", 0x20u, 0x068u, Argument.Float("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("SUB_CR.f64", 0x20u, 0x069u, Argument.Double("left"), Argument.Register("right"), Argument.Register("out"))
    }

    private fun multiply() {
        // multiply registers
        +SimpleInsnFactory("MUL.i8",  0x20u, 0x070u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("MUL.u8",  0x20u, 0x071u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("MUL.i16", 0x20u, 0x072u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("MUL.u16", 0x20u, 0x073u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("MUL.i32", 0x20u, 0x074u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("MUL.u32", 0x20u, 0x075u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("MUL.i64", 0x20u, 0x076u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("MUL.u64", 0x20u, 0x077u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("MUL.f32", 0x20u, 0x078u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("MUL.f64", 0x20u, 0x079u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))

        // multiply register and constant
        +SimpleInsnFactory("MUL_RC.i8",  0x20u, 0x080u, Argument.Register("left"), Argument.Byte("right"), Argument.Register("out"))
        +SimpleInsnFactory("MUL_RC.u8",  0x20u, 0x081u, Argument.Register("left"), Argument.UByte("right"), Argument.Register("out"))
        +SimpleInsnFactory("MUL_RC.i16", 0x20u, 0x082u, Argument.Register("left"), Argument.Short("right"), Argument.Register("out"))
        +SimpleInsnFactory("MUL_RC.u16", 0x20u, 0x083u, Argument.Register("left"), Argument.UShort("right"), Argument.Register("out"))
        +SimpleInsnFactory("MUL_RC.i32", 0x20u, 0x084u, Argument.Register("left"), Argument.Int("right"), Argument.Register("out"))
        +SimpleInsnFactory("MUL_RC.u32", 0x20u, 0x085u, Argument.Register("left"), Argument.UInt("right"), Argument.Register("out"))
        +SimpleInsnFactory("MUL_RC.i64", 0x20u, 0x086u, Argument.Register("left"), Argument.Long("right"), Argument.Register("out"))
        +SimpleInsnFactory("MUL_RC.u64", 0x20u, 0x087u, Argument.Register("left"), Argument.ULong("right"), Argument.Register("out"))
        +SimpleInsnFactory("MUL_RC.f32", 0x20u, 0x088u, Argument.Register("left"), Argument.Float("right"), Argument.Register("out"))
        +SimpleInsnFactory("MUL_RC.f64", 0x20u, 0x089u, Argument.Register("left"), Argument.Double("right"), Argument.Register("out"))
    }

    private fun divide() {
        // divide registers
        +SimpleInsnFactory("DIV.i8",  0x20u, 0x090u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("DIV.u8",  0x20u, 0x091u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("DIV.i16", 0x20u, 0x092u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("DIV.u16", 0x20u, 0x093u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("DIV.i32", 0x20u, 0x094u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("DIV.u32", 0x20u, 0x095u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("DIV.i64", 0x20u, 0x096u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("DIV.u64", 0x20u, 0x097u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("DIV.f32", 0x20u, 0x098u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("DIV.f64", 0x20u, 0x099u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))

        // divide register and constant
        +SimpleInsnFactory("DIV_RC.i8",  0x20u, 0x0a0u, Argument.Register("left"), Argument.Byte("right"), Argument.Register("out"))
        +SimpleInsnFactory("DIV_RC.u8",  0x20u, 0x0a1u, Argument.Register("left"), Argument.UByte("right"), Argument.Register("out"))
        +SimpleInsnFactory("DIV_RC.i16", 0x20u, 0x0a2u, Argument.Register("left"), Argument.Short("right"), Argument.Register("out"))
        +SimpleInsnFactory("DIV_RC.u16", 0x20u, 0x0a3u, Argument.Register("left"), Argument.UShort("right"), Argument.Register("out"))
        +SimpleInsnFactory("DIV_RC.i32", 0x20u, 0x0a4u, Argument.Register("left"), Argument.Int("right"), Argument.Register("out"))
        +SimpleInsnFactory("DIV_RC.u32", 0x20u, 0x0a5u, Argument.Register("left"), Argument.UInt("right"), Argument.Register("out"))
        +SimpleInsnFactory("DIV_RC.i64", 0x20u, 0x0a6u, Argument.Register("left"), Argument.Long("right"), Argument.Register("out"))
        +SimpleInsnFactory("DIV_RC.u64", 0x20u, 0x0a7u, Argument.Register("left"), Argument.ULong("right"), Argument.Register("out"))
        +SimpleInsnFactory("DIV_RC.f32", 0x20u, 0x0a8u, Argument.Register("left"), Argument.Float("right"), Argument.Register("out"))
        +SimpleInsnFactory("DIV_RC.f64", 0x20u, 0x0a9u, Argument.Register("left"), Argument.Double("right"), Argument.Register("out"))

        // divide constant and register
        +SimpleInsnFactory("DIV_CR.i8",  0x20u, 0x0b0u, Argument.Byte("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("DIV_CR.u8",  0x20u, 0x0b1u, Argument.UByte("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("DIV_CR.i16", 0x20u, 0x0b2u, Argument.Short("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("DIV_CR.u16", 0x20u, 0x0b3u, Argument.UShort("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("DIV_CR.i32", 0x20u, 0x0b4u, Argument.Int("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("DIV_CR.u32", 0x20u, 0x0b5u, Argument.UInt("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("DIV_CR.i64", 0x20u, 0x0b6u, Argument.Long("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("DIV_CR.u64", 0x20u, 0x0b7u, Argument.ULong("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("DIV_CR.f32", 0x20u, 0x0b8u, Argument.Float("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("DIV_CR.f64", 0x20u, 0x0b9u, Argument.Double("left"), Argument.Register("right"), Argument.Register("out"))
    }

    private fun modulo() {
        // divide registers
        +SimpleInsnFactory("MOD.i8",  0x20u, 0x0c0u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("MOD.u8",  0x20u, 0x0c1u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("MOD.i16", 0x20u, 0x0c2u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("MOD.u16", 0x20u, 0x0c3u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("MOD.i32", 0x20u, 0x0c4u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("MOD.u32", 0x20u, 0x0c5u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("MOD.i64", 0x20u, 0x0c6u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("MOD.u64", 0x20u, 0x0c7u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("MOD.f32", 0x20u, 0x0c8u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("MOD.f64", 0x20u, 0x0c9u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))

        // divide register and constant
        +SimpleInsnFactory("MOD_RC.i8",  0x20u, 0x0d0u, Argument.Register("left"), Argument.Byte("right"), Argument.Register("out"))
        +SimpleInsnFactory("MOD_RC.u8",  0x20u, 0x0d1u, Argument.Register("left"), Argument.UByte("right"), Argument.Register("out"))
        +SimpleInsnFactory("MOD_RC.i16", 0x20u, 0x0d2u, Argument.Register("left"), Argument.Short("right"), Argument.Register("out"))
        +SimpleInsnFactory("MOD_RC.u16", 0x20u, 0x0d3u, Argument.Register("left"), Argument.UShort("right"), Argument.Register("out"))
        +SimpleInsnFactory("MOD_RC.i32", 0x20u, 0x0d4u, Argument.Register("left"), Argument.Int("right"), Argument.Register("out"))
        +SimpleInsnFactory("MOD_RC.u32", 0x20u, 0x0d5u, Argument.Register("left"), Argument.UInt("right"), Argument.Register("out"))
        +SimpleInsnFactory("MOD_RC.i64", 0x20u, 0x0d6u, Argument.Register("left"), Argument.Long("right"), Argument.Register("out"))
        +SimpleInsnFactory("MOD_RC.u64", 0x20u, 0x0d7u, Argument.Register("left"), Argument.ULong("right"), Argument.Register("out"))
        +SimpleInsnFactory("MOD_RC.f32", 0x20u, 0x0d8u, Argument.Register("left"), Argument.Float("right"), Argument.Register("out"))
        +SimpleInsnFactory("MOD_RC.f64", 0x20u, 0x0d9u, Argument.Register("left"), Argument.Double("right"), Argument.Register("out"))

        // divide constant and register
        +SimpleInsnFactory("MOD_CR.i8",  0x20u, 0x0e0u, Argument.Byte("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("MOD_CR.u8",  0x20u, 0x0e1u, Argument.UByte("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("MOD_CR.i16", 0x20u, 0x0e2u, Argument.Short("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("MOD_CR.u16", 0x20u, 0x0e3u, Argument.UShort("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("MOD_CR.i32", 0x20u, 0x0e4u, Argument.Int("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("MOD_CR.u32", 0x20u, 0x0e5u, Argument.UInt("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("MOD_CR.i64", 0x20u, 0x0e6u, Argument.Long("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("MOD_CR.u64", 0x20u, 0x0e7u, Argument.ULong("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("MOD_CR.f32", 0x20u, 0x0e8u, Argument.Float("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("MOD_CR.f64", 0x20u, 0x0e9u, Argument.Double("left"), Argument.Register("right"), Argument.Register("out"))
    }

    private fun compareEq() {
        // registers are equal
        +SimpleInsnFactory("CMP_EQ.i8",  0x30u, 0x000u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_EQ.u8",  0x30u, 0x001u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_EQ.i16", 0x30u, 0x002u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_EQ.u16", 0x30u, 0x003u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_EQ.i32", 0x30u, 0x004u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_EQ.u32", 0x30u, 0x005u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_EQ.i64", 0x30u, 0x006u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_EQ.u64", 0x30u, 0x007u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_EQ.f32", 0x30u, 0x008u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_EQ.f64", 0x30u, 0x009u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))

        // register equals constant
        +SimpleInsnFactory("CMP_EQ_RC.i8",  0x30u, 0x010u, Argument.Register("left"), Argument.Byte("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_EQ_RC.u8",  0x30u, 0x011u, Argument.Register("left"), Argument.UByte("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_EQ_RC.i16", 0x30u, 0x012u, Argument.Register("left"), Argument.Short("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_EQ_RC.u16", 0x30u, 0x013u, Argument.Register("left"), Argument.UShort("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_EQ_RC.i32", 0x30u, 0x014u, Argument.Register("left"), Argument.Int("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_EQ_RC.u32", 0x30u, 0x015u, Argument.Register("left"), Argument.UInt("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_EQ_RC.i64", 0x30u, 0x016u, Argument.Register("left"), Argument.Long("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_EQ_RC.u64", 0x30u, 0x017u, Argument.Register("left"), Argument.ULong("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_EQ_RC.f32", 0x30u, 0x018u, Argument.Register("left"), Argument.Float("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_EQ_RC.f64", 0x30u, 0x019u, Argument.Register("left"), Argument.Double("right"), Argument.Register("out"))
    }

    private fun compareNe() {
        // registers are equal
        +SimpleInsnFactory("CMP_NE.i8",  0x30u, 0x020u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_NE.u8",  0x30u, 0x021u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_NE.i16", 0x30u, 0x022u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_NE.u16", 0x30u, 0x023u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_NE.i32", 0x30u, 0x024u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_NE.u32", 0x30u, 0x025u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_NE.i64", 0x30u, 0x026u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_NE.u64", 0x30u, 0x027u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_NE.f32", 0x30u, 0x028u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_NE.f64", 0x30u, 0x029u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))

        // register equals constant
        +SimpleInsnFactory("CMP_NE_RC.i8",  0x30u, 0x030u, Argument.Register("left"), Argument.Byte("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_NE_RC.u8",  0x30u, 0x031u, Argument.Register("left"), Argument.UByte("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_NE_RC.i16", 0x30u, 0x032u, Argument.Register("left"), Argument.Short("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_NE_RC.u16", 0x30u, 0x033u, Argument.Register("left"), Argument.UShort("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_NE_RC.i32", 0x30u, 0x034u, Argument.Register("left"), Argument.Int("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_NE_RC.u32", 0x30u, 0x035u, Argument.Register("left"), Argument.UInt("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_NE_RC.i64", 0x30u, 0x036u, Argument.Register("left"), Argument.Long("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_NE_RC.u64", 0x30u, 0x037u, Argument.Register("left"), Argument.ULong("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_NE_RC.f32", 0x30u, 0x038u, Argument.Register("left"), Argument.Float("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_NE_RC.f64", 0x30u, 0x039u, Argument.Register("left"), Argument.Double("right"), Argument.Register("out"))
    }

    private fun compareLt() {
        // divide registers
        +SimpleInsnFactory("CMP_LT.i8",  0x30u, 0x040u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_LT.u8",  0x30u, 0x041u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_LT.i16", 0x30u, 0x042u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_LT.u16", 0x30u, 0x043u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_LT.i32", 0x30u, 0x044u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_LT.u32", 0x30u, 0x045u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_LT.i64", 0x30u, 0x046u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_LT.u64", 0x30u, 0x047u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_LT.f32", 0x30u, 0x048u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_LT.f64", 0x30u, 0x049u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))

        // divide register and constant
        +SimpleInsnFactory("CMP_LT_RC.i8",  0x30u, 0x050u, Argument.Register("left"), Argument.Byte("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_LT_RC.u8",  0x30u, 0x051u, Argument.Register("left"), Argument.UByte("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_LT_RC.i16", 0x30u, 0x052u, Argument.Register("left"), Argument.Short("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_LT_RC.u16", 0x30u, 0x053u, Argument.Register("left"), Argument.UShort("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_LT_RC.i32", 0x30u, 0x054u, Argument.Register("left"), Argument.Int("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_LT_RC.u32", 0x30u, 0x055u, Argument.Register("left"), Argument.UInt("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_LT_RC.i64", 0x30u, 0x056u, Argument.Register("left"), Argument.Long("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_LT_RC.u64", 0x30u, 0x057u, Argument.Register("left"), Argument.ULong("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_LT_RC.f32", 0x30u, 0x058u, Argument.Register("left"), Argument.Float("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_LT_RC.f64", 0x30u, 0x059u, Argument.Register("left"), Argument.Double("right"), Argument.Register("out"))

        // divide constant and register
        +SimpleInsnFactory("CMP_LT_CR.i8",  0x30u, 0x060u, Argument.Byte("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_LT_CR.u8",  0x30u, 0x061u, Argument.UByte("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_LT_CR.i16", 0x30u, 0x062u, Argument.Short("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_LT_CR.u16", 0x30u, 0x063u, Argument.UShort("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_LT_CR.i32", 0x30u, 0x064u, Argument.Int("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_LT_CR.u32", 0x30u, 0x065u, Argument.UInt("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_LT_CR.i64", 0x30u, 0x066u, Argument.Long("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_LT_CR.u64", 0x30u, 0x067u, Argument.ULong("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_LT_CR.f32", 0x30u, 0x068u, Argument.Float("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_LT_CR.f64", 0x30u, 0x069u, Argument.Double("left"), Argument.Register("right"), Argument.Register("out"))
    }

    private fun compareLe() {
        // divide registers
        +SimpleInsnFactory("CMP_LE.i8",  0x30u, 0x070u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_LE.u8",  0x30u, 0x071u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_LE.i16", 0x30u, 0x072u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_LE.u16", 0x30u, 0x073u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_LE.i32", 0x30u, 0x074u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_LE.u32", 0x30u, 0x075u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_LE.i64", 0x30u, 0x076u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_LE.u64", 0x30u, 0x077u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_LE.f32", 0x30u, 0x078u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_LE.f64", 0x30u, 0x079u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))

        // divide register and constant
        +SimpleInsnFactory("CMP_LEC.i8",  0x30u, 0x080u, Argument.Register("left"), Argument.Byte("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_LEC.u8",  0x30u, 0x081u, Argument.Register("left"), Argument.UByte("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_LEC.i16", 0x30u, 0x082u, Argument.Register("left"), Argument.Short("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_LEC.u16", 0x30u, 0x083u, Argument.Register("left"), Argument.UShort("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_LEC.i32", 0x30u, 0x084u, Argument.Register("left"), Argument.Int("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_LEC.u32", 0x30u, 0x085u, Argument.Register("left"), Argument.UInt("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_LEC.i64", 0x30u, 0x086u, Argument.Register("left"), Argument.Long("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_LEC.u64", 0x30u, 0x087u, Argument.Register("left"), Argument.ULong("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_LEC.f32", 0x30u, 0x088u, Argument.Register("left"), Argument.Float("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_LEC.f64", 0x30u, 0x089u, Argument.Register("left"), Argument.Double("right"), Argument.Register("out"))

        // divide constant and register
        +SimpleInsnFactory("CMP_LE_CR.i8",  0x30u, 0x090u, Argument.Byte("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_LE_CR.u8",  0x30u, 0x091u, Argument.UByte("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_LE_CR.i16", 0x30u, 0x092u, Argument.Short("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_LE_CR.u16", 0x30u, 0x093u, Argument.UShort("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_LE_CR.i32", 0x30u, 0x094u, Argument.Int("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_LE_CR.u32", 0x30u, 0x095u, Argument.UInt("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_LE_CR.i64", 0x30u, 0x096u, Argument.Long("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_LE_CR.u64", 0x30u, 0x097u, Argument.ULong("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_LE_CR.f32", 0x30u, 0x098u, Argument.Float("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_LE_CR.f64", 0x30u, 0x099u, Argument.Double("left"), Argument.Register("right"), Argument.Register("out"))
    }

    private fun compareGt() {
        // divide registers
        +SimpleInsnFactory("CMP_GT.i8",  0x30u, 0x0a0u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_GT.u8",  0x30u, 0x0a1u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_GT.i16", 0x30u, 0x0a2u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_GT.u16", 0x30u, 0x0a3u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_GT.i32", 0x30u, 0x0a4u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_GT.u32", 0x30u, 0x0a5u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_GT.i64", 0x30u, 0x0a6u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_GT.u64", 0x30u, 0x0a7u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_GT.f32", 0x30u, 0x0a8u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_GT.f64", 0x30u, 0x0a9u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))

        // divide register and constant
        +SimpleInsnFactory("CMP_GT_RC.i8",  0x30u, 0x0b0u, Argument.Register("left"), Argument.Byte("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_GT_RC.u8",  0x30u, 0x0b1u, Argument.Register("left"), Argument.UByte("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_GT_RC.i16", 0x30u, 0x0b2u, Argument.Register("left"), Argument.Short("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_GT_RC.u16", 0x30u, 0x0b3u, Argument.Register("left"), Argument.UShort("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_GT_RC.i32", 0x30u, 0x0b4u, Argument.Register("left"), Argument.Int("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_GT_RC.u32", 0x30u, 0x0b5u, Argument.Register("left"), Argument.UInt("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_GT_RC.i64", 0x30u, 0x0b6u, Argument.Register("left"), Argument.Long("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_GT_RC.u64", 0x30u, 0x0b7u, Argument.Register("left"), Argument.ULong("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_GT_RC.f32", 0x30u, 0x0b8u, Argument.Register("left"), Argument.Float("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_GT_RC.f64", 0x30u, 0x0b9u, Argument.Register("left"), Argument.Double("right"), Argument.Register("out"))

        // divide constant and register
        +SimpleInsnFactory("CMP_GT_CR.i8",  0x30u, 0x0c0u, Argument.Byte("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_GT_CR.u8",  0x30u, 0x0c1u, Argument.UByte("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_GT_CR.i16", 0x30u, 0x0c2u, Argument.Short("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_GT_CR.u16", 0x30u, 0x0c3u, Argument.UShort("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_GT_CR.i32", 0x30u, 0x0c4u, Argument.Int("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_GT_CR.u32", 0x30u, 0x0c5u, Argument.UInt("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_GT_CR.i64", 0x30u, 0x0c6u, Argument.Long("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_GT_CR.u64", 0x30u, 0x0c7u, Argument.ULong("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_GT_CR.f32", 0x30u, 0x0c8u, Argument.Float("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_GT_CR.f64", 0x30u, 0x0c9u, Argument.Double("left"), Argument.Register("right"), Argument.Register("out"))
    }

    private fun compareGe() {
        // divide registers
        +SimpleInsnFactory("CMP_GE.i8",  0x30u, 0x0d0u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_GE.u8",  0x30u, 0x0d1u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_GE.i16", 0x30u, 0x0d2u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_GE.u16", 0x30u, 0x0d3u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_GE.i32", 0x30u, 0x0d4u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_GE.u32", 0x30u, 0x0d5u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_GE.i64", 0x30u, 0x0d6u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_GE.u64", 0x30u, 0x0d7u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_GE.f32", 0x30u, 0x0d8u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_GE.f64", 0x30u, 0x0d9u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out"))

        // divide register and constant
        +SimpleInsnFactory("CMP_GEC.i8",  0x30u, 0x0e0u, Argument.Register("left"), Argument.Byte("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_GEC.u8",  0x30u, 0x0e1u, Argument.Register("left"), Argument.UByte("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_GEC.i16", 0x30u, 0x0e2u, Argument.Register("left"), Argument.Short("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_GEC.u16", 0x30u, 0x0e3u, Argument.Register("left"), Argument.UShort("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_GEC.i32", 0x30u, 0x0e4u, Argument.Register("left"), Argument.Int("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_GEC.u32", 0x30u, 0x0e5u, Argument.Register("left"), Argument.UInt("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_GEC.i64", 0x30u, 0x0e6u, Argument.Register("left"), Argument.Long("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_GEC.u64", 0x30u, 0x0e7u, Argument.Register("left"), Argument.ULong("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_GEC.f32", 0x30u, 0x0e8u, Argument.Register("left"), Argument.Float("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_GEC.f64", 0x30u, 0x0e9u, Argument.Register("left"), Argument.Double("right"), Argument.Register("out"))

        // divide constant and register
        +SimpleInsnFactory("CMP_GE_CR.i8",  0x30u, 0x0f0u, Argument.Byte("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_GE_CR.u8",  0x30u, 0x0f1u, Argument.UByte("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_GE_CR.i16", 0x30u, 0x0f2u, Argument.Short("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_GE_CR.u16", 0x30u, 0x0f3u, Argument.UShort("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_GE_CR.i32", 0x30u, 0x0f4u, Argument.Int("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_GE_CR.u32", 0x30u, 0x0f5u, Argument.UInt("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_GE_CR.i64", 0x30u, 0x0f6u, Argument.Long("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_GE_CR.u64", 0x30u, 0x0f7u, Argument.ULong("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_GE_CR.f32", 0x30u, 0x0f8u, Argument.Float("left"), Argument.Register("right"), Argument.Register("out"))
        +SimpleInsnFactory("CMP_GE_CR.f64", 0x30u, 0x0f9u, Argument.Double("left"), Argument.Register("right"), Argument.Register("out"))
    }

    private fun jump() {
        +SimpleInsnFactory("JMP",  0x40u, 0x100u, Argument.Label("destination"))
    }

    private fun jumpEq() {
        // registers are equal
        +SimpleInsnFactory("JMP_EQ.i8",  0x40u, 0x000u, Argument.Register("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_EQ.u8",  0x40u, 0x001u, Argument.Register("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_EQ.i16", 0x40u, 0x002u, Argument.Register("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_EQ.u16", 0x40u, 0x003u, Argument.Register("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_EQ.i32", 0x40u, 0x004u, Argument.Register("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_EQ.u32", 0x40u, 0x005u, Argument.Register("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_EQ.i64", 0x40u, 0x006u, Argument.Register("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_EQ.u64", 0x40u, 0x007u, Argument.Register("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_EQ.f32", 0x40u, 0x008u, Argument.Register("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_EQ.f64", 0x40u, 0x009u, Argument.Register("left"), Argument.Register("right"), Argument.Label("destination"))

        // register equals constant
        +SimpleInsnFactory("JMP_EQ_RC.i8",  0x40u, 0x010u, Argument.Register("left"), Argument.Byte("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_EQ_RC.u8",  0x40u, 0x011u, Argument.Register("left"), Argument.UByte("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_EQ_RC.i16", 0x40u, 0x012u, Argument.Register("left"), Argument.Short("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_EQ_RC.u16", 0x40u, 0x013u, Argument.Register("left"), Argument.UShort("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_EQ_RC.i32", 0x40u, 0x014u, Argument.Register("left"), Argument.Int("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_EQ_RC.u32", 0x40u, 0x015u, Argument.Register("left"), Argument.UInt("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_EQ_RC.i64", 0x40u, 0x016u, Argument.Register("left"), Argument.Long("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_EQ_RC.u64", 0x40u, 0x017u, Argument.Register("left"), Argument.ULong("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_EQ_RC.f32", 0x40u, 0x018u, Argument.Register("left"), Argument.Float("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_EQ_RC.f64", 0x40u, 0x019u, Argument.Register("left"), Argument.Double("right"), Argument.Label("destination"))
    }

    private fun jumpNe() {
        // registers are equal
        +SimpleInsnFactory("JMP_NE.i8",  0x40u, 0x020u, Argument.Register("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_NE.u8",  0x40u, 0x021u, Argument.Register("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_NE.i16", 0x40u, 0x022u, Argument.Register("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_NE.u16", 0x40u, 0x023u, Argument.Register("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_NE.i32", 0x40u, 0x024u, Argument.Register("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_NE.u32", 0x40u, 0x025u, Argument.Register("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_NE.i64", 0x40u, 0x026u, Argument.Register("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_NE.u64", 0x40u, 0x027u, Argument.Register("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_NE.f32", 0x40u, 0x028u, Argument.Register("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_NE.f64", 0x40u, 0x029u, Argument.Register("left"), Argument.Register("right"), Argument.Label("destination"))

        // register equals constant
        +SimpleInsnFactory("JMP_NE_RC.i8",  0x40u, 0x030u, Argument.Register("left"), Argument.Byte("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_NE_RC.u8",  0x40u, 0x031u, Argument.Register("left"), Argument.UByte("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_NE_RC.i16", 0x40u, 0x032u, Argument.Register("left"), Argument.Short("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_NE_RC.u16", 0x40u, 0x033u, Argument.Register("left"), Argument.UShort("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_NE_RC.i32", 0x40u, 0x034u, Argument.Register("left"), Argument.Int("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_NE_RC.u32", 0x40u, 0x035u, Argument.Register("left"), Argument.UInt("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_NE_RC.i64", 0x40u, 0x036u, Argument.Register("left"), Argument.Long("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_NE_RC.u64", 0x40u, 0x037u, Argument.Register("left"), Argument.ULong("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_NE_RC.f32", 0x40u, 0x038u, Argument.Register("left"), Argument.Float("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_NE_RC.f64", 0x40u, 0x039u, Argument.Register("left"), Argument.Double("right"), Argument.Label("destination"))
    }

    private fun jumpLt() {
        // divide registers
        +SimpleInsnFactory("JMP_LT.i8",  0x40u, 0x040u, Argument.Register("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_LT.u8",  0x40u, 0x041u, Argument.Register("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_LT.i16", 0x40u, 0x042u, Argument.Register("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_LT.u16", 0x40u, 0x043u, Argument.Register("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_LT.i32", 0x40u, 0x044u, Argument.Register("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_LT.u32", 0x40u, 0x045u, Argument.Register("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_LT.i64", 0x40u, 0x046u, Argument.Register("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_LT.u64", 0x40u, 0x047u, Argument.Register("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_LT.f32", 0x40u, 0x048u, Argument.Register("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_LT.f64", 0x40u, 0x049u, Argument.Register("left"), Argument.Register("right"), Argument.Label("destination"))

        // divide register and constant
        +SimpleInsnFactory("JMP_LT_RC.i8",  0x40u, 0x050u, Argument.Register("left"), Argument.Byte("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_LT_RC.u8",  0x40u, 0x051u, Argument.Register("left"), Argument.UByte("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_LT_RC.i16", 0x40u, 0x052u, Argument.Register("left"), Argument.Short("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_LT_RC.u16", 0x40u, 0x053u, Argument.Register("left"), Argument.UShort("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_LT_RC.i32", 0x40u, 0x054u, Argument.Register("left"), Argument.Int("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_LT_RC.u32", 0x40u, 0x055u, Argument.Register("left"), Argument.UInt("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_LT_RC.i64", 0x40u, 0x056u, Argument.Register("left"), Argument.Long("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_LT_RC.u64", 0x40u, 0x057u, Argument.Register("left"), Argument.ULong("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_LT_RC.f32", 0x40u, 0x058u, Argument.Register("left"), Argument.Float("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_LT_RC.f64", 0x40u, 0x059u, Argument.Register("left"), Argument.Double("right"), Argument.Label("destination"))

        // divide constant and register
        +SimpleInsnFactory("JMP_LT_CR.i8",  0x40u, 0x060u, Argument.Byte("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_LT_CR.u8",  0x40u, 0x061u, Argument.UByte("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_LT_CR.i16", 0x40u, 0x062u, Argument.Short("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_LT_CR.u16", 0x40u, 0x063u, Argument.UShort("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_LT_CR.i32", 0x40u, 0x064u, Argument.Int("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_LT_CR.u32", 0x40u, 0x065u, Argument.UInt("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_LT_CR.i64", 0x40u, 0x066u, Argument.Long("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_LT_CR.u64", 0x40u, 0x067u, Argument.ULong("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_LT_CR.f32", 0x40u, 0x068u, Argument.Float("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_LT_CR.f64", 0x40u, 0x069u, Argument.Double("left"), Argument.Register("right"), Argument.Label("destination"))
    }

    private fun jumpLe() {
        // divide registers
        +SimpleInsnFactory("JMP_LE.i8",  0x40u, 0x070u, Argument.Register("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_LE.u8",  0x40u, 0x071u, Argument.Register("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_LE.i16", 0x40u, 0x072u, Argument.Register("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_LE.u16", 0x40u, 0x073u, Argument.Register("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_LE.i32", 0x40u, 0x074u, Argument.Register("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_LE.u32", 0x40u, 0x075u, Argument.Register("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_LE.i64", 0x40u, 0x076u, Argument.Register("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_LE.u64", 0x40u, 0x077u, Argument.Register("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_LE.f32", 0x40u, 0x078u, Argument.Register("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_LE.f64", 0x40u, 0x079u, Argument.Register("left"), Argument.Register("right"), Argument.Label("destination"))

        // divide register and constant
        +SimpleInsnFactory("JMP_LEC.i8",  0x40u, 0x080u, Argument.Register("left"), Argument.Byte("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_LEC.u8",  0x40u, 0x081u, Argument.Register("left"), Argument.UByte("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_LEC.i16", 0x40u, 0x082u, Argument.Register("left"), Argument.Short("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_LEC.u16", 0x40u, 0x083u, Argument.Register("left"), Argument.UShort("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_LEC.i32", 0x40u, 0x084u, Argument.Register("left"), Argument.Int("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_LEC.u32", 0x40u, 0x085u, Argument.Register("left"), Argument.UInt("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_LEC.i64", 0x40u, 0x086u, Argument.Register("left"), Argument.Long("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_LEC.u64", 0x40u, 0x087u, Argument.Register("left"), Argument.ULong("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_LEC.f32", 0x40u, 0x088u, Argument.Register("left"), Argument.Float("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_LEC.f64", 0x40u, 0x089u, Argument.Register("left"), Argument.Double("right"), Argument.Label("destination"))

        // divide constant and register
        +SimpleInsnFactory("JMP_LE_CR.i8",  0x40u, 0x090u, Argument.Byte("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_LE_CR.u8",  0x40u, 0x091u, Argument.UByte("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_LE_CR.i16", 0x40u, 0x092u, Argument.Short("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_LE_CR.u16", 0x40u, 0x093u, Argument.UShort("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_LE_CR.i32", 0x40u, 0x094u, Argument.Int("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_LE_CR.u32", 0x40u, 0x095u, Argument.UInt("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_LE_CR.i64", 0x40u, 0x096u, Argument.Long("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_LE_CR.u64", 0x40u, 0x097u, Argument.ULong("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_LE_CR.f32", 0x40u, 0x098u, Argument.Float("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_LE_CR.f64", 0x40u, 0x099u, Argument.Double("left"), Argument.Register("right"), Argument.Label("destination"))
    }

    private fun jumpGt() {
        // divide registers
        +SimpleInsnFactory("JMP_GT.i8",  0x40u, 0x0a0u, Argument.Register("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_GT.u8",  0x40u, 0x0a1u, Argument.Register("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_GT.i16", 0x40u, 0x0a2u, Argument.Register("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_GT.u16", 0x40u, 0x0a3u, Argument.Register("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_GT.i32", 0x40u, 0x0a4u, Argument.Register("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_GT.u32", 0x40u, 0x0a5u, Argument.Register("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_GT.i64", 0x40u, 0x0a6u, Argument.Register("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_GT.u64", 0x40u, 0x0a7u, Argument.Register("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_GT.f32", 0x40u, 0x0a8u, Argument.Register("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_GT.f64", 0x40u, 0x0a9u, Argument.Register("left"), Argument.Register("right"), Argument.Label("destination"))

        // divide register and constant
        +SimpleInsnFactory("JMP_GT_RC.i8",  0x40u, 0x0b0u, Argument.Register("left"), Argument.Byte("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_GT_RC.u8",  0x40u, 0x0b1u, Argument.Register("left"), Argument.UByte("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_GT_RC.i16", 0x40u, 0x0b2u, Argument.Register("left"), Argument.Short("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_GT_RC.u16", 0x40u, 0x0b3u, Argument.Register("left"), Argument.UShort("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_GT_RC.i32", 0x40u, 0x0b4u, Argument.Register("left"), Argument.Int("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_GT_RC.u32", 0x40u, 0x0b5u, Argument.Register("left"), Argument.UInt("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_GT_RC.i64", 0x40u, 0x0b6u, Argument.Register("left"), Argument.Long("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_GT_RC.u64", 0x40u, 0x0b7u, Argument.Register("left"), Argument.ULong("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_GT_RC.f32", 0x40u, 0x0b8u, Argument.Register("left"), Argument.Float("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_GT_RC.f64", 0x40u, 0x0b9u, Argument.Register("left"), Argument.Double("right"), Argument.Label("destination"))

        // divide constant and register
        +SimpleInsnFactory("JMP_GT_CR.i8",  0x40u, 0x0c0u, Argument.Byte("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_GT_CR.u8",  0x40u, 0x0c1u, Argument.UByte("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_GT_CR.i16", 0x40u, 0x0c2u, Argument.Short("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_GT_CR.u16", 0x40u, 0x0c3u, Argument.UShort("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_GT_CR.i32", 0x40u, 0x0c4u, Argument.Int("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_GT_CR.u32", 0x40u, 0x0c5u, Argument.UInt("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_GT_CR.i64", 0x40u, 0x0c6u, Argument.Long("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_GT_CR.u64", 0x40u, 0x0c7u, Argument.ULong("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_GT_CR.f32", 0x40u, 0x0c8u, Argument.Float("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_GT_CR.f64", 0x40u, 0x0c9u, Argument.Double("left"), Argument.Register("right"), Argument.Label("destination"))
    }

    private fun jumpGe() {
        // divide registers
        +SimpleInsnFactory("JMP_GE.i8",  0x40u, 0x0d0u, Argument.Register("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_GE.u8",  0x40u, 0x0d1u, Argument.Register("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_GE.i16", 0x40u, 0x0d2u, Argument.Register("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_GE.u16", 0x40u, 0x0d3u, Argument.Register("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_GE.i32", 0x40u, 0x0d4u, Argument.Register("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_GE.u32", 0x40u, 0x0d5u, Argument.Register("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_GE.i64", 0x40u, 0x0d6u, Argument.Register("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_GE.u64", 0x40u, 0x0d7u, Argument.Register("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_GE.f32", 0x40u, 0x0d8u, Argument.Register("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_GE.f64", 0x40u, 0x0d9u, Argument.Register("left"), Argument.Register("right"), Argument.Label("destination"))

        // divide register and constant
        +SimpleInsnFactory("JMP_GEC.i8",  0x40u, 0x0e0u, Argument.Register("left"), Argument.Byte("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_GEC.u8",  0x40u, 0x0e1u, Argument.Register("left"), Argument.UByte("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_GEC.i16", 0x40u, 0x0e2u, Argument.Register("left"), Argument.Short("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_GEC.u16", 0x40u, 0x0e3u, Argument.Register("left"), Argument.UShort("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_GEC.i32", 0x40u, 0x0e4u, Argument.Register("left"), Argument.Int("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_GEC.u32", 0x40u, 0x0e5u, Argument.Register("left"), Argument.UInt("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_GEC.i64", 0x40u, 0x0e6u, Argument.Register("left"), Argument.Long("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_GEC.u64", 0x40u, 0x0e7u, Argument.Register("left"), Argument.ULong("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_GEC.f32", 0x40u, 0x0e8u, Argument.Register("left"), Argument.Float("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_GEC.f64", 0x40u, 0x0e9u, Argument.Register("left"), Argument.Double("right"), Argument.Label("destination"))

        // divide constant and register
        +SimpleInsnFactory("JMP_GE_CR.i8",  0x40u, 0x0f0u, Argument.Byte("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_GE_CR.u8",  0x40u, 0x0f1u, Argument.UByte("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_GE_CR.i16", 0x40u, 0x0f2u, Argument.Short("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_GE_CR.u16", 0x40u, 0x0f3u, Argument.UShort("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_GE_CR.i32", 0x40u, 0x0f4u, Argument.Int("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_GE_CR.u32", 0x40u, 0x0f5u, Argument.UInt("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_GE_CR.i64", 0x40u, 0x0f6u, Argument.Long("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_GE_CR.u64", 0x40u, 0x0f7u, Argument.ULong("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_GE_CR.f32", 0x40u, 0x0f8u, Argument.Float("left"), Argument.Register("right"), Argument.Label("destination"))
        +SimpleInsnFactory("JMP_GE_CR.f64", 0x40u, 0x0f9u, Argument.Double("left"), Argument.Register("right"), Argument.Label("destination"))
    }

    val factoryMap: Map<String, InsnFactory> = factories.associateBy { it.name }
}