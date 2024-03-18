import SwiftUI

struct CustomLinearProgressViewStyle: ProgressViewStyle {
    func makeBody(configuration: Configuration) -> some View {

        GeometryReader { geometry in
            ZStack(alignment: .leading){
            Rectangle()
                .cornerRadius(2)
                .foregroundColor(Color("TertiaryColor"))
                Rectangle()
                    .cornerRadius(2)
                    .foregroundColor(Color("AccentColor"))
                    .frame(width: geometry.size.width * (configuration.fractionCompleted ?? 0))
        }

        }
            .frame(height: 4)

    }
}

#Preview{
    ProgressView(value: 0.5)
        .progressViewStyle(CustomLinearProgressViewStyle())
}
