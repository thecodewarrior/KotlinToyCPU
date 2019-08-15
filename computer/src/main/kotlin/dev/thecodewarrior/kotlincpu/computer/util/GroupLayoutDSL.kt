//@file:UseExperimental(ExperimentalContracts::class)
package dev.thecodewarrior.kotlincpu.computer.util

import java.awt.Component
import java.awt.Container
import javax.swing.GroupLayout
import javax.swing.JComponent
import javax.swing.LayoutStyle
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

inline fun Container.groupLayout(config: GroupLayoutDSL.() -> Unit) {
//    contract {
//        callsInPlace(config, InvocationKind.EXACTLY_ONCE)
//    }
    val layout = if(this.layout is GroupLayout) {
        this.layout as GroupLayout
    } else {
        GroupLayout(this).also { this.layout = it }
    }
    GroupLayoutDSL(layout).config()
}

inline operator fun GroupLayout.rem(config: GroupLayoutDSL.() -> Unit) {
//    contract {
//        callsInPlace(config, InvocationKind.EXACTLY_ONCE)
//    }
    GroupLayoutDSL(this).config()
}

@DslMarker
annotation class GroupLayoutDSLMarker

@GroupLayoutDSLMarker
class GroupLayoutDSL(val layout: GroupLayout) {

    var horizontal: GroupLayout.Group
        get() = error("The horizontal layout group can't be retrieved, only set")
        set(value) {
            layout.setHorizontalGroup(value)
        }

    var vertical: GroupLayout.Group
        get() = error("The vertical layout group can't be retrieved, only set")
        set(value) {
            layout.setVerticalGroup(value)
        }

    var autoCreateContainerGaps: Boolean
        get() = layout.autoCreateContainerGaps
        set(value) {
            layout.autoCreateContainerGaps = value
        }

    var autoCreateGaps: Boolean
        get() = layout.autoCreateGaps
        set(value) {
            layout.autoCreateGaps = value
        }

    var honorsVisibility: Boolean
        get() = layout.honorsVisibility
        set(value) {
            layout.honorsVisibility = value
        }

    var layoutStyle: LayoutStyle
        get() = layout.layoutStyle
        set(value) {
            layout.layoutStyle = value
        }

    inline fun sequential(config: SequentialGroupDSL.() -> Unit): GroupLayout.SequentialGroup {
//        contract {
//            callsInPlace(config, InvocationKind.EXACTLY_ONCE)
//        }
        val group = layout.createSequentialGroup()
        SequentialGroupDSL(layout, group).config()
        return group
    }

    inline fun parallel(alignment: GroupLayout.Alignment = leading, resizable: Boolean = true, config: ParallelGroupDSL.() -> Unit): GroupLayout.ParallelGroup {
//        contract {
//            callsInPlace(config, InvocationKind.EXACTLY_ONCE)
//        }
        val group = layout.createParallelGroup(alignment, resizable)
        ParallelGroupDSL(layout, group).config()
        return group
    }

    inline fun baseline(resizable: Boolean, anchorBaselineToTop: Boolean, config: ParallelGroupDSL.() -> Unit): GroupLayout.ParallelGroup {
//        contract {
//            callsInPlace(config, InvocationKind.EXACTLY_ONCE)
//        }
        val group = layout.createBaselineGroup(resizable, anchorBaselineToTop)
        ParallelGroupDSL(layout, group).config()
        return group
    }

    val leading = GroupLayout.Alignment.LEADING
    val trailing = GroupLayout.Alignment.TRAILING
    val center = GroupLayout.Alignment.CENTER
    val baseline = GroupLayout.Alignment.BASELINE
}

@GroupLayoutDSLMarker
open class GroupDSL<T: GroupLayout.Group>(val layout: GroupLayout, val group: T) {

    fun add(vararg components: Component) {
        components.forEach {
            group.addComponent(it)
        }
    }

    fun add(component: Component, min: Int, pref: Int, max: Int) {
        group.addComponent(component, min, pref, max)
    }

