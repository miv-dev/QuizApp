import Foundation
import Shared

//
//  QuizCard.swift
//  Quiz App
//
//  Created by Михаил on 06.03.2024.
//

import SwiftUI

struct Card: View {
    @State var quantity: Int = 0
    @State var current: Int = 0
    @State var question: String = "Поддерживает с коллегами \n хорошие \n отношения"

    var body: some View {

        ZStack {
            VStack {
                Text("вопрос \(current)/\(quantity)")
                    .foregroundColor(Color("TertiaryColor"))
                    .font(.headline)
                    .frame(alignment: .top)
                Spacer()
            }


            VStack {
                Spacer()
                Text(question)
                    .font(.title)
                    .multilineTextAlignment(.center)
                Spacer()
            }
        }
        .foregroundColor(Color("LightColor"))
        .padding(20)
        .textCase(.lowercase)

    }
}

enum AnimationPhase: CaseIterable {
    case start, middle, end
}


struct QuizCardView: View {
    var currentQuestion: Int
    var questions: [Question]
    @State private var question: Int = 0

    var body: some View {
        GeometryReader { geometry in
            ZStack {

                ForEach(0...questions.count - 1, id: \.self) { value in


                    if value >= currentQuestion {
                        Card(quantity: questions.count, current: value + 1, question: questions[value].question
                        )
                            .zIndex(Double(questions.count - value))
                            .frame(maxWidth: .infinity, maxHeight: .infinity)
                            .background(value == currentQuestion ? Color("SecondColor") : Color("AccentColor"))
                            .cornerRadius(20)
                            .offset(x: value == currentQuestion ? -5 : 5, y: value == currentQuestion ? 5 : -5)
                            .transition(.move(edge: .leading).combined(with: .opacity))
                    }
                }


            }
        }

    }

}
