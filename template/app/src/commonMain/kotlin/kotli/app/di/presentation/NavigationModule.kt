package kotli.app.di.presentation

import kotli.app.showcases.ShowcasesDestination
import kotli.app.ui.screen.template.TemplateDestination
import kotli.app.ui.screen.template_no_args.TemplateNoArgsDestination
import kotli.app.feature.navigation.samples.a.NavigationADestination
import kotli.app.feature.navigation.samples.b.NavigationBDestination
import kotli.app.feature.navigation.samples.c.NavigationCDestination
import kotli.app.presentation.screen.theme.change.ChangeThemeDestination
import kotli.app.presentation.screen.theme.change.ChangeThemeDialogDestination
import org.koin.dsl.module
import shared.presentation.navigation.NavigationStore

val navigationModule = module {
    single {
        NavigationStore(
            destinations = listOf(
                ShowcasesDestination,
                TemplateDestination,
                TemplateNoArgsDestination,
                ChangeThemeDestination,
                ChangeThemeDialogDestination,
                NavigationADestination,
                NavigationBDestination,
                NavigationCDestination,
            )
        )
    }
}