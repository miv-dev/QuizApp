import Shared

class ComponentHolder {
    let lifecycle: LifecycleRegistry

    let root: RootComponent
    init() {
        let lifecycle = LifecycleRegistryKt.LifecycleRegistry()
        
        let koin = KoinModuleKt.doInitKoin(componentContext: DefaultComponentContext(lifecycle: lifecycle))
        root = koin.koin.rootComponent
        
        self.lifecycle = lifecycle

        lifecycle.onCreate()
    }

    deinit {
        lifecycle.onDestroy()
    }
}