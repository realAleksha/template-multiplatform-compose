package feature.navigation.api

import feature.common.api.Feature

interface NavigationFeature : Feature {

    fun setItems(items: List<NavigationItem>)

    fun setType(type: NavigationType)

    fun getType(): NavigationType

    fun setVisible(visible: Boolean)

    fun isVisible(): Boolean
}