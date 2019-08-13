package dev.thecodewarrior.kotlincpu.assembler.instructions

object InstructionRegistry {
    val factories: List<InsnFactory> = listOf(
        SimpleInsnFactory("NOP", 0x0u),

        SimpleInsnFactory("CONST_Z",  0x10u, Argument.Register("reg"), Argument.Boolean("value")),
        SimpleInsnFactory("CONST_B",  0x10u, Argument.Register("reg"), Argument.Byte("value")),
        SimpleInsnFactory("CONST_BU", 0x11u, Argument.Register("reg"), Argument.UByte("value")),
        SimpleInsnFactory("CONST_S",  0x12u, Argument.Register("reg"), Argument.Short("value")),
        SimpleInsnFactory("CONST_SU", 0x13u, Argument.Register("reg"), Argument.UShort("value")),
        SimpleInsnFactory("CONST_I",  0x14u, Argument.Register("reg"), Argument.Int("value")),
        SimpleInsnFactory("CONST_IU", 0x15u, Argument.Register("reg"), Argument.UInt("value")),
        SimpleInsnFactory("CONST_L",  0x16u, Argument.Register("reg"), Argument.Long("value")),
        SimpleInsnFactory("CONST_LU", 0x17u, Argument.Register("reg"), Argument.ULong("value")),
        SimpleInsnFactory("CONST_F",  0x18u, Argument.Register("reg"), Argument.Float("value")),
        SimpleInsnFactory("CONST_D",  0x19u, Argument.Register("reg"), Argument.Double("value")),

        SimpleInsnFactory("HALT", 0xFFFFu)
    )
    val factoryMap: Map<String, InsnFactory> = factories.associateBy { it.name }
}