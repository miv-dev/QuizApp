import Shared
import SwiftUI

struct HomeView: View {
    private let component: HomeComponent

    @StateValue
    private var stack: ChildStack<AnyObject, HomeComponentChild>

    private var activeChild: HomeComponentChild {
        stack.active.instance
    }

    init(component: HomeComponent) {
        self.component = component
        _stack = StateValue(component.stack)
    }


    var body: some View {
        let selectedTab = Binding<Int>(get: {
            switch activeChild {
            case is HomeComponentChildQuizes: 0
            case is HomeComponentChildTopics: 1
            case is HomeComponentChildStatistics: 2
            default:
                0
            }
        }) { newValue in
            switch (newValue) {
            case 1: component.navigateToTopics()
            case 2: component.navigateToStatistics()
            default:
                component.navigateToQuizes()
            }
        }

        NavigationView {
            TabView(selection: selectedTab) {
                VStack {
                    switch activeChild {
                    case is HomeComponentChildQuizes:
                        QuizzesView()
                    default:
                        EmptyView()
                    }
                }
                
                .tabItem {
                    Image(systemName: "questionmark.app.fill")
                    Text("Опросы")
                }
                .foregroundColor(Color("AccentColor"))
                .tag(0)
                VStack {
                    switch activeChild {
                    case is HomeComponentChildTopics:
                        Text("Topics")
                    default:
                        EmptyView()
                    }
                }
                .tabItem {
                    Image(systemName: "folder.fill")
                    Text("Темы")
                }
                .foregroundColor(Color("AccentColor"))
                .tag(1)

                VStack {
                    switch activeChild {
                    case is HomeComponentChildStatistics:
                        Text("Statistics")
                    default:
                        EmptyView()
                    }
                }
                .tabItem {
                    Image(systemName: "chart.bar")
                    Text("Статистика")
                }
                .foregroundColor(Color("AccentColor"))
                .tag(2)
            }
                .toolbar {
                if(!(activeChild is HomeComponentChildStatistics)){
                    Button(action: {}){
                        Image(systemName: "plus.app")
                    }
                }
            }
        }
    }
}

