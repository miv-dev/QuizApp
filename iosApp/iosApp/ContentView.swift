import SwiftUI
import Shared

struct ContentView: View {
    @State private var componentHolder = ComponentHolder()


    var body: some View {
        RootView(component: componentHolder.root)
    }
}

struct RootView: View {
    @StateValue
    private var stack: ChildStack<AnyObject, RootComponentChild>


    private var activeChild: RootComponentChild { stack.active.instance }

    init(component: RootComponent) {
        _stack = StateValue(component.stack)
    }

    var body: some View {

        switch activeChild {
        case let child as RootComponentChildHome:
            HomeView(component: child.component)

        default: EmptyView()
        }
    }
}