    fun add(group: GroupLayout.Group) {
        this.group.addGroup(group)
    }

    fun gap(size: Int) {
        group.addGap(size)
    }

    fun gap(min: Int, pref: Int, max: Int) {
        group.addGap(min, pref, max)
    }

    inline fun sequential(config: SequentialGroupDSL.() -> Unit): GroupLayout.SequentialGroup {
//        contract {
//            callsInPlace(config, InvocationKind.EXACTLY_ONCE)
//        }
        val group = layout.createSequentialGroup()
        SequentialGroupDSL(layout, group).config()
        return group
    }

    inline fun parallel(alignment: GroupLayout.Alignment = leading, resizable: Boolean = true, config: ParallelGroupDSL.() -> Unit): GroupLayout.ParallelGroup {
//        contract {
//            callsInPlace(config, InvocationKind.EXACTLY_ONCE)
//        }
        val group = layout.createParallelGroup(alignment, resizable)
        ParallelGroupDSL(layout, group).config()
        return group
    }

    inline fun baseline(resizable: Boolean, anchorBaselineToTop: Boolean, config: ParallelGroupDSL.() -> Unit): GroupLayout.ParallelGroup {
//        contract {
//            callsInPlace(config, InvocationKind.EXACTLY_ONCE)
//        }
        val group = layout.createBaselineGroup(resizable, anchorBaselineToTop)
        ParallelGroupDSL(layout, group).config()
        return group
    }

    inline fun baseline(config: ParallelGroupDSL.() -> Unit): GroupLayout.ParallelGroup {
//        contract {
//            callsInPlace(config, InvocationKind.EXACTLY_ONCE)
//        }
        val group = layout.createBaselineGroup(true, false)
        ParallelGroupDSL(layout, group).config()
        return group
    }

    val leading = GroupLayout.Alignment.LEADING
    val trailing = GroupLayout.Alignment.TRAILING
    val center = GroupLayout.Alignment.CENTER
    val baseline = GroupLayout.Alignment.BASELINE

    operator fun <T: Component> T.unaryPlus(): T {
        this@GroupDSL.add(this)
        return this
    }

    operator fun <T: GroupLayout.Group> T.unaryPlus(): T {
        this@GroupDSL.add(this)
        return this
    }
}

class ParallelGroupDSL(layout: GroupLayout, group: GroupLayout.ParallelGroup): GroupDSL<GroupLayout.ParallelGroup>(layout, group)

class SequentialGroupDSL(layout: GroupLayout, group: GroupLayout.SequentialGroup): GroupDSL<GroupLayout.SequentialGroup>(layout, group) {

    fun preferredGap(type: LayoutStyle.ComponentPlacement, pref: Int, max: Int) {
        group.addPreferredGap(type, pref, max)
    }

    fun preferredGap(type: LayoutStyle.ComponentPlacement) {
        group.addPreferredGap(type)
    }

    fun preferredGap(component1: JComponent, component2: JComponent, type: LayoutStyle.ComponentPlacement, pref: Int, max: Int) {
        group.addPreferredGap(component1, component2, type, pref, max)
    }

    fun preferredGap(component1: JComponent, component2: JComponent, type: LayoutStyle.ComponentPlacement) {
        group.addPreferredGap(component1, component2, type)
    }

    fun containerGap() {
        group.addContainerGap()
    }

    fun containerGap(pref: Int, max: Int) {
        group.addContainerGap(pref, max)
    }

    infix fun JComponent.related(other: JComponent) {
        preferredGap(this, other, related)
    }

    infix fun JComponent.unrelated(other: JComponent) {
        preferredGap(this, other, unrelated)
    }

    infix fun JComponent.indent(other: JComponent) {
        preferredGap(this, other, indent)
    }

    val related = LayoutStyle.ComponentPlacement.RELATED
    val unrelated = LayoutStyle.ComponentPlacement.UNRELATED
    val indent = LayoutStyle.ComponentPlacement.INDENT
}
