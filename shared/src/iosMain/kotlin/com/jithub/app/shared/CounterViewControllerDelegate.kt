package com.jithub.app.shared

import app.cash.redwood.compose.DisplayLinkClock
import app.cash.redwood.compose.RedwoodComposition
import app.cash.redwood.layout.uiview.UIViewRedwoodLayoutWidgetFactory
import app.cash.redwood.widget.RedwoodUIView
import com.jithub.app.shared.core.StringList
import com.jithub.app.shared.redwood.widget.SchemaWidgetSystem
import dev.icerock.moko.resources.desc.desc
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.plus
import platform.UIKit.UIStackView

@Suppress("unused") // Called from Swift.
class CounterViewControllerDelegate(root: UIStackView) {
  private val scope = MainScope() + DisplayLinkClock
  private val redwoodView = RedwoodUIView()

  init {
    initView(root)
  }

  private fun initView(root: UIStackView) {
    root.addArrangedSubview(redwoodView.value)

    val composition = RedwoodComposition(
      scope = scope,
      view = redwoodView,
      widgetSystem = SchemaWidgetSystem(
        Schema = IosWidgetFactory,
        RedwoodLayout = UIViewRedwoodLayoutWidgetFactory(),
      ),
    )

    val labelCount = MR.strings.label_count.desc().localized()
    composition.setContent {
      Counter(labels = StringList(listOfNotNull(labelCount)))
    }
  }

  fun dispose() {
    scope.cancel()
  }
}
