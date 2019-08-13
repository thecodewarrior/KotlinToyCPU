package dev.thecodewarrior.kotlincpu.assembler.instructions

object InstructionRegistry {
    val factories: List<InsnFactory> = listOf(
        SimpleInsnFactory("NOP", 0x0u),

        // const
        SimpleInsnFactory("CONST_Z",  0x10u, Argument.Register("reg"), Argument.Boolean("value")),
        SimpleInsnFactory("CONST_B",  0x10u, Argument.Register("reg"), Argument.Byte("value")),
        SimpleInsnFactory("CONST_UB", 0x11u, Argument.Register("reg"), Argument.UByte("value")),
        SimpleInsnFactory("CONST_S",  0x12u, Argument.Register("reg"), Argument.Short("value")),
        SimpleInsnFactory("CONST_US", 0x13u, Argument.Register("reg"), Argument.UShort("value")),
        SimpleInsnFactory("CONST_I",  0x14u, Argument.Register("reg"), Argument.Int("value")),
        SimpleInsnFactory("CONST_UI", 0x15u, Argument.Register("reg"), Argument.UInt("value")),
        SimpleInsnFactory("CONST_L",  0x16u, Argument.Register("reg"), Argument.Long("value")),
        SimpleInsnFactory("CONST_UL", 0x17u, Argument.Register("reg"), Argument.ULong("value")),
        SimpleInsnFactory("CONST_F",  0x18u, Argument.Register("reg"), Argument.Float("value")),
        SimpleInsnFactory("CONST_D",  0x19u, Argument.Register("reg"), Argument.Double("value")),

        // arithmetic
        SimpleInsnFactory("INC_B",   0x20u, Argument.Register("reg")),
        SimpleInsnFactory("INC_UB",  0x21u, Argument.Register("reg")),
        SimpleInsnFactory("INC_S",   0x22u, Argument.Register("reg")),
        SimpleInsnFactory("INC_US",  0x23u, Argument.Register("reg")),
        SimpleInsnFactory("INC_I",   0x24u, Argument.Register("reg")),
        SimpleInsnFactory("INC_UI",  0x25u, Argument.Register("reg")),
        SimpleInsnFactory("INC_L",   0x26u, Argument.Register("reg")),
        SimpleInsnFactory("INC_UL",  0x27u, Argument.Register("reg")),

        SimpleInsnFactory("DEC_B",   0x30u, Argument.Register("reg")),
        SimpleInsnFactory("DEC_UB",  0x31u, Argument.Register("reg")),
        SimpleInsnFactory("DEC_S",   0x32u, Argument.Register("reg")),
        SimpleInsnFactory("DEC_US",  0x33u, Argument.Register("reg")),
        SimpleInsnFactory("DEC_I",   0x34u, Argument.Register("reg")),
        SimpleInsnFactory("DEC_UI",  0x35u, Argument.Register("reg")),
        SimpleInsnFactory("DEC_L",   0x36u, Argument.Register("reg")),
        SimpleInsnFactory("DEC_UL",  0x37u, Argument.Register("reg")),

        SimpleInsnFactory("ADD_B",   0x40u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out")),
        SimpleInsnFactory("ADD_UB",  0x41u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out")),
        SimpleInsnFactory("ADD_S",   0x42u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out")),
        SimpleInsnFactory("ADD_US",  0x43u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out")),
        SimpleInsnFactory("ADD_I",   0x44u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out")),
        SimpleInsnFactory("ADD_UI",  0x45u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out")),
        SimpleInsnFactory("ADD_L",   0x46u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out")),
        SimpleInsnFactory("ADD_UL",  0x47u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out")),
        SimpleInsnFactory("ADD_F",   0x48u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out")),
        SimpleInsnFactory("ADD_D",   0x49u, Argument.Register("left"), Argument.Register("right"), Argument.Register("out")),

        SimpleInsnFactory("HALT", 0xFFFFu)
    )
    val factoryMap: Map<String, InsnFactory> = factories.associateBy { it.name }
}