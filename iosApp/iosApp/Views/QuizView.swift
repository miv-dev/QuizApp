import Shared
import SwiftUI

struct QuizView: View {
    private let component: QuizComponent


    @StateValue
    private var model: QuizStoreState

    init(component: QuizComponent) {
        self.component = component
        _model = StateValue(component.model)
    }


    var body: some View {
        let questions = model.questions
        let currentQuestionId = model.currentQuiz

        if questions.count == 0 {
            EmptyView()
        } else {
            VStack(alignment: .center) {
                ProgressView(value: Double(currentQuestionId + 1), total: Double(questions.count))
                    .progressViewStyle(CustomLinearProgressViewStyle())
                Spacer()

                Button(action: {
                    withAnimation {
                        component.onClickPrev()

                    }
                }, label: {
                    Image(systemName: "arrow.backward")
                        .padding(.horizontal, 30)
                        .padding(.vertical, 4)

                })
                    .buttonStyle(.borderedProminent)
                    .tint(Color("AccentColor"))
                    
                    .cornerRadius(15)
                Spacer()

                QuizCardView(
                    currentQuestion: Int(currentQuestionId), questions: questions)
                    .frame(minHeight: 200, maxHeight: 300)
                Spacer()

                CustomSlider(rate: Int(questions[Int(currentQuestionId)].rating)) { rating in
                    component.changeRating(rating: Int32(rating))
                }
                    .frame(height: 50)
                Spacer()

                Button(action: {
                    withAnimation {
                        component.onClickNext()
                    }
                }, label: {
                    Label("Следующий вопрос", systemImage: "arrow.forward")
                        .labelStyle(TrailingIconLabelStyle())
                        .frame(maxWidth: .infinity)
                        .padding(10)


                })
                    .buttonStyle(.borderedProminent)
                    .tint(Color("AccentColor"))
                    .cornerRadius(20)
            }
                .padding(20)
        }


    }
}

